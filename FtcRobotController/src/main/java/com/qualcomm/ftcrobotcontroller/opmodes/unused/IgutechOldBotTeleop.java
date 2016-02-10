package com.qualcomm.ftcrobotcontroller.opmodes.unused;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Kevin on 9/30/2015.
 */
public class IgutechOldBotTeleop extends OpMode {

    DcMotor leftmotor;
    DcMotor rightmotor;

    double JoyThr;
    double JoyYaw;

    double rightPow;
    double leftPow;

    boolean buttonstate;
    boolean modbuttonstate;
    int loopstate;

    @Override
    public void init() {
        leftmotor = hardwareMap.dcMotor.get("leftmotor");
        rightmotor = hardwareMap.dcMotor.get("rightmotor");

        loopstate = 1;
        modbuttonstate = false;
    }

    public void loop() {
        buttonstate = gamepad1.right_bumper;

        JoyThr = -gamepad1.left_stick_y;

        JoyYaw = -gamepad1.right_stick_x;

        if (JoyThr == 0) {
            rightPow = -JoyYaw;
            leftPow = JoyYaw;
        } else if (JoyYaw == 0) {
            rightPow = JoyThr;
            leftPow = JoyThr;
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

        if (loopstate == 1) {
            if (buttonstate == true) {
                loopstate = 2;
                modbuttonstate = true;
            }
        } else if (loopstate == 2) {
            if (buttonstate == false) {
                loopstate = 3;
            }
        } else if (loopstate == 3) {
            if (buttonstate == true) {
                loopstate = 4;
                modbuttonstate = false;
            }
        } else if (loopstate == 4) {
            if (buttonstate == false) {
                loopstate = 1;
            }
        }

        leftmotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        rightmotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        leftmotor.setPower(-rightPow);
        rightmotor.setPower(leftPow);

        telemetry.addData("loopstate", loopstate);

        try {
            Thread.sleep(10);
        } catch (Exception e) {

        }
    }
}
