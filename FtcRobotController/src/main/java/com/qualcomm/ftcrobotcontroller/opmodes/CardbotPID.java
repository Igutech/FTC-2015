package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by Kevin on 9/13/2015.
 */
public class CardbotPID {
    double pgain, igain, dgain;
    double pout, iout, dout;
    double integral = 0;
    double preverror = 0;
    public void setP (double p) {
        pgain = p;
    }
    public void setI (double i) {
        igain = i;
    }
    public void setD (double d) {
        dgain = d;
    }
    public double doCalc(double setPoint, double value) {
        //p calculation
        double error = setPoint-value;
        //i calculation
        integral+=error*10;
        //d calculation
        double derivative = (error - preverror)/10;
        preverror = error;
        //finally, return the result...
        return ((error*pgain)+(integral*igain)+(derivative*dgain));
    }
}
