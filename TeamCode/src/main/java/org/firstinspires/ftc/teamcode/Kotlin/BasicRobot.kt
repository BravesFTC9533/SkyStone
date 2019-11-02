package org.firstinspires.ftc.teamcode.Kotlin

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.common.HardwareManager

class FourWheelRobot {


    val hwManager: HardwareManager

    var motorBL: DcMotorEx
    var motorBR: DcMotorEx

    var motorFL: DcMotorEx
    var motorFR: DcMotorEx



    constructor(hwManager: HardwareManager){
        this.hwManager = hwManager

        this.motorBL = hwManager.getMotor("bl")
        this.motorBL.direction = DcMotorSimple.Direction.REVERSE

        this.motorFL = hwManager.getMotor("fl")
        this.motorFL.direction= DcMotorSimple.Direction.REVERSE

        this.motorFR = hwManager.getMotor("fr")
        this.motorFR.direction = DcMotorSimple.Direction.FORWARD

        this.motorBR = hwManager.getMotor("br")
        this.motorBR.direction = DcMotorSimple.Direction.FORWARD


    }


    fun setMode(mode: DcMotor.RunMode) {
        this.motorBL.mode = mode
        this.motorBR.mode = mode
        this.motorFL.mode = mode
        this.motorFR.mode = mode
    }

}