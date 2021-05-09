package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.teamcode.SaveState;
import org.firstinspires.ftc.teamcode.EncodeController;

import java.io.IOException;

//it inherits SaveState to make sure that the directory in which we save is the same for the decoder
//it is the SaveState variation designed for the decoding part of the replicator
public class InputSaveState extends SaveState{

    In in;//input file parser

    public InputSaveState() {//constructor is the same as the base class
        super();
    }

    public void init_in(String file) {//initializes the input file parser
        in = new In(dir,file);
    }

    public boolean is_invalid() {//checks if the current savestate is invalid
        return time == -1;//this check works because if we reached EOF, than the neutral value returned is -1, so we just check if the time is -1
    }

    public void read() {

        //reads the values in the same order that and OutputSaveState object would write them
        in.update();
        left_stick_y = (in.isDisabled() ? 0:in.getDouble());
        left_stick_x = (in.isDisabled() ? 0:in.getDouble());
        right_stick_y = (in.isDisabled() ? 0:in.getDouble());
        right_stick_x = (in.isDisabled() ? 0:in.getDouble());
        direction = (in.isDisabled() ? 0:in.getDouble());
        red = (in.isDisabled() ? 0:in.getDouble());
        green = (in.isDisabled() ? 0:in.getDouble());
        blue = (in.isDisabled() ? 0:in.getDouble());
        a = (in.isDisabled() ? false:in.getBoolean());
        b = (in.isDisabled() ? false:in.getBoolean());
        x = (in.isDisabled() ? false:in.getBoolean());
        y = (in.isDisabled() ? false:in.getBoolean());
        dpad_up = (in.isDisabled() ? false:in.getBoolean());
        dpad_down = (in.isDisabled() ? false:in.getBoolean());
        distance = (in.isDisabled() ? 0:in.getDouble());
        time = (in.isDisabled() ? 0:in.getDouble());
        left_bumper = (in.isDisabled() ? false:in.getBoolean());
        left_trigger = (in.isDisabled() ? 0:in.getDouble());//TODO
        dpad_left = (in.isDisabled() ? false:in.getBoolean());
        dpad_right = (in.isDisabled() ? false:in.getBoolean());
        right_bumper = (in.isDisabled() ? false:in.getBoolean());
        right_trigger = (in.isDisabled() ? 0:in.getDouble());//TODO
    }
}
