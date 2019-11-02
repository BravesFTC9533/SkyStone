package org.firstinspires.ftc.teamcode.Kotlin.Drives

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.teamcode.Kotlin.FourWheelRobot
import org.firstinspires.ftc.teamcode.Kotlin.GamePad
import org.firstinspires.ftc.teamcode.Robot
import kotlin.math.absoluteValue

class MecDrive2 : IDrive2 {

    enum class TurnDirection {
        /** Robot will turn in a CLOCKWISE direction
         */
        CLOCKWISE,
        /** Robot wll turn in a Counter Clockwise Direction
         */
        COUNTER_CLOCKWISE
    }

    var yaw: Double? = null
    var axial: Double? = null
    var lateral: Double? = null

    var robot: FourWheelRobot
    var gamePad: GamePad?


    constructor(gamePad: GamePad?, robot: FourWheelRobot)
    {
        this.gamePad = gamePad
        this.robot = robot
    }

    override fun setYaw(y: Double) {
        this.yaw = clip(y)
    }
    override fun setAxial(a: Double) {
        this.axial = clip(a) * -1
    }
    override fun setLateral(l: Double) {
        this.lateral = clip(l)
    }

    override fun manualDrive() {
        setAxial(-gamePad!!.getLeftStickY())
        setLateral(gamePad!!.getLeftStickX())
        setYaw(-gamePad!!.getRightStickX())
    }

    override fun moveRobot(axial: Double, lateral: Double, yaw: Double) {
        setYaw(yaw)
        setLateral(lateral)
        setAxial(axial)
        moveRobot()
    }
    override fun moveRobot() {


        var v = clip(axial?:0.0)
        var h = clip(lateral?:0.0)
        var r = clip(yaw?:0.0)

        // add vectors
        var frontLeft = v - h + r
        var frontRight = v + h - r
        var backRight = v - h - r
        var backLeft = v + h + r


        var max = getMax(frontLeft, frontRight, backLeft, backRight)

        if(max>1) {
            frontLeft /= max
            frontRight /= max
            backLeft /= max
            backRight /= max
        }

        robot.motorBL.power = backLeft
        robot.motorBR.power = backRight
        robot.motorFL.power = frontLeft
        robot.motorFR.power = frontRight


    }

    fun setPower(power: Double) {
        robot.motorBL.power = power
        robot.motorBR.power = power
        robot.motorFL.power = power
        robot.motorFR.power = power
    }

    override fun stop() {
        robot.motorFR.power = 0.0
        robot.motorBR.power = 0.0
        robot.motorFL.power = 0.0
        robot.motorFR.power = 0.0

    }

    fun getMax(fl: Double, fr: Double, bl: Double, br: Double) : Double{
        var list = listOf(
                fl.absoluteValue,
                fr.absoluteValue,
                bl.absoluteValue,
                br.absoluteValue)

        return list.max()?:0.0
    }


    override fun turnDegrees(direction: TurnDirection, degrees: Int, power: Double) {
        var degrees = degrees
        val inchesPerDegree = Robot.WHEEL_DISTANCE_INCHES / 90 // Find how many inches are in a degree
        degrees *= inchesPerDegree.toInt()

        if (direction == TurnDirection.COUNTER_CLOCKWISE) {
            degrees = -degrees
        }

        moveByInches(power, degrees.toDouble(), (-degrees).toDouble())
    }

    override fun moveByInches(power: Double, inches: Double) {
        moveByInches(power, inches, inches)
    }

    override fun moveByInches(power: Double, leftInches: Double, rightInches: Double) {
        encoderDrive(
                power,
                (Robot.COUNTS_PER_INCH* leftInches).toInt(),
                (Robot.COUNTS_PER_INCH.toInt() * rightInches).toInt()
        )
    }
    private fun encoderDrive(targetSpeed: Double, leftTicks: Int, rightTicks: Int) {

        robot.motorBL.targetPosition += leftTicks
        robot.motorFL.targetPosition += leftTicks

        robot.motorBR.targetPosition += rightTicks
        robot.motorFR.targetPosition += rightTicks


        robot.setMode(DcMotor.RunMode.RUN_TO_POSITION)

        setPower(targetSpeed)

        while(isBusy()) {}
        stop()

    }

    override fun isBusy(): Boolean {
        return (robot.motorFL.isBusy() || robot.motorFR.isBusy() || robot.motorBR.isBusy() || robot.motorBL.isBusy())
    }

    override fun initDrive() {


    }

    private fun clip(p: Double):Double {
        return Range.clip(p, -1.0, 1.0)
    }
}
