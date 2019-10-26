package org.firstinspires.ftc.teamcode.Kotlin

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.ClassFactory
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix
import org.firstinspires.ftc.robotcore.external.navigation.*
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable
import org.firstinspires.ftc.teamcode.Kotlin.Drives.IDrive2
import kotlin.math.absoluteValue
import kotlin.math.asin
import kotlin.math.hypot


class Robot_Navigation {

    private val mmPerInch = 25.4f
    private val mmTargetHeight = 6 * mmPerInch          // the height of the center of the target image above the floor

    private val stoneZ = 2.00f * mmPerInch

    // Constants for the center support targets
    private val bridgeZ = 6.42f * mmPerInch
    private val bridgeY = 23 * mmPerInch
    private val bridgeX = 5.18f * mmPerInch
    private val bridgeRotY = 59f                                 // Units are degrees
    private val bridgeRotZ = 180f

    // Constants for perimeter targets
    private val halfField = 72 * mmPerInch
    private val quadField = 36 * mmPerInch

    private var phoneXRotate = 0f
    private var phoneYRotate = 0f
    private val phoneZRotate = 0f

    lateinit var targets : VuforiaTrackables

    val max_targets = 13

    val yaw_gain: Double = 0.018
    val lateral_gain: Double = 0.0027
    val axial_gain: Double = 0.0017

    val on_axis: Double = 10.0
    val close_enough: Double = 20.0


    var targetFound = false
    var targetName:String? = null
    var robotX=0.0
    var robotY=0.0
    var robotBearing=0.0
    var targetRange=0.0
    var targetBearing=0.0
    var relativeBearing=0.0

    val camera_choice: VuforiaLocalizer.CameraDirection = VuforiaLocalizer.CameraDirection.BACK
    private val PHONE_IS_PORTRAIT = false

    lateinit var robot: FourWheelRobot
    lateinit var drive: IDrive2

    fun initVuforia(drive: IDrive2, hardwareMap: HardwareMap) {
        this.drive = drive


        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.packageName)
        var parameters = VuforiaLocalizer.Parameters(cameraMonitorViewId)
        parameters.vuforiaLicenseKey = "AeWceoD/////AAAAGWvk7AQGLUiTsyU4mSW7gfldjSCDQHX76lt9iPO5D8zaboG428r" +
                "dS9WN0+AFpAlc/g4McLRAQIb5+ijFCPJJkLc+ynXYdhljdI2k9R4KL8t3MYk/tbmQ75st9VI7//2vNkp0JHV6oy4HXltxVFcEbtBYeT" +
                "BJ9CFbMW+0cMNhLBPwHV7RYeNPZRgxf27J0oO8VoHOlj70OYdNYos5wvDM+ZbfWrOad/cpo4qbAw5iB95T5I9D2/KRf1HQHygtDl8/O" +
                "tDFlOfqK6v2PTvnEbNnW1aW3vPglGXknX+rm0k8b0S7GFJkgl7SLq/HFNl0VEIVJGVQe9wt9PB6bJuxOMMxN4asy4rW5PRRBqasSM7O" +
                "Ll4W"


        parameters.cameraDirection = camera_choice
        parameters.useExtendedTracking = false
        //var vuforia:VuforiaLocalizer = ClassFactory.createVuforiaLocalizer()

        var vuforia = ClassFactory.getInstance().createVuforia(parameters)

        targets = vuforia.loadTrackablesFromAsset("Skystone")


        val stoneTarget = targets[0]
        stoneTarget.name = "Stone Target"
        val blueRearBridge = targets[1]
        blueRearBridge.name = "Blue Rear Bridge"
        val redRearBridge = targets[2]
        redRearBridge.name = "Red Rear Bridge"
        val redFrontBridge = targets[3]
        redFrontBridge.name = "Red Front Bridge"
        val blueFrontBridge = targets[4]
        blueFrontBridge.name = "Blue Front Bridge"
        val red1 = targets[5]
        red1.name = "Red Perimeter 1"
        val red2 = targets[6]
        red2.name = "Red Perimeter 2"
        val front1 = targets[7]
        front1.name = "Front Perimeter 1"
        val front2 = targets[8]
        front2.name = "Front Perimeter 2"
        val blue1 = targets[9]
        blue1.name = "Blue Perimeter 1"
        val blue2 = targets[10]
        blue2.name = "Blue Perimeter 2"
        val rear1 = targets[11]
        rear1.name = "Rear Perimeter 1"
        val rear2 = targets[12]
        rear2.name = "Rear Perimeter 2"

        /** For convenience, gather together all the trackable objects in one easily-iterable collection */
        val allTrackables = ArrayList<VuforiaTrackable>()
        allTrackables.addAll(targets)

