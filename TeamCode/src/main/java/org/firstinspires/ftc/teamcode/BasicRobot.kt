package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareMap

class BasicRobot {

    var hwMap: HardwareMap

    var motorLeft: DcMotorEx
    var motorRight: DcMotorEx



    constructor(hwMap: HardwareMap){
        this.hwMap = hwMap

        this.motorLeft = getMotor("motorLeft")
        this.motorRight = getMotor("motorRight")


    }


    private fun getMotor(deviceName: String) : DcMotorEx {
        return hwMap.get(DcMotorEx::class.java, deviceName);
    }

}