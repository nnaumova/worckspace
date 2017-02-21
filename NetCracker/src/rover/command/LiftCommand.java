package rover.command;

import rover.Rover;

public class LiftCommand implements RoverCommand {
    Rover rover;

    public LiftCommand(Rover rover) {
        this.rover = rover;
    }

    public void execute() {
    }

    @Override
    public String toString() {
        return "Rover lifted";
    }

    @Override
    public boolean equals(Object o) {
 
        if (!(o instanceof LiftCommand)) {
            return false;
        }
        else {
            return true;
        }
    }
 }