package org.firstinspires.ftc.teamcode.PracticeBot;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
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

    private double oneInch = ENCODER_TICKS / WHEEL_CIRCUMFERENCE;


    private static final int DRIFT_CORRECTION = 50;

    double power = 0.5;
    private static final int ENCODER_TICKS = 288;
    private static final double TICKS_PER_DEGREE = 3.11;
    private static final double LEFT_TICKS_PER_DEGREE = 3.11;

    public enum TurnDirection {
        LEFT, RIGHT
    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();



        leftMotor = hardwareMap.get(DcMotorEx.class, "l");
        rightMotor = hardwareMap.get(DcMotorEx.class, "r");
        lift = hardwareMap.get(DcMotor.class, "lift");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        runtime.reset();
        
        //moveInches(95, 0.6);

        //moveInches(80, 0.8, 3);
        //turn(TurnDirection.LEFT, 90, 12);
        //turn(TurnDirection.RIGHT, 90, 12);
        lift(90,0.75,6);
        //servo();


    }

    private void lift(double degrees, double power, double timeOutSeconds) {
        runtime.reset();
        int ticks = 15;
        lift.setTargetPosition(lift.getCurrentPosition() + ticks);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(.75);
        while (opModeIsActive() && lift.isBusy() &&
                runtime.seconds() < timeOutSeconds) {

        }
        lift.setPower(0);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    private void turn(TurnDirection turn, int degrees, double timeOutSeconds) {
        runtime.reset();
        int leftTicks = (int) (TICKS_PER_DEGREE * degrees);
        int rightTicks = (int) -(TICKS_PER_DEGREE * degrees);

        if (turn == TurnDirection.LEFT) {
            leftTicks *= -0.88;
            rightTicks *= -0.88;
        } else {
            leftTicks *= 0.88;
            rightTicks *= 0.88;
        }
        leftMotor.setTargetPosition(leftMotor.getCurrentPosition() + leftTicks);
        rightMotor.setTargetPosition(rightMotor.getCurrentPosition() + rightTicks);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(.25);
        while (opModeIsActive() && leftMotor.isBusy() && rightMotor.isBusy() &&
                runtime.seconds() < timeOutSeconds) {

        }
        setPower(0);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void moveInches(int numInches, double power, double timeOutSeconds) {
        runtime.reset();
        addPosition((int)oneInch * numInches);
        //setPower(power);
        int baseVelocity = 450;
        setVelocity(baseVelocity);
        while(opModeIsActive() && (leftMotor.isBusy() && rightMotor.isBusy()) && runtime.seconds() < timeOutSeconds)
        {
            //put correcting code here

            //get position for each motor
            //compare positions
            //if required, if left ticks > right ticks, increase right velocity some
            //opposite for if right > left
            //else set them back to base
        }
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

    private void servo() {
        if(servo.getPosition() == 1) {
            servo.setPosition(0);
        } else {
            servo.setPosition(1);
        }
    }
}

