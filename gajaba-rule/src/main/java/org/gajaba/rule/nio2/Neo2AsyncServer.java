package org.gajaba.rule.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: kasuncp
 * Date: 8/7/12
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class Neo2AsyncServer {

    private AsynchronousServerSocketChannel listener;
    private InetSocketAddress address;
    private boolean listen = true;


    public Neo2AsyncServer(){

        try {
            listener = AsynchronousServerSocketChannel.open();
            address = new InetSocketAddress("localhost", 0);
            listener.bind(address);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Neo2AsyncServer(String host, int port){

        try {
            listener = AsynchronousServerSocketChannel.open();
            address = new InetSocketAddress(host, port);
            listener.bind(address);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void accept(){

        listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

            public void completed(AsynchronousSocketChannel channel, Void attribute) {
                try {
                    System.out.println("Server: completed method executing");
                    while (true) {
                        ByteBuffer buffer = ByteBuffer.allocate(32);
                        Future<Integer> readFuture = channel.read(buffer);
                        Integer number = readFuture.get();
                        System.out.println("Server: Message received: " + new String(buffer.array()));
                    }

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (ExecutionException ex){
                    ex.printStackTrace();
                }
            }

            public void failed(Throwable ex, Void atttribute) {
                System.out.println("Server: CompletionHandler exception");
                ex.printStackTrace();
            }
        });

        while(listen) {
            // wait for client messages
        }
    }
}
