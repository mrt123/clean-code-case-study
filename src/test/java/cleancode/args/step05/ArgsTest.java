package cleancode.args.step05;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
        char existingArgument1 = 'l';
        char existingArgument2= 'd';
        char nonExistingArgument = 'y';

        assertEquals(true, testedObject.getBoolean(existingArgument1));
        assertEquals(false, testedObject.getBoolean(existingArgument2));
        assertEquals(false, testedObject.getBoolean(nonExistingArgument));
        // not verifying call to falseIfNull since its private
    }

    @Test
    public void getString() {
        assertEquals("folder1", testedObject.getString('d'));
        assertEquals("", testedObject.getString('x'));
        // not verifying call to falseIfNull since its private
    }
}
