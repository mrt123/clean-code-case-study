package cleancode.args.step06;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ArgsTest {

    Args testedObject;
    final String validSchema = "l,p#,d*";
    final String[] validCommandArgs = {"-l", "true", "-d", "folder1", "-p", "8080"};

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
        char existingArgument3= 'p';
        char nonExistingArgument = 'y';

        assertEquals(true, testedObject.getBoolean(existingArgument1));
        assertEquals(false, testedObject.getBoolean(nonExistingArgument));

        assertEquals("folder1", testedObject.getString(existingArgument2));
        assertEquals(8080, testedObject.getInt(existingArgument3));

        // not verifying call to falseIfNull since its private
    }

    @Test
    public void getString() {
        assertEquals("folder1", testedObject.getString('d'));
        assertEquals("", testedObject.getString('x'));
        // not verifying call to falseIfNull since its private
    }
}
