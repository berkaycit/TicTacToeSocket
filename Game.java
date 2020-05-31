public class Game {

    public static final int ROWS = 3;
    public static final int COLS = 3;

    public enum GameState {
        CONTINUE,
        WON,
        DRAW
    }

    //player1 is the host
    public Player player1;
    //player2 is the client
    public Player player2;
    public Player currentPlayer;

    public GameState state;
    private Board board;

    public Game(String p1name, String p2name, Board board) {
        this.board = board;
        player1 = new Player(p1name, 'X');
        player2 = new Player(p2name, 'O');
        currentPlayer = player1;
    }

    private void changeCurrentPlayer(){
        if(currentPlayer == player1)
            currentPlayer = player2;
        else
            currentPlayer = player1;
    }

    public void placeMark(int row, int col){
        row--; col--;
        if (board.getCell(row,col).isEmpty()) {
            board.setCell(row, col, currentPlayer);

            if (gameEnded(row, col)){
                //TODO:reset game
                //board.reset();
            }else{
                changeCurrentPlayer();
            }
        }
    }

    public boolean isValid(int row, int col) {
        row--; col--;
        if(outOfBounds(row) || outOfBounds(col)) {
            return false;
        } else return !alreadySet(row, col);
    }

    private boolean outOfBounds(int num) {
        return num < -1 || num > 2;
    }

    private boolean alreadySet(int row, int col) {
        return !(board.getCell(row,col).isEmpty());
    }

    public boolean gameEnded(int row, int col) {
        if (board.checkWinnerFor(currentPlayer, row, col)) {
            state = GameState.WON;
            return true;
        }

        if (board.isFull()) {
            state = GameState.DRAW;
            return true;
        }

        return false;
    }

}
