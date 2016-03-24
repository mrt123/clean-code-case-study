package cleancode.args.step03;   // TODO: description of step here

import lombok.Data;

import java.text.ParseException;
import java.util.*;

public @Data
class Args {
    private String schema;
    private String[] args;
    private boolean valid = true;     // step02
    private Set<Character> unexpectedArguments = new TreeSet<Character>();
    private Map<Character, Boolean> booleanArgs = new HashMap<Character, Boolean>();

    private Map<Character, String> stringArgs = new HashMap<Character, String>();   // step02

    private Set<Character> argsFound = new HashSet<Character>();    // step02
    private int currentArgument;                                     // step02
    private char errorArgument = '\0';                               // step02

    enum ErrorCode {                 // step02
        OK, MISSING_STRING
    }

    private ErrorCode errorCode = ErrorCode.OK;          // step02

    public Args(String schema, String[] args) throws ParseException {
        this.schema = schema;
        this.args = args;
        valid = parse();
    }

    public boolean isValid() {
        return valid;
    }

    private boolean parse() throws ParseException {   // step02 now throws ParseException
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

    private void parseSchemaElement(String element) throws ParseException {  // step02 now throws ParseException
        char elementId = element.charAt(0);  // element = "l" or "d*"
        String elementTail = element.substring(1);   // elementTail = ""  or "*"
        validateSchemaElementId(elementId);  // elementId = "l" or "d"
        if (isBooleanSchemaElement(elementTail)) parseBooleanSchemaElement(elementId);  // true if elementTail =""
        else if (isStringSchemaElement(elementTail)) parseStringSchemaElement(elementId);  // true if elementTail ="*"
    }

    private void validateSchemaElementId(char elementId) throws ParseException {
        if (!Character.isLetter(elementId)) {
            throw new ParseException(
                    "Bad character:" + elementId + "in Args format: " + schema, 0);
        }
    }

    private void parseStringSchemaElement(char elementId) {
        stringArgs.put(elementId, "");
    }

    private boolean isStringSchemaElement(String elementTail) {
        return elementTail.equals("*");
    }

    private boolean isBooleanSchemaElement(String elementTail) {  // step02 new method (previously one liner)
        return elementTail.length() == 0;
    }

    private void parseBooleanSchemaElement(char elementId) {
        booleanArgs.put(elementId, false);
    }

    private boolean parseArguments() {
        for (currentArgument = 0; currentArgument < args.length; currentArgument++) {  // step02 introduced currentArgument
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

    private boolean setArgument(char argChar) {   // step02 - new method
        boolean set = true;
        if (isBoolean(argChar))
            setBooleanArg(argChar, true);   // init the booleanArgs hashMap
        else if (isString(argChar))
            setStringArg(argChar, "");
        else
            set = false;
        return set;
    }

    private void setStringArg(char argChar, String s) {   // step02 - new method
        currentArgument++;   // increment to slide, eg: from "d" to "dirName"
        try {
            stringArgs.put(argChar, args[currentArgument]);
        } catch (ArrayIndexOutOfBoundsException e) {
            valid = false;
            errorArgument = argChar;
            errorCode = ErrorCode.MISSING_STRING;
        }
    }

    private boolean isString(char argChar) {
        return stringArgs.containsKey(argChar);
    }

    private void setBooleanArg(char argChar, boolean value) {
        booleanArgs.put(argChar, value);
    }

    private boolean isBoolean(char argChar) {
        return booleanArgs.containsKey(argChar);
    }

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

    public boolean getBoolean(char arg) {
        return falseIfNull(booleanArgs.get(arg));
    }

    private boolean falseIfNull(Boolean b) {
        return b == null ? false : b;
    }

    public String getString(char arg) {
        return blankIfNull(stringArgs.get(arg));
    }

    private String blankIfNull(String s) {
        return s == null ? "" : s;
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