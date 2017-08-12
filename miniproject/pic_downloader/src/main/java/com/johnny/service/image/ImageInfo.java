package com.johnny.service.image;

public class ImageInfo {
    private int height;
    private int width;

    public ImageInfo(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String toString() {
        return "height: " + this.height + ", width: " + this.width;
    }
}
