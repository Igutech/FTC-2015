package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Sahas on 10/17/2015.
 */
public class SimpleOp_V1 extends OpMode {

    DcMotor leftMotor1;
    DcMotor leftMotor2;
    DcMotor rightMotor1;
    DcMotor rightMotor2;
    DcMotor wormGear;

    int robotMode = 0;
    int robotModePrev = 0;
    double rightTriggerState;
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

    @Override
    public void init() {
        leftMotor1 = hardwareMap.dcMotor.get("left1");
        leftMotor2 = hardwareMap.dcMotor.get("left2");
        wormGear =hardwareMap.dcMotor.get("worm1");
        rightMotor1 = hardwareMap.dcMotor.get("right1");
        rightMotor2 = hardwareMap.dcMotor.get("right2");
    }

    @Override
    public void loop() {
        updateModes();

        switch(robotMode) {
            case 0:
                drive(1);
                break;
            case 1:
                if(robotModePrev==0)
                {
                    //drive motor until touch at bottom
                }
                else if(robotModePrev==2)
                {
                    //drive motor reverse untill touch at top
                }
                break;
            case 2:
                drive(1);
                break;
            default:
                //do nothing
                break;
        }


        roboStatus();
    }
    public void roboStatus()
    {
        switch(robotMode) {
            case 0:
                nameMode = "Wheel Mode";
                break;
            case 1:
                nameMode = "Transitioning...";
                break;
            case 2:
                nameMode = "Track Mode";
                break;
            default:
                nameMode = "Something isn't working...";
                break;
        }
        telemetry.addData("RobotMode: ", nameMode);

    }
    public void updateModes()
    {
        if(rightTriggerState > 0.9)
        {
            if(robotMode==0)
            {
                robotMode=1;
                robotModePrev=0;
            }
            else if(robotMode==2)
            {
                robotMode=1;
                robotModePrev=2;
            }
        }
    }
    public void drive(float power)
    {
        JoyThr = -gamepad1.left_stick_y;
        JoyYaw = gamepad1.right_stick_x;

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

        if(gamepad1.dpad_down)
        {
            wormGear.setPower(1);
        }
        else if(gamepad1.dpad_up)
        {
           wormGear.setPower(-1);
        }
        else
        {
            wormGear.setPower(0);
        }

        leftPow = (leftPow)*power;
        rightPow= (rightPow)*power;

        leftMotor1.setPower(leftPow);
        leftMotor2.setPower(-leftPow);

        rightMotor1.setPower(-rightPow);
        rightMotor2.setPower(rightPow);
    }
}
