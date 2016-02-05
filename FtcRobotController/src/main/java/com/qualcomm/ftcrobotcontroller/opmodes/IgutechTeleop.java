package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DigitalChannelController;

/**
 * Created by The team.
 */
public class IgutechTeleop extends OpMode {

    DcMotor leftMotor, rightMotor, armMotor1, armMotor2, winch, brush; //define DC motors
    Servo armServo, climberServo, magicRelease, redFlipper, blueFlipper; //define servos
    String nameMode;

    double JoyThr, JoyYaw, rightPow, leftPow, armMovement, armscaling, offset;

    DcMotorDriver driver;

    double sloMo = 1;

    String team = "";

    boolean firstLoop = true;

    @Override
    public void init() {

        armMotor1 = hardwareMap.dcMotor.get("arm1");
        armMotor2 = hardwareMap.dcMotor.get("arm2");
        leftMotor = hardwareMap.dcMotor.get("left2");
        rightMotor = hardwareMap.dcMotor.get("right2");
        winch = hardwareMap.dcMotor.get("winch");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);


        armServo = hardwareMap.servo.get("armservo");
        climberServo = hardwareMap.servo.get("climber");

        magicRelease = hardwareMap.servo.get("magicRelease");
        redFlipper = hardwareMap.servo.get("redFlipper");
        blueFlipper = hardwareMap.servo.get("blueFlipper");

        climberServo.setPosition(.5);
        magicRelease.setPosition(.85);
        armServo.setPosition(.5);
        //redFlipper.setPosition(0);
        //blueFlipper.setPosition(0);
    }

    @Override
    public void loop() {
        colorChoosing(); //The currently unused servo control software
        unusedCode();    //Currently unused code that's commented out
        servoControls(); //Code which controls all the servos
        motorControls(); //Code which controls all the motors

    }

    public void servoControls()
    {
        //This is where all the servo controlling runs
        if(gamepad2.x) //move the servo that drops blocks from the arm
        {
            armServo.setPosition(1);
        }
        else if(gamepad2.b)
        {
            armServo.setPosition(0);
        }
        else
        {
            armServo.setPosition(.5);
        }

        if (gamepad2.dpad_down) { //Control climber release servo
            climberServo.setPosition(0);
        }
        if (gamepad2.dpad_up) {
            climberServo.setPosition(.7);
        }
        if (gamepad2.dpad_left || gamepad2.dpad_right) {
            climberServo.setPosition(.5);
        }
        if (gamepad1.a && gamepad2.a) { //Magic release servo
            magicRelease.setPosition(0);
        } else {
            magicRelease.setPosition(.85);
        }
        if (gamepad2.left_trigger >= .3) {
            redFlipper.setPosition(.5);
        } else {
            redFlipper.setPosition(.98);
        }
        if (gamepad2.right_trigger >= .3) {
            blueFlipper.setPosition(.5);
        } else {
            blueFlipper.setPosition(.98);
        }
    }

    public void motorControls() {
        //This is where the arm, winch, and brush movement is along with driving control
        JoyThr = -gamepad1.left_stick_y;
        JoyYaw = -gamepad1.right_stick_x;

        armMovement = -gamepad2.left_stick_y;

        if (-armMovement > 0) { //properly scale the arm
            armscaling = .3;
        } else if (-armMovement < 0) {
            armscaling = .3;
        }

        if (gamepad2.left_trigger > .9) { //activate the winch system
            winch.setPower(1);
        } else if (gamepad2.right_trigger > .9) {
            winch.setPower(-1);
        } else {
            winch.setPower(0);
        }
        //this is the code for the brushes
        if (gamepad2.left_bumper) {
            brush.setPower(1);//full forward and backwards are used
        } else if (gamepad2.right_bumper) {
            brush.setPower(-1);
        } else {
            brush.setPower(0);
        }

        /*
        ##########################################
        #      Below Code drives the robot       #
        #+----+----------------------------------+#
        #| 1. | Limit the throttle to 90% in     |#
        #|    | either direction                 |#
        #| 2. | Factor in yaw                    |#
        #| 3. | Scale back left and right powers |#
        #|    | so we don't violate the          |#
        #|    | 1 < x > -1 rule                  |#
        #| 4. | Factor in slo-mo setting         |#
        #+----+----------------------------------+#
        ##########################################
        */

        if (JoyThr > .90) {
            JoyThr = .90;
        } else if (JoyThr < -.90) {
            JoyThr = -.90;
        }


        rightPow = JoyThr + (JoyYaw * .5);
        leftPow = JoyThr + (-JoyYaw * .5);

        if (rightPow > 1) {
            leftPow -= (rightPow - 1.0);
            rightPow = 1.0;
        }
        if (leftPow > 1) {
            rightPow -= (leftPow - 1.0);
            leftPow = 1.0;
        }
        if (rightPow < -1) {
            leftPow += (-1.0 - rightPow);
            rightPow = -1.0;
        }
        if (leftPow < -1) {
            rightPow += (-1.0 - leftPow);
            leftPow = -1.0;
        }


        sloMo = 1 - gamepad1.right_trigger;

        telemetry.addData("Slow-Mo factor", sloMo); //Read the slo-mo value to the Telemetry
        if (sloMo <= .30) { //Limit the slo-mo factor to not pass 30%
            sloMo = .30;
        }

        rightPow = rightPow * sloMo;
        leftPow = leftPow * sloMo;

        armMotor1.setDirection(DcMotor.Direction.FORWARD);
        armMotor2.setDirection(DcMotor.Direction.REVERSE);

        armMotor1.setPower(armMovement * armscaling); //Write arm movements to the motors.
        armMotor2.setPower(armMovement * armscaling);

        leftMotor.setPower(leftPow); //Write driving movements to the motors.
        rightMotor.setPower(rightPow);

    }

    public void colorChoosing() {
        /*
        if (firstLoop) {
            while (!gamepad1.start) {
                telemetry.addData("Alliance", team);
                if (gamepad1.b) {
                    team = "red";
                } else if (gamepad1.x) {
                    team = "blue";
                }
            }
            firstLoop=false;
        }

        if (team.equals("red")) {
            redFlipper.setPosition(1);
        } else {
            blueFlipper.setPosition(1);
        }
        */
    }

    public void unusedCode() {

    }
}
