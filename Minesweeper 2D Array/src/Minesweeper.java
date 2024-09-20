import java.util.Random;

public class Minesweeper {
    private char[][] board;
    private boolean[][] mines;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private int rows;
    private int cols;
    private int numMines;
    private boolean gameOver;

    public Minesweeper(int rows, int cols, int numMines) {
        this.rows = rows;
        this.cols = cols;
        this.numMines = numMines;
        this.board = new char[rows][cols];
        this.mines = new boolean[rows][cols];
        this.revealed = new boolean[rows][cols];
        this.flagged = new boolean[rows][cols];
        this.gameOver = false;

        initializeBoard();
        placeMines();
        calculateNumbers();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean getGameOver() {
        return this.gameOver;
    }

    public void setGameOver(boolean status) {
        this.gameOver = status;
    }

    private void initializeBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] = '?';
                mines[row][col] = false;
                revealed[row][col] = false;
                flagged[row][col] = false;
            }
        }
    }

    private void placeMines() {
        Random rand = new Random();
        int minesPlaced = 0;

        while (minesPlaced < numMines) {
            int row = rand.nextInt(rows);
            int col = rand.nextInt(cols);

            if (!mines[row][col]) {
                mines[row][col] = true;
                minesPlaced++;
            }
        }
    }

    private void calculateNumbers() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!mines[row][col]) {
                    int mineCount = countAdjacentMines(row, col);
                    board[row][col] = (mineCount > 0) ? (char) ('0' + mineCount) : ' ';
                }
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int r = Math.max(0, row - 1); r <= Math.min(rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(cols - 1, col + 1); c++) {
                if (mines[r][c]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void displayBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (revealed[row][col]) {
                    System.out.print(board[row][col] + " ");
                } else if (flagged[row][col]) {
                    System.out.print("F ");
                } else {
                    System.out.print("? ");
                }
            }
            System.out.println();
        }
    }

    public void playerMove(int row, int col, String action) {
        if (action.equals("reveal")) {
            revealCell(row, col);
        } else if (action.equals("flag")) {
            flagCell(row, col);
        } else if (action.equals("unflag")) {
            unflagCell(row, col);
        }
    }

    public boolean checkWin() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!mines[row][col] && !revealed[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkLoss(int row, int col) {
        return mines[row][col] && revealed[row][col];
    }

    private void revealCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols || revealed[row][col] || flagged[row][col]) {
            return;
        }

        revealed[row][col] = true;

        if (mines[row][col]) {
            gameOver = true;
        } else if (board[row][col] == ' ') {
            for (int r = Math.max(0, row - 1); r <= Math.min(rows - 1, row + 1); r++) {
                for (int c = Math.max(0, col - 1); c <= Math.min(cols - 1, col + 1); c++) {
                    revealCell(r, c);
                }
            }
        }
    }

    private void flagCell(int row, int col) {
        if (!revealed[row][col]) {
            flagged[row][col] = true;
        }
    }

    private void unflagCell(int row, int col) {
        if (!revealed[row][col]) {
            flagged[row][col] = false;
        }
    }
}
