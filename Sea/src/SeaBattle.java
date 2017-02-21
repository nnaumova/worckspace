
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SeaBattle {

    static int[][] board;
    static int[][] rivalBoard;
    static int[][] battleship;
    static int[][] cruiser1;
    static int[][] cruiser2;
    static int[][] destroyer1;
    static int[][] destroyer2;
    static int[][] destroyer3;
    static int[][] submarine1;
    static int[][] submarine2;
    static int[][] submarine3;
    static int[][] submarine4;

    static Messaging msg;
    static int deadCounter;
    static int enemyDeadShips;
    static boolean fireEnable = false;
    static boolean hited = false;
    static int[] lastIndexes = new int[2];

    public static void main(String[] args) throws Exception {
        board = new int[10][10];
        rivalBoard = new int[10][10];

        battleship = new int[4][2];
        cruiser1 = new int[3][2];
        cruiser2 = new int[3][2];
        destroyer1 = new int[2][2];
        destroyer2 = new int[2][2];
        destroyer3 = new int[2][2];
        submarine1 = new int[1][2];
        submarine2 = new int[1][2];
        submarine3 = new int[1][2];
        submarine4 = new int[1][2];

        deadCounter = 0; //my dead ships
        enemyDeadShips = 0;

        placeRivalBoard();
        placeBattleship();
        placeCruiser(cruiser1);
        placeCruiser(cruiser2);
        placeDestroyer(destroyer1);
        placeDestroyer(destroyer2);
        placeDestroyer(destroyer3);
        placeSubmarine(submarine1);
        placeSubmarine(submarine2);
        placeSubmarine(submarine3);
        placeSubmarine(submarine4);

        msg = new Messaging();

        //TimeUnit.SECONDS.sleep(2);
        msg.setConnection();

        while (deadCounter != 10 || enemyDeadShips != 10) {

            while (!fireEnable) {
                if (deadCounter == 10) {
                    System.out.println("YOU LOSE!");
                    msg.closeConnection();
                    System.exit(0);
                }
                int[] shot = msg.recieveShotMessage();
                takeShot(shot[0], shot[1]);

            }
            shoot();
            System.out.println("Enemy has " + (10 - enemyDeadShips) + " ships");
            System.out.println("Our dead ships: " + deadCounter);
            showBoard(board);
            System.out.println();
            showBoard(rivalBoard);
        }

        System.out.println((deadCounter == 10) ? "YOU LOOSE!" : "YOU WIN!");
        msg.closeConnection();
    }

    public static int[] randIndexes() {
        //Random rand = new Random();
        int[] indArr = new int[2];

        //if (!hited) {
        int i = (int) (Math.random() * 10 + 0);
        int j = (int) (Math.random() * 10 + 0);

        while (rivalBoard[i][j] != 0) {
            i = (int) (Math.random() * 10 + 0);
            j = (int) (Math.random() * 10 + 0);
        }
        indArr[0] = i;
        indArr[1] = j;
        /* } else {
         int newi = -1;
         int newj = -1;
         while (newi == -1 || newj == -1) {
         if (lastIndexes[0] + 1 < 10) {
         newi = (rivalBoard[lastIndexes[0] + 1][lastIndexes[1]] == 0) ? lastIndexes[0] + 1 : -1;
         newj = lastIndexes[1];
         } else {
         newi = (rivalBoard[lastIndexes[0] - 1][lastIndexes[1]] == 0) ? lastIndexes[0] - 1 : -1;
         newj = lastIndexes[1];
         }
         if (newi == -1) {
         if (lastIndexes[1] + 1 < 10) {
         newj = (rivalBoard[lastIndexes[0]][lastIndexes[1] + 1] == 0) ? lastIndexes[1] + 1 : -1;
         newi = lastIndexes[0];
         } else {
         newj = (rivalBoard[lastIndexes[0]][lastIndexes[1] - 1] == 0) ? lastIndexes[1] - 1 : -1;
         newi = lastIndexes[0];
         }
         }
         }
         indArr[0] = newi;
         indArr[1] = newj;
         }*/

        return indArr;
    }

    public static void shoot() throws Exception {
        String answer;

        int[] indexes = randIndexes();

        System.out.println("Indexses chose");

        msg.sendShotMessage(indexes[0], indexes[1]);
        TimeUnit.SECONDS.sleep(1);
        answer = msg.recieveStatusMessage();

        if (answer.equals("hit")) {
            System.out.println("we hit");
            setIfHit(indexes[0], indexes[1]);
            shoot();
            return;
        }
        if (answer.equals("miss")) {
            System.out.println("we miss");
            setIfMiss(indexes[0], indexes[1]);
            fireEnable = false;
            return;
        }
        if (answer.equals("dead")) {
            enemyDeadShips += 1;
            if (enemyDeadShips == 10) {
                System.out.println("YOU WIN!");
                msg.closeConnection();
                System.exit(0);
            }
            setIfHit(indexes[0], indexes[1]);
            shoot();
            return;
        }
    }

    public static void setIfHit(int i, int j) {
        rivalBoard[i][j] = -2;
    }

    public static void setIfMiss(int i, int j) {
        rivalBoard[i][j] = 1;
    }

    public static void takeShot(int a, int b) throws Exception {
        if (board[a][b] != -1) {
            System.out.println("Entered miss IF statement with status code: " + board[a][b]);
            // System.out.println("board[a][b]: " + board[a][b]);
            String status = "miss";
            System.out.println("Enemy missed!");

            msg.sendStatusMessage(status);
            fireEnable = true;
        } else {
            System.out.println("Entered miss ELSE statement with status code: " + board[a][b]);
            board[a][b] = -2;

            if (isHit(battleship, 4, a, b)) {
                if (isDead(battleship, 4)) {
                    String status = "dead";
                    msg.sendStatusMessage(status);
                    System.out.println("Our ship is dead at " + a + " " + b);
                    deadCounter++;
                    return;
                } else {
                    String status = "hit";
                    System.out.println("Our ship is hit at " + a + " " + b);
                    msg.sendStatusMessage(status);
                    return;
                }
            }

            if (isHit(cruiser1, 3, a, b)) {
                if (isDead(cruiser1, 3)) {
                    String status = "dead";
                    msg.sendStatusMessage(status);
                    System.out.println("Our ship is dead at " + a + " " + b);
                    deadCounter++;
                    return;
                } else {
                    String status = "hit";
                    System.out.println("Our ship is hit at " + a + " " + b);
                    msg.sendStatusMessage(status);
                    return;
                }
            }

            if (isHit(cruiser2, 3, a, b)) {
                if (isDead(cruiser2, 3)) {
                    String status = "dead";
                    msg.sendStatusMessage(status);
                    System.out.println("Our ship is dead at " + a + " " + b);
                    deadCounter++;
                    return;
                } else {
                    String status = "hit";
                    System.out.println("Our ship is hit at " + a + " " + b);
                    msg.sendStatusMessage(status);
                    return;
                }
            }

            if (isHit(destroyer1, 2, a, b)) {
                if (isDead(destroyer1, 2)) {
                    String status = "dead";
                    msg.sendStatusMessage(status);
                    System.out.println("Our ship is dead at " + a + " " + b);
                    deadCounter++;
                    return;
                } else {
                    String status = "hit";
                    System.out.println("Our ship is hit at " + a + " " + b);
                    msg.sendStatusMessage(status);
                    return;
                }
            }

            if (isHit(destroyer2, 2, a, b)) {
                if (isDead(destroyer2, 2)) {
                    String status = "dead";
                    msg.sendStatusMessage(status);
                    System.out.println("Our ship is dead at " + a + " " + b);
                    deadCounter++;
                    return;
                } else {
                    String status = "hit";
                    System.out.println("Our ship is hit at " + a + " " + b);
                    msg.sendStatusMessage(status);
                    return;
                }
            }

            if (isHit(destroyer3, 2, a, b)) {
                if (isDead(destroyer3, 2)) {
                    String status = "dead";
                    msg.sendStatusMessage(status);
                    System.out.println("Our ship is dead at " + a + " " + b);
                    deadCounter++;
                    return;
                } else {
                    String status = "hit";
                    System.out.println("Our ship is hit at " + a + " " + b);
                    msg.sendStatusMessage(status);
                    return;
                }
            }

            if (isHit(submarine1, 1, a, b)) {
                if (isDead(submarine1, 1)) {
                    String status = "dead";
                    msg.sendStatusMessage(status);
                    System.out.println("Our ship is dead at " + a + " " + b);
                    deadCounter++;
                    return;
                } else {
                    String status = "hit";
                    System.out.println("Our ship is hit at " + a + " " + b);
                    msg.sendStatusMessage(status);
                    return;
                }
            }

            if (isHit(submarine2, 1, a, b)) {
                if (isDead(submarine2, 1)) {
                    String status = "dead";
                    msg.sendStatusMessage(status);
                    System.out.println("Our ship is dead at " + a + " " + b);
                    deadCounter++;
                    return;
                } else {
                    String status = "hit";
                    System.out.println("Our ship is hit at " + a + " " + b);
                    msg.sendStatusMessage(status);
                    return;
                }
            }

            if (isHit(submarine3, 1, a, b)) {
                if (isDead(submarine3, 1)) {
                    String status = "dead";
                    msg.sendStatusMessage(status);
                    System.out.println("Our ship is dead at " + a + " " + b);
                    deadCounter++;
                    return;
                } else {
                    String status = "hit";
                    System.out.println("Our ship is hit at " + a + " " + b);
                    msg.sendStatusMessage(status);
                    return;
                }
            }

            if (isHit(submarine4, 1, a, b)) {
                if (isDead(submarine4, 1)) {
                    String status = "dead";
                    msg.sendStatusMessage(status);
                    System.out.println("Our ship is dead at " + a + " " + b);
                    deadCounter++;
                    return;
                } else {
                    String status = "hit";
                    System.out.println("Our ship is hit at " + a + " " + b);
                    msg.sendStatusMessage(status);
                    return;
                }
            }
        }
    }

    public static boolean isHit(int[][] ship, int size, int a, int b) {
        System.out.println("Entering isHit for a ship with size of " + size);
        for (int i = 0; i < size; ++i) {
            if (ship[i][0] == a & ship[i][1] == b) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDead(int[][] ship, int size) {
        System.out.println("Entering isDead for a ship with size of " + size);
        int counter = 0;

        for (int i = 0; i < size; i++) {
            if (board[ship[i][0]][ship[i][1]] == -2) {
                counter++;
            }
        }
        if (counter == size) {
            return true;
        } else {
            return false;
        }
    }

    public static void placeRivalBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                rivalBoard[i][j] = 0;
            }
        }
    }

    public static void placeBattleship() {
        Random rand = new Random(); // random integer from 0 to 1 to define ship
        // direction
        int direction = rand.nextInt((1 - 0) + 1) + 0; // 0 - horizontal, 1 - vertical

        if (direction == 0) {
            int i = rand.nextInt((9 - 0) + 1) + 0;
            int j = rand.nextInt((6 - 0) + 1) + 0;

            int i_bound = i + 2;
            int j_bound = j + 5;

            int k = i - 1;
            int l = j - 1;

            if (i == 0) {
                k = 0;
            } else if (i == 9) {
                i_bound--;
            }

            if (j == 0) {
                l = 0;
            } else if (j == 6) {
                j_bound--;
            }

            for (int a = k; a < i_bound; a++) {
                for (int b = l; b < j_bound; b++) {
                    board[a][b] = 1;
                }
            }

            int bound = j + 4;

            for (int m = 0; j < bound; j++, m++) {
                board[i][j] = -1;
                battleship[m][0] = i;
                battleship[m][1] = j;
            }
        } else {
            int i = rand.nextInt((6 - 0) + 1) + 0;
            int j = rand.nextInt((9 - 0) + 1) + 0;

            int i_bound = i + 5;
            int j_bound = j + 2;

            int k = i - 1;
            int l = j - 1;

            if (i == 0) {
                k = 0;
            } else if (i == 6) {
                i_bound--;
            }

            if (j == 0) {
                l = 0;
            } else if (j == 9) {
                j_bound--;
            }

            for (int a = k; a < i_bound; a++) {
                for (int b = l; b < j_bound; b++) {
                    board[a][b] = 1;
                }
            }

            int bound = i + 4;

            for (int m = 0; i < bound; i++, m++) {
                board[i][j] = -1;
                battleship[m][0] = i;
                battleship[m][1] = j;
            }
        }
    }

    public static void placeCruiser(int[][] cruiser) {
        Random rand = new Random(); // random integer from 0 to 1 to define ship
        // direction
        int direction = rand.nextInt((1 - 0) + 1) + 0; // 0 - horizontal, 1 -
        // vertical

        if (direction == 0) {
            int i = rand.nextInt((9 - 0) + 1) + 0;
            int j = rand.nextInt((7 - 0) + 1) + 0;

            if (board[i][j] == 0 && board[i][j + 1] == 0 && board[i][j + 2] == 0) {
                int i_bound = i + 2;
                int j_bound = j + 4;

                int k = i - 1;
                int l = j - 1;

                if (i == 0) {
                    k = 0;
                } else if (i == 9) {
                    i_bound--;
                }

                if (j == 0) {
                    l = 0;
                } else if (j == 7) {
                    j_bound--;
                }

                for (int a = k; a < i_bound; a++) {
                    for (int b = l; b < j_bound; b++) {
                        board[a][b] = 1;
                    }
                }

                int bound = j + 3;

                for (int m = 0; j < bound; j++, m++) {
                    board[i][j] = -1;
                    cruiser[m][0] = i;
                    cruiser[m][1] = j;
                }
            } else {
                placeCruiser(cruiser);
            }
        } else {
            int i = rand.nextInt((7 - 0) + 1) + 0;
            int j = rand.nextInt((9 - 0) + 1) + 0;

            if (board[i][j] == 0 && board[i + 1][j] == 0 && board[i + 2][j] == 0) {
                int i_bound = i + 4;
                int j_bound = j + 2;

                int k = i - 1;
                int l = j - 1;

                if (i == 0) {
                    k = 0;
                } else if (i == 7) {
                    i_bound--;
                }

                if (j == 0) {
                    l = 0;
                } else if (j == 9) {
                    j_bound--;
                }

                for (int a = k; a < i_bound; a++) {
                    for (int b = l; b < j_bound; b++) {
                        board[a][b] = 1;
                    }
                }

                int bound = i + 3;

                for (int m = 0; i < bound; i++, m++) {
                    board[i][j] = -1;
                    cruiser[m][0] = i;
                    cruiser[m][1] = j;
                }
            } else {
                placeCruiser(cruiser);
            }
        }
    }

    public static void placeDestroyer(int[][] destroyer) {
        Random rand = new Random(); // random integer from 0 to 1 to define ship
        // direction
        int direction = rand.nextInt((1 - 0) + 1) + 0; // 0 - horizontal, 1 -
        // vertical

        if (direction == 0) {
            int i = rand.nextInt((9 - 0) + 1) + 0;
            int j = rand.nextInt((8 - 0) + 1) + 0;

            if (board[i][j] == 0 && board[i][j + 1] == 0) {
                int i_bound = i + 2;
                int j_bound = j + 3;

                int k = i - 1;
                int l = j - 1;

                if (i == 0) {
                    k = 0;
                } else if (i == 9) {
                    i_bound--;
                }

                if (j == 0) {
                    l = 0;
                } else if (j == 8) {
                    j_bound--;
                }

                for (int a = k; a < i_bound; a++) {
                    for (int b = l; b < j_bound; b++) {
                        board[a][b] = 1;
                    }
                }

                int bound = j + 2;

                for (int m = 0; j < bound; j++, m++) {
                    board[i][j] = -1;
                    destroyer[m][0] = i;
                    destroyer[m][1] = j;
                }
            } else {
                placeDestroyer(destroyer);
            }
        } else {
            int i = rand.nextInt((8 - 0) + 1) + 0;
            int j = rand.nextInt((9 - 0) + 1) + 0;

            if (board[i][j] == 0 && board[i + 1][j] == 0) {
                int i_bound = i + 3;
                int j_bound = j + 2;

                int k = i - 1;
                int l = j - 1;

                if (i == 0) {
                    k = 0;
                } else if (i == 8) {
                    i_bound--;
                }

                if (j == 0) {
                    l = 0;
                } else if (j == 9) {
                    j_bound--;
                }

                for (int a = k; a < i_bound; a++) {
                    for (int b = l; b < j_bound; b++) {
                        board[a][b] = 1;
                    }
                }

                int bound = i + 2;

                for (int m = 0; i < bound; i++, m++) {
                    board[i][j] = -1;
                    destroyer[m][0] = i;
                    destroyer[m][1] = j;
                }
            } else {
                placeDestroyer(destroyer);
            }
        }
    }

    public static void placeSubmarine(int[][] submarine) {
        Random rand = new Random();

        int i = rand.nextInt((9 - 0) + 1) + 0;
        int j = rand.nextInt((9 - 0) + 1) + 0;

        if (board[i][j] == 0) {
            int i_bound = i + 2;
            int j_bound = j + 2;

            int k = i - 1;
            int l = j - 1;

            if (i == 0) {
                k = 0;
            } else if (i == 9) {
                i_bound--;
            }

            if (j == 0) {
                l = 0;
            } else if (j == 9) {
                j_bound--;
            }

            for (int a = k; a < i_bound; a++) {
                for (int b = l; b < j_bound; b++) {
                    board[a][b] = 1;
                }
            }

            board[i][j] = -1;
            submarine[0][0] = i;
            submarine[0][1] = j;
        } else {
            placeSubmarine(submarine);
        }
    }

    public static void showBoard(int[][] board) {
        System.out.println("    A   B   C   D   E   F   G   H   I   J");
        System.out.println();

        for (int row = 0; row < 10; row++) {
            System.out.print((row) + "");
            for (int column = 0; column < 10; column++) {
                if (board[row][column] == -1) { //корабль
                    System.out.print("   " + "0");
                } else if (board[row][column] == 0) { //вода
                    System.out.print("   " + ".");
                } else if (board[row][column] == 1) { //стреляное место; место у коробля
                    System.out.print("   " + "*");
                } else if (board[row][column] == -2) { //стреляная ячейка с кораблем
                    System.out.print("   " + "x");
                }

            }
            System.out.println();
        }
    }
}
