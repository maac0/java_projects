package mini.java.basic.arrays.test;

import java.util.Arrays;

public class PasswordGroup implements Nameable{
    private Password[] passwords;
    private String name;

    public PasswordGroup(Password ...  passwords) {
        this.passwords = passwords;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "PasswordGroup{" +
                "name='" + name + '\'' +
                ", passwords=" + Arrays.toString(passwords) +
                '}';
    }
    public boolean check(String o, int ... password) {
        for (Password value : passwords) {
            if (value instanceof PinPassword) {
                PinPassword a = (PinPassword) value;
                if (a.getName().equals(o) && Arrays.equals(a.getPassword(), password)) return true;
            }
        }
        return false;
    }
    public boolean check(String o, String password){
        for (Password value : passwords) {
            if(value instanceof TextPassword)
            {
                TextPassword a = (TextPassword) value;
                if(value.getName().equals(o) && a.getPassword().equals(password)) return true;
            }
        }
        return false;
    }
}