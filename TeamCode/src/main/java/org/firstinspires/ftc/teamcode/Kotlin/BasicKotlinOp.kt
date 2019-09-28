package org.firstinspires.ftc.teamcode.Kotlin

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.Kotlin.Drives.IDrive2
import org.firstinspires.ftc.teamcode.Kotlin.Drives.MecDrive2
import org.firstinspires.ftc.teamcode.common.IDrive
import org.firstinspires.ftc.teamcode.Kotlin.Drives.MecanumDrive
import org.firstinspires.ftc.teamcode.common.HardwareManager
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode as LinearOpMode

@TeleOp(name = "Concept: Telemetry", group = "Concept")
class BasicKotlinOp : LinearOpMode(), IButtonHandler {


    private lateinit var basicRobot: FourWheelRobot
    private lateinit var gamepad: Gamepad
    private lateinit var hwManager: HardwareManager

    private lateinit var ftcGamepad: GamePad

    private lateinit var drive: IDrive2

    override fun runOpMode() {

        gamepad = this.gamepad1
        hwManager = HardwareManager(hardwareMap)
        basicRobot = FourWheelRobot(hwManager)


        ftcGamepad = GamePad(gamepad, this)

        drive = MecDrive2(ftcGamepad, basicRobot)



        while(!(isStarted || isStopRequested))
        {
            idle()
        }

        while(opModeIsActive())
        {
            telemetry.addData("voltage", "%.1f volts") { getBatteryVoltage() }


            drive.manualDrive()
            drive.moveRobot()

        }


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