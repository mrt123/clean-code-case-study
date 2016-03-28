package cleancode.args.step08; // desc:   delete specific Marshaller maps (sub part2)

import lombok.Data;

import java.text.ParseException;
import java.util.*;

public @Data
class Args {
    private String schema;
    private String[] args;
    private boolean valid = true;
    private Set<Character> unexpectedArguments = new TreeSet<Character>();

    private Map<Character, ArgumentMarshaler> marshalers = new HashMap<Character, ArgumentMarshaler>();   // step08

    private Set<Character> argsFound = new HashSet<Character>();
    private int currentArgument;
    private char errorArgument = '\0';

    enum ErrorCode {
        OK, MISSING_STRING, MISSING_INTEGER, INVALID_INTEGER
    }

    private ErrorCode errorCode = ErrorCode.OK;

    public Args(String schema, String[] args) throws ParseException {
        this.schema = schema;
        this.args = args;
        valid = parse();
    }

    public boolean isValid() {
        return valid;
    }

    private boolean parse() throws ParseException {
        if (schema.length() == 0 && args.length == 0)
            return true;
        parseSchema();
        parseArguments();
        return valid;
    }

    private boolean parseSchema() throws ParseException {
        for (String element : schema.split(",")) {  // schema = "l,p#,d*"
            if (element.length() > 0) {
                String trimmedElement = element.trim();
                parseSchemaElement(trimmedElement);
            }
        }
        return true;
    }

    private void parseSchemaElement(String element) throws ParseException {
        char elementId = element.charAt(0);  // element = "l" or "d*"
        String elementTail = element.substring(1);   // elementTail = ""  or "*"
        validateSchemaElementId(elementId);  // elementId = "l" or "d"
        if (isBooleanSchemaElement(elementTail)) parseBooleanSchemaElement(elementId);  // true if elementTail =""
        else if (isStringSchemaElement(elementTail)) parseStringSchemaElement(elementId);  // true if elementTail ="*"
        else if (isIntegerSchemaElement(elementTail)) parseIntegerSchemaElement(elementId);  // true if elementTail ="#"
    }

    private void validateSchemaElementId(char elementId) throws ParseException {
        if (!Character.isLetter(elementId)) {
            throw new ParseException(
                    "Bad character:" + elementId + "in Args format: " + schema, 0);
        }
    }

    private void parseStringSchemaElement(char elementId) {
        marshalers.put(elementId, new StringArgumentMarshaler());   // step08
    }

    private boolean isStringSchemaElement(String elementTail) {
        return elementTail.equals("*");
    }

    private boolean isBooleanSchemaElement(String elementTail) {
        return elementTail.length() == 0;
    }

    private void parseBooleanSchemaElement(char elementId) {
        marshalers.put(elementId, new BooleanArgumentMarshaler());   // step08
    }


    private boolean isIntegerSchemaElement(String elementTail) {
        return elementTail.equals("#");
    }


    private void parseIntegerSchemaElement(char elementId) {
        marshalers.put(elementId, new IntegerArgumentMarshaler());   // step08
    }

    private boolean parseArguments() {
        for (currentArgument = 0; currentArgument < args.length; currentArgument++) {
            String arg = args[currentArgument];
            parseArgument(arg);
        }
        return true;
    }

    private void parseArgument(String arg) {
        if (arg.startsWith("-"))
            parseElements(arg);

    }

    private void parseElements(String arg) {
        for (int i = 1; i < arg.length(); i++)
            parseElement(arg.charAt(i));   // arg.charAt(i)   = 'l' or 'p' or 'd'
    }

    private void parseElement(char argChar) {
        if (setArgument(argChar))
            argsFound.add(argChar);
        else {
            unexpectedArguments.add(argChar);
            valid = false;
        }
    }

