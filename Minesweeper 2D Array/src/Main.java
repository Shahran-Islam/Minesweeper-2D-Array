import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Minesweeper game = new Minesweeper(10, 10, 10);
        Scanner scanner = new Scanner(System.in);

        while (!game.getGameOver()) {
            game.displayBoard();
            System.out.println("Enter row (0 to " + (game.getRows() - 1) + "): ");
            int row = scanner.nextInt();
            System.out.println("Enter column (0 to " + (game.getCols() - 1) + "): ");
            int col = scanner.nextInt();
            System.out.println("Enter action (reveal/flag/unflag): ");
            String action = scanner.next();

            game.playerMove(row, col, action);

            if (game.checkWin()) {
                System.out.println("Congratulations! You've won the game.");
                break;
            }
            if (game.checkLoss(row, col)) {
                System.out.println("Game Over! You hit a mine.");
                game.setGameOver(true);
                game.displayBoard();
            }
        }

        scanner.close();
    }
}
