package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.BaseLinearOpMode;
import org.firstinspires.ftc.teamcode.common.Config;
import org.firstinspires.ftc.teamcode.common.FtcGamePad;
import org.firstinspires.ftc.teamcode.controllers.LiftController;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@TeleOp(name="Java: Teleop", group="Java")
public class TeleopMode extends BaseLinearOpMode implements FtcGamePad.ButtonHandler {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private FtcGamePad driverGamePad;
    private FtcGamePad operatorGamePad;
    private LiftController liftController;
    private Config config;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        config = new Config(hardwareMap.appContext);
        driverGamePad = new FtcGamePad("Driver", gamepad1, this);
        operatorGamePad = new FtcGamePad("Operator", gamepad2, this);

        Initialize(hardwareMap, driverGamePad);

        liftController = new LiftController(hardwareMap, config, telemetry);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            drive.handle();
            driverGamePad.update();
            telemetry.update();
        }
    }

    @Override
    public void gamepadButtonEvent(FtcGamePad gamepad, int button, boolean pressed) {
        liftController.gamepadButtonEvent(gamepad, button, pressed);
    }
}