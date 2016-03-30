package cleancode.args.step12;

import java.util.Iterator;

public interface ArgumentMarshaler {

    void set(Iterator<String> currentArgument);

    Object get();
}
