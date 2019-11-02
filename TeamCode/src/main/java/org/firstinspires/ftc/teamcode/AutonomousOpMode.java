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

    private Config.Position startingPosition;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing Robot. Hands Clear!");
        telemetry.update();

        Initialize(hardwareMap);

        config = new Config(hardwareMap.appContext);
        liftController = new LiftController(hardwareMap, config, telemetry);

        startingPosition = config.getPosition();

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


    private void redBricks() {
        moveByInches(0.6, 4);

        turnDegrees(TurnDirection.COUNTER_CLOCKWISE, 90, 0.6);

        moveByInches(0.6, 15);

        turnDegrees(TurnDirection.CLOCKWISE, 90, 0.6);

        moveByInches(0.6, 8);

        boolean isFound = false;

        while(opModeIsActive()) {
            updateVuforia();
            if(targetVisible) {
                // Y position is the offset left and right right is positive
                while(positionY != 0) {
                    drive.drive(0, positionY, 0);
                }
            }
        }

        moveByInches(0.6, -positionX / 4);

    }

    private void blueBricks() {

    }

    private void blueBuilding() {

    }

    private void redBuilding() {

    }
}
