package game;

import java.io.Serializable;

public class ProcessAction implements Serializable{
    private double point1X;
    private double point1Y;
    private double point2X;
    private double point2Y;
    private double angle;
    private double power;

    private double x,y;

    @Override
    public String toString() {
        return "ProcessAction{" +
                "point1X=" + point1X +
                ", point1Y=" + point1Y +
                ", point2X=" + point2X +
                ", point2Y=" + point2Y +
                ", angle=" + angle +
                ", power=" + power +
                '}';
    }

    public ProcessAction(double point1X, double point1Y, double point2X, double point2Y) {
        this.point1X = point1X;
        this.point1Y = point1Y;
        this.point2X = point2X;
        this.point2Y = point2Y;
        calculateAngle();
        calculatePower();
        detectDamage();
    }

    private void calculatePower()
    {
        x = point1X - point2X;
        y = point2Y - point1Y;
        power = Math.sqrt(x*x + y*y) ;
        if(power >= 250) power = 250;
        power = power/250.0 * 100;
    }

    private void calculateAngle()
    {

        x = point1X - point2X;
        y = point2Y - point1Y;
        angle = Math.atan(y/x) *180 / Math.PI;
        if(point1X < point2X) angle = 180 + angle;
    }

    public double getAngle() {
        return angle;
    }

    public double getPower() {
        return power;
    }

    public void detectDamage()
    {
        double R = power*power*Math.sin(2*angle*Math.PI/180.0)/10;
        System.out.println("R = "+R);
        double t = 750.0/power/Math.cos(angle*Math.PI/180.0);
        double y = power*Math.sin(angle*Math.PI/180.0)*t - 5*t*t;
        System.out.println("y = "+ y);
    }
}
