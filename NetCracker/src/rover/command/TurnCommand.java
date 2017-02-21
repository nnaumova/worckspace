package rover.command;

import rover.Rover;
import rover.constants.Direction;

public class TurnCommand implements RoverCommand {
    Rover rover;
    Direction direction;

    public TurnCommand(Rover rover, Direction direction) {
        this.rover = rover;
        this.direction = direction;
    }

    public void execute() {
        rover.turnTo(direction);
    }

    @Override
    public String toString() {
        return "Direction " +  direction.name();
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof TurnCommand)) {
            return false;
        }
        else if (this.direction == ((TurnCommand) o).direction){
            return true;
        }
        else {
            return false;
        }
    }
}