        /**
         * In order for localization to work, we need to tell the system where each target is on the field, and
         * where the phone resides on the robot.  These specifications are in the form of <em>transformation matrices.</em>
         * Transformation matrices are a central, important concept in the math here involved in localization.
         * See <a href="https://en.wikipedia.org/wiki/Transformation_matrix">Transformation Matrix</a>
         * for detailed information. Commonly, you'll encounter transformation matrices as instances
         * of the {@link OpenGLMatrix} class.
         *
         * If you are standing in the Red Alliance Station looking towards the center of the field,
         *     - The X axis runs from your left to the right. (positive from the center to the right)
         *     - The Y axis runs from the Red Alliance Station towards the other side of the field
         *       where the Blue Alliance Station is. (Positive is from the center, towards the BlueAlliance station)
         *     - The Z axis runs from the floor, upwards towards the ceiling.  (Positive is above the floor)
         *
         * Before being transformed, each target image is conceptually located at the origin of the field's
         *  coordinate system (the center of the field), facing up.
         */

        // Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
        // Rotated it to to face forward, and raised it to sit on the ground correctly.
        // This can be used for generic target-centric approach algorithms
        stoneTarget.location = OpenGLMatrix
                .translation(0f, 0f, stoneZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 90f, 0f, -90f))

        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge.location = OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 0f, bridgeRotY, bridgeRotZ))

        blueRearBridge.location = OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 0f, -bridgeRotY, bridgeRotZ))

        redFrontBridge.location = OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 0f, -bridgeRotY, 0f))

        redRearBridge.location = OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 0f, bridgeRotY, 0f))

        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.location = OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90f, 0f, 180f))

        red2.location = OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90f, 0f, 180f))

        front1.location = OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90f, 0f, 90f))

        front2.location = OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90f, 0f, 90f))

        blue1.location = OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90f, 0f, 0f))

        blue2.location = OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90f, 0f, 0f))

        rear1.location = OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90f, 0f, -90f))

        rear2.location = OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, 90f, 0f, -90f))


        //
        // Create a transformation matrix describing where the phone is on the robot.
        //
        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
        // Lock it into Portrait for these numbers to work.
        //
        // Info:  The coordinate frame for the robot looks the same as the field.
        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
        //
        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
        // pointing to the LEFT side of the Robot.
        // The two examples below assume that the camera is facing forward out the front of the robot.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (camera_choice == VuforiaLocalizer.CameraDirection.BACK) {
            phoneYRotate = -90f
        } else {
            phoneYRotate = 90f
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90f
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        val CAMERA_FORWARD_DISPLACEMENT = 4.0f * mmPerInch   // eg: Camera is 4 Inches in front of robot center
        val CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch   // eg: Camera is 8 Inches above ground
        val CAMERA_LEFT_DISPLACEMENT = 0f     // eg: Camera is ON the robot's center line


    }

    fun targetsAreVisible() : Boolean {
        var targetTestID = 0
        while((targetTestID < max_targets) && !targetIsVisible(targetTestID)) {
            targetTestID++
        }
        return targetFound
    }

    fun targetIsVisible(targetId: Int) : Boolean {
        val target = targets[targetId]
        val listener = target.listener as VuforiaTrackableDefaultListener
        var location: OpenGLMatrix? = null

        if( (target!=null) && (listener!=null) && listener.isVisible) {
            targetFound = true
            targetName = target.name

            location = listener.updatedRobotLocation
            if(location!=null) {

                var trans = location.translation
                var rot = Orientation.getOrientation(location, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES)

                robotX = trans[0].toDouble()
                robotY = trans[1].toDouble()

                robotBearing = rot.thirdAngle.toDouble()

                // target range is based on distance from robot position to origin.
                targetRange = hypot(robotX, robotY)

                // target bearing is based on angle formed between the X axis to the target range line
                targetBearing = Math.toDegrees(-asin(robotY / targetRange))

                // Target relative bearing is the target Heading relative to the direction the robot is pointing.
                relativeBearing = targetBearing - robotBearing
            }
            targetFound = true
        }
        else {
            targetFound = false
            targetName = "None"
        }
        return targetFound
    }

    fun activateTracking() : Unit {
        targets?.activate()
    }

    fun deactivateTracking() : Unit {
        targets?.deactivate()
    }




    fun cruseControl(standOffdistance: Double):  Boolean {
        var closeEnough = false

        // Priority #1 Rotate to always be pointing at the target (for best target retention).
        val y = relativeBearing * yaw_gain

        // Priority #2  Drive laterally based on distance from X axis (same as y value)
        val l = robotY * lateral_gain

        // Priority #3 Drive forward based on the desiredHeading target standoff distance
        val a = -(robotX + standOffdistance) * axial_gain

        // Send the desired axis motions to the robot hardware.
        drive.setYaw(y)
        drive.setAxial(a)
        drive.setLateral(l)

        // Determine if we are close enough to the target for action.
        closeEnough = (robotX + standOffdistance).absoluteValue < close_enough && (robotY.absoluteValue) < on_axis


        return closeEnough
    }



}