package com.example.filemanager;

public enum Constant {
ROW(0),GRID(1);
    private int value;

    Constant(int value) {
    this.value = value;
    }

    public int getValue() {
        return value;
    }
}
