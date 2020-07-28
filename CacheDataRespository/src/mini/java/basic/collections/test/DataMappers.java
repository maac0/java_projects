package mini.java.basic.collections.test;

import java.io.StringReader;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class DataMappers {
    /**
     * Maps line of data to @SimpleDataRepository.Product
     * @param objects Data loaded from DataLoader
     * @return Object[] parsed into @SimpleDataRepository.Product
     * @throws ProductDataMalformedException thrown if data is malformed/cannot be parsed
     */

    public static SimpleDataRepository.Product mapToProduct(Object[] objects) throws ProductDataMalformedException {
        Locale.setDefault(new Locale("en"));
        int id;
        String name;
        double price;
        boolean e;
        try {
            id = Integer.parseInt((String) objects[0]);
            name = (String) objects[1];
            price = Double.parseDouble((String) objects[2]);
            e = Boolean.parseBoolean((String) objects[3]);
        }
        catch(ArrayIndexOutOfBoundsException| IllegalAccessError | InstantiationError | NumberFormatException a){
            throw new ProductDataMalformedException();
        }
        return new SimpleDataRepository.Product(id, name, price, e);
    }

    /**
     * Maps line of data to @SimpleDataRepository.Warehouse
     * @param objects Data loaded from DataLoader
     * @return Object[] parsed into @SimpleDataRepository.Warehouse
     * @throws WarehouseDataMalformedException thrown if data is malformed/cannot be parsed
     */
    public static SimpleDataRepository.Warehouse mapToWarehouse(Object[] objects) throws WarehouseDataMalformedException {
        Locale.setDefault(new Locale("en"));
        int id;
        String name;
        boolean price;
        List<Integer> e;
        try {
            id = Integer.parseInt((String) objects[0]);
            name = (String) objects[1];
            price = Boolean.parseBoolean((String) objects[2]);
            e = new ArrayList<Integer>();
            Scanner s1 = new Scanner((String) objects[3]).useDelimiter(",");
            while(s1.hasNext()) e.add(s1.nextInt());
        }
        catch(ArrayIndexOutOfBoundsException | IllegalAccessError | InstantiationError | NumberFormatException | ClassCastException a){
            throw new WarehouseDataMalformedException();
        }
        return new SimpleDataRepository.Warehouse(id, name, price, e);
    }
}
