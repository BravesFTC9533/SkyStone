package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.common.Config;
import org.firstinspires.ftc.teamcode.common.SimpleMenu;


@Autonomous(name="Configuration", group="Config")
public class AutonomousConfig extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    public static SimpleMenu menu = new SimpleMenu("Autonomous Menu");

    @Override
    public void runOpMode() {
        Config config = new Config(hardwareMap.appContext);

        menu.clearOptions();

        menu.addOption("Position", Config.Position.class, config.getPosition());
        menu.setGamepad(gamepad1);
        menu.setTelemetry(telemetry);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            menu.displayMenu();

            switch (menu.getCurrentChoiceOf("Position")) {
                case "BLUE_BRICKS":
                    config.setPosition(Config.Position.BLUE_BRICKS);
                    break;
                case "BLUE_BUILDINGS":
                    config.setPosition(Config.Position.BLUE_BUILDINGS);
                    break;
                case "RED_BRICKS":
                    config.setPosition(Config.Position.RED_BRICKS);
                    break;
                case "RED_BUILDINGS":
                    config.setPosition(Config.Position.RED_BUILDINGS);
                    break;
            }

            sleep(50);
        }

        config.save();
    }
}