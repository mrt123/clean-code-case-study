package cleancode.args.step05;

public class ArgumentMarshaler {
    private boolean booleanValue = false;
    private String stringValue;   // step04

    public void setBoolean(boolean value) {
        booleanValue = value;
    }

    public boolean getBoolean() {
        return booleanValue;
    }

    public void setString(String s) {
        stringValue = s;
    }


    // step04
    public String getString() {
        return stringValue == null ? "" : stringValue;
    }
}
