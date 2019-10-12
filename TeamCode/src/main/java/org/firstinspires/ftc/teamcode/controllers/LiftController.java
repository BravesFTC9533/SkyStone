package org.firstinspires.ftc.teamcode.controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.BaseController;
import org.firstinspires.ftc.teamcode.common.FtcGamePad;

public class LiftController extends BaseController {

    public static double MAX_LIFT_HEIGHT_IN_TICKS = 1000.0;
    public static double LIFT_SPEED = 1.0;

    public DcMotor lift;
    private Telemetry telemetry;

    public Servo leftServo;
    public Servo rightServo;
    public Servo leftLiftServo;
    public Servo rightLiftServo;

    public LiftController(HardwareMap hardwareMap, Telemetry telemetry) {
        super(hardwareMap);
        this.telemetry = telemetry;
        lift = hardwareMap.dcMotor.get("lift");
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int startEnc = lift.getCurrentPosition();

        lift.setTargetPosition(200);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(LIFT_SPEED);

        leftServo      = hardwareMap.servo.get("flservo");
        rightServo     = hardwareMap.servo.get("frservo");
        leftLiftServo  = hardwareMap.servo.get("llservo");
        rightLiftServo = hardwareMap.servo.get("lrservo");

        initServos();
    }

    private void initServos() {
        leftLiftServo.setDirection(Servo.Direction.REVERSE);

        leftServo.setPosition(0);
        rightServo.setPosition(0);
        leftLiftServo.setPosition(0);
        rightLiftServo.setPosition(0);
    }

    public void stop() {
        lift.setPower(0);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void toggleLift() {
        if(lift.getCurrentPosition() > 0) {
            lift.setTargetPosition(0);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(LIFT_SPEED);
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            lift.setTargetPosition((int) MAX_LIFT_HEIGHT_IN_TICKS);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(LIFT_SPEED);
            lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    @Override
    public void gamepadButtonEvent(FtcGamePad gamepad, int button, boolean pressed) {
        telemetry.log().add("Lift: " + lift.getCurrentPosition());
        telemetry.update();
        switch(button) {
            case FtcGamePad.GAMEPAD_A:
                if(pressed) {
                    lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    lift.setPower(-LIFT_SPEED);
                } else{
                    lift.setPower(0);
                    lift.setTargetPosition(lift.getCurrentPosition());
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                break;
            case FtcGamePad.GAMEPAD_Y:
                if(pressed) {
                    lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    lift.setPower(LIFT_SPEED);
                } else {
                    lift.setPower(0);
                    lift.setTargetPosition(lift.getCurrentPosition());
                    lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                break;
        }
    }
}
