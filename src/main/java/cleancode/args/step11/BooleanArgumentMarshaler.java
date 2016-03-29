package cleancode.args.step11;

import java.util.Iterator;

public class BooleanArgumentMarshaler implements ArgumentMarshaler {
    private boolean booleanValue = false;

    // step11
    public void set(Iterator<String> currentArgument) {
        booleanValue = true;
    }

    public Object get() {
        return booleanValue;
    }
}