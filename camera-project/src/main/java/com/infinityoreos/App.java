package com.infinityoreos;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.core.MatOfRect;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import nu.pattern.OpenCV;

public class App {
    public static void main(String[] args) {
        OpenCV.loadLocally();

        System.out.println("");
        System.out.println("Hello, I am running.");
        System.out.println("OpenCV version: " + Core.VERSION);
        System.out.println("");

        // prepare the cascade, images, and MatOfRect for facial detection on a LIVE
        // CAMERA.
        Mat frame = new Mat();
        Mat rectangleFrame = new Mat();
        Mat greyFrame = new Mat();

        MatOfRect faces = new MatOfRect();
        MatOfRect sides = new MatOfRect();

        Rect[] facesArray;
        Rect[] sidesArray;

        String faceCascadePath = "src/main/resources/haarcascade_frontalface_default.xml";
        String sideCascadePath = "src/main/resources/haarcascade_profileface.xml";

        // check they have correctly loaded
        CascadeClassifier faceClassifier = new CascadeClassifier(faceCascadePath);
        if (faceClassifier.empty()) {
            System.out.println("Could not load front face front classifier!");
            return;
        }

        CascadeClassifier sideClassifier = new CascadeClassifier(sideCascadePath);

        if (sideClassifier.empty()) {
            System.out.println("Could not load side profile cascade!");
            return;
        }

        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            System.out.println("Frame failed to open!");
            return;
        }
        ;

        // Set the size of the camera to be smaller to save resources
        camera.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);

        // Check the camera width is set correctly
        System.out.println(camera.get(Videoio.CAP_PROP_FRAME_WIDTH));
        System.out.println(camera.get(Videoio.CAP_PROP_FRAME_HEIGHT));

        while (camera.read(frame)) {
            // The camera is actively reading frames.
            Imgproc.cvtColor(
                    frame,
                    greyFrame,
                    Imgproc.COLOR_BGR2GRAY);
            rectangleFrame = frame.clone();

            // feed classifier the grey frames
            faceClassifier.detectMultiScale(
                    greyFrame,
                    faces,
                    1.1,
                    5,
                    0,
                    new Size(30, 30),
                    new Size(400, 400));

            sideClassifier.detectMultiScale(
                    greyFrame,
                    sides,
                    1.1,
                    5,
                    0,
                    new Size(30, 30),
                    new Size(400, 400));

            facesArray = faces.toArray();
            sidesArray = sides.toArray();

            // Add the rectangles for front of face and side of face
            for (Rect face : facesArray) {
                Imgproc.rectangle(rectangleFrame, face, new Scalar(0, 0, 255, 10));
            }

            for (Rect side : sidesArray) {
                Imgproc.rectangle(rectangleFrame, side, new Scalar(0, 255, 0, 10));
            }

            // Display the image
            HighGui.imshow("Face tracker", rectangleFrame);
            int key = HighGui.waitKey(10);

            if (key == 'Q') {
                System.out.println("Quitting camera.");
                break;
            }

        }
        // Cleaning up
        System.out.println("Cleaning up.");

        frame.release();
        rectangleFrame.release();
        greyFrame.release();
        faces.release();
        sides.release();
        camera.release();

        HighGui.destroyAllWindows();
        System.exit(0);

    }
}