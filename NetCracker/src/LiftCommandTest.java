import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.Test;

import rover.command.LiftCommand;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LiftCommandTest extends AbstractCommandTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
        testedInstance = new LiftCommand(rover);
    }

    @Override
    protected String expectedToString() {
        return "Rover lifted";
    }

    @Test
    public void testLift() {
        testedInstance.execute();

        verify(rover, times(1)).lift();
    }

}