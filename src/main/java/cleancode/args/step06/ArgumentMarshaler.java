package cleancode.args.step06;

public abstract class ArgumentMarshaler {   // step06 - made class abstract
    // step06 - changed private to protected - once tests passed move do specific ArgMarshaller and removed getters and setters for specific args
    // protected boolean booleanValue;
    // protected String stringValue;
    // protected int integerValue;

    public abstract void set(String s);  // step06

    public abstract Object get(); // step06
}
