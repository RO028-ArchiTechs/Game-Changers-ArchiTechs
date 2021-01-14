package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */

public class HardwareV01 extends drivetrainMecanum{
    /* Public OpMode members. */
    public DcMotor accelerator = null;

    public Servo loadServo    = null;


    /* local OpMode members. */


    /* Constructor */
    public HardwareV01(HardwareMap ahwMap){
        super(ahwMap);



        accelerator = hwMap.get(DcMotor.class, "ACC");

        accelerator.setDirection(DcMotor.Direction.REVERSE);

        accelerator.setPower(0);

        accelerator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        loadServo = hwMap.get(Servo.class,"LOAD");

        loadServo.setPosition(0.5);
    }

    /* Initialize standard Hardware interfaces */

}
