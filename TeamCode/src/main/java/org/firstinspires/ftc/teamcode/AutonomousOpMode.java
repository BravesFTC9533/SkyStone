package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.teamcode.common.BaseLinearOpMode;
import org.firstinspires.ftc.teamcode.common.Config;
import org.firstinspires.ftc.teamcode.controllers.LiftController;

@Autonomous(name = "Java: Autonomous", group = "Concept")
public class AutonomousOpMode extends BaseLinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private LiftController liftController;
    private Config config;

    private Config.Position startingPosition = Config.Position.BLUE_BRICKS;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing Robot. Hands Clear!");
        telemetry.update();

        Initialize(hardwareMap);

        config = new Config(hardwareMap.appContext);
        liftController = new LiftController(hardwareMap, config, telemetry);

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

        liftController.initLift();

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
        boolean foundTarget = false;
        double distance = 0;
        while(opModeIsActive() && !foundTarget) {
            updateVuforia();
            if(targetVisible && ((VuforiaTrackableDefaultListener)stoneTarget.getListener()).isVisible() && opModeIsActive()) {
                distance = Math.abs(positionX);
                foundTarget = true;
            }
        }
        turnDegrees(TurnDirection.CLOCKWISE, 180, 0.5);
        moveByInches(0.7, -distance);
    }

    private void blueBricks() {
        moveByInches(0.6, 10);
        grabBrick();
    }

    private void redBricks() {

    }

    private void blueBuilding() {

    }

    private void redBuilding() {

    }
}
