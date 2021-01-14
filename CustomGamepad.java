package org.firstinspires.ftc.teamcode;

//Implemens a custom gamepad class that uses the same variables as the FTC one, but doesnt implement the other methods
public class CustomGamepad {
    boolean a;
    boolean b;
    boolean x;
    boolean y;
    boolean dpad_up;
    boolean dpad_down;
    boolean dpad_left;
    boolean dpad_right;
    boolean left_stick;
    boolean right_stick;
    boolean left_bumper;
    boolean right_bumper;
    double left_trigger;
    double right_trigger;
    double left_stick_x;
    double left_stick_y;
    double right_stick_x;
    double right_stick_y;
    boolean back;
    boolean start;
    boolean guide;

    //Setting all fields to a neutral state
    CustomGamepad() {
        a = false;
        b = false;
        x = false;
        y = false;
        dpad_up = false;
        dpad_down = false;
        dpad_left = false;
        dpad_right = false;
        left_stick = false;
        right_stick = false;
        left_bumper = false;
        right_bumper = false;
        left_trigger = 0;
        right_trigger = 0;
        left_stick_x = 0;
        left_stick_y = 0;
        right_stick_x = 0;
        right_stick_y = 0;
        back = false;
        start = false;
        guide = false;
    }
}
