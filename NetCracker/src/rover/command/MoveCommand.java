package rover.command;

import rover.Rover;
import rover.programmable.ProgrammableRover;

public class MoveCommand implements RoverCommand {
	Rover rover;

	public MoveCommand(Rover rover) {
		this.rover = rover;
	}

	public MoveCommand(ProgrammableRover rover2) {
		// TODO Auto-generated constructor stub
	}

	public void execute() {
		rover.move();
	}

	@Override
	public String toString() {
		return "Rover moved";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MoveCommand)) {
			return false;
		} else {
			return true;
		}
	}
}