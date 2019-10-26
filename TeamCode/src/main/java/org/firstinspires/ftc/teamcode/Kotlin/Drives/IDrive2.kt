package org.firstinspires.ftc.teamcode.Kotlin.Drives

interface IDrive2 {
    fun setYaw(y: Double): Unit
    fun setAxial(a: Double) : Unit
    fun setLateral(l: Double) : Unit


    fun manualDrive() : Unit

    fun moveRobot() : Unit
    fun moveRobot(axial: Double, lateral: Double, yaw: Double)

    fun isBusy() : Boolean
    fun moveByInches(power: Double, inches: Double)
    fun moveByInches(power: Double, leftInches: Double, rightInches: Double)

    fun turnDegrees(direction: MecDrive2.TurnDirection, degrees: Int, power: Double)
    fun stop() : Unit
    fun initDrive() : Unit
}