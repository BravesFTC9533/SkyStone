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

    private StartingPosition startingPosition = StartingPosition.BLUE_BRICKS;

    public enum StartingPosition {
        BLUE_BUILDING,
        BLUE_BRICKS,
        RED_BUILDING,
        RED_BRICKS
    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing Robot. Hands Clear!");
        telemetry.update();

        Initialize(hardwareMap);

        telemetry.addData("Status", "Robot Initialized");
        telemetry.update();

        telemetry.addData("Status", "Initializing Vuforia");
        telemetry.update();

        try {
            initializeVuforia();
        } catch (Exception e) {
            telemetry.addData("Status", "Vuforia Could not be Initialized!");
            telemetry.update();
            return;
        }

        telemetry.addData("Status", "Vuforia Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        switch(startingPosition) {
            case BLUE_BRICKS:
                blueBricks();
                break;
            case BLUE_BUILDING:
                blueBuilding();
                break;
            case RED_BRICKS:
                redBricks();
                break;
            case RED_BUILDING:
                redBuilding();
                break;
        }
    }

    private void grabBrick() {
        while(opModeIsActive()) {
            updateVuforia();
            telemetry.addData("Block Distance", positionX + "mm");
            telemetry.update();
        }
    }

    private void blueBricks() {
        moveByInches(5, 0.5);
        //grabBrick();
    }

    private void redBricks() {

    }

    private void blueBuilding() {

    }

    private void redBuilding() {

    }



}
