package com.qualcomm.ftcrobotcontroller.opmodes.unused;

/**
 * Created by Kevin on 9/13/2015.
 */
public class CardbotPID {
    double pgain, igain, dgain;
    double pout, iout, dout;
    double integral = 0;
    double preverror = 0;
    public void setP (double p) { //set the p gain
        pgain = p;
    }
    public void setI (double i) { //set the i gain
        igain = i;
    }
    public void setD (double d) { //set the d gain
        dgain = d;
    }
    public double doCalc(double setPoint, double value) { //run the pid calculation
        //p calculation
        double error = setPoint-value; //p = error*p gain
        //i calculation
        integral+=error*10; //i = (i + error*time)*i gain
        //d calculation
        double derivative = (error - preverror)/10; //d = ((error-previous error)/time)*d gain
        preverror = error; //set the previous error to the current error for the next iteration
        //finally, return the result...
        return ((error*pgain)+(integral*igain)+(derivative*dgain)); // PID = p+i+d
    }
}
