package cleancode.args.step08;


public class StringArgumentMarshaler extends ArgumentMarshaler {

    private String stringValue = "";

    public void set(String s) {
        stringValue = s;
    }

    public Object get() {
        return stringValue;
    }
}