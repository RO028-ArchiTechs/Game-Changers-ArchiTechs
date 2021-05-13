package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;


public class StartDetectingPipeline extends OpenCvPipeline {

    StartDetectingPipeline(){
        super();
    }

    @Override
    public Mat processFrame(Mat input) {
        Rect rectCrop = new Rect(input.height() / 2,0,input.height() - input.height() / 2 + 1,input.width() - 0 + 1);
        input = input.submat(rectCrop);
        Imgproc.medianBlur(input,input,5);
        Scalar low = new Scalar(175,85,0);
        Scalar high = new Scalar(255,197,109);
        Core.inRange(input,low,high,input);
        Imgproc.cvtColor(input,input,Imgproc.COLOR_GRAY2BGR);

        List<MatOfPoint> contours = Arrays.asList();
        Mat hierarchy = new Mat();
        Imgproc.findContours(input,contours,hierarchy,Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);

        for(MatOfPoint contour:contours){
            MatOfPoint2f  contour2f = new MatOfPoint2f( contour.toArray() );
            RotatedRect rect = Imgproc.minAreaRect(contour2f);
            double w = rect.size.width;
            double h = rect.size.height;

            double hpw = h / w;
            double wph = w / h;

            Point[] vertices = new Point[4];
            rect.points(vertices);

            if(h * w >= 150 && ((wph >= 1.3 && wph <= 1.6) || (hpw >= 1.3 && hpw <= 1.6))){
                ///4 rings,draw green
                for (int j = 0; j < 4; j++){
                    Imgproc.line(input, vertices[j], vertices[(j+1)%4], new Scalar(0,255,0));
                }
            }else if(h * w >= 50 && ((wph >= 2.8 && wph <= 3.8) || (hpw >= 2.8 && hpw <= 3.8))){
                ///1 ring,draw blue
                for (int j = 0; j < 4; j++){
                    Imgproc.line(input, vertices[j], vertices[(j+1)%4], new Scalar(255,0,0));
                }
            }
        }

        return input;
    }
}
