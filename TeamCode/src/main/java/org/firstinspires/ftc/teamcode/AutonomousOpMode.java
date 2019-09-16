package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.common.BaseLinearOpMode;

@Autonomous(name="Java: Autonomous", group="Java")

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

        robot = new Robot(hardwareMap);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            switch (startingPosition) {
                case BLUE_BRICKS:
                    blueBricks();
                    break;

            }
        }
    }

    private void blueBricks() {
        // Find Sticker Positions
        // I DONT KNOW HOW TO USE VUFORIA!!!!!!
        // Move Forward
        robot.moveForwardByInches(24);

        // Move Back Slightly
        robot.moveForwardByInches(2);

        // Turn 90 degrees Clockwise

        // Go Under Bridge
        robot.moveForwardByInches(48);

        // Turn Right

        //Move Forward
        robot.moveForwardByInches(12);

        // Drop Off Brick

        // Done
    }
}
