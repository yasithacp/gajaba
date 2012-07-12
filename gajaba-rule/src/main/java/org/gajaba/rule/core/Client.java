package org.gajaba.rule.core;

public class Client {
    private String name;

    public Client(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
