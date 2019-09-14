package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode as LinearOpMode

@TeleOp(name = "Concept: Telemetry", group = "Concept")
class BasicKotlinOp : LinearOpMode(), IButtonHandler {

    override fun gamepadButtonEvent(gamePad: GamePad, button: Int, pressed: Boolean) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private lateinit var basicRobot: BasicRobot
    private lateinit var gamepad: Gamepad


    override fun runOpMode() {

        basicRobot = BasicRobot(hardwareMap)


        gamepad = this.gamepad1

        while(!(isStarted || isStopRequested))
        {
            idle()
        }

        while(opModeIsActive())
        {
            telemetry.addData("voltage", "%.1f volts") { getBatteryVoltage() }

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



}