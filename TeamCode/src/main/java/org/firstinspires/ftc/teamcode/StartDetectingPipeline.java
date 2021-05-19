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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StartDetectingPipeline extends OpenCvPipeline {

    private int fr[] = {0,0,0};

    StartDetectingPipeline(){
        super();
        fr[0] = 0;
        fr[1] = 0;
        fr[2] = 0;
    }

    public int get_state(){
        int ans = 0;
        for(int i = 0;i < 3;i++){
            if(fr[ans] < fr[i]){
                ans = i;
            }
        }
        return ans;
    }

    @Override
    public Mat processFrame(Mat input) {
        int state = 0;
        Rect rectCrop = new Rect(0,input.height() / 2,input.width(),input.height() - input.height() / 2);
        //input = input.submat(rectCrop);
        Imgproc.medianBlur(input,input,5);
//        Scalar low = new Scalar(170,85,0,0);
//        Scalar high = new Scalar(255,197,109,255);
        Scalar low = new Scalar((int)(95 * 360 / (255)),130,130);
        Scalar high = new Scalar((int)(110 * 360 / (255)),255,255);
        Imgproc.cvtColor(input,input,Imgproc.COLOR_BGR2HSV_FULL);
        Core.inRange(input,low,high,input);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        Imgproc.findContours(input,contours,hierarchy,Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);

        Imgproc.cvtColor(input,input,Imgproc.COLOR_GRAY2BGR);

        for(MatOfPoint contour:contours){
            MatOfPoint2f  contour2f = new MatOfPoint2f( contour.toArray() );
            RotatedRect rect = Imgproc.minAreaRect(contour2f);
            double w = rect.size.width;
            double h = rect.size.height;
            if(h > 0 && w > 0){

                double hpw = h / w;
                double wph = w / h;

                Point[] vertices = new Point[4];
                rect.points(vertices);

                if(h * w >= 150 && ((wph >= 1.3 && wph <= 1.6) || (hpw >= 1.3 && hpw <= 1.6))){
                    ///4 rings,draw green
                    for (int j = 0; j < 4; j++){
                        Imgproc.line(input, vertices[j], vertices[(j+1)%4], new Scalar(0,255,0));
                    }
                    state = 2;
                }else if(h * w >= 50 && ((wph >= 2.6 && wph <= 3.9) || (hpw >= 2.6 && hpw <= 3.9))){
                    ///1 ring,draw blue
                    for (int j = 0; j < 4; j++){
                        Imgproc.line(input, vertices[j], vertices[(j+1)%4], new Scalar(255,0,0));
                    }
                    state = 1;
                }
            }
        }
        fr[state]++;
        return input;
    }
}
