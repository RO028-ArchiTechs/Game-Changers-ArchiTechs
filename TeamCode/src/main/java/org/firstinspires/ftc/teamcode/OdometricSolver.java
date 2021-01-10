package org.firstinspires.ftc.teamcode;

public class OdometricSolver {
    private final double L_MM_PER_TICK = 0;
    private final double R_MM_PER_TICK = 0;
    private final double B_MM_PER_TICK = 0;
    private final double L_DISTANCE = 0;
    private final double R_DISTANCE = 0;
    private final double B_DISTANCE = 0;
    private final double eps = 1e-3;

    double x,y,theta;

    OdometricSolver(double x,double y,double theta){
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    void update_ticks(int l_ticks,int r_ticks,int b_ticks){
        double l = l_ticks * L_MM_PER_TICK;
        double r = r_ticks * R_MM_PER_TICK;
        double b = b_ticks * B_MM_PER_TICK;
        double dx = 0,dy = 0,dt = 0;
        if(Math.abs(Math.abs(l) - Math.abs(r)) < eps){
            if(l * r >= 0){
                dt = 0;
                dx = 0;
                dy = (l + r) / 2;
            }else{
                dt = (r) / (R_DISTANCE);
                dx = 0;
                dy = 0;
            }
        }else if(Math.abs(r) - Math.abs(l) > eps){
            double X = 2 * L_DISTANCE * l / (r - l);
            dt = l / X;
            dy = Math.sin(dt) * (L_DISTANCE + X);
            dx = Math.cos(dt) * (L_DISTANCE + X) - (L_DISTANCE + X);
        }else{
            double X = 2 * R_DISTANCE * r / (l - r);
            dt = -r / X;
            dy = Math.sin(-dt) * (R_DISTANCE + X);
            dx = -Math.cos(-dt) * (R_DISTANCE + X) + (R_DISTANCE + X);
        }

        double expected_back_rotation = B_DISTANCE * dt;
        double back_strafe = b - expected_back_rotation;
        dx += back_strafe * Math.cos(dt);
        dy += back_strafe * Math.sin(dt);

        double a_dx,a_dy;///absolute change
        a_dx = dx * Math.cos(dt) -  dy * Math.sin(theta);
        a_dy = dx * Math.sin(dt) + dy * Math.cos(theta);

        x += a_dx;
        y += a_dy;
        theta += dt;
    }

    public double get_x(){
        return x;
    }

    public double get_y(){
        return y;
    }

    public double get_theta() {
        return theta;
    }
}
