package mini.java.basic.collections.test;

/**
 * Simple exception
 */
public class NoDataException extends Exception {
    public NoDataException () {
        System.out.println("Data not found!");
    }
}
