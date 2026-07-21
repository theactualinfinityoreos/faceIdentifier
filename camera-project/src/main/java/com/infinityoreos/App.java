package com.infinityoreos;

import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Size;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class App {

    public static void main(String[] args) {
        OpenCV.loadLocally();

        System.out.println("");
        System.out.println("Hello, I am running.");
        System.out.println("OpenCV version: " + Core.VERSION);
        System.out.println("");

        // capture a single image into a Mat to prepare for processing.

        String fileName = "cat.jpg";
        Mat originalImage = Imgcodecs.imread(fileName);
        Mat originalWithRectangle = originalImage.clone();
        Mat grayScaleImage = new Mat();
        Mat blurredGrayScaleImage = new Mat();

        // need details about the original to draw the photo correctly
        // because I am working in a public space, Lena.png has been replaced with
        // cat.jpg
        System.out.println("Number of rows: " + originalImage.rows());
        System.out.println("Number of columns: " + originalImage.cols());

        Imgproc.rectangle(
                originalWithRectangle,
                new Point(271, 405),
                new Point(542, 675),
                new Scalar(0, 0, 255),
                2);

        Imgproc.putText(
                originalWithRectangle,
                "cat",
                new Point(271, 393),
                Imgproc.FONT_HERSHEY_PLAIN,
                2,
                new Scalar(255, 255, 255));

        Imgproc.cvtColor(originalImage, grayScaleImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(grayScaleImage, blurredGrayScaleImage, new Size(5, 5), 0);

        HighGui.imshow("Original with Rectangle", originalWithRectangle);
        HighGui.imshow("Blurred Greyscale", blurredGrayScaleImage);
        HighGui.waitKey(0);

        // cleanup
        originalImage.release();
        originalWithRectangle.release();
        grayScaleImage.release();
        blurredGrayScaleImage.release();
        HighGui.destroyAllWindows();
        System.exit(0);

    }
}