package rover.programmable;


import java.util.Map;

import rover.GroundVisor;
import rover.Point;
import rover.Rover;
import rover.command.RoverCommand;
import rover.constants.Direction;
import stats.SimpleRoverStatsModule;

/**
 * Этот класс должен уметь все то, что умеет обычный Rover, но при этом он еще должен уметь выполнять программы,
 * содержащиеся в файлах
 */
public class ProgrammableRover extends Rover implements ProgramFileAware {
    private SimpleRoverStatsModule simpleRoverStatsModule;
    private RoverProgram roverProgram;

    public ProgrammableRover(GroundVisor groundVisor, SimpleRoverStatsModule simpleRoverStatsModule) {
        super(groundVisor);
        this.simpleRoverStatsModule = simpleRoverStatsModule;
    }

    public void executeProgramFile(String path) {
        RoverCommandParser roverCommandParser = new RoverCommandParser(this, path);
        roverProgram = roverCommandParser.getProgram();

        for(RoverCommand command : roverProgram.getCommands()) {
            command.execute();
        }
    }

    public void move() {
        super.move();

        if ((boolean) this.getSettings().get(RoverProgram.STATS)) {
            simpleRoverStatsModule.registerPosition(getCurrentPosition());
        }
    }

    public void turnTo(Direction direction) {
        super.turnTo(direction);
    }

    public void lift() {
        super.lift();
    }

    public void land(Point position, Direction direction) {
        super.land(position, direction);
    }

    public Point getCurrentPosition() {
        return super.getCurrentPosition();
    }

    public Direction getDirection() {
        return super.getDirection();
    }
 
    public Map<String, Object> getSettings() {
        return roverProgram.getSettings();
    }
 }