package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.LiftController;

public class Robot {

    public static final double     NEVE_COUNTS_PER_MOTOR_NEVE = 1120;      // eg: Rev Side motor
    static final double            DRIVE_GEAR_REDUCTION    = 45.0 / 35.0;             // This is < 1.0 if geared UP
    static final double            WHEEL_DIAMETER_INCHES   = 4.0 ;           // For figuring circumference
    public static final double     COUNTS_PER_INCH = (NEVE_COUNTS_PER_MOTOR_NEVE * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    public HardwareMap hardwareMap;

    public Telemetry telemetry;
    public LiftController lift;

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
       // this.lift = new LiftController(hardwareMap);
        setupMotors();
    }

    private void setupMotors() {
        frontLeft = hardwareMap.dcMotor.get("fl");
        frontRight = hardwareMap.dcMotor.get("fr");
        backLeft = hardwareMap.dcMotor.get("bl");
        backRight = hardwareMap.dcMotor.get("br");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);

        setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setMotorMode(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);
    }

    private void setEncoderTicks(int ticks) {
        frontLeft.setTargetPosition(ticks + frontLeft.getCurrentPosition());
        frontRight.setTargetPosition(ticks + frontRight.getCurrentPosition());
        backLeft.setTargetPosition(ticks + backLeft.getCurrentPosition());
        backRight.setTargetPosition(ticks + backRight.getCurrentPosition());
    }

    public void setSpeed(double speed) {
        frontRight.setPower(speed);
        frontLeft.setPower(speed);
        backRight.setPower(speed);
        backLeft.setPower(speed);
    }

    public void moveByEncoderTicks(int ticks, double speed) {
        setEncoderTicks(ticks);
        setMotorMode(DcMotor.RunMode.RUN_TO_POSITION);
        setSpeed(speed);
        setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while(isBusy()) {}
    }

    public boolean isBusy(){
        if(frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy()) {
            return true;
        }
        return false;
    }

    public void moveForwardByInches(int inches, double power) {
        moveByEncoderTicks((int) COUNTS_PER_INCH * inches, power);
    }

}
