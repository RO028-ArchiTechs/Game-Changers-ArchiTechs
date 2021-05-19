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

    ImageGatheringPipeline(){
        super();
        File directory = new File(dir);//create directory object
        directory.mkdir();//create the working directory
    }

    public void saveFrame() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        if(lastFrame != null) {
            Imgcodecs.imwrite(dir + "/" + timeStamp + ".png", lastFrame);
        }
    }

    @Override
    public Mat processFrame(Mat input) {
        if(this.lastFrame == null){
            this.lastFrame = new Mat(input.rows(),input.cols(),input.type());
        }
        input.copyTo(this.lastFrame);
        return input;
    }
}
