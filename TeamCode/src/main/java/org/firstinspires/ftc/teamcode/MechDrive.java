package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Common.FtcGamePad;
import org.firstinspires.ftc.teamcode.Common.FtcGamePad.ButtonHandler;
import org.firstinspires.ftc.teamcode.Common.MecanumDrive;
import org.firstinspires.ftc.teamcode.Kotlin.GamePad;
import org.jetbrains.annotations.NotNull;

@TeleOp(name="Basic: Linear OpMode", group="Test Opmode")

public class MechDrive extends LinearOpMode implements FtcGamePad.ButtonHandler {

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
        drive = new MecanumDrive(robot, driverGamePad);

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