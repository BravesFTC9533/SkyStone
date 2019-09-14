package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")

public class BasicOpMode_Linear extends LinearOpMode {

    private DcMotor backLeft;
    private DcMotor backRight;

    @Override
    public void runOpMode() {

        backLeft = hardwareMap.dcMotor.get("bl");
        backRight = hardwareMap.dcMotor.get("br");

        waitForStart();

        while (opModeIsActive()) {
            backLeft.setPower(1);
            backRight.setPower(1);
        }
    }
}
