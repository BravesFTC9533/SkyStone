package org.firstinspires.ftc.teamcode.Kotlin

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.Common.IDrive
import org.firstinspires.ftc.teamcode.Kotlin.Drives.MecanumDrive
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode as LinearOpMode

@TeleOp(name = "Concept: Telemetry", group = "Concept")
class BasicKotlinOp : LinearOpMode(), IButtonHandler {


    private lateinit var basicRobot: BasicRobot
    private lateinit var gamepad: Gamepad

    private lateinit var ftcGamepad: GamePad

    private lateinit var drive:IDrive

    override fun runOpMode() {

        basicRobot = BasicRobot(hardwareMap)
        gamepad = this.gamepad1

        ftcGamepad = GamePad(gamepad, this)

        drive = MecanumDrive(hardwareMap, ftcGamepad)

        while(!(isStarted || isStopRequested))
        {
            idle()
        }

        while(opModeIsActive())
        {
            telemetry.addData("voltage", "%.1f volts") { getBatteryVoltage() }

            drive.handle()
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