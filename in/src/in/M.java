package in;

public class M {
	

	public static void main(String[] args) {
		int ySize = 2;
		int xSize = 3;
		int land[][] = new int[xSize][ySize];
		int a[] = {1,2,3,4,5,6};
		int i=-1;
		for(int y=0; y<ySize; y++){
			for(int x=0; x<xSize; x++){
				i++;
				land[x][y]=a[i];
				System.out.println("("+x+","+y+") "+a[i]);
				}

	}

}
}
