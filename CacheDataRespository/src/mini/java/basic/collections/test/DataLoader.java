package mini.java.basic.collections.test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DataLoader {
    /**
     * DataLoader loads data from file. Data is saved in csv files in format
     * table;field1;field2;field3... Data is filtered using @dataFileName, and if no
     * line is found, function should rise NoDataException. Loaded data is converted to Object[]
     * by splitting against the ";"
     * @param dataFileName data file name/path
     * @param dataset name of dataset to load from file
     * @return loaded data, in form of List of Object[]
     * @throws IOException Bubbled IOException
     * @throws NoDataException No data in file for dataset
     */

    public List<Object[]> getData(String dataFileName, String dataset) throws IOException, NoDataException {
        List<Object[]> dataSetObjects = new ArrayList<>();
    int length = 0;
    BufferedReader a = new BufferedReader(new InputStreamReader(new FileInputStream(dataFileName)));
    String str;
        while((str = a.readLine()) != null) {
        Scanner s1 = new Scanner(new StringReader(str)).useDelimiter(";");
        String type = null;
        if ((type = s1.next()).equals(dataset)) {
            dataSetObjects.add(new Object[]{});
            List<Object> data = new ArrayList<>();
            while (s1.hasNext()) {
                data.add((Object) s1.next());
            }
            dataSetObjects.set(length, data.toArray());
            length++;
        }
    }
        if(length == 0) throw new NoDataException();
        return dataSetObjects;
}
}
