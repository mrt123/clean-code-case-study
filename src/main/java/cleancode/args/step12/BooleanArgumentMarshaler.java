package cleancode.args.step12;

import java.util.Iterator;

public class BooleanArgumentMarshaler implements ArgumentMarshaler {
    private boolean booleanValue = false;

    public void set(Iterator<String> currentArgument) {
        booleanValue = true;
    }

    public Object get() {
        return booleanValue;
    }
}