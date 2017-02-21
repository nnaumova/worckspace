import static org.junit.Assert.*;

import org.junit.Test;


import org.junit.Test;

import rover.GroundVisor;
import rover.OutOfGroundException;
import rover.Point;
import static org.junit.Assert.*;

public class GroundVisorTest extends AbstractRoverTest {

    private GroundVisor testedInstance = new GroundVisor(GROUND_2x2);

    @Test
    public void testHasObstaclesReturnsTrueOnOccupiedCell() throws OutOfGroundException {
        assertTrue("Cell (0,1) must be occupied", testedInstance.hasObstacles(new Point(0, 1)));
    }

    @Test
    public void testHasObstaclesReturnsFalseOnFreeCell() throws OutOfGroundException {
        assertFalse("Cell (0,0) must be free", testedInstance.hasObstacles(new Point(0, 0)));
    }

}