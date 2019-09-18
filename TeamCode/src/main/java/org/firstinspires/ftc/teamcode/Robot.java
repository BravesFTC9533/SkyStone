package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Robot {

    public static final double     REV_COUNTS_PER_MOTOR_REV = 288;      // eg: Rev Side motor
    static final double            DRIVE_GEAR_REDUCTION    = 60.0 / 125.0 ;             // This is < 1.0 if geared UP
    static final double            WHEEL_DIAMETER_INCHES   = 3.543 ;           // For figuring circumference
    public static final double     COUNTS_PER_INCH = (REV_COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    public HardwareMap hardwareMap;
    public ModernRoboticsI2cGyro modernRoboticsI2cGyro;

    public Telemetry telemetry;
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        setupMotors();
        modernRoboticsI2cGyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro");
        telemetry.log().add("Gyro Calibrating. Do Not Move!");
        telemetry.update();
        modernRoboticsI2cGyro.calibrate();
        telemetry.log().add("Gyro Calibrated!");
        telemetry.update();
    }

    private void setupMotors() {
        frontLeft = hardwareMap.dcMotor.get("fl");
        frontRight = hardwareMap.dcMotor.get("fr");
        backLeft = hardwareMap.dcMotor.get("bl");
        backRight = hardwareMap.dcMotor.get("br");
    }

    public void setMotorsMode(DcMotor.RunMode runMode) {
        frontLeft.setMode(runMode);
        frontRight.setMode(runMode);
        backLeft.setMode(runMode);
        backRight.setMode(runMode);
    }

    public void moveMotorsByTick(int ticks) {
        setMotorsMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + ticks);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + ticks);
        backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);
        setMotorsMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void moveForwardByInches(int inches) {
        moveMotorsByTick((int) (inches * COUNTS_PER_INCH));
    }

}
