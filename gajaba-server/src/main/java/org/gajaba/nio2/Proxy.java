package org.gajaba.nio2;

import org.gajaba.rule.core.RuleDefinition;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Proxy {
    private static Integer port = 8080;
    private static int serverPort = 8000;
    private static ExecutorService es = Executors.newCachedThreadPool();
    public static final int BUFFER_SIZE = 1024;
    private static final Logger logger = Logger.getLogger("org.gajaba.Proxy");

    private RuleDefinition ruleDef;

    /**
     * Constructor
     * @param def RuleDefinition
     */
    public Proxy(RuleDefinition def) {
        ruleDef = def;
    }

    private static abstract class Handler<A> implements CompletionHandler<Integer, A> {
        @Override
        public void failed(Throwable exc, A attachment) {
            error(exc, attachment);
        }
    }

    /**
     * Log when error occurred
     * @param exc
     * @param attachment
     */
    private static void error(Throwable exc, Object attachment) {
        logger.log(Level.WARNING, "IO failure in " + attachment, exc);
    }
    public void start() throws IOException, InterruptedException {

        CountDownLatch done = new CountDownLatch(1);

        AsynchronousServerSocketChannel open = AsynchronousServerSocketChannel.open();
        final AsynchronousServerSocketChannel listener =
                open.bind(new InetSocketAddress(Proxy.port));
        final Queue<ByteBuffer> queue = new ConcurrentLinkedQueue<>();

        listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            public void completed(final AsynchronousSocketChannel client, Void att) {
                // accept the next connection
                listener.accept(null, this);

                String host = ruleDef.evaluateRequest(client);
                final AsynchronousSocketChannel server;
                try {
                    server = AsynchronousSocketChannel.open();
                    server.connect(new InetSocketAddress(host, serverPort)).get();
                } catch (Exception e) {
                    error(e, "connect failed: " + host + ":" + port);
                    System.exit(1);
                    return;
                }

                read(client, server);
                read(server, client);
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                error(exc, "accept");
                System.exit(1);
            }

            private ByteBuffer getBuffer() {
                ByteBuffer poll = queue.poll();
                if (poll == null) {
                    return ByteBuffer.allocate(BUFFER_SIZE);
                }
                return poll;
            }

            private void read(final AsynchronousSocketChannel reader, AsynchronousSocketChannel writer) {
                final ByteBuffer buffer = getBuffer();
                reader.read(buffer, writer, new Handler<AsynchronousSocketChannel>() {
                    @Override
                    public void completed(Integer result, AsynchronousSocketChannel writer) {
                        if (result == -1) {
                            return;
                        }
                        writer.write((ByteBuffer) buffer.flip(), buffer, new Handler<ByteBuffer>() {
                            @Override
                            public void completed(Integer result, ByteBuffer attachment) {
                                queue.add((ByteBuffer) buffer.clear());
                            }
                        });
                        read(reader, writer);
                    }
                });
            }
        });

        done.await();
    }
}