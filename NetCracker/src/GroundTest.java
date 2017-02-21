import static org.junit.Assert.*;

import org.junit.Test;

import static rover.constants.CellState.*;

import org.junit.Test;

import rover.Ground;
import rover.GroundCell;
import rover.OutOfGroundException;


public class GroundTest extends AbstractRoverTest {

	@Test
	public void testInitializeSquareGround() throws OutOfGroundException {
		assertSquareGround(GROUND_2x2);
	}

	@Test
	public void testInitializeOnExcessiveParametersCount()
			throws OutOfGroundException {
		Ground ground = new Ground(2, 2);

		ground.initialize(new GroundCell(FREE), new GroundCell(FREE),
				new GroundCell(OCCUPIED), new GroundCell(OCCUPIED),
				new GroundCell(FREE));

		assertSquareGround(ground);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInitializeFailsOnInsufficientParameters() {
		Ground ground = new Ground(2, 2);

		ground.initialize(new GroundCell(FREE), new GroundCell(FREE),
				new GroundCell(OCCUPIED));
	}

	@Test(expected = OutOfGroundException.class)
	public void testGetCellThrowsExceptionOnExcessiveWidth() throws Exception {
		GROUND_2x2.getCell(5, 1);
	}

	@Test(expected = OutOfGroundException.class)
	public void testGetCellThrowsExceptionOnExcessiveLength() throws Exception {
		GROUND_2x2.getCell(1, 5);
	}

	@Test(expected = OutOfGroundException.class)
	public void testGetCellThrowsExceptionOnNegativeX() throws Exception {
		GROUND_2x2.getCell(-5, 1);
	}

	@Test(expected = OutOfGroundException.class)
	public void testGetCellThrowsExceptionOnNegativeY() throws Exception {
		GROUND_2x2.getCell(1, -5);
	}
}