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

    DeviceInterfaceModule DIM;
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor changeMotor;

    Servo armServo;
    Servo climberServo;
    Servo magicRelease;

    Servo redFlipper;
    Servo blueFlipper;

    int robotMode = 0;
    String nameMode;

    double JoyThr;
    double JoyYaw;

    double rightPow;
    double leftPow;

    DcMotor armMotor1;
    DcMotor armMotor2;

    DcMotor winch;

    DcMotorDriver driver;

    double armMovement;

    double sloMo = 1;

    double offset;

    double armscaling;

    String team = "";

    boolean firstLoop = true;

    @Override
    public void init() {
        changeMotor = hardwareMap.dcMotor.get("worm1");
        changeMotor.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        armMotor1 = hardwareMap.dcMotor.get("arm1");
        armMotor2 = hardwareMap.dcMotor.get("arm2");
        leftMotor = hardwareMap.dcMotor.get("left2");
        rightMotor = hardwareMap.dcMotor.get("right2");
        winch = hardwareMap.dcMotor.get("winch");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        //DIM = hardwareMap.deviceInterfaceModule.get("dim");

        armServo = hardwareMap.servo.get("armservo");
        climberServo = hardwareMap.servo.get("climber");

        magicRelease = hardwareMap.servo.get("magicRelease");
        /*
        redFlipper = hardwareMap.servo.get("redFlipper");
        blueFlipper = hardwareMap.servo.get("blueFlipper");
        armServo.setDirection(Servo.Direction.FORWARD);
        */
        DcMotorController leftMotorController;
        DcMotorController rightMotorController;

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
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void servoControls()
    {
        //This is where all the servo controlling runs
        if(gamepad2.x)
        {
            armServo.setPosition(1);
            //DIM.setDigitalChannelState(0, true);
            //DIM.setDigitalChannelState(1, false);
        }
        else if(gamepad2.b)
        {
            armServo.setPosition(0);
        }
        else
        {
            armServo.setPosition(.5);
        }
        if (gamepad2.left_bumper) {
            changeMotor.setPower(1);
        } else if (gamepad2.right_bumper) {
            changeMotor.setPower(-1);
        } else {
            changeMotor.setPower(0);
        }

        if (gamepad2.dpad_down) {
            climberServo.setPosition(0);
        }
        if (gamepad2.dpad_up) {
            climberServo.setPosition(.7);
        }
        if (gamepad2.dpad_left || gamepad2.dpad_right) {
            climberServo.setPosition(.5);
        }
        if (gamepad1.a && gamepad2.a) {
            magicRelease.setPosition(0);
        } else {
            magicRelease.setPosition(.85);
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void motorControls() {
        //This is where the driving and arm movement is
        JoyThr = -gamepad1.left_stick_y;
        JoyYaw = -gamepad1.right_stick_x;

        armMovement = -gamepad2.left_stick_y;

        if (-armMovement > 0) {
            armscaling = .3;
        } else if (-armMovement < 0) {
            armscaling = .3;
        }

        if (gamepad2.left_trigger > .9) {
            winch.setPower(1);
        } else if (gamepad2.right_trigger > .9) {
            winch.setPower(-1);
        } else {
            winch.setPower(0);
        }

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

        telemetry.addData("Slow-Mo factor", sloMo);

        if (sloMo <= .30) {
            sloMo = .30;
        }

        rightPow = rightPow * sloMo;
        leftPow = leftPow * sloMo;

        armMotor1.setDirection(DcMotor.Direction.FORWARD);
        armMotor2.setDirection(DcMotor.Direction.REVERSE);

        armMotor1.setPower(armMovement * armscaling);
        armMotor2.setPower(armMovement * armscaling);

        leftMotor.setPower(leftPow);
        rightMotor.setPower(rightPow);

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void unusedCode() {

    }
}
