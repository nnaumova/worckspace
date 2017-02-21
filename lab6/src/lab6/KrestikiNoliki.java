package lab6;


import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class KrestikiNoliki extends Applet implements ActionListener {

	private static final long serialVersionUID = 1L;
	/**
	 * The array of buttons for the game
	 */
	Button squares[];
	Button newGameButton;
	/**
	 * A textfields where the game writes the action and the result
	 */
	Label score;
	Label labWin;
	Label labLoose;
	int win = 0;
	int loose = 0;
	/**
	 * The remaining empty cells
	 */
	int emptySquaresLeft = 9;
	/**
	 * The number of cells
	 */
	private static final int BOARD_SIZE = 9;

	public void init() {

		// The Manager of the location of the applet, font and color
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);

		// Craft applet
		Font appletFont = new Font("Monospased", Font.BOLD, 20);
		this.setFont(appletFont);

		// The button “New Game” and an action listener
		labWin = new Label("You: " + win);
		labLoose = new Label("PC: " + loose);
		newGameButton = new Button("New Game");
		newGameButton.addActionListener(this);
		Panel topPanel = new Panel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(newGameButton, "North");
		topPanel.add(labWin, "West");
		topPanel.add(labLoose, "East");
		this.add(topPanel, "North");
		Panel centerPanel = new Panel();
		centerPanel.setLayout(new GridLayout(3, 3));
		this.add(centerPanel, "Center");
		score = new Label("Your turn");
		this.add(score, "South");

		// The array to store references to 9 buttons
		squares = new Button[BOARD_SIZE];

		// buttons

		for (int i = 0; i < BOARD_SIZE; i++) {

			squares[i] = new Button();
			squares[i].addActionListener(this);
			squares[i].setBackground(Color.blue);
			centerPanel.add(squares[i]);
		}
	}

	/**
	 * This method finds the winnig line.
	 * 
	 * @param cell1
	 *            is the first cell ,
	 * @param cell2
	 *            is the second cell, and
	 * @param cell3
	 *            is the third cell to highlight
	 * @return "true" or "false" this statement
	 */
	private boolean areEqual(int cell1, int cell2, int cell3) {
		return (!squares[cell1].getLabel().isEmpty()
				&& squares[cell1].getLabel().equals(squares[cell2].getLabel()) && squares[cell1]
				.getLabel().equals(squares[cell3].getLabel()));
	}

	/**
	 * Function Resets all cells.
	 * 
	 * @param numberOfCell
	 *            is the number of the cell, which needs to be cleared
	 **/
	private void clearCell(int numberOfCell) {
		squares[numberOfCell].setEnabled(true);
		squares[numberOfCell].setLabel("");
		squares[numberOfCell].setBackground(Color.blue);

	}

	/**
	 * @param ActionEvent
	 *            object
	 */
	public void actionPerformed(ActionEvent e) {
		Button theButton = (Button) e.getSource();
		// The button New Game
		if (theButton == newGameButton) {
			for (int i = 0; i < BOARD_SIZE; i++) {
				clearCell(i);
			}
			emptySquaresLeft = 9;
			score.setText("Your turn");
			newGameButton.setEnabled(true);
		}

		String winner = "";
		// One of the cells

		for (int i = 0; i < BOARD_SIZE; i++) {

			if (theButton == squares[i]) {
				if (squares[i].getLabel() != "X"
						&& squares[i].getLabel() != "O") {
					squares[i].setLabel("X");
					winner = lookForWinner();
				} else {
					continue;
				}

				if (!winner.isEmpty()) {
					endTheGame();

				} else {
					computerMove();
					winner = lookForWinner();

					if (!winner.isEmpty()) {
						endTheGame();
					}
				}
				break;
			}
		}

		if (winner.equals("X")) {
			++win;
			labWin.setText("You: " + win);
			score.setText("You win!");

		} else if (winner.equals("O")) {
			++loose;
			score.setText("You lose...");
			labLoose.setText("PC: " + loose);

		} else if (winner.equals("T")) {
			score.setText("Draw");
		}
	}

	// The end of the method actionPerformed

	/**
	 * This function looks for a winner
	 * 
	 * @return "X", "O", "T" – the draw, "" - still no winner
	 */
	String lookForWinner() {
		String theWinner = "";
		emptySquaresLeft--;
		if (emptySquaresLeft == 0) {
			return "T";
		}
		// Check the first row . the elements of the array 0,1,2
		if (areEqual(0, 1, 2)) {
			theWinner = squares[0].getLabel();
			highlightWinner(0, 1, 2);
			// Check the second row . the elements of the array 3,4,5
		} else if (areEqual(3, 4, 5)) {
			theWinner = squares[3].getLabel();
			highlightWinner(3, 4, 5);
			// Check the third row . the elements of the array 6,7,8
		} else if (areEqual(6, 7, 8)) {
			theWinner = squares[6].getLabel();
			highlightWinner(6, 7, 8);
			// Checking of the first column . the elements of the array 0,3,6
		} else if (areEqual(0, 3, 6)) {
			theWinner = squares[0].getLabel();
			highlightWinner(0, 3, 6);
			// Checking of the second column . the elements of the array 1,4,7
		} else if (areEqual(1, 4, 7)) {
			theWinner = squares[1].getLabel();
			highlightWinner(1, 4, 7);
			// Checking of the third column. The elements of the array 2,5,8
		} else if (areEqual(2, 5, 8)) {
			theWinner = squares[2].getLabel();
			highlightWinner(2, 5, 8);
			// Checking the first diagonal. the elements of the array 0,4,8
		} else if (areEqual(0, 4, 8)) {
			theWinner = squares[0].getLabel();
			highlightWinner(0, 4, 8);
			// Checking the second diagonal. the elements of the array 2,4,6
		} else if (areEqual(2, 4, 6)) {
			theWinner = squares[2].getLabel();
			highlightWinner(2, 4, 6);
		}
		return theWinner;
	}

	/**
	 * Method applies a set of rules to find the best computer course. if not
	 * found, is randomly selected cell.
	 */
	void computerMove() {
		int selectedSquare;
		selectedSquare = findEmptySquare("O");
		if (selectedSquare == -1)
			selectedSquare = findEmptySquare("X");

		if ((selectedSquare == -1) && (squares[4].getLabel().isEmpty())) {
			selectedSquare = 4;
		}

		if (selectedSquare == -1) {
			selectedSquare = getRandomSquare();
		}
		squares[selectedSquare].setLabel("O");
	}

	/**
	 * Method checks each row, column, and diagonal to know whether two cells
	 * with the same slogans and empty cell.
	 * 
	 * @param passed
	 *            X - user and O - PC
	 * @return the number of empty cells, or -1 if not found two cells with the
	 *         same labels
	 */
	int findEmptySquare(String player) {
		int weight[] = new int[BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (squares[i].getLabel().equals("O"))
				weight[i] = -1;
			else if (squares[i].getLabel().equals("X"))
				weight[i] = 1;
			else
				weight[i] = 0;
		}
		int twoWeights = player.equals("O") ? -2 : 2;
		if (weight[0] + weight[1] + weight[2] == twoWeights) {
			if (weight[0] == 0)
				return 0;
			else if (weight[1] == 0)
				return 1;
			else
				return 2;
		}
		if (weight[3] + weight[4] + weight[5] == twoWeights) {
			if (weight[3] == 0)
				return 3;
			else if (weight[4] == 0)
				return 4;
			else
				return 5;
		}
		if (weight[6] + weight[7] + weight[8] == twoWeights) {
			if (weight[6] == 0)
				return 6;
			else if (weight[7] == 0)
				return 7;
			else
				return 8;
		}

		if (weight[0] + weight[3] + weight[6] == twoWeights) {
			if (weight[0] == 0)
				return 0;
			else if (weight[3] == 0)
				return 3;
			else
				return 6;
		}

		if (weight[1] + weight[4] + weight[7] == twoWeights) {
			if (weight[1] == 0)
				return 1;
			else if (weight[4] == 0)
				return 4;
			else
				return 7;
		}

		if (weight[2] + weight[5] + weight[8] == twoWeights) {
			if (weight[2] == 0)
				return 2;
			else if (weight[5] == 0)
				return 5;
			else
				return 8;
		}

		if (weight[0] + weight[4] + weight[8] == twoWeights) {

			if (weight[0] == 0)
				return 0;
			else if (weight[4] == 0)
				return 4;
			else
				return 8;
		}

		if (weight[2] + weight[4] + weight[6] == twoWeights) {
			if (weight[2] == 0)
				return 2;
			else if (weight[4] == 0)
				return 4;
			else
				return 6;
		}
		// Not found two identical adjacent cells
		return -1;
	}

	/**
	 * Method selects any empty cell
	 * 
	 * @return randomly selected number of cells
	 */
	int getRandomSquare() {
		boolean gotEmptySquare = false;
		int selectedSquare = -1;
		do {
			selectedSquare = (int) (Math.random() * BOARD_SIZE);
			if (squares[selectedSquare].getLabel().isEmpty()) {
				gotEmptySquare = true;
			}
		} while (!gotEmptySquare);
		return selectedSquare;
	}

	/**
	 * This method selects the winning line.
	 * 
	 * @param the
	 *            first, the second and the third cells to highlight
	 */
	void highlightWinner(int win1, int win2, int win3) {
		squares[win1].setBackground(Color.RED);
		squares[win2].setBackground(Color.RED);
		squares[win3].setBackground(Color.RED);
	}

	/**
	 * This function ends the game.
	 */
	void endTheGame() {
		newGameButton.setEnabled(true);
		for (int i = 0; i < BOARD_SIZE; i++) {
			squares[i].setEnabled(false);
		}
	}
}