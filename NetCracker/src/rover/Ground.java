package rover;

public class Ground {

	private GroundCell[][] landscape;
	private int xSize;
	private int ySize;

	public Ground(int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		landscape = new GroundCell[xSize][ySize];
	}

	public void initialize(GroundCell... args) throws IllegalArgumentException {

		int numCells = xSize * ySize;

		if (args.length < numCells) {
			throw new IllegalArgumentException();
		} else {
			int i = -1;
			for (int y = 0; y < ySize; y++) {
				for (int x = 0; x < xSize; x++) {
					i++;
					landscape[x][y] = args[i];
				}
			}
		}
	}

	public GroundCell getCell(int x, int y) throws OutOfGroundException {
		if (!isReachableCell(x, y))
			throw new OutOfGroundException();
		else
			return landscape[x][y];
	}

	protected boolean isReachableCell(int x, int y) {
		if (x >= 0 && x < xSize && y >= 0 && y < ySize)
			return true;
		else
			return false;
	}

}
