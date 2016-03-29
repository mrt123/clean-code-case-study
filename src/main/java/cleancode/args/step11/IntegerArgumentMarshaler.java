package cleancode.args.step11;

import java.util.Iterator;

public class IntegerArgumentMarshaler implements ArgumentMarshaler {

    private int intValue = 0;

    // step11
    public void set(Iterator<String> currentArgument) {

        String parameter = null;

        try {
            parameter = currentArgument.next();
            intValue = Integer.parseInt(parameter);
        } catch (ArrayIndexOutOfBoundsException e) {
            // valid = false;
            // throw new ArgsException();
        } catch (NumberFormatException e) {
            //valid = false;
            //errorCode = ErrorCode.INVALID_INTEGER; throw e;
        }
    }

    public Object get() {
        return intValue;
    }
}