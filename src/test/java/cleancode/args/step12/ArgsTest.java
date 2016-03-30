package cleancode.args.step12;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * step10 conclusions:
 * - test only public methods
 * - test should never consider internals of a method
  */



public class ArgsTest {

    Args validArgs, invalidArgs;

    @org.junit.Before
    public void setUp() throws Exception {
        // ARRANGE
        String schema1 = "l,p#,d*";
        String[] args1 = {"-l", "true", "-d", "folder1", "-p", "8080"};

        String schema2 = "l,p#,z*";
        String[] args2 = {"-l", "true", "-d", "folder1", "-p", "8080"};

        // ACT
        validArgs = new Args(schema1, args1);
        invalidArgs = new Args(schema2, args2);
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    // step10 - modified constructor test
    @Test
    public void isValid() throws Exception {
        assertEquals(true, validArgs.isValid());
        assertEquals(false, invalidArgs.isValid());
    }

    @Test
    public void getBoolean() {
        char existingArgument1 = 'l';
        char nonExistingArgument = 'y';

        assertEquals(true, validArgs.getBoolean(existingArgument1));
        assertEquals(false, validArgs.getBoolean(nonExistingArgument));

    }

    @Test
    public void getString() {
        assertEquals("folder1", validArgs.getString('d'));
        assertEquals("", validArgs.getString('x'));
    }

    @Test
    public void getInt() {
        assertEquals(8080, validArgs.getInt('p'));
        assertEquals(0, validArgs.getInt('x'));
    }

    @Test    // straight from the book step12
    public void testSimpleDoublePresent() throws Exception {
        Args args = new Args("x##", new String[] {"-x","42.3"});
        assertTrue(args.isValid());
        assertEquals(1, args.cardinality());
        assertTrue(args.has('x'));
        assertEquals(42.3, args.getDouble('x'), .001); }
}
