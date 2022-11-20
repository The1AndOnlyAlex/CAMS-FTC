/*
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * This particular OpMode executes a POV Game style Teleop for a direct drive robot
 * The code is structured as a LinearOpMode
 *
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the arm using the Gamepad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@Autonomous(name="Robot: Teleop POV Final", group="Robot")
public class AutoScoreOneTurnRight extends LinearOpMode {
    /* Declare OpMode members. */
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor linearSlide = null;
    //public Servo claw = null;

    public static final double MIN_POSITION = 0.0;
    public static final double MAX_POSITION = 0.5;

    @Override
    public void runOpMode() {
        double left;
        double right;
        double drive;
        double turn;

        //Telemetry update variables:

        // Define and Initialize Motors and Servos
        leftDrive = hardwareMap.get(DcMotor.class, "MotorA");
        rightDrive = hardwareMap.get(DcMotor.class, "MotorB");
        //linearSlide = hardwareMap.get(DcMotor.class, "MotorC");
        //claw = hardwareMap.get(Servo.class, "ServoA");
        //leftArm    = hardwareMap.get(DcMotor.class, "left_arm");
        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        //linearSlide.setDirection(DcMotor.Direction.FORWARD);
        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //claw.setPosition(0.0);

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press Play.");    //
        telemetry.update();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //Move forward 19 ticks
        while (leftDrive.getCurrentPosition() < 380) {
            leftDrive.setPower(0.3);
            rightDrive.setPower(0.3);
            sleep(50);
        }
        //Turn 90 degrees (19 ticks) right
        while (leftDrive.getCurrentPosition() < 980) {
            leftDrive.setPower(0.3);
            rightDrive.setPower(-0.3);
            sleep(50);
        }
        //Move forward 30 ticks
        while (leftDrive.getCurrentPosition() < 1580) {
            leftDrive.setPower(0.3);
            rightDrive.setPower(0.3);
            sleep(50);
        }
        //Move backward 19 ticks
        while (leftDrive.getCurrentPosition() > 1200) {
            leftDrive.setPower(-0.3);
            rightDrive.setPower(-0.3);
            sleep(50);
        }
        //Turn 90 degrees (19 ticks) left
        while (leftDrive.getCurrentPosition() > 600) {
            leftDrive.setPower(-0.3);
            rightDrive.setPower(0.3);
            sleep(50);
        }
        //Move forward 50 ticks
        while (leftDrive.getCurrentPosition() < 1600) {
            leftDrive.setPower(0.3);
            rightDrive.setPower(0.3);
            sleep(50);
        }
    }
}