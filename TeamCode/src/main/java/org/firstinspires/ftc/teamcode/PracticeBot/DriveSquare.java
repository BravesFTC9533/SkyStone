package org.firstinspires.ftc.teamcode.PracticeBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "AutonoumsOpmode", group = "Linear Opmode")

public class DriveSquare extends LinearOpMode {

    //@Override

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotorEx leftMotor;
    private DcMotorEx rightMotor;
    private DcMotor lift;
    private Servo servo;

    private static final double DISTANCE_BETWEEN_WHEELS = 15.0;
    private static final double WHEEL_DIAMETER = 3.33;
    private static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
    private static final double NUMBER_OF_TICKS_WHEN_DOWN = 1;
    private static final double NUMBER_OF_TICKS_WHEN_UP = 1;


    private double oneInch = ENCODER_TICKS / WHEEL_CIRCUMFERENCE;


    private static final int DRIFT_CORRECTION = 50;

    double power = 0.5;
    private static final int ENCODER_TICKS = 288;
    private static final double TICKS_PER_DEGREE = 35;

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
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        runtime.reset();


            //moveInches(22, 3, 5);
            //firstActions();
            //moveInches(-10,3,5);
            turn(TurnDirection.LEFT,90,5);
            //servo.setPosition(0);
        //turn(TurnDirection.LEFT,90,5);

        //turn(TurnDirection.LEFT,90,5);


        //servo.setPosition(1);
        //servo.wait(300);


    }

    private void firstActions() {
        lift(-200, 1, 7);
        servo(5);
        lift(180, 1, 10);
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

    private void turn(TurnDirection turn, int degrees, double timeOutSeconds) {
        runtime.reset();
        int leftTicks = (int) (TICKS_PER_DEGREE * degrees);
        int rightTicks = (int) -(TICKS_PER_DEGREE * degrees);

        double tickAdjustment = 0.1472;
        if (turn == TurnDirection.LEFT) {
            leftTicks *= -tickAdjustment; //0.88
            rightTicks *= -tickAdjustment;
        } else {
            leftTicks *= tickAdjustment;
            rightTicks *= tickAdjustment;
        }
        leftMotor.setTargetPosition(leftMotor.getCurrentPosition() + leftTicks);
        rightMotor.setTargetPosition(rightMotor.getCurrentPosition() + rightTicks);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(1);
        while (opModeIsActive() && leftMotor.isBusy() && rightMotor.isBusy() &&
                runtime.seconds() < timeOutSeconds) {

        }
        setPower(0);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void moveInches(int numInches, double power, double timeOutSeconds) {
        runtime.reset();
        addPosition((int)oneInch * -numInches);
        setVelocity(128 * (int) power);
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

    private void addPosition(int ticks) {
        leftMotor.setTargetPosition(leftMotor.getCurrentPosition() + ticks);
        rightMotor.setTargetPosition(rightMotor.getCurrentPosition() + ticks);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
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

