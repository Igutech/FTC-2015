package com.qualcomm.ftcrobotcontroller.opmodes;

import android.widget.Switch;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Sahas on 10/17/2015.
 */
public class IguNewbotTeleop extends OpMode {

    DcMotor leftMotor1;
    DcMotor leftMotor2;
    DcMotor rightMotor1;
    DcMotor rightMotor2;

    int robotMode = 1;
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

        rightMotor1 = hardwareMap.dcMotor.get("right1");
        rightMotor2 = hardwareMap.dcMotor.get("right2");


    }

    @Override
    public void loop() {
        ModeChooser();

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

        leftMotor1.setPower(leftPow);
        leftMotor2.setPower(-leftPow);

        rightMotor1.setPower(-rightPow);
        rightMotor2.setPower(rightPow);
        roboStatus();
    }
    public void roboStatus()
    {
        switch(robotMode) {
            case 0:
                nameMode = "Regular Mode";
                break;
            case 1:
                nameMode = "Transitioning...";
                break;
            case 2:
                nameMode = "Track Mode";
                break;
            case 3:
                nameMode = "High-Angle Mode";
                break;
            case 4:
                nameMode = "Pullup Mode";
                break;
            case 5:
                nameMode = "Tumble Mode";
                break;
            default:
                nameMode = "Something isn't working...";
                break;
        }
        telemetry.addData("RobotMode: ", nameMode);

    }
    public void ModeChooser()
    {
        if(/*Both back triggers are pressed and robotMode=!0*/)
        {
            robotMode = 0;
        }
        else if(/*Between mode 0 and mode 2*/)
        {
            robotMode = 1;
        }
        else if(/*Both back triggers are pressed and robotMode=!2*/)
        {
            robotMode = 2;
        }
        else if(/*Gyro indicates that the robot is on an angle between 40 degrees and 60 degrees*/)
    }


}
