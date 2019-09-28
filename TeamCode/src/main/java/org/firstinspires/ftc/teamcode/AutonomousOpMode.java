package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.common.BaseLinearOpMode;

@Autonomous(name = "Java: Autonomous", group = "Concept")
public class AutonomousOpMode extends BaseLinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private Robot robot;

    private StartingPosition startingPosition = StartingPosition.BLUE_BRICKS;

    private boolean[] stickerPositions = new boolean[6];

    public enum StartingPosition {
        BLUE_BUILDING,
        BLUE_BRICKS,
        RED_BUILDING,
        RED_BRICKS
    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new Robot(hardwareMap, telemetry);

        waitForStart();
        runtime.reset();

        if(startingPosition == StartingPosition.BLUE_BRICKS) {
            blueBricks();
        }
    }

    private void blueBricks() {
        // Move 96 in. forward
        robot.moveByEncoderTicks(1000, 1);
    }

}
