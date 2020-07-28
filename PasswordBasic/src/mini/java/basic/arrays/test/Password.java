package mini.java.basic.arrays.test;

abstract class Password implements Nameable{
    protected String name;
    abstract boolean check(Object password);

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}

