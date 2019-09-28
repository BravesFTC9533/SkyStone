package org.firstinspires.ftc.teamcode.Kotlin.Drives

interface IDrive2 {
    fun setYaw(y: Double): Unit
    fun setAxial(a: Double) : Unit
    fun setLateral(l: Double) : Unit


    fun manualDrive() : Unit

    fun moveRobot() : Unit
    fun moveRobot(axial: Double, lateral: Double, yaw: Double)

    fun initDrive() : Unit
}