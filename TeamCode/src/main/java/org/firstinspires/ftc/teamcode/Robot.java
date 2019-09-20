package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {

    public static final double     REV_COUNTS_PER_MOTOR_REV = 288;      // eg: Rev Side motor
    static final double            DRIVE_GEAR_REDUCTION    = 60.0 / 125.0 ;             // This is < 1.0 if geared UP
    static final double            WHEEL_DIAMETER_INCHES   = 3.543 ;           // For figuring circumference
    public static final double     COUNTS_PER_INCH = (REV_COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    public HardwareMap hardwareMap;

    public Telemetry telemetry;
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        setupMotors();
    }

    private void setupMotors() {
        frontLeft = hardwareMap.dcMotor.get("fl");
        frontRight = hardwareMap.dcMotor.get("fr");
        backLeft = hardwareMap.dcMotor.get("bl");
        backRight = hardwareMap.dcMotor.get("br");

        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setMotorsMode(DcMotor.RunMode runMode) {
        frontLeft.setMode(runMode);
        frontRight.setMode(runMode);
        backLeft.setMode(runMode);
        backRight.setMode(runMode);
    }

    public void setMotorPowers(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);
    }

    public void setTargetPositions(int targetPositions) {
        frontLeft.setTargetPosition(targetPositions);
        frontRight.setTargetPosition(targetPositions);
        backRight.setTargetPosition(targetPositions);
        backLeft.setTargetPosition(targetPositions);
    }

    public void moveForwardByInches(int inches, double power) {
        setTargetPositions(288);
        setMotorPowers(power);
        if(frontLeft.getCurrentPosition() >= 100) {
            setMotorPowers(0);
        }
    }

}
