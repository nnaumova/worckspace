package rover.command;



import rover.Point;
import rover.Rover;
import rover.constants.Direction;

public class LandCommand implements RoverCommand {
	 
    Rover rover;
    Point point;
    Direction direction;

    public LandCommand(Rover rover, Point point, Direction direction) {
        this.rover = rover;
        this.point = point;
        this.direction = direction;
    }

    public void execute() {
        rover.land(point, direction);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Land at ");
        sb.append(point.toString());
        sb.append(" with direction ");
        sb.append(direction.name());

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof LandCommand)) {
            return false;
        }
        else if (this.point.equals(( (LandCommand) o).point) &&
                 this.direction == ((LandCommand) o).direction){
            return true;
        }
        else {
            return false;
        }
    }
 }