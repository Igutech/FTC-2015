package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Logan on 11/13/2015.
 */
public class Logans_New_Teleop extends OpMode {

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
        leftMotor1 = hardwareMap.dcMotor.get("left1");
        leftMotor2 = hardwareMap.dcMotor.get("left2");

        rightMotor1 = hardwareMap.dcMotor.get("right1");
        rightMotor2 = hardwareMap.dcMotor.get("right2");

        changeMotor = hardwareMap.dcMotor.get("worm1");

        driver = new DcMotorDriver(hardwareMap);

        DcMotorController leftMotorController;
        DcMotorController rightMotorController;

        leftMotorController = hardwareMap.dcMotorController.get("leftMotorController");
        rightMotorController = hardwareMap.dcMotorController.get("rightMotorController");

        //wheelsOut = hardwareMap.touchSensor.get("wheelsDown");
        //wheelsIn = hardwareMap.touchSensor.get("wheelsUp");
    }

    @Override
    public void loop() {
        //ModeChooser();

        //wheelsUp = wheelsIn.isPressed();
        //wheelsDown = wheelsOut.isPressed();

        /*if(wheelsUp && wheelsDown) {
            telemetry.addData("The touch sensors are both appearing as touched at the same time.", "That's not good.");

        } else */if(gamepad1.a) {
            changeMotor.setPower(1);
        }
        else if(gamepad1.b)
        {
            changeMotor.setPower(-1);
        }/*else if(gamepad1.left_trigger >= .5) {
                if(wheelsUp) {
                    while(wheelsOut.isPressed() == false) {
                        rightMotor1.setPower(0);
                        rightMotor2.setPower(0);
                        leftMotor1.setPower(0);
                        leftMotor2.setPower(0);
                        changeMotor.setPower(-.8);
                        telemetry.addData("Transitioning from wheel mode to tread mode...", " ");
                    }
                } else if(wheelsDown) {
                    while (wheelsIn.isPressed() == false) {
                        rightMotor1.setPower(0);
                        rightMotor2.setPower(0);
                        leftMotor1.setPower(0);
                        leftMotor2.setPower(0);
                        changeMotor.setPower(.8);
                        telemetry.addData("Transitioning from tread mode to wheel mode...", " ");
                    }
                } else if(wheelsUp == false && wheelsDown == false) {
                        telemetry.addData("It knows the controller button is pressed, but thinks that neither button sensor is pressed. Crap.", " ");
                }
        } else if(wheelsUp) {
            telemetry.addData("Tread Mode", " ");
        } else if(wheelsDown) {
            telemetry.addData("Wheel Mode", " ");
        } else if (wheelsUp == false && wheelsDown == false) {
            telemetry.addData("Neither touch sensor is pressed... :/", " ");
        }*/

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



        sloMo = 1 - gamepad1.right_trigger;

        telemetry.addData("Slow-Mo factor", sloMo);

        if(sloMo <= .30){
            sloMo = .30;
        }

        rightPow = rightPow * sloMo;
        leftPow = leftPow * sloMo;

        leftMotor1.setPower(leftPow);
        leftMotor2.setPower(-leftPow);

        rightMotor1.setPower(-rightPow);
        rightMotor2.setPower(rightPow);
        //roboStatus();

        //telemetry


        telemetry.addData("Right Encoder Value: ", driver.getEncoderPosition("right"));
        telemetry.addData("Left Encoder Value: ", driver.getEncoderPosition("left"));

        try {
            Thread.sleep(3);
        } catch (Exception e) {  }
    }

    /*public void roboStatus() {
        switch (robotMode) {
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

    }*/

    /*public void ModeChooser() {
        if (gamepad1.left_trigger >= .5 && robotMode != 0) {
            robotMode = 0;
        }
        else if(Between mode 0 and mode 2)
        {
            robotMode = 1;
        }
        else if (gamepad1.left_trigger >= .5 && robotMode != 2) {
            robotMode = 2;
        }
        else if(Pull up button)
        {
            robotMode = 4;
        }

        else if(Tumble mode)
        {
            robotMode = 5;
        }
        */
    }

