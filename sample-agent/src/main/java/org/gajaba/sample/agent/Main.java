package org.gajaba.sample.agent;


import org.gajaba.agent.Agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        Agent agent = new Agent();
        if (args.length > 0) {
            if(args.length>1){
                agent.start(args[0],args[1]);
            }else{
                agent.start(args[0]);
            }
        } else {
            System.out.println("enter local IP as first argument");
            return;
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
}
