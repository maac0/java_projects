package mini.oo20.lab10;
import mini.lab10.Metrizable;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class RealVector implements Metrizable<RealVector>, Comparable<RealVector>, Serializable {
    static final long serialVersionUID = 1L;

    private List<Double> vector; // kolekcja/tablica obiektów double

    public RealVector(Double ... dataa){
        try {
            this.vector = Arrays.asList(dataa);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        }

    public Double get(int i) {
        return vector.get(i);
    }

    // zaimplementuj przy pomocy odleglosci od punktu (0.0,0.0)
    public int compareTo(RealVector o) {
        RealVector zero = new RealVector(0.0, 0.0, 0.0, 0.0);
        return Double.compare(this.distanceTo(zero), o.distanceTo(zero));
    }

    public double distanceTo(RealVector o){
        int thiss = this.vector.size();
        int others = o.vector.size();
        boolean comp = thiss > others;
        int dimension;
        double suma = 0.0;
        if(comp){
            for(int i = 0; i < others; i++){
                suma += Math.abs(vector.get(i) - o.get(i));
            }
            for(int i = others; i < thiss; i++){
                suma += Math.abs(vector.get(i));
            }
        }
        else{
            for(int i = 0; i < thiss; i++){
                suma += Math.abs(vector.get(i) - o.get(i));
            }
            for(int i = thiss; i < others; i++){
                suma += Math.abs(o.get(i));
            }
        }
        return suma;
    }
}
