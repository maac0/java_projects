package mini.oo20.lab10;

import mini.lab10.Metrizable;

import java.io.InvalidClassException;
import java.io.Serializable;
import java.util.*;
import java.util.function.ToDoubleBiFunction;

public class Klasterer<T> {
    private List<T> data; // Dane
    private ToDoubleBiFunction<T, T> distance;
    private int iterations;

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public Klasterer(List<T> data, ToDoubleBiFunction<T, T> distanceFun){
        this.data = data;
        this.distance = distanceFun;
    } // Konstruktor (1)

    public Klasterer(List<T> data) throws InvalidClassException{
        if (!data.isEmpty() && data.get(0)
                instanceof Metrizable && data.get(0)
                instanceof Serializable) {
            this.data = data;
            this.distance = null;
        } else throw new InvalidClassException("");
    } // Konstruktor (2)

    public Map<T, List<T>> kmedoids(int k, int[] randomstart) throws IllegalArgumentException{
        int k1 = Math.min(k, randomstart.length);
        if(this.data == null || this.data.size() == 0) throw new IllegalArgumentException("");
        Map<T, List<T>> map = new HashMap<>();
        Map<T, List<T>> prevmap = new HashMap<>();
        List<List<T>> nestedList = new ArrayList<List<T>>(k1);
        List<T> kmedians = new ArrayList<T>(k1);
        for (int i = 0; i < k1; i++) {
            nestedList.add(i, new ArrayList<T>(1000));
            kmedians.add(i, data.get(randomstart[i]));
            kmedians.set(i, data.get(randomstart[i]));
        }
        double min;
        double minglob;
        double temp;
        int pointer = 0;
        int iterations = 0;

        if (distance == null) {
            while (!prevmap.equals(map) || iterations == 0) {
                prevmap = map;
                for (T datum : data) {
                    min = Double.POSITIVE_INFINITY;
                    for (int j = 0; j < k1; j++) {
                        temp = ((Metrizable<T>) datum).distanceTo(kmedians.get(j));
                        if (temp < min) {
                            min = temp;
                            pointer = j;
                        }
                    }
                    nestedList.get(pointer).add(datum);
                }
                map = new HashMap<>();
                for (int i = 0; i < k1; i++) {
                    map.put(kmedians.get(i), nestedList.get(i));
                    minglob = Double.POSITIVE_INFINITY;
                    List<T> help = nestedList.get(i);
                    for (T elem1 : help) {
                        double suma = 0;
                        min = Double.NEGATIVE_INFINITY;
                        for (T elem2 : help) {
                            suma += ((Metrizable<T>) elem1).distanceTo(elem2);
                        }
                        if (suma < minglob) {
                            minglob = suma;
                            kmedians.set(i, elem1);
                        }
                    }
                    nestedList.set(i, new ArrayList<T>());
                }
                iterations++;
            }
            setIterations(iterations);
            return map;
        }
        else {
            while (!prevmap.equals(map) || iterations == 0) {
                prevmap = map;
                for (T datum : data) {
                    min = Double.POSITIVE_INFINITY;
                    for (int j = 0; j < k1; j++) {
                        temp = distance.applyAsDouble(datum, kmedians.get(j));
                        if (temp < min) {
                            min = temp;
                            pointer = j;
                        }
                    }
                    nestedList.get(pointer).add(datum);
                }
                map = new HashMap<>();
                for (int i = 0; i < k1; i++) {
                    map.put(kmedians.get(i), nestedList.get(i));
                    minglob = Double.POSITIVE_INFINITY;
                    List<T> help = nestedList.get(i);
                    for (T elem1 : help) {
                        double suma = 0;
                        min = Double.NEGATIVE_INFINITY;
                        for (T elem2 : help) {
                            suma += distance.applyAsDouble(elem1, elem2);
                        }
                        if (suma < minglob) {
                            minglob = suma;
                            kmedians.set(i, elem1);
                        }
                    }
                    nestedList.set(i, new ArrayList<T>());
                }

                iterations++;
            }
            setIterations(iterations);
            return map;
        }
    }
}
