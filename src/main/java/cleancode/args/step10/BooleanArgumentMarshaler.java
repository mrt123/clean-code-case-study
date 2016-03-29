package cleancode.args.step10;

public class BooleanArgumentMarshaler extends ArgumentMarshaler {
    private boolean booleanValue = false;

    public void set(String s) {
        booleanValue = true;    // step06 - as per book
    }

    public Object get() {
        return booleanValue;
    }
}