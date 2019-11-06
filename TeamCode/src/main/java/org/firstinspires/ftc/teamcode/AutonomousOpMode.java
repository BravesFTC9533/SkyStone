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

    boolean isTargetFound = false;

    private void redBricks() {
        moveByInches(0.6, 2);
        turnDegrees(TurnDirection.COUNTER_CLOCKWISE, 90, 0.5);

        moveByInches(0.6, 26);

        turnDegrees(TurnDirection.CLOCKWISE, 90, 0.5);

        while(opModeIsActive() && !isTargetFound) {
            updateVuforia();
            turnDegrees(TurnDirection.CLOCKWISE, 5, 1);
            if(targetVisible) {
                isTargetFound = false;
            }
        }

        telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", firstAngle, secondAngle, thirdAngle);
        telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
        positionX / mmPerInch, positionY / mmPerInch, positionZ / mmPerInch);
    }

    private void blueBricks() {

    }

    private void blueBuilding() {

    }

    private void redBuilding() {

    }
}
