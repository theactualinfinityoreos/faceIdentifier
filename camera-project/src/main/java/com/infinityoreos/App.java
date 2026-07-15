package com.infinityoreos;

import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class App {

    public static void main(String[] args) {
        OpenCV.loadLocally();

        System.out.println("");
        System.out.println("Hello, I am running.");
        System.out.println("OpenCV version: " + Core.VERSION);
        System.out.println("");

        // Creating a Mat of 640x480 using CvType.CV_8UC3
        // note that for a image of width x height, Mat uses Rows first then Columns
        // so we gotta inverse that thang
        // so it gotta be ... new Mat(480, 640, ...) not ... new Mat(640, 480, ...)
        Mat image = new Mat(480, 640, CvType.CV_8UC3);

        // printing the number of rows, columns, channels, size, whether it's empty
        System.out.println(image.rows());
        System.out.println(image.cols());
        System.out.println(image.channels());
        System.out.println(image.size());
        System.out.println(image.empty());
        // assigning the pixel at (0,0) to be Blue (remember that coordinates are
        // assigned as (row, column, values) not (x, y, values). This means that (100,
        // 200, colour) is approximately x = 200, y = 100, colour = colour)
        image.put(0, 0, new double[] { 255, 0, 0 });

        // Getting the raw value of the pixel at (0,0) (it looks like a mess).
        // It looks like a mess because Java is printing the internal representation of
        // an array when we pass it into println().

        // Assigning the array as an array of doubles in pixel is the correct approach.
        // We can also print the array cleanly with Arrays.toString(); from
        // import.java.util.Arrays;
        System.out.println(image.get(0, 0));

        // reading and printing out the BGR values of that same pixel at (0,0)
        double[] pixel = image.get(0, 0);
        System.out.println(pixel[0]);
        System.out.println(pixel[1]);
        System.out.println(pixel[2]);

        // (post feedback) it is good to release native memory (Mat) when finished.
        // this is important in bigger projects, eg. continuously reading a camera feed.

        image.release();
    }
}