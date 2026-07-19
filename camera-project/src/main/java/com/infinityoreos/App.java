package com.infinityoreos;

import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class App {

    public static void main(String[] args) {
        OpenCV.loadLocally();

        System.out.println("");
        System.out.println("Hello, I am running.");
        System.out.println("OpenCV version: " + Core.VERSION);
        System.out.println("");

        // assign the mat image the mat of the image file Lena.jpg.
        String imageName = "Lena.png";
        Mat image = Imgcodecs.imread(imageName);
        System.out.println("The file name is " + imageName);

        // printing the number of rows, columns, channels, size, whether it's empty
        System.out.println(image.rows());
        System.out.println(image.cols());
        System.out.println(image.channels());
        System.out.println(image.size());

        if (image.empty()) {
            System.out.println("The image could not be found!");
            image.release();
            return;
        }

        // reading and printing out the BGR values of that same pixel at (0,0)
        double[] pixel = image.get(0, 0);
        System.out.println(pixel[0]);
        System.out.println(pixel[1]);
        System.out.println(pixel[2]);

        // now, reassign the value of the pixel at that point to be blue.

        image.put(0, 0, new double[] { 255, 0, 0 });

        // I want to begin seeing the images. The image will be saved.

        // imwrite is a boolean. Capture it to allow it to be used.
        boolean ImageSaved = Imgcodecs.imwrite("output.png", image);

        System.out.println("Image saved: " + ImageSaved);

        // (post feedback) it is good to release native memory (Mat) when finished.
        // this is important in bigger projects, eg. continuously reading a camera feed.
        image.release();
    }
}