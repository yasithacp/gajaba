package org.gajaba.nio2;

import org.gajaba.rule.core.RuleDefinition;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Proxy {
    private static Integer port = 8081;
    private static int serverPort = 8000;
    private static ExecutorService es = Executors.newCachedThreadPool();
    public static final int BUFFER_SIZE = 1024;
    private static final Logger logger = Logger.getLogger("org.gajaba.Proxy");

    private RuleDefinition ruleDef;

    /**
     * Constructor
     *
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
     *
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

//                String host = "localhost";
                String sb = readLine(client).toString();
                String url = getIP(sb);
                String host = ruleDef.evaluateRequest(client,url);

                if (host != null) {
                    final AsynchronousSocketChannel server;
                    try {
                        server = AsynchronousSocketChannel.open();
                        server.connect(new InetSocketAddress(host, serverPort)).get();
                    } catch (Exception e) {
                        error(e, "connect failed: " + host + ":" + port);
                        System.exit(1);
                        return;
                    }

                    read(sb,client, server);
                    read(server, client);
                } else {
                    try {
                        client.close();
                    } catch (IOException e) {
                    }
                }
            }

            private String getIP(String sb) {
                int space1 = sb.indexOf(" ");
                int space2 = sb.indexOf(" ",space1+1);
                return sb.substring(space1,space2);
            }

            private StringBuilder readLine(AsynchronousSocketChannel client) {
                final StringBuilder builder = new StringBuilder(BUFFER_SIZE);
                readLine(builder, client);
                return builder;
            }

            private void readLine(StringBuilder builder, AsynchronousSocketChannel client) {
                final ByteBuffer buffer = ByteBuffer.allocate(128);
                Future<Integer> read = client.read(buffer);
                for (int i = 0; i < buffer.position(); i++) {

                }
                try {
                    read.get();
                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                }
                buffer.flip();
                boolean needMore = true;
                while (buffer.hasRemaining()) {
                    char c = (char) buffer.get();
                    if (c == '\n') {
                        needMore = false;
                    }
                    builder.append(c);
                }
                buffer.clear();
                if (needMore) {
                    readLine(builder, client);
                }
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

            private void read(final String firstLine, final AsynchronousSocketChannel reader, AsynchronousSocketChannel writer) {
                byte[] bytes = firstLine.getBytes();
                final ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
                buffer.put(bytes);
                buffer.flip();
                writer.write(buffer, buffer, new Handler<ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        buffer.clear();
                    }
                });
                read(reader, writer);
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