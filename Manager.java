import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.net.Socket;

public class Manager {

    private Game game;
    private Board board;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private String message;
    private int row, col;

    Scanner input = new Scanner(System.in);

    private int getChoice() {
        int choice = 0;
        do {
            System.out.println("\n(1) Host a new game");
            System.out.println("(2) Join an existing game");
            System.out.println("(3) Exit");
            System.out.println("Your choice? ");
            choice = input.nextInt();
        }while (choice<0 || choice>3);

        return choice;
    }

    private void initNetwork(boolean hostIsYou) throws IOException, UnknownHostException {
        if(hostIsYou){
            ServerSocket serverSocket = new ServerSocket(3333);
            System.out.println("Waiting for an opponent ");
            socket = serverSocket.accept();
        }else{
            socket = new Socket("localhost", 3333);
        }

        System.out.println("Connected");

        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void startGame() throws IOException {
        int choice;
        board = new Board();

        System.out.println("Welcome to Tic Tac Toe Game");
        System.out.println("Pick a username? ");
        String userName = input.next();

        choice = getChoice();

        if(choice == 1){
            initNetwork(true);
            game = new Game(userName, "player2", board);
            playGame(true);

        }else{
            initNetwork(false);
            game = new Game("player1", userName, board);
            playGame(false);
        }

    }

    private boolean makeMove(Player mPlayer) throws IOException {
        if(game.state == Game.GameState.CONTINUE) {
            if (game.currentPlayer == mPlayer) {
                do {
                    System.out.println("Enter row number");
                    row = input.nextInt();
                    System.out.println("Enter col number");
                    col = input.nextInt();
                } while (!game.isValid(row, col));

                game.placeMark(row, col);
                board.printBoard();

                writer.println(game.state.ordinal() + "," + row + "," + col);

            } else {
                System.out.println("Waiting for opponent to make move");
                message = reader.readLine();

                game.placeMark(Integer.parseInt(message.substring(2, 3)), Integer.parseInt(message.substring(4, 5)));
                board.printBoard();

                if (message.charAt(0) == '1') {
                    System.out.println("You lost");
                    return true;
                }
            }

            if(game.state == Game.GameState.WON)
                System.out.println("You win");
            else if(game.state == Game.GameState.DRAW)
                System.out.println("It is a draw");

            return false;
        }else return true;
    }

    private void playGame(boolean hostIsYou) throws IOException {
        game.state = Game.GameState.CONTINUE;
        board.printBoard();
        boolean isOver;

        do {
            if (hostIsYou)
                isOver = makeMove(game.player1); //->host player
            else
                isOver = makeMove(game.player2); //->client player

        } while (!isOver);
    }

    public static void main(String[] args) {
        try {
            new Manager().startGame();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
