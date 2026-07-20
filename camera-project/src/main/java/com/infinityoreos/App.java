package com.infinityoreos;

import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;

public class App {

    public static void main(String[] args) {
        OpenCV.loadLocally();

        System.out.println("");
        System.out.println("Hello, I am running.");
        System.out.println("OpenCV version: " + Core.VERSION);
        System.out.println("");

        // create a camera feed, and Mat 'frame' for the capturing of frames.
        Mat frame = new Mat();
        VideoCapture camera = new VideoCapture(0);

        // check the camera has actually opened.
        if (!camera.isOpened()) {
            System.out.println("Camera failed to open!");
            camera.release();
            return;
        }
        System.out.println("The camera has been opened.");
        System.out.println("Press 'q' to quit the camera.");

        // while loop to continuously read the next frame into the Mat 'frame'
        while (true) {
            boolean frameRead = camera.read(frame);

            if (!frameRead) {
                System.out.print("Frame could not be read!");
                break;
            }

            // display the frame captured & declare the waitKey
            HighGui.imshow("Camera Frame", frame);
            int key = HighGui.waitKey(10);

            if (key != -1) {
                System.out.println("Raw key: " + key);
                System.out.println("Masked key: " + (key & 0xFF));
            }

            if (key == 'Q') {
                System.out.println("Quitting camera.");
                break;
            }
        }

        System.out.println("We are now going to cleanup and release.");
        camera.release();
        frame.release();
        HighGui.destroyAllWindows();
        System.exit(0);
    }
}