    // step08
    private boolean setArgument(char argChar) {
        ArgumentMarshaler m = marshalers.get(argChar);
        if (m instanceof BooleanArgumentMarshaler)
            setBooleanArg(m);   // init the booleanArgs Marshaller
        else if (m instanceof StringArgumentMarshaler)
            setStringArg(m);
        else if (m instanceof IntegerArgumentMarshaler)
            setIntArg(m);
        else
            return false;
        return true;
    }

    private void setStringArg(ArgumentMarshaler m) {         // step08
        currentArgument++;   // increment to slide, eg: from "d" to "dirName"
        try {
            m.set(args[currentArgument]);                 // step08
        } catch (ArrayIndexOutOfBoundsException e) {
            valid = false;
            errorCode = ErrorCode.MISSING_STRING;
        }
    }

    // step08
//    private boolean isInt(ArgumentMarshaler m) {
//        return m instanceof IntegerArgumentMarshaler;   // step08 - changed to pass test before this functionality was in-lined
//    }


    private void setIntArg(ArgumentMarshaler m) { currentArgument++;
        String parameter = null;
        try {
            parameter = args[currentArgument];
            m.set(parameter);
        } catch (ArrayIndexOutOfBoundsException e) {
            valid = false;
            errorCode = ErrorCode.MISSING_INTEGER;
        } catch (NumberFormatException e) { valid = false;
            valid = false;
            errorCode = ErrorCode.INVALID_INTEGER;
        }
    }

//    private boolean isString(ArgumentMarshaler m) {
//        return m instanceof StringArgumentMarshaler;// step08 - changed to pass test before this functionality was in-lined
//    }

    private void setBooleanArg(ArgumentMarshaler m) {
        m.set("true");
    }

//    private boolean isBoolean(ArgumentMarshaler m) {
//        return m instanceof BooleanArgumentMarshaler;   // step08 - changed to pass test before this functionality was in-lined
//    }

    public int cardinality() {
        return argsFound.size();
    }

    public String usage() {
        if (schema.length() > 0)
            return "-[" + schema + "]";
        else
            return "";
    }

    public String errorMessage() throws Exception {
        if (unexpectedArguments.size() > 0) {
            return unexpectedArgumentMessage();
        } else
            switch (errorCode) {
                case MISSING_STRING:
                    return String.format("Could not find string parameter for -%c.",
                            errorArgument);
                case MISSING_INTEGER:
                    return String.format("Could not find integer parameter for -%c.",
                            errorArgument);
                case INVALID_INTEGER:
                    return String.format("Could not parse integer parameter for -%c.",
                            errorArgument);
                case OK:
                    throw new Exception("TILT: Should not get here.");
            }
        return "";
    }

    private String unexpectedArgumentMessage() {
        StringBuffer message = new StringBuffer("Argument(s) -");
        for (char c : unexpectedArguments) {
            message.append(c);
        }
        message.append(" unexpected.");
        return message.toString();
    }

    // step08
    public boolean getBoolean(char arg) {
        ArgumentMarshaler am = marshalers.get(arg);
        boolean b;
        try {
            b = am != null && (Boolean) am.get();
        } catch (ClassCastException e) {            // step08 decided to deal with the ClassCastException (say acceptance test failed)
            b = false;
        }
        return b;
    }

    public String getString(char arg) {
        ArgumentMarshaler am = marshalers.get(arg);        // step08
        return am == null ? "" : (String) am.get();
    }

    public int getInt(char arg) {
        ArgumentMarshaler am = marshalers.get(arg);      // step08
        return am == null ? 0 : (Integer)am.get();
    }

    public boolean has(char arg) {
        return argsFound.contains(arg);
    }

    public static void main(String[] args) {
        try {
            Args arg = new Args("l,p#,d*", args);   // example args: "-l true -p 8080 -d folder1"

            boolean logging = arg.getBoolean('l');
            String directory = arg.getString('d');

            System.out.println(String.format("logging = " + logging + ", dir = " + directory));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}