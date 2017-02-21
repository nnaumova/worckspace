package rover.programmable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import rover.command.RoverCommand;

public class RoverProgram {
    public static final String LOG = "log";
    public static final String STATS = "stats";
    public static final String SEPARATOR = "===";
    
    protected Map<String, Object> settings;
        protected Collection<RoverCommand> commands;
    
        public RoverProgram() {
            settings = new HashMap<String, Object>();
            commands = new ArrayList<RoverCommand>();
        }
    
        public final Map<String, Object> getSettings() {
            return Collections.unmodifiableMap(settings);
        }
    
        public Collection<RoverCommand> getCommands() {
            return commands;
        }
     
}