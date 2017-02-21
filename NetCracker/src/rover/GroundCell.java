package rover;

import rover.constants.CellState;

public class GroundCell {
	
	private CellState state;
	
	public GroundCell(CellState state){
		this.state = state;
	}

	public CellState getState(){
		return state;
	}

}
