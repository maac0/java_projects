package mini.java.lab8;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Kwantylator <T extends Comparable<? super T>> {
    private T[] data; // Dane

    public Kwantylator(T ... data) {
        if(data == null || data.length == 0){
            this.data = null;
            return;
        }
        this.data = data;
    }

    public T median() {
        if(data == null || data.length == 0) return null;
        T[] data1 = data;
        Arrays.sort(data1);
        int length = data1.length;
        if (length % 2 == 0) {
            return data1[length / 2 - 1];
        }
        else {
            return data[(length - 1) / 2];
        }
    }

    ; // Zwraca medianę
    public T kwantyl(double p){
        if(data == null || data.length == 0) return null;
        assert p >= 0 && p <= 1;
        T[] data1 = data;
        Arrays.sort(data1);
        int a = (int) Math.floor(p * data.length);
        return  data1[a];
    }; // Zwraca kwantyl dla p: p w przedziale [0,1]
}
