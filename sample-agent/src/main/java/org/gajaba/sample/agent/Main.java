package org.gajaba.sample.agent;


import org.gajaba.agent.Agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Agent agent = new Agent();
        if (args.length > 0) {
            if (args.length > 1) {
                agent.start(args[1], args[0]); // eg: agent "myAgent" 10.19.4.2
            } else {
                agent.start(getIPAddress(), args[0]); // eg: agent "myAgent"
            }
        } else {
            agent.start(getIPAddress(), String.valueOf(System.currentTimeMillis()));
        }

        while (true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print('>');
            String line = reader.readLine();
            if (!line.isEmpty()) {
                String[] pair = line.split("=");
                if (pair.length == 2) {
                    agent.publish(pair[0], pair[1]);
                } else {
                    System.out.println("Error line: " + line);
                }
            }
        }
    }

    public static String getIPAddress() {
        List<InetAddress> ipAddresses = new ArrayList<InetAddress>();
        String ipAddress = null;

        Enumeration e;
        try {
            e = NetworkInterface.getNetworkInterfaces();

            while (e.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) e.nextElement();
                if (ni.isLoopback() || !ni.isUp()) continue;

                for (Enumeration e2 = ni.getInetAddresses(); e2.hasMoreElements(); ) {
                    InetAddress ip = (InetAddress) e2.nextElement();
                    ipAddresses.add(ip);
                }
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }
        if (ipAddresses.isEmpty()) {
            return null;
        } else {
            for (InetAddress ip : ipAddresses) {
                if (ip instanceof Inet4Address) {
                    ipAddress = ip.getHostAddress();
                    break;
                }
            }
        }

        if (ipAddress == null) {
            ipAddress = ipAddresses.get(0).getHostAddress();
        }

        return ipAddress;
    }
}
