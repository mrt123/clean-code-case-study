package cleancode.args.step05;

public class ArgumentMarshaler {
    private boolean booleanValue = false;
    private String stringValue;
    private int integerValue;     // step05


    public void setBoolean(boolean value) {
        booleanValue = value;
    }

    public boolean getBoolean() {
        return booleanValue;
    }

    public void setString(String s) {
        stringValue = s;
    }

    public String getString() {
        return stringValue == null ? "" : stringValue;
    }

    // step05
    public void setInteger(int i) {
        integerValue = i;
    }

    // step05
    public int getInteger() {
        return integerValue;
    }
}
