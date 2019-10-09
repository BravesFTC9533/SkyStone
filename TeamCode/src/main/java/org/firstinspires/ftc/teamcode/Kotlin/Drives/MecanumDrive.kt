package org.firstinspires.ftc.teamcode.Kotlin.Drives

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.teamcode.drive.IDrive
import org.firstinspires.ftc.teamcode.Kotlin.GamePad

class MecanumDrive

(private val hardwareMap: HardwareMap, private val driverGamepad: GamePad) : IDrive {


    var fl: DcMotorEx
    var fr: DcMotorEx
    var bl: DcMotorEx
    var br: DcMotorEx
    var isReverse = false

    init {

        fl = createMotor("fl", false)
        fr = createMotor("fr", true)
        bl = createMotor("bl", false)
        br = createMotor("br", true)
    }

    private fun createMotor(deviceName: String, setReverse: Boolean): DcMotorEx {
        val motor = hardwareMap.get(DcMotorEx::class.java, deviceName)
        motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        if (setReverse) {
            motor.direction = DcMotorSimple.Direction.REVERSE
        }
        return motor
    }


    override fun getIsReverse(): Boolean {
        return isReverse
    }

    override fun setIsReverse(value: Boolean) {
        isReverse = value
    }

    override fun handle() {

        //mechDrive.Drive(-gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

        var h: Double
        var v: Double
        var r: Double

        h = -driverGamepad.getLeftStickX()
        v = driverGamepad.getLeftStickY()
        r = -driverGamepad.getRightStickX()

        if (Math.abs(h) < MIN_SPEED) {
            h = 0.0
        }
        if (Math.abs(v) < MIN_SPEED) {
            v = 0.0
        }
        if (Math.abs(r) < MIN_SPEED) {
            r = 0.0
        }

        if (isReverse) {
            h *= -1.0
            v *= -1.0
        }


        h = clipMotorPower(h)
        v = clipMotorPower(v)
        r = clipMotorPower(r)

        // add vectors
        var frontLeft = v - h + r
        var frontRight = v + h - r
        var backRight = v - h - r
        var backLeft = v + h + r

        // since adding vectors can go over 1, figure out max to scale other wheels
        val max = Math.max(
                Math.abs(backLeft),
                Math.max(
                        Math.abs(backRight),
                        Math.max(
                                Math.abs(frontLeft), Math.abs(frontRight)
                        )
                )
        )
        // only need to scale power if max > 1
        if (max > 1) {
            frontLeft = scalePower(frontLeft, max)
            frontRight = scalePower(frontRight, max)
            backLeft = scalePower(backLeft, max)
            backRight = scalePower(backRight, max)
        }

        fl.power = frontLeft
        fr.power = frontRight
        bl.power = backLeft
        br.power = backRight

        //robot.Drive(frontLeft, frontRight, backLeft, backRight);


    }

    override fun drive(ly: Double, lx: Double, rx: Double) {

    }

    override fun drive(left: Double, right: Double) {

    }

    override fun stop() {

    }

    override fun setMode(runMode: DcMotor.RunMode) {
        fl!!.mode = runMode
        fr!!.mode = runMode
        bl!!.mode = runMode
        br!!.mode = runMode
    }

    override fun driveToPosition(leftPosition: Int, rightPosition: Int) {

    }

    companion object {
        //private final Robot robot;

        private val MIN_SPEED = 0.2


        // Scale motor power based on the max for all wheels
        // 1, 1, 1, 3 will become .33, .33, .33, 1
        fun scalePower(value: Double, max: Double): Double {
            return if (max == 0.0) {
                0.0
            } else value / max
        }

        // motor power clipping helper
        fun clipMotorPower(value: Double): Double {
            return Range.clip(value, -1.0, 1.0)
        }

        fun scale(power: Double): Double {
            var modifier = 1

            if (power == 0.0) {
                return 0.0
            }

            if (power < 0) {
                modifier *= -1
            }

            return power * power * modifier.toDouble()
        }
    }
}
