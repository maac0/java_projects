package mini.java.lab8;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


public class FileDoubleKwantylator {
    private Double [] data; // Dane

    public FileDoubleKwantylator(FileInputStream data) throws IOException {

        this.data = new Double[1000];
        BufferedReader a = new BufferedReader(new InputStreamReader(data));
        String str = "";
        int i = 0;
        while((str = a.readLine()) != null){
            this.data[i] = Double.valueOf(str);
            i++;
        }
    }
    public void wypiszTekstowo(FileWriter f, Double ... abc) throws IOException {
        Locale.setDefault(new Locale("en"));

        for (Double aDouble : abc) {
            f.write(this.kwantyl(aDouble) + " ");
        }
    }
    public void wypiszBinarnie(FileOutputStream f, Double ... abc) throws IOException {
        ArrayList<Double> x = new ArrayList<>(Arrays.asList(abc));
        DataOutputStream output = new DataOutputStream(f);
        for(Double aDouble : x){
            output.writeDouble(this.kwantyl(aDouble));
        }
    }


    public Double[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "FileDoubleKwantylator{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    public Double median() {
        Double[] data1 = data;
        Arrays.sort(data1);
        int length = data1.length;
        if (length % 2 == 0) {
            return data1[length / 2 - 1];
        } else {
            return data1[(length - 1) / 2];
        }
    }
    public Double kwantyl(double p){
        if(data == null || data.length == 0) return null;
        assert p >= 0 && p <= 1;
        Double[] data1 = data;
        Arrays.sort(data1);
        int a = ((int) (Math.floor(p * data.length)) - 1);
        return  data1[a];
        }; // Zwraca kwantyl dla p: p w przedziale [0,1]


}
