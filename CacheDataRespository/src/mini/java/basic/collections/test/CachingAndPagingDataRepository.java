package mini.java.basic.collections.test;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Caching and paging data repository
 *
 * Cache can be implemented in any *sensible* way (this means using maps). Cache should have
 * ability to retrieve both full datasets (for both entities Product and Warehouse)
 * and single elements by id (this is enforced by tests).
 *
 * You also have to provide correct implementation for @see mini.java.basic.collections.test.DataLoader
 * and @see mini.java.basic.collections.test.DataMapper
 *
 * Hints:
 * - Basic map functionality was showcased in labs before
 * - You don't have to have only one map for caching
 * - Some methods are simpler to implement, some harder - as
 *   each is worth the same number of points, if you get stuck,
 *   see if you can implement next one.
 * - Think about what functions will need to clear caches and how.
 *
 * Scoring:
 * - Each passed test (with correct implementation)
 *   is according to its @difficulty annotation
 * @see DataRepositoryTest
 *
 * Entities:
 * @see SimpleDataRepository.Product
 * @see SimpleDataRepository.Warehouse
 */
public class CachingAndPagingDataRepository {
    private SimpleDataRepository dataRepository;
    private boolean allWarehouse = false;
    private boolean allProduct= false;
    private HashMap<Integer, SimpleDataRepository.Product> products = new HashMap<>();
    private HashMap<Integer, SimpleDataRepository.Warehouse> warehouses = new HashMap<>();

    public CachingAndPagingDataRepository() throws ProductDataMalformedException, NoDataException, WarehouseDataMalformedException, IOException {
        dataRepository = new SimpleDataRepository("dataset.csv");
    }

    /**
     * This function should not be changed!!!
     * @return Returns number of calls registered by fake database engine
     */
    public int getRepositoryCalls() {
        return dataRepository.getCalls();
    }

    /***
     * Returns all products from database. Can be used to pre-populate single element
     * cache (for use in getProductById(id)). Can also be used as a helper function in
     * other methods.
     * @see CachingAndPagingDataRepository#getProductsById(int)
     * @return List of all products
     */
    public Collection<SimpleDataRepository.Product> getAllProducts() {
        if(!this.allProduct){
            products = new HashMap<>();
            for(SimpleDataRepository.Product b: dataRepository.getProducts()){
                products.put(b.getId(), b);
           }
            allProduct = true;
        }
        return products.values();
    }
    /***
     * Returns all warehouses from database. Should fill products field in each retrieved warehouse element
     * @see CachingAndPagingDataRepository#getProductsById(int)
     * @return List of all warehouses
     */
    public Collection<SimpleDataRepository.Warehouse> getAllWarehouses() {
        if(!this.allWarehouse){
            this.getAllProducts();
            warehouses = new HashMap<>();
            for(SimpleDataRepository.Warehouse b: dataRepository.getWarehouses()){
                List<SimpleDataRepository.Product> prod = new ArrayList<>();
                for(Integer a: b.getProductIds()){
                    prod.add(products.get(a));
                }
                b.setProducts(prod);
                warehouses.put(b.getId(), b);
            }
            allWarehouse = true;
        }
        return warehouses.values();
    }

    /***;
     * Retrieves product from database. In best case scenario should be aware of getAllProducts() method.
     * @see CachingAndPagingDataRepository#getAllProducts()
     * @param i id of product to retrieve
     * @return Retrieved product
     */
    public SimpleDataRepository.Product getProductsById(int i) {
        if(!products.containsKey(i)) products.put(i, dataRepository.getProductById(i));
        return products.get(i);
    }

    /***
     * Returns all products from database, sorted by name.
     * @return List of products
     */
    public Collection<SimpleDataRepository.Product> getAllProductsSortedByName() {
        this.getAllProducts();
        List<SimpleDataRepository.Product> a = new ArrayList<>(products.values());
        Stream<SimpleDataRepository.Product> b = a.stream().sorted(new Comparator<SimpleDataRepository.Product>() {
            @Override
            public int compare(final SimpleDataRepository.Product o1, final SimpleDataRepository.Product o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return b.collect(Collectors.toList());
        }


    /***
     * Returns all products from database, filtered by expires true sorted by name.
     * @return List of products
     */
    public Collection<SimpleDataRepository.Product> getAllProductsFilteredByExpiresTrueAndSortedByName() {
        this.getAllProducts();
        Stream<SimpleDataRepository.Product> b = new ArrayList<SimpleDataRepository.Product>(products.values()).stream().filter(SimpleDataRepository.Product::isExpires);
        b = b.sorted(new Comparator<SimpleDataRepository.Product>() {
            @Override
            public int compare(final SimpleDataRepository.Product o1, final SimpleDataRepository.Product o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return b.collect(Collectors.toList());
    }


    /***
     * Returns page of products from database. Should behave gracefully - return empty
     * list - if page is out of bounds.
     * @param page Page number
     * @param pagesize Size of page
     * @return List of products
     */
    public Collection<SimpleDataRepository.Product> getProductsPage(int page, int pagesize) {
        this.getAllProducts();
        int tmp = page * pagesize + 1;
        List<SimpleDataRepository.Product> b = new ArrayList<>();
        for(int i = tmp; i < tmp + pagesize; i++){
            if(products.containsKey(i)) b.add(products.get(i));
            else return b;
        }
        return b;
    }

    /**
     * Updates product in database
     * @param i Id of product to update
     * @param product Product data to save to database
     */
    public void updateProduct(int i, SimpleDataRepository.Product product) {
        dataRepository.updateProduct(i, product);
        reset();
    }

    /**
     * Upserts product in database - inserts if id is missing, updates if id exists in database
     * @param product Product data to save to database
     */
    public void upsertProduct(SimpleDataRepository.Product product) {
        if(!dataRepository.upsertProduct(product)){
            allProduct = false;
        }
        else{
            reset();
        }
    }
    public void reset(){
            products = new HashMap<>();
            warehouses = new HashMap<>();
            allWarehouse = false;
            allProduct = false;
    }




}
