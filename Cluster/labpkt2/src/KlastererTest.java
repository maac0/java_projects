import mini.oo20.lab10.Klasterer;
import mini.oo20.lab10.RealVector;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class KlastererTest {


    @org.junit.Test
    public void simpleKlasterCountEquals(){
        double [] data = {0.8570148, 5.3146791, 10.3715080};

        List<Double> list = new ArrayList <Double>();
        for (double x: data) list.add(x);

        var kw1 = new Klasterer<Double>(list, (x, y)->{ return Math.abs(x-y);});

        Map <Double, List <Double>> klasters = kw1.kmedoids(3, new int[] {0,1,2});

        assertEquals(klasters.size(), 3);

        Double [] medoids = klasters.keySet().toArray(Double[]::new);
        int size = 0;
        for (Double m: medoids) size+= klasters.get(m).size();

        assertEquals(size, data.length);

        Arrays.sort(medoids);

        assertEquals(medoids[0], 0.8570148, 0.1);
        assertEquals(medoids[1], 5.3146791, 0.1);
        assertEquals(medoids[2], 10.3715080, 0.1);
    }



    @org.junit.Test
    public void klasterCountEquals(){
        double [] data = {0.8570148, 4.5331345,  1.5746724, -0.2382224,  5.3146791, -0.7105860,  8.9225746,  5.0332195,  8.7771482,
                10.3715080,  4.1147194,  6.1246110,  8.9117522,  8.8460691, -1.3072297};

        List<Double> list = new ArrayList <Double>();
        for (double x: data) list.add(x);

        var kw1 = new Klasterer<Double>(list, (x, y)->{ return Math.abs(x-y);});

        Map <Double, List <Double>> klasters = kw1.kmedoids(3, new int [] {0,7,13});

        assertEquals(klasters.size(), 3);

        Double [] medoids = klasters.keySet().toArray(Double[]::new);
        int size = 0;
        for (Double m: medoids) size+= klasters.get(m).size();

        assertEquals(size, data.length);

    }


    @org.junit.Test
    public void klasterIterationsSet(){
        double [] data = {0.8570148, 4.5331345,  1.5746724, -0.2382224,  5.3146791, -0.7105860,  8.9225746,  5.0332195,  8.7771482,
                10.3715080,  4.1147194,  6.1246110,  8.9117522,  8.8460691, -1.3072297};

        List<Double> list = new ArrayList <Double>();
        for (double x: data) list.add(x);

        var kw1 = new Klasterer<Double>(list, (x, y)->{ return Math.abs(x-y);});

        Map <Double, List <Double>> klasters = kw1.kmedoids(3, new int [] {0,7,13});

        assertFalse(kw1.getIterations() < 1);

    }


    @org.junit.Test
    public void klasterSizesEqual(){
        double [] data = {0.8570148, 4.5331345,  1.5746724, -0.2382224,  5.3146791, -0.7105860,  8.9225746,  5.0332195,  8.7771482,
                10.3715080,  4.1147194,  6.1246110,  8.9117522,  8.8460691, -1.3072297};

        List<Double> list = new ArrayList <Double>();
        for (double x: data) list.add(x);

        var kw1 = new Klasterer<Double>(list, (x, y)->{ return Math.abs(x-y);});

        Map <Double, List <Double>> klasters = kw1.kmedoids(3, new int [] {0,7,13});

        Double [] medoids = klasters.keySet().toArray(Double[]::new);

        assertEquals(klasters.get(medoids[0]).size(), 5);
        assertEquals(klasters.get(medoids[1]).size(), 5);
        assertEquals(klasters.get(medoids[2]).size(), 5);
    }


    @org.junit.Test
    public void klasterMedoidsEqual(){
        double [] data = {0.8570148, 4.5331345,  1.5746724, -0.2382224,  5.3146791, -0.7105860,  8.9225746,  5.0332195,  8.7771482,
                10.3715080,  4.1147194,  6.1246110,  8.9117522,  8.8460691, -1.3072297};

        List<Double> list = new ArrayList <Double>();
        for (double x: data) list.add(x);

        var kw1 = new Klasterer<Double>(list, (x, y)->{ return Math.abs(x-y);});

        Map <Double, List <Double>> klasters = kw1.kmedoids(3, new int [] {0,7,13});

        int nk = klasters.size();

        Double [] medoids = klasters.keySet().toArray(Double[]::new);
        Arrays.sort(medoids);

        assertEquals(medoids[0].doubleValue(), -0.23, 1.0);
        assertEquals(medoids[1].doubleValue(), 5.03, 1.0);
        assertEquals(medoids[2].doubleValue(), 8.91, 1.0);
    }


    @org.junit.Test
    public void klasterCount2Equals(){
        double [] data = {0.8570148, 4.5331345,  1.5746724, -0.2382224,  5.3146791, -0.7105860,  8.9225746,  5.0332195,  8.7771482,
                10.3715080,  4.1147194,  6.1246110,  8.9117522,  8.8460691, -1.3072297, 100.67342};

        List<Double> list = new ArrayList <Double>();
        for (double x: data) list.add(x);

        var kw1 = new Klasterer<Double>(list, (x, y)->{ return Math.abs(x-y);});

        Map <Double, List <Double>> klasters3 = kw1.kmedoids(3, new int [] {0,7,14});

        assertEquals(klasters3.size(), 3);

        Map <Double, List <Double>> klasters = kw1.kmedoids(4, new int [] {3,10,4,12});

        assertEquals(klasters.size(), 4);

        Double [] medoids = klasters.keySet().toArray(Double[]::new);
        Arrays.sort(medoids);

        List<Double> kelem = klasters.get(medoids[3]);

        assertFalse(!kelem.contains(data[data.length-1]));

        int size = 0;
        for (Double m: medoids) size+= klasters.get(m).size();

        assertEquals(size, data.length);


    }


    @org.junit.Test
    public void klasterCountTooBigEquals(){
        double [] data = {0.8570148, 4.5331345,  1.5746724, -0.2382224,  5.3146791, -0.7105860,  8.9225746,  5.0332195,  8.7771482,
                10.3715080,  4.1147194,  6.1246110,  8.9117522,  8.8460691, -1.3072297};

        List<Double> list = new ArrayList <Double>();
        for (double x: data) list.add(x);

        var kw1 = new Klasterer<Double>(list, (x, y)->{ return Math.abs(x-y);});

        Map <Double, List <Double>> klasters = kw1.kmedoids(data.length + 1,
                new int [] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14});

        assertEquals(klasters.size(), data.length);

        Double [] medoids = klasters.keySet().toArray(Double[]::new);
        int size = 0;
        for (Double m: medoids) size+= klasters.get(m).size();

        assertEquals(size, data.length);

    }


    @org.junit.Test
    public void badArguments1(){

        List<Double> list = null;
        boolean exceptionCaught = false;

        try {
            var kw1 = new Klasterer<Double>(list, (x, y) -> {
                return Math.abs(x - y);
            });


            Map <Double, List <Double>> klasters = kw1.kmedoids(2, new int [] {0,1});

        } catch (IllegalArgumentException ex) {
            exceptionCaught = true;

        }

        assertFalse(!exceptionCaught);

    }


    @org.junit.Test
    public void badArguments2() {

        List<Double> list = new ArrayList<Double>();
        boolean exceptionCaught = false;

        try {
            var kw1 = new Klasterer<Double>(list, (x, y) -> {
                return Math.abs(x - y);
            });

            Map <Double, List <Double>> klasters = kw1.kmedoids(2, new int [] {0,1});

        } catch (IllegalArgumentException ex) {
            exceptionCaught = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        assertFalse(!exceptionCaught);

    }


    @org.junit.Test
    public void badArguments3(){

        double [] data = {0.8570148, 4.5331345,  1.5746724, -0.2382224,  5.3146791, -0.7105860,  8.9225746,  5.0332195,  8.7771482,
                10.3715080,  4.1147194,  6.1246110,  8.9117522,  8.8460691, -1.3072297};

        List<Double> list = new ArrayList <Double>();
        for (double x: data) list.add(x);

        boolean exceptionCaught = false;

        try {
              var kw1 = new Klasterer<Double>(list);

            Map <Double, List <Double>> klasters = kw1.kmedoids(2, new int [] {2,11});

        } catch (InvalidClassException ex) {
            exceptionCaught = true;
        }

        assertFalse(!exceptionCaught);

    }


    @org.junit.Test
    public void stringKlasterCountEquals(){
        String [] data = {"abracadabra", "alamakota", "kotamaala", "abecadlo", "kaloryfer", "zen"};

        List<String> list = new ArrayList <String>();
        for (String x: data) list.add(x);

        var kw1 = new Klasterer<String>(list, (x, y)->{int min = Math.min(x.length(), y.length());
            int d = 0,i=0;
            for (; i < min; i++) {
                d += Math.abs((int)x.charAt(i)-(int)y.charAt(i));
            }
            if (x.length()>y.length()) for (;i < x.length();i++) d+= x.charAt(i);
            if (y.length()>x.length()) for (;i < y.length();i++) d+= y.charAt(i);
            return d;
        });

        Map <String, List <String>> klasters = kw1.kmedoids(2, new int [] {1,4});

        assertEquals(klasters.size(), 2);

        String [] medoids = klasters.keySet().toArray(String[]::new);
        int size = 0;
        for (String m: medoids) size+= klasters.get(m).size();

        assertEquals(size, data.length);

        Arrays.sort(medoids);
        assertEquals(medoids[0], "kaloryfer");
        assertEquals(medoids[1], "kotamaala");
    }


    @org.junit.Test
    public void vectorDistanceCheck() {

        RealVector v1 = new RealVector(1.0,2.0);
        RealVector v2 = new RealVector(4.0, 3.0);

        double distance = v1.distanceTo(v2);

        assertEquals(distance, 4.0, 0.0001);
    }

    @org.junit.Test
    public void vectorDistance2Check() {

        RealVector v1 = new RealVector(1.0,2.0);
        RealVector v2 = new RealVector(4.0, 3.0, 1.0);

        double distance = v1.distanceTo(v2);

        assertEquals(distance, 5.0, 0.0001);
    }

    @org.junit.Test
    public void klasterObjects() {

        ArrayList <RealVector> arr = new ArrayList <RealVector>();

        try {

            FileInputStream fin = new FileInputStream("obiekty.bin");
            ObjectInputStream oin = new ObjectInputStream(fin);

            while (fin.available() > 0) {
                RealVector v = (RealVector) oin.readObject();
                arr.add(v);
            }
            oin.close();
            fin.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        Klasterer<RealVector> kw1 = null;
        try {
            kw1 = new Klasterer<RealVector>(arr);
        } catch (InvalidClassException e) {
            e.printStackTrace();
        }

        Map<RealVector, List<RealVector>> klasters= kw1.kmedoids(4, new int [] {915, 505, 968, 391});

        assertEquals(klasters.size(), 4);
        assertFalse(kw1.getIterations() < 3);

        RealVector[] medoids = klasters.keySet().toArray(RealVector[]::new);
        Arrays.sort(medoids);
        assertEquals(medoids[0].get(0), -3.82, 0.1);

        RealVector o = new RealVector(0.0, 0.0);

        assertFalse(medoids[0].distanceTo(o) > medoids[1].distanceTo(o));
        assertFalse(medoids[1].distanceTo(o) > medoids[2].distanceTo(o));
    }

}