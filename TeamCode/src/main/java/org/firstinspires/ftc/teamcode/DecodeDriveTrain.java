package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cCompassSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import org.firstinspires.ftc.teamcode.DecodeController;

import java.util.Locale;


@Autonomous(name="#T>DecodeDrivetrain", group="Linear Opmode")
//@Disabled
public class DecodeDriveTrain extends LinearOpMode {

    // Declare Hardware, timing, etc

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontleft  = null;
    private DcMotor frontright = null;
    private DcMotor backleft = null;
    private DcMotor backright = null;
    private DcMotor lift = null;
    private Servo grip = null;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        frontleft  = hardwareMap.get(DcMotor.class, "FL");
        frontright  = hardwareMap.get(DcMotor.class, "FR");
        backleft  = hardwareMap.get(DcMotor.class, "BL");
        backright  = hardwareMap.get(DcMotor.class, "BR");
        lift  = hardwareMap.get(DcMotor.class, "LT");
        grip = hardwareMap.get(Servo.class, "GR");

        DecodeController controller = new DecodeController(this,"save1");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        frontleft.setDirection(DcMotor.Direction.FORWARD);
        frontright.setDirection(DcMotor.Direction.REVERSE);
        backleft.setDirection(DcMotor.Direction.FORWARD);
        backright.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        controller.reset_runtime();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            controller.read_state();

            // Setup a variable for each drive wheel to save power level for telemetry
            double frontleftPower;
            double frontrightPower;
            double backleftPower;
            double backrightPower;

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double shift = (controller.get_right_bumper()) ? 1.0 : 0.5 ;
            double driveY = - shift * controller.get_left_stick_y();
            double driveX = - shift * controller.get_left_stick_x();
            double turn  =  controller.get_right_stick_x();

            double servo = (controller.get_x()) ? 0.8 : 1.0;
            double dir = (controller.get_left_bumper()) ? -1.0 : 1.0;
            double liftPower = dir * controller.get_left_trigger();
            frontleftPower = Range.clip(driveY - driveX + turn, -1.0, 1.0) ;
            frontrightPower = Range.clip(driveY + driveX - turn, -1.0, 1.0) ;
            backleftPower = Range.clip(driveY + driveX + turn, -1.0, 1.0) ;
            backrightPower = Range.clip(driveY - driveX - turn, -1.0, 1.0) ;

            grip.setPosition(servo);

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            // Send calculated power to wheels
            frontleft.setPower(frontleftPower);
            frontright.setPower(frontrightPower);
            backleft.setPower(backleftPower);
            backright.setPower(backrightPower);
            lift.setPower(liftPower);





            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}