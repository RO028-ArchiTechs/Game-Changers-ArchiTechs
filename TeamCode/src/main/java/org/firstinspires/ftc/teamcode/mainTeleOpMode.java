package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/// TO BE REMOVED AND WRITTEN NICLEY ///


/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="MAIN", group="drivetrain")
//@Disabled
public class mainTeleOpMode extends LinearOpMode{

    HardwareV01 hardware           = null;
    //private DcMotor Accelerator = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        hardware=new HardwareV01(hardwareMap);
        //Accelerator  = hardwareMap.get(DcMotor.class, "ACC");

        //Accelerator.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        //runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double frontLeftPower;
            double frontRightPower;
            double backLeftPower;
            double backRightPower;
            double accPower = gamepad1.right_trigger;

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double driveY = -gamepad1.left_stick_y;
            double driveX = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;
            frontLeftPower = Range.clip(driveY + driveX + turn, -1.0, 1.0);
            frontRightPower = Range.clip(driveY - driveX - turn, -1.0, 1.0);
            backLeftPower = Range.clip(driveY - driveX + turn, -1.0, 1.0);
            backRightPower = Range.clip(driveY + driveX - turn, -1.0, 1.0);

            //test for commit
            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            // Send calculated power to wheels
            hardware.FrontLeft.setPower(frontLeftPower);
            hardware.FrontRight.setPower(frontRightPower);
            hardware.BackLeft.setPower(backLeftPower);
            hardware.BackRight.setPower(backRightPower);
            hardware.Accelerator.setPower(accPower);

            // Show the elapsed game time and wheel power.
            //telemetry.addData("Status", "Run Time: " + runtime.toString());
            // telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}
