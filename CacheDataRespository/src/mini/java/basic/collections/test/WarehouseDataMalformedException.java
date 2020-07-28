package mini.java.basic.collections.test;

/**
 * Simple excaption for malformed @Warehouse data
 */
public class WarehouseDataMalformedException extends Exception {
    public WarehouseDataMalformedException(){
        System.out.println("Data is malformed!!!");
    }
}
