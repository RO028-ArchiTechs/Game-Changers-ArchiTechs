package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.openftc.easyopencv.OpenCvPipeline;

import java.io.File;
import java.text.SimpleDateFormat;


public class ImageGatheringPipeline extends OpenCvPipeline {
    protected final static String dir = Environment.getExternalStorageDirectory().getPath() + "/" + "FIRST_" + "/" + "Images";//save state working directory
    Mat lastFrame = null;


    public void saveFrame() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        Imgcodecs.imwrite(dir + "/" + timeStamp + ".png", lastFrame);
    }

    @Override
    public Mat processFrame(Mat input) {
        input.copyTo(this.lastFrame);
        return input;
    }
}
