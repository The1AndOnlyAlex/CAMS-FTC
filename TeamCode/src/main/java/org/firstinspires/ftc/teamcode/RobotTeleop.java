/* Copyright (c) 2017 FIRST. All rights reserved.
 *
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


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

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

@TeleOp(name="Robot: Teleop", group="Robot")
@Disabled
public class RobotTeleop extends LinearOpMode {

    /* Declare OpMode members. */
    public DcMotor  MotorA   = null;
    public DcMotor  MotorB  = null;
    public DcMotor MotorC = null;
    public DcMotor MotorD = null;
    public Servo Servo1 = null;

    //double clawOffset = 0.5;

    public static final double MID_SERVO   =  0.6 ;
    public static final double CLAW_SPEED  = 1.0 ;
    public static final double ELEVATOR_UP_FAST  = 1 ;
    public static final double ELEVATOR_DOWN_FAST  = -1 ;
    public static final double CRANE_UP  = 0.75 ;
    public static final double CRANE_DOWN  = -0.75 ;

    @Override
    public void runOpMode() {
        double left;
        double right;
        double max;
        double elevatorPower;
        double cranePower;

        int minLift = -600;
        int maxLift = 600;
        int targetPositionLift;

        // Define and Initialize Motors
        MotorA  = hardwareMap.get(DcMotor.class, "MotorA");
        MotorB = hardwareMap.get(DcMotor.class, "MotorB");
        MotorC  = hardwareMap.get(DcMotor.class, "MotorC");
        MotorD  = hardwareMap.get(DcMotor.class, "MotorD");
        Servo1 = hardwareMap.get(Servo.class, "Servo1");

        MotorA.setDirection(DcMotor.Direction.REVERSE);
        MotorB.setDirection(DcMotor.Direction.FORWARD);
        MotorC.setDirection(DcMotor.Direction.FORWARD);
        MotorD.setDirection(DcMotor.Direction.FORWARD);
        Servo1.setPosition(MID_SERVO);

        MotorC.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorC.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorC.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        targetPositionLift = MotorC.getCurrentPosition();

        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press Play.");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Robot Drive Base Speed
            left = -gamepad1.left_stick_y/2;
            //right  =  gamepad1.right_stick_x/2;
            right = -gamepad1.right_stick_y/2;

            max = Math.abs(left);
            if (max > 1.0)
            {
                left /= max;
            }

            max = Math.abs(right);
            if (max > 1.0)
            {
                right /= max;
            }


            //MotorA.setPower(right+left);
            //MotorB.setPower(-right+left);
            MotorA.setPower(left);
            MotorB.setPower(right);
/*

            // Robot Claw Open/Close
            if (gamepad1.right_bumper)
                clawOffset += CLAW_SPEED;
            else if (gamepad1.left_bumper)
                clawOffset -= CLAW_SPEED;
            clawOffset = Range.clip(clawOffset, 1, 0);
            Servo1.setPosition(clawOffset);
  */
            if (gamepad1.right_bumper) {
                Servo1.setPosition(0.6);  //decrease to open more
            }
            if (gamepad1.left_bumper) {
                Servo1.setPosition(0.95);  //increase to close more
            }



            // Robot Lift Speed
            if ((gamepad1.dpad_down)&&(MotorC.getCurrentPosition() < maxLift)) {
                targetPositionLift += 20;
            }
            else if ((gamepad1.dpad_up)&&(MotorC.getCurrentPosition() > minLift)) {
                targetPositionLift -= 20;
            }

            if(targetPositionLift>=maxLift)
            {
                targetPositionLift = maxLift;
            }
            if(targetPositionLift <= minLift)
            {
                targetPositionLift = minLift;
            }
            MotorC.setTargetPosition(targetPositionLift);
            MotorC.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            MotorC.setPower(0.6);



            // Robot Arm Speed
            if (gamepad1.y)
                cranePower = CRANE_UP;
            else if (gamepad1.a)
                cranePower = CRANE_DOWN;
            else
                cranePower = 0.0;
            MotorD.setPower(cranePower);

            // Send telemetry message to signify robot running;
            telemetry.addData("left: ",  "%.2f", left);
            telemetry.addData("right: ",  "%.2f", right);
            telemetry.addData("claw offset: ",  "Offset = %.2f");
            //telemetry.addData("left",  "%.2f", elevatorPower);
            telemetry.addData("crane: ",  "%.2f", cranePower);

            telemetry.addData("lift max: ",  "%d", maxLift);
            telemetry.addData("lift min: ",  "%d", minLift);
            telemetry.addData("lift Target Position: ",  "%d", targetPositionLift);
            telemetry.addData("lift Current Position: ",  "%d", MotorC.getCurrentPosition());

            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}