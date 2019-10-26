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
        // Boolean for later when the trackable will be found
        boolean foundTarget = false;

        // Turn towards the left target
        turnDegrees(TurnDirection.COUNTER_CLOCKWISE, 90, 0.5);

        while(opModeIsActive() && !foundTarget) {
            updateVuforia();
            turnDegrees(TurnDirection.CLOCKWISE, 2, 1);
            if (targetVisible && ((VuforiaTrackableDefaultListener) stoneTarget.getListener()).isVisible() && opModeIsActive()) {
                foundTarget = true;
            }
        }
        turnDegrees(TurnDirection.CLOCKWISE, 180, 0.6);
        liftController.setServoPosition(LiftController.ServoPosition.SERVO_POSITION_OPEN);
        moveByInches(0.6, positionX);
        liftController.setServoPosition(LiftController.ServoPosition.SERVO_POSITION_CLOSED);
        moveByInches(0.6, -positionX);
        turnDegrees(TurnDirection.COUNTER_CLOCKWISE, 180, 0.6);
    }

    private void blueBricks() {
        // Move off the wall
        moveByInches(0.6, 2);
        grabBrick();
        // Move back
        moveByInches(0.6, -2);
        turnDegrees(TurnDirection.CLOCKWISE, 90, 0.6);
        moveByInches(0.6, 72);
        turnDegrees(TurnDirection.COUNTER_CLOCKWISE, 90, 0.6);
        moveByInches(0.6, 7);
        turnDegrees(TurnDirection.COUNTER_CLOCKWISE, 180, 0.6);
        liftController.setServoPosition(LiftController.ServoPosition.SERVO_POSITION_OPEN);
    }

    private void redBricks() {

    }

    private void blueBuilding() {

    }

    private void redBuilding() {

    }
}
