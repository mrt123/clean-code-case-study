package cleancode.args.step11;

import java.util.Iterator;

public class StringArgumentMarshaler implements ArgumentMarshaler {

    private String stringValue = "";

    // step11
    public void set(Iterator<String> currentArgument) {
        stringValue = currentArgument.next();
    }

    public Object get() {
        return stringValue;
    }
}