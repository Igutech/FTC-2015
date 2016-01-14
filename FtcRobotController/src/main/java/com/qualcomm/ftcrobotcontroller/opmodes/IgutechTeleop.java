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
 * Created by Logan on 11/13/2015.
 */
public class IgutechTeleop extends OpMode {

    DeviceInterfaceModule DIM;
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor changeMotor;
    //TouchSensor wheelsOut;
    //TouchSensor wheelsIn;

    Servo armServo;
    Servo climberServo;

    int robotMode = 0;
    String nameMode;
    /*
    0 - regular driving
    1 - transitioning to tracks
    2 - Low mountain mode
    3 - High mountain mode
    4 - Pullup mode
    5 - Tumble mode (we don't want this!)
     */

    double JoyThr;
    double JoyYaw;

    double rightPow;
    double leftPow;

    DcMotor armMotor1;
    DcMotor armMotor2;

    DcMotorDriver driver;

    double armMovement;

    double sloMo = 1;

    double offset;

    double armscaling;


    //boolean wheelsUp;
    //boolean wheelsDown;


    @Override
    public void init() {

        changeMotor = hardwareMap.dcMotor.get("worm1");
        changeMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        armMotor1 = hardwareMap.dcMotor.get("arm1");
        armMotor2 = hardwareMap.dcMotor.get("arm2");
        leftMotor = hardwareMap.dcMotor.get("left2");
        rightMotor = hardwareMap.dcMotor.get("right2");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        //DIM = hardwareMap.deviceInterfaceModule.get("dim");

        armServo = hardwareMap.servo.get("armservo");
        climberServo = hardwareMap.servo.get("climber");
        armServo.setDirection(Servo.Direction.FORWARD);

        DcMotorController leftMotorController;
        DcMotorController rightMotorController;

        //DIM.setDigitalChannelMode(0, DigitalChannelController.Mode.OUTPUT);
        //DIM.setDigitalChannelMode(1, DigitalChannelController.Mode.OUTPUT);

        climberServo.setPosition(.7);

        armServo.setPosition(.5);

    }

    @Override
    public void loop() {

        if (gamepad2.left_bumper) {
            changeMotor.setPower(1);
        } else if (gamepad2.right_bumper) {
            changeMotor.setPower(-1);
        } else {
            changeMotor.setPower(0);
        }

        if (gamepad2.a) {
            climberServo.setPosition(.2);
        }
        if (gamepad2.y) {
            climberServo.setPosition(1);
        }

        /*if (gamepad2.a) {
            climberServo.setPosition(1);
        } else {
            climberServo.setPosition(.7);
        }*/



        JoyThr = -gamepad1.left_stick_y;
        JoyYaw = -gamepad1.right_stick_x;

        armMovement = -gamepad2.left_stick_y;

        if(-armMovement > 0) {
            armscaling = .3;
        } else if(-armMovement < 0) {
            armscaling = .3;
        }

        /*if(gamepad2.right_trigger > .3)
        {
            RightFlipper.setPosition(1);
        }
        else if(gamepad2.right_trigger < .3)
        {
            RightFlipper.setPosition(0);
        }

        if(gamepad2.left_trigger > .3)
        {
            LeftFlipper.setPosition(1);
        }
        else if(gamepad2.left_trigger < .3)
        {
            LeftFlipper.setPosition(0);
        }*/

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

        if(gamepad2.x)
        {
            armServo.setPosition(1);
            //DIM.setDigitalChannelState(0, true);
            //DIM.setDigitalChannelState(1, false);
        }
        else if(gamepad2.b)
        {
            armServo.setPosition(0);
            //DIM.setDigitalChannelState(0, false);
            //DIM.setDigitalChannelState(1, true);
        }
        else
        {
            armServo.setPosition(.5);
            //DIM.setDigitalChannelState(0, false);
            //DIM.setDigitalChannelState(1, false);
        }

        sloMo = 1 - gamepad1.right_trigger;

        telemetry.addData("Slow-Mo factor", sloMo);

        if (sloMo <= .30) {
            sloMo = .30;
        }

        rightPow = rightPow * sloMo;
        leftPow = leftPow * sloMo;

        armMotor1.setDirection(DcMotor.Direction.FORWARD);
        armMotor2.setDirection(DcMotor.Direction.REVERSE);

        armMotor1.setPower(armMovement*armscaling);
        armMotor2.setPower(armMovement*armscaling);

        leftMotor.setPower(leftPow);
        rightMotor.setPower(rightPow);
        //roboStatus();

        //telemetry

        //try {
        //    Thread.sleep(3);
        //} catch (Exception e) {
        //}
    }
}
