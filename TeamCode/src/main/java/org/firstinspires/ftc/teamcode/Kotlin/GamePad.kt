package org.firstinspires.ftc.teamcode.Kotlin
import com.qualcomm.robotcore.hardware.Gamepad


public class GamePad {

    var prev_buttons: Int = 0

    val BUTTON_A:Int            = (1 shl  0)
    val BUTTON_B:Int            = (1 shl  1)
    val BUTTON_X:Int            = (1 shl  2)
    val BUTTON_Y:Int            = (1 shl  3)
    val BUTTON_BACK:Int         = (1 shl  4)
    val BUTTON_START:Int        = (1 shl  5)
    val BUTTON_LBUMPER:Int      = (1 shl  6)
    val BUTTON_RBUMPER:Int      = (1 shl  7)
    val BUTTON_LSTICK_BTN:Int   = (1 shl  8)
    val BUTTON_RSTICK_BTN:Int   = (1 shl  9)
    val BUTTON_DPAD_LEFT:Int    = (1 shl 10)
    val BUTTON_DPAD_RIGHT:Int   = (1 shl 11)
    val BUTTON_DPAD_UP:Int      = (1 shl 12)
    val BUTTON_DPAD_DOWN:Int    = (1 shl 13)

    var buttonHandler: IButtonHandler;


    var gamepad:Gamepad
    constructor(gamepad: Gamepad, buttonHandler: IButtonHandler) {
        this.gamepad = gamepad

        this.buttonHandler = buttonHandler

        this.prev_buttons = getButtonsPressed()

    }

    fun getLeftStickX() : Double {
        return this.gamepad.left_stick_x.toDouble()
    }
    fun getLeftStickY() : Double {
        return this.gamepad.left_stick_y.toDouble()
    }

    fun getRightStickX() : Double {
        return this.gamepad.right_stick_x.toDouble()
    }
    fun getRightStickY() : Double {
        return this.gamepad.right_stick_y.toDouble()
    }

    fun getButtonsPressed() : Int {
        var buttons = 0
        //buttons = buttons or (gamepad.a == true ? BUTTON_A : 0)
        if (gamepad.a) buttons = buttons or BUTTON_A
        if (gamepad.b) buttons = buttons or BUTTON_B
        if (gamepad.x) buttons = buttons or BUTTON_X
        if (gamepad.y) buttons = buttons or BUTTON_Y
        if (gamepad.back) buttons = buttons or BUTTON_BACK
        if (gamepad.start) buttons = buttons or BUTTON_START
        if (gamepad.left_bumper) buttons = buttons or BUTTON_LBUMPER
        if (gamepad.right_bumper) buttons = buttons or BUTTON_RBUMPER
        if (gamepad.left_stick_button) buttons = buttons or BUTTON_LSTICK_BTN
        if (gamepad.right_stick_button) buttons = buttons or BUTTON_RSTICK_BTN
        if (gamepad.dpad_left) buttons = buttons or BUTTON_DPAD_LEFT
        if (gamepad.dpad_right) buttons = buttons or BUTTON_DPAD_RIGHT
        if (gamepad.dpad_up) buttons = buttons or BUTTON_DPAD_UP
        if (gamepad.dpad_down) buttons = buttons or BUTTON_DPAD_DOWN
        return buttons
    }

    fun update() {
        var currentButtons = getButtonsPressed()

        //only care if changed from previous
        var changed = currentButtons xor prev_buttons


        var buttonMask = 0

        while (changed != 0) {
            //
            // buttonMask contains the least significant set bit.
            //
            buttonMask = changed and (changed xor -changed).inv()
            if (currentButtons and buttonMask != 0) {
                //
                // Button is pressed.
                //

                buttonHandler.gamepadButtonEvent(this, buttonMask, true)
            } else {
                //
                // Button is released.
                //

                buttonHandler.gamepadButtonEvent(this, buttonMask, true)
            }
            //
            // Clear the least significant set bit.
            //
            changed = changed and buttonMask.inv()
        }

        prev_buttons = currentButtons
    }


}