package com.a35ksyd.kafka.model;

public record DemoModel(int val1, int val2, String op) {


    @Override
    public String toString() {
        return "DemoModel [val1=" + val1 + ", val2=" + val2 + ", op=" + op + "]";
    }
}