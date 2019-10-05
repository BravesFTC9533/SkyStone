package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class BaseController {

    protected HardwareMap hardwareMap;

    public BaseController(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public abstract void gamepadButtonEvent(FtcGamePad gamepad, int button, boolean pressed);
}
