import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public class CsvReadFile {
	
	final static int sizeVector = 80; //10 20 60
	final static String classString = "1 0 0 0";
	final static String letter = "u";
	final static String country = "usa";
	final static int numCountries = 230; //230 300 300 340

	public ArrayList<Stocks> read(String fileName, int n) {
		ArrayList<Stocks> instanses = new ArrayList<Stocks>();
		for (int i = 1; i <= n; i++) {
			instanses.add(new Stocks());
			try {
				CsvReader reader = new CsvReader(fileName + letter + " (" + i + ").csv");
				reader.setDelimiter(',');
				reader.readHeaders();
				// System.out.println(reader.getHeaderCount() );
				reader.readRecord();

				for (; reader.readRecord();) {
					instanses.get(i - 1).setDate(reader.get("Date"));
					instanses.get(i - 1).setOpen(
							Double.valueOf(reader.get("Open")));
					instanses.get(i - 1).setClose(
							Double.valueOf(reader.get("Close")));
					// String date = reader.get("Date");
					// Double open = Double.valueOf(reader.get("Open"));
					// Double close = Double.valueOf(reader.get("Close"));

				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instanses;
	}

	public static ArrayList<Stocks> normalize(ArrayList<Stocks> instanses) {
		ArrayList<Stocks> normInstanses = new ArrayList<Stocks>();
		for (int i = 0; i < instanses.size(); i++) {
			normInstanses.add(new Stocks());

			for (int j = 0; j < instanses.get(0).getSize(); j++) {
				String date = instanses.get(0).getDate(j);
				int flag = 0;
				for (int t = 0; t < instanses.get(i).getSize(); t++) {
					if (date.equals(instanses.get(i).getDate(t))) {
						normInstanses.get(i).setDate(date);
						normInstanses.get(i).setOpen(
								instanses.get(i).getOpen(t));
						normInstanses.get(i).setClose(
								instanses.get(i).getClose(t));
						flag = 1;
						// System.out.println(date + " xxx" );
					}

				}
				if (flag == 0) {
					normInstanses.get(i).setDate(date);
					normInstanses.get(i).setOpen(0.0);
					normInstanses.get(i).setClose(0.0);
					// System.out.println(date + " 00" );
				}

				// String [] d1 = instanses.get(0).getDate(t).split("-");
				// String [] d2 = instanses.get(i).getDate(j).split("-");
				// normInstanses.get(i).setDate(instanses.get(0).getDate(j));
				// if(getIntVal(d1[2])==getIntVal(d2[2])){

				// }
			}
		}

		return normInstanses;
	}

	public static int getIntVal(String str) {
		char[] st = str.toCharArray();
		int num = 0;
		if (st[0] == '0') {
			num = Integer.valueOf(st[1]);
		} else
			num = Integer.valueOf(str);
		return num;
	}

	public static ArrayList<ArrayList<Double>> getAllCorrelations(
			ArrayList<Stocks> normBrazil) {

		int numDays = 300;
		int step = 5;
		ArrayList<ArrayList<Double>> corr = new ArrayList<ArrayList<Double>>();
		int size =normBrazil.get(0).getSize() - 1;
		int iterator = 0;

		for (int dayStart = 0; dayStart < size - numDays; dayStart += step) {
			corr.add(new ArrayList<Double>());
			 System.out.println( dayStart + " ���� ������ "  );

			for (int j = 0; j < normBrazil.size(); j++) {
				if (!normBrazil.get(j).hasZeroYield(dayStart, dayStart + numDays)) {
					
				
				//if (normBrazil.get(j).get1Yield(dayStart) != 0.0
				//		&& normBrazil.get(j).get1Yield(dayStart + numDays) != 0.0) {

					double a[] = normBrazil.get(j).getYield(dayStart,
							dayStart + numDays);
					//System.out.println( j +  " ������ ������"  );

					for (int t = j + 1; t < normBrazil.size(); t++) {
						
						if (!normBrazil.get(t).hasZeroYield(dayStart, dayStart + numDays)) {
						
						//if (normBrazil.get(t).get1Yield(dayStart) != 0.0
						//		&& normBrazil.get(t).get1Yield(
						//				dayStart + numDays) != 0.0) {
							//System.out.println( t +  " ������ ������"  );
							// System.out.println( normBrazil.get(t).getSize() +
							// " t= " + t );

							double b[] = normBrazil.get(t).getYield(dayStart,
									dayStart + numDays);
							// Corr c = new Corr();
							// System.out.println("��� " + iterator + " ���� " +
							// dayStart );

							// corr.get(iterator).add(c.pirsonCorr(a, b));
							corr.get(iterator)
									.add(new PearsonsCorrelation().correlation(
											a, b));
							//System.out.println( corr.get(iterator).toString() +  " ����������"  );

							// System.out.println(a.toString() + " a");
							// System.out.println(b.toString() + " b");
							// System.out.println(c.pirsonCorr(a, b));
						}
					}
				}

			}
			iterator++;

		}
		return corr;

	}

	public static int[][] getQuantityCorrelations(
			ArrayList<ArrayList<Double>> corr) {
		int result[][] = new int[corr.size()][sizeVector];
		//double interval = 2/sizeVector;
		for (int i = 0; i < corr.size(); i++) { // ��� ������ 500 ����
			//System.out.println(i);

			int n = 0;
			for (double o = -1; o < 0.975; o = o + 0.025 ) {
				//System.out.println(" o " +o);

				for (int j = 0; j < corr.get(i).size(); j++) {
					double cor = corr.get(i).get(j);
					if (cor < o + 0.025 && cor > o){
						//System.out.println(cor + " "+ i + "i n" +n);
						result[i][n] += 1;
					}

				}
				n++;
			}
		}

		return result;

	}
	

	public static void writeIntoFiles(int[][] finaly) {
		File testf = new File("C:/�����/������/data/" + sizeVector + "/test/" + country +".txt");
		File trainf = new File("C:/�����/������/data/" + sizeVector + "/train/" + country +".txt");
		File testClass = new File("C:/�����/������/data/" + sizeVector + "/test Class/" + country +".txt");
		File trainClass = new File("C:/�����/������/data/" + sizeVector + "/train Class/" + country +".txt");
		
		
		try {
			// ���������, ��� ���� ���� �� ���������� �� ������� ���
			if (!testf.exists()) {
				testf.createNewFile();
			}
			if (!trainf.exists()) {
				trainf.createNewFile();
			}
			if (!testClass.exists()) {
				testClass.createNewFile();
			}
			if (!trainClass.exists()) {
				trainClass.createNewFile();
			}

			// PrintWriter ��������� ����������� ������ � ����
			PrintWriter test = new PrintWriter(testf.getAbsoluteFile());
			PrintWriter train = new PrintWriter(trainf.getAbsoluteFile());
			PrintWriter testClas = new PrintWriter(testClass.getAbsoluteFile());
			PrintWriter trainClas = new PrintWriter(
					trainClass.getAbsoluteFile());

			try {
				// ���������� ����� � ����
				for (int i = 0; i < finaly.length; i++) {
					//ArrayList <Integer> array = new ArrayList <Integer>();
					//for(int g = 0; g<40;g++){
					//	array.add(finaly[i][g]);
					//}
					if (i % 5 == 0) {
						for (int j = 0; j < finaly[i].length; j++) {
						test.print(finaly[i][j] + " ");
						}
						test.println();
						
						testClas.println(classString); //��� ����� ����� ��������
					} else {
						for (int j = 0; j < finaly[i].length; j++) {
							train.print(finaly[i][j] + " ");
							}
						train.println();
						trainClas.println(classString);

					}
				}

			} finally {
				// ����� ���� �� ������ ������� ����
				// ����� ���� �� ���������
				test.close();
				train.close();
				testClas.close();
				trainClas.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static void main(String[] args) throws IOException {
		String readFile = "C:/�����/������/data/" + country + "/";

		CsvReadFile russia = new CsvReadFile();
		ArrayList<Stocks> brazil = russia.read(readFile, numCountries); // 341, 497, 530
		System.out.println("���� " + brazil.get(1).getSize());
		/*
		 * for(int i = 0; i<brazil.size(); i++){ Stocks curr = brazil.get(i);
		 * System.out.println(i + " " +curr.getSize()); for(int j = 0;
		 * j<brazil.get(i).getSize(); j++){
		 * 
		 * System.out.println(curr.getDate(j)+ " " + curr.getOpen(j)+ " " +
		 * curr.getClose(j)); }
		 * 
		 * }
		 */
		
		//for(int i = 0; i<brazil.size(); i++){
		//	System.out.println(brazil.get(i).printPrice());
		//}

		ArrayList<Stocks> normBrazil = normalize(brazil);
		System.out.println("���� ���� " + normBrazil.get(1).getSize());
		for (Stocks item : normBrazil) {
			item.smooth();
			item.calcYield();
		}
		//for(int i = 0; i<normBrazil.size(); i++){
		//	System.out.println(normBrazil.get(i).printYield());
		//}

		System.out.println(normBrazil.size() + " ���������� ��������");

		ArrayList<ArrayList<Double>> corr = getAllCorrelations(normBrazil);
		
		

		int[][] finaly = getQuantityCorrelations(corr);
		

		int forDiagram[] = new int[sizeVector];
		System.out.println(finaly.length + " ���������� ��������");

		for (int i = 0; i < finaly.length; i++) {

			for (int j = 0; j < finaly[i].length; j++) {
				forDiagram[j] += finaly[i][j];
			}

		}

		
		
		writeIntoFiles(finaly);

		/*File testf = new File("C:/�����/������/data/corrbrazilia.txt");
		File trainf = new File("C:/�����/������/data/number.txt");
		File checkf = new File("C:/�����/������/data/diagrambrazilia.txt");
		try {
			// ���������, ��� ���� ���� �� ���������� �� ������� ���
			if (!testf.exists()) {
				testf.createNewFile();
			}
			if (!trainf.exists()) {
				trainf.createNewFile();
			}
			if (!checkf.exists()) {
				checkf.createNewFile();
			}
			
			

			// PrintWriter ��������� ����������� ������ � ����
			PrintWriter test = new PrintWriter(testf.getAbsoluteFile());
			PrintWriter train = new PrintWriter(trainf.getAbsoluteFile());
			PrintWriter check = new PrintWriter(checkf.getAbsoluteFile());

			try {
			
				for(int i = 0; i<corr.size(); i++){
					for(int j = 0; j<corr.get(i).size(); j++){
						test.print(corr.get(i).get(j) + " ");
					}
					test.println();
				}
				
				
				/*for (int i = 0; i < corr.size(); i++) {

					test.println(corr.get(i).toString());
				}
				for (int i = 0; i < finaly.length; i++) {
					for (int j = 0; j < finaly[i].length; j++) {


					train.println(finaly[i][j] + ", ");
					}
				}
				for (int j = 0; j < 40; j++) {
					check.print(forDiagram[j] + ", ");
				}
			} finally {
				// ����� ���� �� ������ ������� ����
				// ����� ���� �� ���������
				test.close();
				train.close();
				check.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}*/

	}
}
