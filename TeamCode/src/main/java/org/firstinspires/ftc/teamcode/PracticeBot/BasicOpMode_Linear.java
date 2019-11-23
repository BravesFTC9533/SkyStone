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

package org.firstinspires.ftc.teamcode.PracticeBot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
//@Disabled
public class BasicOpMode_Linear extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor lift = null;

    private Servo servoLeft = null;
    private Servo servoRight = null;
    private static final double TICKS_PER_DEGREE = 3.233888889;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDrive  = hardwareMap.get(DcMotor.class, "l");
        rightDrive = hardwareMap.get(DcMotor.class, "r");
        lift = hardwareMap.get(DcMotor.class, "lift");

        servoLeft = hardwareMap.get(Servo.class, "lservo");
        servoRight = hardwareMap.get(Servo.class, "rservo");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        lift.setDirection(DcMotorSimple.Direction.REVERSE);
        servoRight.setDirection(Servo.Direction.REVERSE);

        //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        servoLeft.setPosition(0);
        servoRight.setPosition(0);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            double leftPower;
            double rightPower;

            double drive =  gamepad1.left_stick_y;
            double turn  =  -gamepad1.right_stick_x;
            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

            handleGamepad();

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.addData("Motor Position", "left (%d), right (%d)",
                    leftDrive.getCurrentPosition(), rightDrive.getCurrentPosition());
            telemetry.addData("Lift", "Position: %d", lift.getCurrentPosition());
            telemetry.update();
        }
    }

    private boolean isPressed = false;

    private boolean buttonPressed = false;

    private void handleGamepad() {
        // Lift Control

        if(gamepad1.x) {
            lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
//        if (gamepad2.atRest() && gamepad1.dpad_down) {
//            lift.setPower(1);
//        } else if (gamepad2.atRest() && gamepad1.dpad_up) {
//            lift.setPower(-1);
//        } else if (gamepad2.atRest()){
//            lift.setPower(0);
//        }

        if (gamepad2.dpad_down) {
            if(lift.getCurrentPosition() < 6800) {
                lift.setPower(1);
            } else {
                lift.setPower(0);
            }

        } else if (gamepad2.dpad_up) {
            if(lift.getCurrentPosition() > 0) {
                lift.setPower(-1);
            } else {
                lift.setPower(0);
            }
        } else {
            lift.setPower(0);
        }

        //hold button to close servo
//        if(gamepad1.a) {
//            servoRight.setPosition(1);
//            servoLeft.setPosition(1);
//        }
//        else
//        {
//            servoRight.setPosition(0);
//            servoLeft.setPosition(0);
//        }

        //toggle button state
        if(gamepad2.a && !buttonPressed)
        {
            buttonPressed = true;
            isPressed = !isPressed;
        }
        if(!gamepad2.a){
            buttonPressed = false;
        }

        servoRight.setPosition(isPressed ? 1 : 0);
        servoLeft.setPosition(isPressed ? 1: 0);


        // Servo Control
//        if (gamepad1.a) {
//            if (isPressed) {
//                servoLeft.setPosition(1);
//                servoRight.setPosition(1);
//                isPressed = !isPressed;
//            } else {
//                servoLeft.setPosition(0);
//                servoRight.setPosition(0);
//                isPressed = !isPressed;
//            }
//        }
    }
}
