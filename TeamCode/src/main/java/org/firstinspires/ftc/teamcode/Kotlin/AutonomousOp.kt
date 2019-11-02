package org.firstinspires.ftc.teamcode.Kotlin

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Kotlin.Drives.IDrive2
import org.firstinspires.ftc.teamcode.Kotlin.Drives.MecDrive2
import org.firstinspires.ftc.teamcode.common.Config
import org.firstinspires.ftc.teamcode.common.HardwareManager
import org.firstinspires.ftc.teamcode.controllers.LiftController
import kotlin.time.seconds


@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
@Autonomous(name = "Kotlin Autonomous", group = "Concept")
class AutonomousOp : LinearOpMode()
{

    private val runtime = ElapsedTime()
    private lateinit var basicRobot: FourWheelRobot
    private lateinit var hwManager: HardwareManager
    private lateinit var config: Config

    private lateinit var liftController: LiftController

    var nav = Robot_Navigation()

    private lateinit var drive: IDrive2


    override fun runOpMode() {

        telemetry.addLine("Configuring..")
        telemetry.update()

        hwManager = HardwareManager(hardwareMap)
        basicRobot = FourWheelRobot(hwManager)
        drive = MecDrive2(null, basicRobot)

        config = Config(hardwareMap.appContext)
        liftController = LiftController(hardwareMap, config, telemetry)

        nav.initVuforia(drive, hardwareMap)
        basicRobot.setMode(DcMotor.RunMode.RUN_USING_ENCODER)


        nav.activateTracking()

        telemetry.addLine("waiting to start..")
        telemetry.update()

        while(!(isStarted || isStopRequested))
        {
            idle()
        }

        runtime.reset()
        liftController.initLift()


        when (config.position){
            Config.Position.BLUE_BRICKS -> blueBricks()
            Config.Position.BLUE_BUILDING -> blueBuilding()
            Config.Position.RED_BRICKS -> redBricks()
            Config.Position.RED_BUILDING -> redBuilding()
        }


        //use safe movement to move close to stone
        //attempt to find stone
        //use nav to move to stone
        //grab stone and lift ?

        //use safe movement to move to placement
        //lower lift? and drop stone?
        //park


    }


    fun pause() {
        sleep(2000)
    }

    fun redBricks() {

        runtime.reset()
        drive.moveRobot(0.0, -0.6, 0.0)
        while(opModeIsActive() && runtime.seconds() < 3) {}
        drive.stop()

        drive.moveByInches(0.6, -10.0)

        runtime.reset()
        while(opModeIsActive() && runtime.seconds() < 1.25) {}

        while(opModeIsActive()) {
            if(nav.targetIsVisible(0)) {
                while(opModeIsActive() && !nav.cruseControl(5.0)) {
                    drive.moveRobot()
                }
                break
            }
        }
        drive.stop()


//        drive.moveByInches(0.6, 2.0)
//        drive.turnDegrees(MecDrive2.TurnDirection.COUNTER_CLOCKWISE, 90, 0.6)
//        drive.moveByInches(0.6, 30.0)
//        drive.turnDegrees(MecDrive2.TurnDirection.CLOCKWISE, 90, 0.6)



//        telemetry.addLine("Move out from wall")
//        telemetry.update()
//        runtime.reset()
//        drive.moveRobot(0.4, 0.0, 0.0)
//        while(opModeIsActive() && runtime.seconds() < 0.5){}
//        drive.stop()
//
//        pause()
//
////        drive.moveByInches(0.5, 4.0)
////        runEncoderDrive(2.0)
//
//
//
//
//        //pause()
//
//        telemetry.addLine("move left into wall")
//        telemetry.update()
//        runtime.reset()
//        drive.moveRobot(0.0, -0.8, 0.0)
//        while(opModeIsActive() && runtime.seconds() < 2){}
//        drive.stop()
//
//        pause()
//
//
//        telemetry.addLine("move towards bricks")
//        telemetry.update()
//        runtime.reset()
//        drive.moveRobot(0.4, 0.4, 0.0)
//        while(opModeIsActive() && runtime.seconds() < 0.75){}
//        drive.stop()
//
//        pause()
//
//
//
//        //scan across bricks for skystone
//        telemetry.addLine("looking for skystone")
//        telemetry.update()
//        runtime.reset()
//        drive.moveRobot(0.0, 0.4, 0.0)
//
//
//
//
//
//
//
////        telemetry.addLine("turn 90")
////        telemetry.update()
////        drive.turnDegrees(MecDrive2.TurnDirection.COUNTER_CLOCKWISE, 50, 0.6)
////        runEncoderDrive(3.0)
//
//
//
//
//        if(!found) {
//            // just grab
//            telemetry.addLine("brick not found")
//            telemetry.update()
//
//        } else {
//            telemetry.addLine("found brick")
//            telemetry.update()
//            liftController.setServoPosition(LiftController.ServoPosition.SERVO_POSITION_OPEN)
//            //move to brick
//
//            telemetry.addLine("moving into block")
//            telemetry.update()
//            while(opModeIsActive() && !nav.cruseControl(2.0))
//            {
//                drive.moveRobot()
//            }
//            drive.stop()
//
//            telemetry.addLine("move forward")
//            telemetry.update()
//            drive.moveByInches(1.0, 4.0)
//            runEncoderDrive(2.0)
//
//            telemetry.addLine("grab ")
//            telemetry.update()
//            liftController.setServoPosition(LiftController.ServoPosition.SERVO_POSITION_OPEN)
//
//
//        }

    }


    fun redBuilding () {



    }
    fun blueBricks() {
    }
    fun blueBuilding() {

    }
    fun runEncoderDrive(durationSeconds: Double) {
        runtime.reset()
        while(opModeIsActive() && drive.isBusy() && runtime.seconds() < durationSeconds) {  }
        drive.stop()
        basicRobot.setMode(DcMotor.RunMode.RUN_USING_ENCODER)
    }
}