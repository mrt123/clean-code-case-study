package cleancode.args.step08;

public class IntegerArgumentMarshaler extends ArgumentMarshaler {

    private int intValue = 0;

    public void set(String s) {
        intValue = Integer.valueOf(s);   // book uses Integer.parseInt() and handles NumberFormatException here
    }

    public Object get() {
        return intValue;
    }
}