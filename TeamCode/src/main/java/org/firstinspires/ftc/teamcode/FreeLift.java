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

@TeleOp(name="Lift Control", group="Robot")
//@Disabled
public class FreeLift extends LinearOpMode {

    /* Declare OpMode members. */
    public DcMotor MotorC = null;


    @Override
    public void runOpMode() {
        double left;
        double right;
        double max;

        int minLift = -1300;
        int maxLift = 1300;


        // Define and Initialize Motors

        MotorC  = hardwareMap.get(DcMotor.class, "MotorC");

        MotorC.setDirection(DcMotor.Direction.REVERSE);

        MotorC.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MotorC.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorC.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int targetPositionLift = 0;


        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press Play.");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            // Robot Lift Speed
            if ((gamepad1.left_bumper)&&(MotorC.getCurrentPosition() > minLift)) {   //down
                targetPositionLift -= 20; //incrementing target position
            }
            else if ((gamepad1.right_bumper)&&(MotorC.getCurrentPosition() < maxLift)) {   //up
                targetPositionLift += 20; //incrementing target position
            }

            if(targetPositionLift>=maxLift)
            {
                targetPositionLift = maxLift;
            }

            if(targetPositionLift <= minLift)
            {
                targetPositionLift = minLift;
            }

            //if (MotorC.getCurrentPosition() !=  targetPositionLift && MotorD.getCurrentPosition() != -targetPositionLift) {
            MotorC.setTargetPosition(targetPositionLift);
            //MotorD.setTargetPosition(targetPositionLift);

            MotorC.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //MotorD.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            MotorC.setPower(0.9);
            //MotorD.setPower(0.9);
            //}


            if (!MotorC.isBusy()) {
             MotorC.setPower(0);
             //MotorD.setPower(0);
            }



            // Send telemetry message to signify robot running;
            telemetry.addData("claw offset: ",  "Offset = %.2f");

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