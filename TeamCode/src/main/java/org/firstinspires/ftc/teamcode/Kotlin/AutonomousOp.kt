package org.firstinspires.ftc.teamcode.Kotlin

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Kotlin.Drives.IDrive2
import org.firstinspires.ftc.teamcode.Kotlin.Drives.MecDrive2
import org.firstinspires.ftc.teamcode.common.HardwareManager


@Autonomous(name = "Kotlin Autonomous", group = "Concept")
class AutonomousOp : LinearOpMode()
{

    private val runtime = ElapsedTime()
    private lateinit var basicRobot: FourWheelRobot
    private lateinit var hwManager: HardwareManager

    var nav = Robot_Navigation()

    private lateinit var drive: IDrive2

    override fun runOpMode() {

        drive = MecDrive2(null, basicRobot)
        hwManager = HardwareManager(hardwareMap)
        basicRobot = FourWheelRobot(hwManager)

        nav.initVuforia(drive)


        nav.activateTracking()

        while(!(isStarted || isStopRequested))
        {
            idle()
        }


        //determine where we are
//        runtime.reset()
//        while(opModeIsActive() && runtime.seconds() < 3.0 ) {
//            if(nav.targetsAreVisible()) {
//                break
//            }
//        }





        //use safe movement to move close to stone
        //attempt to find stone
        //use nav to move to stone
        //grab stone and lift ?

        //use safe movement to move to placement
        //lower lift? and drop stone?
        //park


    }




    sealed class State {
        object Initial : State()

        object DrivingToSkystone : State()


        object GrabingStone : State()
        object PlacingStone : State()
    }

    sealed class Event {
        object OnStart : Event()

        object OnFindSkystone : Event()

        object OnReadyToGrab : Event()

        object OnReadyToPlace : Event()
    }

    sealed class SideEffect {
        object LogInitialized : SideEffect()
        object LogDrivingToSkystone : SideEffect()
    }
}