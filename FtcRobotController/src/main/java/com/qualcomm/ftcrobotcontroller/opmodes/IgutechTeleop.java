package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Logan on 11/13/2015.
 */
public class IgutechTeleop extends OpMode {

    DcMotor leftMotor1;
    DcMotor leftMotor2;
    DcMotor rightMotor1;
    DcMotor rightMotor2;
    DcMotor changeMotor;
    //TouchSensor wheelsOut;
    //TouchSensor wheelsIn;

    Servo armServo;

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

    DcMotorDriver driver;

    double sloMo = 1;

    double offset;

    //boolean wheelsUp;
    //boolean wheelsDown;


    @Override
    public void init() {

        changeMotor = hardwareMap.dcMotor.get("worm1");

        armServo = hardwareMap.servo.get("armservo");
        armServo.scaleRange(0,1);


        driver = new DcMotorDriver(hardwareMap, true);

        DcMotorController leftMotorController;
        DcMotorController rightMotorController;

        armServo.setPosition(0);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            changeMotor.setPower(1);
        } else if (gamepad1.b) {
            changeMotor.setPower(-1);
        }

        if (gamepad1.x) { //move the servo to "1"
            armServo.setPosition(1);
        } else { //move the servo to "0"
            armServo.setPosition(0);
        }

        JoyThr = -gamepad1.left_stick_y;
        JoyYaw = -gamepad1.right_stick_x;

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
            leftPow += (rightPow + 1.0);
            rightPow = -1.0;
        }
        if (leftPow < -1) {
            rightPow += (leftPow + 1.0);
            leftPow = -1.0;
        }



        sloMo = 1 - gamepad1.right_trigger;

        telemetry.addData("Slow-Mo factor", sloMo);

        if (sloMo <= .30) {
            sloMo = .30;
        }

        rightPow = rightPow * sloMo;
        leftPow = leftPow * sloMo;

        driver.driveLeftTrain(leftPow);

        driver.driveRightTrain(rightPow);
        //roboStatus();

        //telemetry


        try {
            Thread.sleep(3);
        } catch (Exception e) {
        }
    }
}

