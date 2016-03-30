package cleancode.args.step12;

import java.util.Iterator;
import java.util.NoSuchElementException;

// step12 - new Class
public class DoubleArgumentMarshaler implements ArgumentMarshaler {
    private double doubleValue = 0;

    public void set(Iterator<String> currentArgument) {
        String parameter = null;

        try {
            parameter = currentArgument.next();
            doubleValue = Double.parseDouble(parameter);
        } catch (NoSuchElementException e) {
//            errorCode = ErrorCode.MISSING_DOUBLE;
//            throw new ArgsException();
        } catch (NumberFormatException e) {
//            errorParameter = parameter; errorCode = ErrorCode.INVALID_DOUBLE; throw new ArgsException();
        }
    }

    public Object get() {
        return doubleValue;
    }
}