package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
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

    //boolean wheelsUp;
    //boolean wheelsDown;


    @Override
    public void init() {

        changeMotor = hardwareMap.dcMotor.get("worm1");


        driver = new DcMotorDriver(hardwareMap, true);

        DcMotorController leftMotorController;
        DcMotorController rightMotorController;
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            changeMotor.setPower(1);
        } else if (gamepad1.b) {
            changeMotor.setPower(-1);
        }

        JoyThr = gamepad1.left_stick_y;
        JoyYaw = -gamepad1.right_stick_x;

        if (JoyThr > .90) {
            JoyThr = .90;
        } else if (JoyThr < -.90) {
            JoyThr = -.90;
        }

        if (JoyThr == 0) {
            rightPow = JoyYaw;
            leftPow = -JoyYaw;
        } else if (JoyYaw == 0) {
            rightPow = -JoyThr;
            leftPow = -JoyThr;
        } else if (JoyYaw < 0 && JoyYaw >= -1) {
            leftPow = ((2 * JoyYaw) + 1) * JoyThr;
            rightPow = JoyThr;
            //Joyyaw will be -1 through 0
            //if the joyyaw = -0.5,  0
            //if the joyyaw = -0.75, -.5
            //if the joyyaw = -1.0,  -1
        } else if (JoyYaw > 0 && JoyYaw <= 1) {
            rightPow = ((-2 * JoyYaw) + 1) * JoyThr;
            leftPow = JoyThr;
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

