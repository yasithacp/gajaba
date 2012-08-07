package org.gajaba.rule.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: kasuncp
 * Date: 8/7/12
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class Neo2AsyncClient {

    AsynchronousSocketChannel client;
    InetSocketAddress address;
    Future<Void> future;

    public Neo2AsyncClient(){

        try {
            client = AsynchronousSocketChannel.open();
            address = new InetSocketAddress("localhost", 0);

            future = client.connect(address);


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Neo2AsyncClient(String host, int ip){

        try {
            client = AsynchronousSocketChannel.open();
            address = new InetSocketAddress(host, ip);

            future = client.connect(address);


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void prepare(){

        try {
            System.out.println("Client: Waiting for the connection to complete");
            future.get();

            String message = "";
            while(!message.equals("quit")) {
                System.out.print("Enter a message: ");
                Scanner scanner = new Scanner(System.in);
                message = scanner.nextLine();
                System.out.println("Client: Sending ...");
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                System.out.println("Client: Message sent: " + new String(buffer.array()));
                client.write(buffer);
            }

        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
    }
}
