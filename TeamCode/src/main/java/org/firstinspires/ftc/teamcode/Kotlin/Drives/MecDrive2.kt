package org.firstinspires.ftc.teamcode.Kotlin.Drives

import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.teamcode.Kotlin.FourWheelRobot
import org.firstinspires.ftc.teamcode.Kotlin.GamePad
import kotlin.math.absoluteValue

class MecDrive2 : IDrive2 {



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
        this.axial = clip(a)
    }
    override fun setLateral(l: Double) {
        this.lateral = clip(l)
    }

    override fun manualDrive() {
        setAxial(gamePad!!.getLeftStickY())
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

        var x = 0.0
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

    fun getMax(fl: Double, fr: Double, bl: Double, br: Double) : Double{
        var list = listOf(
                fl.absoluteValue,
                fr.absoluteValue,
                bl.absoluteValue,
                br.absoluteValue)

        return list.max()?:0.0
    }

    override fun initDrive() {


    }

    private fun clip(p: Double):Double {
        return Range.clip(p, -1.0, 1.0)
    }
}
