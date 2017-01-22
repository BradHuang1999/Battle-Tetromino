package server;

import java.io.PrintWriter;
import java.util.ArrayList;

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
    protected ArrayList<User> viewers = new ArrayList<User>();
    protected ArrayList<PrintWriter> viewerOutputs = new ArrayList<PrintWriter>();

    public GameRoom(String gameName, String gameType){
        this.gameName = gameName;
        this.gameType = gameType;

        this.game = (gameType.equals("Solo") || gameType.equals("Watch AI")) ? new Game() : new DoubleGame();
        this.maxPlayers = (gameType.equals("Online Battle")) ? 2 : 1;
        this.playersInRoom = 0;
    }

    public void enterPlayer(User user, PrintWriter output){
        // TODO
        for (User player : players){
            if (player == user){
                output.println("**permission to play\n" + gameName);
                output.flush();
                return;
            }
        }

        players.add(user);
        playerOutputs.add(output);
        playersInRoom++;

        output.println("**permission to play\n" + gameName);
        output.flush();
    }

    public void enterViewer(User user, PrintWriter output){
        // TODO
        for (User viewer : viewers){
            if (viewer == user){
                output.println("**permission to view\n" + gameName);
                output.flush();
                return;
            }
        }

        viewers.add(user);
        viewerOutputs.add(output);
        output.println("**permission to view \n" + gameName);
        output.flush();
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
