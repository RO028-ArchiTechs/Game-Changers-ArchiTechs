package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

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
    public DcMotor  Accelerator   = null;

    //public Servo    leftClaw    = null;
    //public Servo    rightClaw   = null;


    /* local OpMode members. */


    /* Constructor */
    public HardwareV01(HardwareMap ahwMap){
        super(ahwMap);
        Accelerator  = hwMap.get(DcMotor.class, "ACC");

        Accelerator.setDirection(DcMotor.Direction.REVERSE);

        Accelerator.setPower(0);

        Accelerator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /* Initialize standard Hardware interfaces */

}
