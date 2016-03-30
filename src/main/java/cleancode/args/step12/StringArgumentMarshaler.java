package cleancode.args.step12;

import java.util.Iterator;

public class StringArgumentMarshaler implements ArgumentMarshaler {

    private String stringValue = "";

    public void set(Iterator<String> currentArgument) {
        stringValue = currentArgument.next();
    }

    public Object get() {
        return stringValue;
    }
}