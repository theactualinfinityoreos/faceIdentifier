package com.infinityoreos;

import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public class App {

    public static void main(String[] args) {
        OpenCV.loadLocally();

        System.out.println("");
        System.out.println("Hello, I am running.");
        System.out.println("OpenCV version: " + Core.VERSION);

        Mat image = new Mat();
        System.out.println("Matrix created: " + image);
    }
}