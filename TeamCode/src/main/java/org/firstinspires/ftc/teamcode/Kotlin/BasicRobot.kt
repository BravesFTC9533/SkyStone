package org.firstinspires.ftc.teamcode.Kotlin

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareMap

class BasicRobot {

    var hwMap: HardwareMap

    var motorBL: DcMotorEx
    var motorBR: DcMotorEx

    var motorFL: DcMotorEx
    var motorFR: DcMotorEx



    constructor(hwMap: HardwareMap){
        this.hwMap = hwMap

        this.motorBL = getMotor("motorBL")
        this.motorBR = getMotor("motorBR")

        this.motorFL = getMotor("motorFL")
        this.motorFR = getMotor("motorFR")


    }


    private fun getMotor(deviceName: String) : DcMotorEx {
        return hwMap.get(DcMotorEx::class.java, deviceName)
    }

}