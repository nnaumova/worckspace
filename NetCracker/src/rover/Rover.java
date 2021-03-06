package rover;

import rover.constants.Direction;

public class Rover implements Moveable, Turnable, Liftable, Landable {

	private Point roverPosition;
	private Direction roverDirection;
	private boolean inAir;
	private GroundVisor visor;

	public Rover(GroundVisor visor) {
		inAir = false;
		roverPosition = new Point(0, 0);
		roverDirection = Direction.SOUTH;
		this.visor = visor;
	}

	@Override
	public void land(Point position, Direction direction) {
		if (inAir) {
			try {
				if (!visor.hasObstacles(position)) {
					roverPosition = position;
					roverDirection = direction;
					inAir = false;
				}
			} catch (OutOfGroundException e) {
				/* TODO: add logging */
			}
		}

	}

	@Override
	public void lift() {
		if (!inAir) {
			inAir = true;
			roverPosition = null;
			roverDirection = null;
		}

	}

	@Override
	public void turnTo(Direction newDirection) {
		roverDirection = newDirection;

	}

	@Override
	public void move() {
		 if (roverPosition != null || roverDirection != null) {
		int x = roverPosition.getX();
		int y = roverPosition.getY();

		switch (roverDirection) {
		case SOUTH:
			y++;
			break;
		case NORTH:
			y--;
			break;
		case WEST:
			x--;
			break;
		case EAST:
			x++;
			break;

		}
		Point newPosition = new Point(x, y);
		try {
			if (!visor.hasObstacles(newPosition)) {
				roverPosition = newPosition;
			}
		} catch (OutOfGroundException e) {
			lift();
		}
		 }

	}

	public Point getCurrentPosition() {

		return roverPosition;
	}

	public Direction getDirection() {

		return roverDirection;
	}

	 public boolean isAirborne() {
		         return inAir;
		     }

}
