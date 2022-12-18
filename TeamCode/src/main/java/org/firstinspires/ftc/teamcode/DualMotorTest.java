package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="Dual Motor Test", group="Robot")
@Disabled
public class DualMotorTest extends LinearOpMode {
    /* Declare OpMode members. */
    public DcMotor leftDrive   = null;
    public DcMotor rightDrive = null;

    @Override
    public void runOpMode() {
        double left;
        double max;
        final int control = 25;
        //Control (min 1) gives the driver more control over the free speed motor.

        // Define and Initialize Motors
        leftDrive  = hardwareMap.get(DcMotor.class, "MotorA");
        rightDrive  = hardwareMap.get(DcMotor.class, "MotorB");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press Play.");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Hello", "Motor test in progress.");

            // Normalize the values so neither exceed +/- 1.0
            left = gamepad1.left_stick_y;

            max = Math.abs(Math.pow(left, 2 * control - 1));
            if (max > 1.0)
            {
                left /= max;
            }

            // Output the safe vales to the motor drives.
            leftDrive.setPower(left);
            rightDrive.setPower(left);

            telemetry.addData("Power",  "%.2f", left);
            telemetry.addData("Left Encoder",  "%d", leftDrive.getCurrentPosition());
            telemetry.addData("Right Encoder",  "%d", rightDrive.getCurrentPosition());

            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}