package server;

import game.queue.Queue;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Brad Huang on 1/21/2017.
 */
public class GameRoom{
    private Game game;
    private String gameName;
    private String gameType;

    private int playersInRoom;
    private int maxPlayers;

    protected ArrayList<User> players = new ArrayList<User>();
    protected ArrayList<PrintWriter> playerOutputs = new ArrayList<PrintWriter>();

    private ArrayList<Queue<Character>> tetrominoQueues = new ArrayList<Queue<Character>>();

    public GameRoom(String gameName, String gameType){
        this.gameName = gameName;
        this.gameType = gameType;

        this.game = (gameType.equals("Solo") || gameType.equals("Watch AI")) ? new Game() : new DoubleGame();
        this.maxPlayers = (gameType.equals("Online Battle")) ? 2 : 1;
        this.playersInRoom = 0;
    }

    public void enterPlayer(User user, PrintWriter output){
        for (User player : players){
            if (player == user){
                output.println("**permission to play\n" + gameName);
                output.flush();

                for (User playa: players){
                    if (playa != user){
                        output.println("**onlineGame\n" + gameName + "\ngetOpponent\n" + playa.getNickName() + " " + playa.getUserIconPic());
                    }
                }
                return;
            }
        }

        output.println("**permission to play\n" + gameName);
        output.flush();

        for (User player: players){
            output.println("**onlineGame\n" + gameName + "\ngetOpponent\n" + player.getNickName() + " " + player.getUserIconPic());
        }

        players.add(user);
        playerOutputs.add(output);
        tetrominoQueues.add(new Queue<Character>());
        playersInRoom++;
    }

    public void handleCommand(User user, String command, String data){
        if (command.equals("requestTetromino")){
            Character tetrominoChar = new Character[]{'I', 'S', 'O', 'L', 'Z', 'T', 'J'}[new Random().nextInt(7)];
            char mychar;
            for (int i = 0; i < players.size(); i++){
                tetrominoQueues.get(i).enqueue(tetrominoChar);

                System.out.println(" -- enqueued " + players.get(i).nickName() + " a " + tetrominoChar);

                if (players.get(i) == user){
                    mychar = tetrominoQueues.get(i).dequeue();
                    playerOutputs.get(i).println("**onlineGame\n" + gameName + "\nenqueueTetromino\n" + mychar);
                    System.out.println(" -- sent " + user.nickName() + " a " + mychar);
                    playerOutputs.get(i).flush();
                }
            }

        } else {
            for (int i = 0; i < players.size(); i++){
                if (players.get(i) != user){
                    playerOutputs.get(i).println("**onlineGame\n" + gameName + "\n" + command + "\n" + data);
                    playerOutputs.get(i).flush();
                }
            }
        }
    }

    public void removeUser(User user){
        for (int i = 0; i < players.size(); i++){
            if (players.get(i) == user){
                players.remove(i);
                playerOutputs.remove(i);
                playersInRoom--;
            }
        }
    }

    public Game getGame(){
        return game;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public String getGameName(){
        return gameName;
    }

    public void setGameName(String gameName){
        this.gameName = gameName;
    }

    public String getGameType(){
        return gameType;
    }

    public void setGameType(String gameType){
        this.gameType = gameType;
    }

    public int getPlayersInRoom(){
        return playersInRoom;
    }

    public int getMaxPlayers(){
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers){
        this.maxPlayers = maxPlayers;
    }

}
