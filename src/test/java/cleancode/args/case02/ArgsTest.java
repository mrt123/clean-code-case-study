package cleancode.args.case02;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArgsTest {

    Args testedObject;
    final String validSchema = "l,p#,d*";
    final String[] validCommandArgs = {"-l", "true", "-d", "folder1"};

    @org.junit.Before
    public void setUp() throws Exception {
        testedObject = new Args("l,p#,d*", validCommandArgs);
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @Test
    public void constructor() {
        assertEquals(validSchema, testedObject.getSchema());
        assertArrayEquals(validCommandArgs, testedObject.getArgs());
    }

    @Test
    public void isValid() {
        assertEquals(true, testedObject.isValid());
        testedObject.setValid(false);
        assertEquals(false, testedObject.isValid());
    }

    @Test
    public void getBoolean() {
        assertEquals(true, testedObject.getBoolean('l'));
        assertEquals(false, testedObject.getBoolean('d'));
        assertEquals(false, testedObject.getBoolean('x'));
        // not verifying call to falseIfNull since its private
    }

    @Test
    public void getString() {
        assertEquals("folder1", testedObject.getString('d'));
        assertEquals("", testedObject.getString('x'));
        // not verifying call to falseIfNull since its private
    }
}
