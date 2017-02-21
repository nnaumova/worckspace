package rover.command;

public class LoggingCommand implements RoverCommand {

	private RoverCommand currentCommand;

	public LoggingCommand(RoverCommand command) {
		this.currentCommand = command;
	}

	public void execute() {
		currentCommand.execute();

	}

	@Override
	public String toString() {
		return currentCommand.toString();
	}
}