package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;


/**
 * Created by 9533 on 2/3/2018.
 */


public class MecanumDrive implements IDrive {

    private final FtcGamePad driverGamepad;
    private Gamepad gamepad1;
    private Robot robot;
    private Telemetry telemetry;
    //private final Robot robot;

    private static final double MIN_SPEED = 0.2;
    private final DcMotor fl;
    private final DcMotor fr;
    private final DcMotor bl;
    private final DcMotor br;

    boolean reverse = false;

    public MecanumDrive(Robot robot, Telemetry telemetry, FtcGamePad driveGamepad, Gamepad gamepad1){
        this.driverGamepad = driveGamepad;
        this.gamepad1 = gamepad1;
        this.telemetry = telemetry;
        this.fl = robot.frontLeft;
        this.fr = robot.frontRight;
        this.bl = robot.backLeft;
        this.br = robot.backRight;
        this.robot = robot;
    }

    public MecanumDrive(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br, FtcGamePad driverGamepad) {
        this.fl = fl;
        this.fr = fr;
        this.bl = bl;
        this.br = br;
        this.driverGamepad = driverGamepad;
    }

    public boolean getIsReverse(){
        return reverse;
    }

    public void setIsReverse(boolean value){
        reverse = value;
    }

    public void handle() {
        double leftPower;
        double rightPower;

        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.left_stick_x;
        double strafe = gamepad1.right_stick_x;

        leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
        rightPower   = Range.clip(drive - turn, -1.0, 1.0);

        if(strafe > 0) {
            robot.backLeft.setPower(strafe);
            robot.frontLeft.setPower(strafe);
            robot.backRight.setPower(-strafe);
            robot.frontRight.setPower(-strafe);
        } else if(strafe < 0) {
            robot.backLeft.setPower(-strafe);
            robot.frontLeft.setPower(-strafe);
            robot.backRight.setPower(strafe);
            robot.frontRight.setPower(strafe);
        }

        robot.frontLeft.setPower(leftPower);
        robot.backLeft.setPower(leftPower);
        robot.frontRight.setPower(rightPower);
        robot.backRight.setPower(rightPower);
    }

    @Override
    public void drive(double ly, double lx, double rx) {

    }

    @Override
    public void drive(double left, double right) {
        fl.setPower(left);
        bl.setPower(left);
        fr.setPower(right);
        br.setPower(right);
    }

    @Override
    public void stop() {

    }

    @Override
    public void setMode(DcMotor.RunMode runMode) {
        fl.setMode(runMode);
        fr.setMode(runMode);
        bl.setMode(runMode);
        br.setMode(runMode);
    }

    @Override
    public void driveToPosition(int leftPosition, int rightPosition) {

    }


    // Scale motor power based on the max for all wheels
    // 1, 1, 1, 3 will become .33, .33, .33, 1
    public static double scalePower(double value, double max){
        if(max == 0){return  0;}
        return  value / max;
    }

    // motor power clipping helper
    public static double clipMotorPower(double value){
        return Range.clip(value, -1, 1);
    }

    public static double scale(double power){
        int modifier = 1;

        if (power == 0 )
        {
            return 0;
        }

        if(power < 0){
            modifier *= -1;
        }

        return  (power * power * modifier);
    }
}