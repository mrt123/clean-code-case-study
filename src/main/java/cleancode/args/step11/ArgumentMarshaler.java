package cleancode.args.step11;

import java.util.Iterator;

public interface ArgumentMarshaler {  // step11 changed to interface

    void set(Iterator<String> currentArgument);  // step11

    Object get();
}
