package com.infinityoreos;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.core.MatOfRect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import nu.pattern.OpenCV;

public class App {

    public static void main(String[] args) {
        OpenCV.loadLocally();

        System.out.println("");
        System.out.println("Hello, I am running.");
        System.out.println("OpenCV version: " + Core.VERSION);
        System.out.println("");

        // prepare the cascade, images, and MatOfRect for facial detection
        String cascadeName = "src/main/resources/haarcascade_frontalface_default.xml";
        String imageName = "face1.jpeg";

        Mat originalImage = Imgcodecs.imread("src/main/resources/" + imageName);
        if (originalImage.empty()) {
            System.out.println("Image failed to load!");
            originalImage.release();
            return;
        }

        Mat imageRectAroundFaces = originalImage.clone();
        Mat imageGrayScale = new Mat();

        MatOfRect faces = new MatOfRect();

        CascadeClassifier classifier = new CascadeClassifier(cascadeName);

        if (classifier.empty()) {
            System.out.println("Classifier failed to load!");
            return;
        }

        // make the grayscale copy
        Imgproc.cvtColor(originalImage, imageGrayScale, Imgproc.COLOR_BGR2GRAY);

        // detect the faces
        classifier.detectMultiScale(imageGrayScale, faces);
        // convert our Mat of faces to a Rect[] to iterate over
        Rect[] facesArray = faces.toArray();

        System.out.println("Number of faces found: " + facesArray.length);

        for (Rect face : facesArray) {
            System.out.println(face);
            Imgproc.rectangle(
                    imageRectAroundFaces, face, new Scalar(0, 255, 0, 2)); // the '2' is thickness
        }

        // HighGui.imshow("Grayscale", imageGrayScale);
        HighGui.imshow("Original with Rectangles", imageRectAroundFaces);
        HighGui.waitKey(0);

        originalImage.release();
        imageRectAroundFaces.release();
        imageGrayScale.release();
        faces.release();
        HighGui.destroyAllWindows();
        System.exit(0);

    }
}