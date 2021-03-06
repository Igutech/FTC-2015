package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by The Team.
 */
public class IgutechTeleop extends OpMode {

    DcMotor leftMotor, rightMotor, armMotor1, armMotor2, winch, brush; //define DC motors
    Servo armServo, climberServo, magicRelease, climbAssist; //define servos
    DcMotorController leftMotorController, rightMotorController, armcontroller;
    String nameMode, status;
    int statusTicker = 0;
    int ticktimer = 0;
    long previoustick;
    long setTime;
    int toggleswitch =1;
    int targetPos = 0;
    int switchingmodes = 0;
    int switchingmodespast = 0;
    Boolean wasThereJustSwitch = false;

    double idleServoPos = .5; //was.438

    double JoyThr, JoyYaw, rightPow, leftPow, armMovement, armscaling, offset;


    double sloMo = 1;

    String team = "";

    boolean firstLoop = true;

    @Override
    public void init() {

        armMotor1 = hardwareMap.dcMotor.get("arm1");
        armMotor2 = hardwareMap.dcMotor.get("arm2");
        armMotor1.setDirection(DcMotor.Direction.FORWARD);
        armMotor2.setDirection(DcMotor.Direction.REVERSE);
        armMotor1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        armMotor2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftMotor = hardwareMap.dcMotor.get("left2");
        rightMotor = hardwareMap.dcMotor.get("right2");
        winch = hardwareMap.dcMotor.get("winch");
        brush = hardwareMap.dcMotor.get("worm1");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
        leftMotorController = hardwareMap.dcMotorController.get("leftMotorController");
        rightMotorController = hardwareMap.dcMotorController.get("rightMotorController");
        armcontroller = hardwareMap.dcMotorController.get("armcontroller");

        armServo = hardwareMap.servo.get("armservo");
        climberServo = hardwareMap.servo.get("climber");
        climbAssist = hardwareMap.servo.get("climbAssist");
        magicRelease = hardwareMap.servo.get("magicRelease");
        setTime = System.currentTimeMillis();
        climbAssist.setPosition(idleServoPos);
        climberServo.setPosition(.5);
        magicRelease.setPosition(.25);
        armServo.setPosition(.5);
    }

    @Override
    public void loop() {
        //colorChoosing(); //The currently unused servo control software
        //unusedCode();    //Currently unused code that's commented out
        servoControls(); //Code which controls all the servos

        //toggle modes code
        if(gamepad2.left_stick_button){
           if(switchingmodes==switchingmodespast){if(switchingmodes==1){switchingmodes=0;}else{switchingmodes=1;}}
        }else{switchingmodespast=switchingmodes;}

        if(switchingmodes == 1 && switchingmodespast == 1){
            //LOCK MODE CODE FOR ARM!!!
            if(wasThereJustSwitch)
            {
                setTime = System.currentTimeMillis();
                wasThereJustSwitch = false;
            }

            telemetry.addData("Time", appclock());
            experimentalArm();
        }
        if(switchingmodes == 0 && switchingmodespast == 0){
            //FREE MODE CODE FOR ARM!!!
            armMotor2.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            armMotor1.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

            wasThereJustSwitch = true;
            armMovement = -gamepad2.left_stick_y;

            if (-armMovement > 0) {
                armscaling = .3;
            }else if (-armMovement < 0) {
                armscaling = .3;
            }

            //Move arm
            armMotor1.setPower(armMovement * armscaling); //Write arm movements to the motors.
            armMotor2.setPower(armMovement * armscaling);
        }


        motorControls(); //Code which controls all the motors
        status();//shows robot status

    }

    public void experimentalArm()
    {
        if(appclock()<1500)
        {
            armMotor1.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            armMotor2.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            //things to do in first 1 second
            statusTicker = 1; //set status to reset encoders
        }
        if(appclock()>1500 && appclock()<3000)
        {
            armMotor1.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            armMotor2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            //things to do in seconds 1-2
            statusTicker = 1; //set status to reset encoders
        }
        if(appclock()>3000)
        {
            armMotor1.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            armMotor2.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            if(toggleswitch ==1)
            {
                previoustick = appclock();
                previoustick = previoustick + 10;
                toggleswitch = 0;
            }
            if(toggleswitch == 0)
            {
                if(appclock() >= previoustick)
                {
                    ticktimer++;
                    targetPos = targetPos - ((Math.round(10 * gamepad2.left_stick_y)/10)*10);
                    toggleswitch =1;
                }
            }
            telemetry.addData("Target Value: ", targetPos);

            armMotor1.setTargetPosition(targetPos);
            armMotor2.setTargetPosition(targetPos);
            //setting the power of the armsystem
            if(Math.abs(gamepad2.left_stick_y)>0.03)
            {
                armMotor1.setPower(gamepad2.left_stick_y); //the motor moves at stick speed
                armMotor2.setPower(gamepad2.left_stick_y);
            }else{
                armMotor1.setPower(1); //obviously if the stick isn't being moved, the motor holds
                armMotor2.setPower(1);
            }

            //things to do after 2 seconds
            statusTicker = 2; //status is running
        }
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
            magicRelease.setPosition(.55);
        } else {
            magicRelease.setPosition(.25);
        }

        if (gamepad1.left_trigger > .7) {
            climbAssist.setPosition(1);
        } else if (gamepad1.left_bumper) {
            climbAssist.setPosition(0);
        } else {
            climbAssist.setPosition(idleServoPos);
        }
    }

    public void motorControls() {
        //This is where the arm, winch, and brush movement is along with driving control
        JoyThr = -gamepad1.left_stick_y;
        JoyYaw = -gamepad1.right_stick_x;

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

    public void status()
    {
        /*if(statusTicker == 1)
        {
            status = "Reseting arm encoders";
        }
        if(statusTicker == 2)
        {
            status = "Arm Live in ServoMode";
        }*/

        //these show the current arm mode
        if(switchingmodes == 1 && switchingmodespast == 1){telemetry.addData("Toggle mode ", "LOCK MODE");}
        if(switchingmodes == 0 && switchingmodespast == 0){telemetry.addData("Toggle mode ", "FREE MODE");}

        //telemetry.addData("Status: ", status);
    }

    public long appclock()
    {
        return System.currentTimeMillis()-setTime;
    }
}
