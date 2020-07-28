package mini.java.lab8;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileObjectKwantylator {
    private My2DObject[] data; // Dane

    public FileObjectKwantylator(FileInputStream fin) throws IOException {
        ArrayList<My2DObject> data = new ArrayList<>();
        ObjectInputStream in = new ObjectInputStream(fin);
        while (true) {
            try {
                data.add((My2DObject) in.readObject());
            }
            catch(EOFException e) {break;}
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        My2DObject[] dat = new My2DObject[data.size()];
        for (int a = 0; a < data.size(); a++){
            dat[a] = data.get(a);
        }
        this.data = dat;
    }

    public My2DObject median() {
        My2DObject[] data1 = data;
        Arrays.sort(data1);
        int length = data1.length;
        if (length % 2 == 0) {
            return data1[length / 2 - 1];
        } else {
            return data1[(length - 1) / 2];
        }
    }
}
