package mini.java.lab8;

import java.io.Serializable;

public class My2DObject implements java.io.Serializable, Comparable<My2DObject> {
    static final long serialVersionUID = 1;
    private double x1;
    private double x2;

    public My2DObject(double x1, double x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    @Override
    public int compareTo(My2DObject my2DObject) {
        if (x1 > my2DObject.getX1()){ return 1;}
        else if (x1 < my2DObject.getX1()) { return -1;}
        else{
            if (x2 > my2DObject.getX2()){return 1;}
            else if (x2< my2DObject.getX2()){return -1;}
            else {return 0;}
        }
    }
}
