public class Board {

    private Cell[][] board;

    public Board() {
        board = new Cell[Game.ROWS][Game.COLS];
        for (int row = 0; row < Game.ROWS; row++) {
            for (int col = 0; col < Game.COLS; col++) {
                board[row][col] = new Cell(null);
            }
        }
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    public void setCell(int row, int col, Player player){
        board[row][col].player = player;
    }

    public void reset() {
        for (int row = 0; row < Game.ROWS; row++) {
            for (int col = 0; col < Game.COLS; col++) {
                board[row][col].reset();
            }
        }
    }

    public boolean isFull() {
        for (Cell[] row : board)
            for (Cell cell : row)
                if (cell.isEmpty())
                    return false;
        return true;
    }

    public void printBoard(){
        System.out.println("    1   2   3");
        System.out.println("   ___________");
        for(int row = 0; row < Game.ROWS; row++){
            System.out.print(row + 1 + " ");
            for(int col = 0; col < Game.COLS; col++){
                System.out.print("|");
                System.out.print(" " + board[row][col].getSymbol() + " ");
                if(col == 2)
                    System.out.print("|");
            }
            System.out.println("\n   ___________");
        }
    }

    //Winner Algorithm -> https://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe.html
    public boolean checkWinnerFor(Player player, int currentRow, int currentCol) {

        return (board[currentRow][0].player == player         // 3-in-the-row
                && board[currentRow][1].player == player
                && board[currentRow][2].player == player
                || board[0][currentCol].player == player      // 3-in-the-column
                && board[1][currentCol].player == player
                && board[2][currentCol].player == player
                || currentRow == currentCol            // 3-in-the-diagonal
                && board[0][0].player == player
                && board[1][1].player == player
                && board[2][2].player == player
                || currentRow + currentCol == 2    // 3-in-the-opposite-diagonal
                && board[0][2].player == player
                && board[1][1].player == player
                && board[2][0].player == player);
    }

}
