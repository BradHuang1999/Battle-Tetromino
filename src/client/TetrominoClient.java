package client;

import game.games.TetrisGame;

import java.io.*;
import java.net.Socket;

/**
 * @author Brad Huang
 * @date Jan 21, 2017
 * a central hub for client
 */
public class TetrominoClient{
    // setup login and game GUIs
    private LogInGUI login;
    protected TetrisGame game;

    protected Socket socket;
    protected ClientBufferedReader input;
    protected PrintWriter output;

    protected boolean outputOpen;

    protected String nickName;
    protected String iconPath;

    /**
     * start a chatServ client
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
        TetrominoClient client = new TetrominoClient();
        client.start();
    }

    /**
     * start the connection
     *
     * @throws IOException
     */
    public void start() throws IOException{
        login = new LogInGUI(this);     // set up and display login UI
        login.setVisible(true);
    }

    /**
     * close the battleTetromino client UI
     */
    public void close() {
        try {  //after leaving the main loop we need to close all the sockets
            this.output.println("**close");       // send the server a closing message
            this.output.flush();

            try {
                Thread.sleep(1000);     // wait for any extra response from server
            } catch (InterruptedException e) {
            }

            this.outputOpen = false;       // stop receiving message from server

            input.close();         // close the sockets
            output.close();
            socket.close();

            System.exit(0);
        } catch (Exception e) {
            System.out.println("Failed to close socket");
        }
    }
}