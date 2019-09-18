package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Robot;


/**
 * Created by 9533 on 2/3/2018.
 */


public class MecanumDrive implements IDrive {

    private final FtcGamePad driverGamepad;
    private Gamepad gamepad1;
    //private final Robot robot;

    private static final double MIN_SPEED = 0.2;
    private final DcMotor fl;
    private final DcMotor fr;
    private final DcMotor bl;
    private final DcMotor br;

    private ModernRoboticsI2cGyro gyro;

    private int rawX, rawY, rawZ, heading, integratedZ;

    boolean reverse = false;

    public MecanumDrive(Robot robot, FtcGamePad driveGamepad, Gamepad gamepad1){
        this.driverGamepad = driveGamepad;
        this.gamepad1 = gamepad1;
        this.fl = robot.frontLeft;
        this.fr = robot.frontRight;
        this.bl = robot.backLeft;
        this.br = robot.backRight;
        gyro = robot.modernRoboticsI2cGyro;
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

    public void handle(){

        rawX = gyro.rawX();
        rawY = gyro.rawY();
        rawZ = gyro.rawZ();
        heading = gyro.getHeading();
        integratedZ = gyro.getIntegratedZValue();

        double r = -Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_x, gamepad1.left_stick_y) - Math.PI / 4;
        double rightX = -gamepad1.right_stick_x;
        final double v1 = r * Math.cos(robotAngle) - rightX;
        final double v2 = r * Math.sin(robotAngle) + rightX;
        final double v3 = r * Math.sin(robotAngle) - rightX;
        final double v4 = r * Math.cos(robotAngle) + rightX;

        fl.setPower(v1);
        fr.setPower(v2);
        bl.setPower(v3);
        br.setPower(v4);

    }

    @Override
    public void drive(double ly, double lx, double rx) {

    }

    @Override
    public void drive(double left, double right) {

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