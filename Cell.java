public class Cell {

    public Player player;

    public Cell(Player player) {
        this.player = player;
    }

    public boolean isEmpty() {
        return player == null;
    }

    public char getSymbol(){
        if (isEmpty())
            return ' ';
        else
            return player.symbol;
    }

    public void reset(){
        player = null;
    }

}

