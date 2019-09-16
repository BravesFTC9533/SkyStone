package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.FtcGamePad;
import org.firstinspires.ftc.teamcode.common.MecanumDrive;

@TeleOp(name="Java: Teleop", group="Java")

public class TeleopMode extends LinearOpMode implements FtcGamePad.ButtonHandler {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Robot robot;
    private MecanumDrive drive;
    private FtcGamePad driverGamePad;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new Robot(hardwareMap);
        driverGamePad = new FtcGamePad("Driver", gamepad1, this);
        drive = new MecanumDrive(robot, driverGamePad, gamepad1);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            drive.handle();
            driverGamePad.update();
        }
    }

    @Override
    public void gamepadButtonEvent(FtcGamePad gamepad, int button, boolean pressed) {

    }
}