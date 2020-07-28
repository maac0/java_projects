package mini.java.basic.arrays.test;
import java.util.Arrays;

class PinPassword extends Password{
    private int[] password; // PIN
    public PinPassword(int ... password) {
        this.password = password;
    }

    public int[] getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "PinPassword{" +
                "password=" + "****" +
                ", name='" + name +'\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PinPassword that = (PinPassword) o;
        return Arrays.equals(password, that.password);
    }
    @Override
    public int hashCode() {
        return Arrays.hashCode(password);
    }

    boolean check(Object password){
        if(password instanceof int[]) return Arrays.equals(this.password, (int[]) password);
        else return false;
    }

}