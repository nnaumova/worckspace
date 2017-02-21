package rover;

import rover.constants.CellState;

public class GroundVisor {
	private Ground ground;

	public GroundVisor(Ground ground) {
		this.ground = ground;
	}

	public boolean hasObstacles(Point point) throws OutOfGroundException {
		GroundCell cell = ground.getCell(point.getX(), point.getY());

		return (cell.getState() == CellState.OCCUPIED);
	}

}
