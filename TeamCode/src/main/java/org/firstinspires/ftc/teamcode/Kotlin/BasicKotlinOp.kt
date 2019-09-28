package org.firstinspires.ftc.teamcode.Kotlin

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.Kotlin.Drives.IDrive2
import org.firstinspires.ftc.teamcode.Kotlin.Drives.MecDrive2
import org.firstinspires.ftc.teamcode.common.IDrive
import org.firstinspires.ftc.teamcode.Kotlin.Drives.MecanumDrive
import org.firstinspires.ftc.teamcode.common.HardwareManager
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode as LinearOpMode

@TeleOp(name = "Kotlin Hot", group = "Concept")
class BasicKotlinOp : LinearOpMode(), IButtonHandler {


    private lateinit var basicRobot: FourWheelRobot
    private lateinit var gamepad: Gamepad
    private lateinit var hwManager: HardwareManager

    private lateinit var ftcGamepad: GamePad

    var nav = Robot_Navigation()

    private lateinit var drive: IDrive2

    val TARGET_DISTANCE = 400.0    // Hold robot's center 400 mm from target

    override fun runOpMode() {

        gamepad = this.gamepad1
        hwManager = HardwareManager(hardwareMap)
        basicRobot = FourWheelRobot(hwManager)


        ftcGamepad = GamePad(gamepad, this)

        drive = MecDrive2(ftcGamepad, basicRobot)


        drive.initDrive()
        nav.initVuforia(drive)

        nav.activateTracking()

        while(!(isStarted || isStopRequested))
        {
            idle()
        }


        while(opModeIsActive())
        {

            telemetry.addData(">", "Press Left Bumper to track target")

            telemetry.addData("voltage", "%.1f volts") { getBatteryVoltage() }

            if(nav.targetsAreVisible() && gamepad.left_bumper) {
                nav.cruseControl(TARGET_DISTANCE)
            } else {
                drive.manualDrive()
            }

            drive.moveRobot()

            telemetry.update()
        }


        telemetry.addData(">", "Shutting Down. Bye!")
        telemetry.update()
    }

    internal fun getBatteryVoltage(): Double {
        var result = java.lang.Double.POSITIVE_INFINITY
        for (sensor in hardwareMap.voltageSensor) {
            val voltage = sensor.voltage
            if (voltage > 0) {
                result = Math.min(result, voltage)
            }
        }
        return result
    }

    override fun gamepadButtonEvent(gamePad: GamePad, button: Int, pressed: Boolean) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}