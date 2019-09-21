package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LiftController {

    public static double MAX_LIFT_HEIGHT_IN_TICKS = 1000.0;
    public static double LIFT_SPEED = 1.0;

    public DcMotor lift;

    public LiftController(HardwareMap hardwareMap) {
        this.lift = hardwareMap.dcMotor.get("lift");
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

}
