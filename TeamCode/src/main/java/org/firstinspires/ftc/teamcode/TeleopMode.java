package org.firstinspires.ftc.teamcode;
                                                                                        
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.Config;
import org.firstinspires.ftc.teamcode.common.FtcGamePad;
import org.firstinspires.ftc.teamcode.controllers.LiftController;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@TeleOp(name="Java: Teleop", group="Java")
public class TeleopMode extends LinearOpMode implements FtcGamePad.ButtonHandler {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Robot robot;
    private MecanumDrive drive;
    private FtcGamePad driverGamePad;
    private FtcGamePad operatorGamePad;
    private LiftController liftController;
    private Config config;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new Robot(hardwareMap, telemetry);
        driverGamePad = new FtcGamePad("Driver", gamepad1, this);
        operatorGamePad = new FtcGamePad("Operator", gamepad2, this);
        liftController = new LiftController(hardwareMap);
        drive = new MecanumDrive(robot.frontLeft, robot.frontRight, robot.backLeft, robot.backRight, driverGamePad);
        config = new Config(hardwareMap.appContext);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            drive.handle();
            driverGamePad.update();
            telemetry.addData("Max_Lift_Ticks: ", liftController.lift.getCurrentPosition());
            telemetry.update();
        }
    }

    @Override
    public void gamepadButtonEvent(FtcGamePad gamepad, int button, boolean pressed) {
        liftController.gamepadButtonEvent(gamepad, button, pressed);
    }
}