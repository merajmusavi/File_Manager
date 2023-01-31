package com.example.filemanager;

public enum Constant {
    GRID(1),ROW(0);
    private int value;
    Constant(int value) {
    this.value = value;
    }

    public int getValue() {
        return value;
    }
}
