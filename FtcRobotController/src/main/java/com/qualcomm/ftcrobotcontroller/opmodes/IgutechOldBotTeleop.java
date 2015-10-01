package com.qualcomm.ftcrobotcontroller.opmodes;

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
    @Override
    public void init() {
        leftmotor = hardwareMap.dcMotor.get("leftmotor");
        rightmotor = hardwareMap.dcMotor.get("rightmotor");
    }
    public void loop() {
        JoyThr = -gamepad1.left_stick_y;

        JoyYaw = gamepad1.right_stick_x;

        if(JoyYaw==0)
        {
            rightPow = JoyThr;
            leftPow = JoyThr;
        }
        if(JoyYaw<0&&JoyYaw>=-1)
        {
            leftPow = ((2*JoyYaw)+1)*JoyThr;
            rightPow = JoyThr;
            //Joyyaw will be -1 through 0
            //if the joyyaw = -0.5,  0
            //if the joyyaw = -0.75, -.5
            //if the joyyaw = -1.0,  -1
        }
        if(JoyYaw>0&&JoyYaw<=1) {
            rightPow = ((-2*JoyYaw)+1)*JoyThr;
            leftPow = JoyThr;
        }
        leftmotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        rightmotor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        leftmotor.setPower(-rightPow);
        rightmotor.setPower(leftPow);

        try {
            Thread.sleep(10);
        } catch (Exception e) {

        }
    }
}
