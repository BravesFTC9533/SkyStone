package org.firstinspires.ftc.teamcode.PracticeBot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Gyroscope;

@Autonomous(name = "AutonoumsOpmode", group = "Linear Opmode")

public class DriveSquare extends LinearOpMode {


    private ElapsedTime runtime = new ElapsedTime();
    private DcMotorEx leftMotor;
    private DcMotorEx rightMotor;
    private DcMotor lift;
    private Servo servo;

    private static final double DISTANCE_BETWEEN_WHEELS = 14;
    private static final double WHEEL_DIAMETER = 3.33;
    private static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
    private static final double NUMBER_OF_TICKS_WHEN_DOWN = 1;
    private static final double NUMBER_OF_TICKS_WHEN_UP = 1;


    private double oneInch = ENCODER_TICKS / WHEEL_CIRCUMFERENCE;
    private double tickPerDegree = (17.5 * oneInch) / 90;

    private static final int DRIFT_CORRECTION = 24;

    double power = 0.5;
    private static final int ENCODER_TICKS = 288;
    private static final double TICKS_PER_DEGREE = 35;

    private BNO055IMU imu;

    public enum TurnDirection {
        LEFT, RIGHT
    }


    //@Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftMotor = hardwareMap.get(DcMotorEx.class, "l");
        rightMotor = hardwareMap.get(DcMotorEx.class, "r");
        lift = hardwareMap.get(DcMotor.class, "lift");
        servo = hardwareMap.get(Servo.class, "lservo");

        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        servo.setDirection(Servo.Direction.FORWARD);

        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        runtime.reset();

//        rightMotor.setTargetPosition((int) (oneInch * DISTANCE_BETWEEN_WHEELS));
//        leftMotor.setTargetPosition((int) -(oneInch * DISTANCE_BETWEEN_WHEELS));
//
//        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        rightMotor.setPower(1);
//        leftMotor.setPower(1);
//
//        while(opModeIsActive()) {}

        turn(TurnDirection.LEFT, 90, 1, 5);
//          blueBrick();
    }

    private void blueBrick() {
        servo.setPosition(0);
        moveInches(21, 1, 5);
        lift(-370, 1, 3);
        servo.setPosition(1);
        waitMill(1000);
        lift(200, 1, 5);
        turn(TurnDirection.LEFT, 90, 1, 1);
        moveInches(60, 1, 5);
        lift(-130, 1, 5);
        servo.setPosition(0);
        waitMill(1000);
        lift(120,1,5);
        moveInches(-30, 1, 5);
    }

    private void redBrick() {
        servo.setPosition(0);
        moveInches(22, 1, 5);
        lift(-200, 1, 7);
        servo.setPosition(1);
        waitMill(1000);
        lift(150, 1, 5);
        turn(TurnDirection.LEFT, 90, 1, 5);
        moveInches(60, 1, 5);
        lift(-110, 1, 5);
        servo.setPosition(0);
        waitMill(1000);
        lift(100, 1, 5);
        moveInches(10, 3, 5);
    }

    private void lift(double degrees, double power, double timeOutSeconds) {
        runtime.reset();
        int ticks = (int)(TICKS_PER_DEGREE * degrees);
        lift.setTargetPosition(lift.getCurrentPosition() + ticks);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(power);
        while(opModeIsActive() && lift.isBusy() && runtime.seconds() < timeOutSeconds) {

        }
        lift.setPower(0);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    private void turn(TurnDirection turn, int degrees, double power, double timeOutSeconds) {
         float degree = (float) (oneInch * DISTANCE_BETWEEN_WHEELS) / 68f;
            rightMotor.setTargetPosition (-degrees * (int) degree);
            leftMotor.setTargetPosition(-degrees * (int) degree);

            if(turn == TurnDirection.LEFT) {
                rightMotor.setTargetPosition(degrees * (int) degree);
                leftMotor.setTargetPosition(-degrees * (int) degree);
            }

            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            rightMotor.setPower(power);
            leftMotor.setPower(power);

            while(opModeIsActive() && rightMotor.isBusy() && leftMotor.isBusy()) {}
    }


    private void moveInches(float numInches, double power, double timeOutSeconds) {
        runtime.reset();
        addPosition((int)oneInch * -numInches);
        setVelocity(600 * (int) power);
        while(opModeIsActive() && (leftMotor.isBusy() && rightMotor.isBusy()) && runtime.seconds() < timeOutSeconds){}
        setPower(0);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    private void setVelocity(int velocity){
        leftMotor.setVelocity(velocity + DRIFT_CORRECTION);
        rightMotor.setVelocity(velocity);
    }

    private void setPower(double power) {
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }

    private void setMode(DcMotor.RunMode runMode) {
        leftMotor.setMode(runMode);
        rightMotor.setMode(runMode);
    }

    private void addPosition(float ticks) {
        leftMotor.setTargetPosition(leftMotor.getCurrentPosition() + (int) ticks);
        rightMotor.setTargetPosition(rightMotor.getCurrentPosition() + (int) ticks);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void waitMill(float mill) {
        runtime.reset();
        while(opModeIsActive() && runtime.milliseconds() < mill) {idle();}
    }

    private void servo(int timeOutSeconds) {
        if (servo.getPosition() == 0) {
            servo.setPosition(1);
            while(runtime.seconds() < timeOutSeconds) {}
        } else {
            servo.setPosition(0);
            while(runtime.seconds() < timeOutSeconds) {}
        }

    }

}

