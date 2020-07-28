package mini.java.basic.arrays.test;
import java.util.Objects;

class TextPassword extends Password{
    private String password;

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "TextPassword{" +
                "password=" + "****" +
                ", name='" + name + '\'' +
                '}';
    }


    public TextPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextPassword that = (TextPassword) o;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
    @Override
    boolean check(Object password) {
        if(password instanceof String) return this.password.equals(password);
        else return false;
    }
}
