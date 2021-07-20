import java.util.InputMismatchException;
import java.util.Scanner;

public class PlaySession implements Runnable {

    private static ThreadLocal<Board> gameContext = new ThreadLocal<>();
    private Integer boardSize;
    private Integer numPlayers;


    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        //Taking the board size
        synchronized (gameContext) {
            System.out.println(Thread.currentThread().getName() + " Enter the board size:");
            while (true) {
                try {
                    boardSize = in.nextInt();
                    if (boardSize < 3) {
                        System.out.println(Thread.currentThread().getName() + " Increase board size");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println(Thread.currentThread().getName() +
                            " Invalid input: re-enter board size:");
                    in.nextLine();
                    continue;
                }
            }
        }

        //Taking number of players
        synchronized (gameContext) {
            System.out.println(Thread.currentThread().getName() + " Enter the number of Players:");
            while (true) {
                try {
                    numPlayers = in.nextInt();
                    if (numPlayers >= boardSize || numPlayers <= 1) {
                        System.out.println(Thread.currentThread().getName() + " Change Number of players");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println(Thread.currentThread().getName() +
                            " Invalid input: re-enter number of players:");
                    in.nextLine();
                    continue;
                }
            }
        }

        gameContext.set(new Board(boardSize, numPlayers));

        do{
            synchronized (gameContext) {
                System.out.println(Thread.currentThread().getName() + " Player #" + (int) (gameContext.get().getCurrent() + 1) + " Enter Slot number");
                int numInput;
                int turnResult;
                try {
                    numInput = in.nextInt();
                    turnResult = gameContext.get().turn(numInput);
                } catch (InputMismatchException e) {
                    System.out.println(Thread.currentThread().getName() +
                            " Invalid input; re-enter slot number:");
                    in.nextLine();
                    continue;
                }

                if (turnResult == -1) {
                    System.out.println(Thread.currentThread().getName() + " Invalid input: re-enter slot number");
                    continue;
                } else if (turnResult == -2) {
                    System.out.println(Thread.currentThread().getName() + " Slot already taken: re-enter slot number");
                    continue;
                }
                gameContext.get().printBoard();
            }

        }while(gameContext.get().getWinner() == 0);

        if(gameContext.get().getWinner() == -1){
            System.out.println(Thread.currentThread().getName() + " DRAW !!");
        } else {
            System.out.println(Thread.currentThread().getName() + " Player #" + (int)gameContext.get().getWinner() + " Won !!!");
        }
    }
}
