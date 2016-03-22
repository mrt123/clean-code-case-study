package cleancode.args.case02;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArgsTest {

    Args args;

    @org.junit.Before
    public void setUp() throws Exception {
        String[] commandArgs = {"-l", "true", "-d", "folder1"};
        args = new Args("l,p#,d*", commandArgs);
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @Test
    public void isValid() {

        assertEquals(true, args.isValid());
        args.setValid(false);
        assertEquals(false, args.isValid());
    }
}
