package org.firstinspires.ftc.teamcode.Kotlin

public interface IButtonHandler {
    fun gamepadButtonEvent(gamePad: GamePad, button: Int, pressed: Boolean);
}