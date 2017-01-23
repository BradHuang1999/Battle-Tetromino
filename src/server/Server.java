package server;

/**
 * @author Charlie Lin & Brad Huang
 * @date Jan 21, 2017
 */

//imports for network communication
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server{
    /**
     * Main
     * main program
     *
     * @param args parameters from command line
     */
    public static void main(String[] args) throws IOException{
        new Server().go(); //start the server
    }

    // Server Socket
    private ServerSocket serverSock;

    // Boolean for accepting client connections
    private boolean running = true;

    // ArrayLists of user objects and threads for users
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Thread> clientThreads = new ArrayList<Thread>();
    private ArrayList<GameRoom> gameRooms = new ArrayList<GameRoom>();

    private ArrayList<String> messagesNick = new ArrayList<String>();
    private ArrayList<String> messagesIcon = new ArrayList<String>();
    private ArrayList<String> messagesTime = new ArrayList<String>();
    private ArrayList<String> messages = new ArrayList<String>();

    // File and PrintWriter
    private File messageFile = new File("resources/messages.txt");
    private PrintWriter usersFile = new PrintWriter(new FileWriter(messageFile, true));

    // number of users
    private int userNum = 0;

    // socket port number
    public static final String IP_ADDRESS = "127.0.0.1";
    public static final int PORT_NUM = 1234;

    JFrame serverFrame;
    JLabel titleLbl;
    JLabel userLbl;
    JButton terminateBtn;

    /**
     * constructor: set up a server
     * @throws IOException
     */
    public Server() throws IOException{

        // Variable for total number of users
        try {
            // Assigns port to server
            serverSock = new ServerSocket(PORT_NUM);

            // Read users from text file
            Scanner messagesReader = new Scanner(messageFile);
            while (messagesReader.hasNextLine()){
                messagesNick.add(messagesReader.nextLine());
                messagesIcon.add(messagesReader.nextLine());
                messagesTime.add(messagesReader.nextLine());
                messages.add(messagesReader.nextLine());
            }
            messagesReader.close();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Closing");
                usersFile.close();

                try {
                    PrintWriter messageFilePw = new PrintWriter(messageFile);
                    for (int i = 0; i < messages.size(); i++){
                        if (messages.get(i) != null){
                            messageFilePw.println(messagesNick.get(i));
                            messageFilePw.println(messagesIcon.get(i));
                            messageFilePw.println(messagesTime.get(i));
                            messageFilePw.println(messages.get(i));
                        }
                    }
                    messageFilePw.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }, "Shutdown-thread"));

            System.out.println("Waiting for client connection..");

        } catch (IOException e){
            System.out.println("Initiation Failed");
            System.exit(-1);
        }

        serverFrame = new JFrame("BattleTetromino Server");
        serverFrame.setSize(300, 300);
        serverFrame.setResizable(false);
        serverFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        serverFrame.getContentPane().setLayout(null);
        serverFrame.setAlwaysOnTop(true);

        titleLbl = new JLabel("Battle Tetromino");
        titleLbl.setFont(new Font("Trebuchet MS", Font.PLAIN, 35));
        titleLbl.setBounds(0, 60, 300, 45);
        titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
        serverFrame.getContentPane().add(titleLbl);

        userLbl = new JLabel("0 User Online");
        userLbl.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
        userLbl.setBounds(0, 135, 300, 20);
        userLbl.setHorizontalAlignment(SwingConstants.CENTER);
        serverFrame.getContentPane().add(userLbl);

        terminateBtn = new JButton("Quit");
        terminateBtn.setBounds(90, 200, 120, 30);
        terminateBtn.addActionListener(e -> {
            System.exit(0);
        });
        serverFrame.getContentPane().add(terminateBtn);

        serverFrame.setVisible(true);
    }

    /**
     * Go
     * Starts the server
     */
    public void go() throws IOException{
        // Continually loops to accept all users
        Socket tempSocket;
        User thisUser;
        try {
            while (running){
                // Adds user to arraylist
                tempSocket = serverSock.accept();
                thisUser = new User(tempSocket);
                users.add(thisUser);  // wait for connection

                // Creates a separate thread for each user
                clientThreads.add(new Thread(new ConnectionHandler(thisUser)));
                clientThreads.get(clientThreads.size() - 1).start(); //start the new thread

                System.out.println("New client connected");
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error accepting connection");
            System.exit(-1);
        }
    }

    //***** Inner class - thread for client connection
    class ConnectionHandler implements Runnable{
        private PrintWriter output; // assign printwriter to network stream
        private ServerBufferedReader input; // Stream for network input
        private Socket client;  // keeps track of the client socket
        private User user;
        private String inputMessage;

        boolean waiting;

        /**
         * ConnectionHandler
		 * Constructor
		 * @param User object with client information
		 */
        ConnectionHandler(User user){
            this.user = user;
            // Assigns socket variable
            this.client = user.socket;

            try {
                //assign all input and output to client
                this.output = new PrintWriter(client.getOutputStream());
                InputStreamReader stream = new InputStreamReader(this.client.getInputStream());
                this.input = new ServerBufferedReader(stream);
            } catch (IOException e){
                e.printStackTrace();
            }
            running = true;
        }

        /**
         * run
		 * executed on start of thread
		 */
        public synchronized void run(){
            waiting = true;
            while (waiting){
                // Checks username and password
                try {
                    if (input.ready()){
                        inputMessage = input.readLine();
                        if (inputMessage.equals("**login")){
                            // Takes in the username
                            user.nickName(input.readLine());
                            user.setUserIconPic(input.readLine());

                            userNum++;
                            if (userNum == 1){
                                userLbl.setText("1 user online");
                            } else {
                                userLbl.setText(userNum + " users online");
                            }

                            output.println("**successful");
                            waiting = false;
                        } else {
                            output.println("Invalid Command Received");
                            output.flush();
                        }
                    }
                    // If nothing is sent to client
                } catch (IOException e){
                    System.out.println("No Username or Password Received");
                    e.printStackTrace();
                }
            }

            waiting = true;

            // sends all messages to the client
            for (int i = 0; i < messages.size(); i++){
                output.println("**message\n" +
                        messagesNick.get(i) + "\n" +
                        messagesIcon.get(i) + "\n" +
                        messagesTime.get(i) + "\n" +
                        messages.get(i));
            }
            output.flush();

            new Thread(() -> {
                while (waiting){    // Loop to keep receiving messages from the client
                    for (int i = 0; i < gameRooms.size(); i++){
                        if (gameRooms.get(i) != null){
                            output.println("**gameRoom");      // Sends all the game room info to the client
                            output.println(gameRooms.get(i).getGameName());
                            output.println(gameRooms.get(i).getGameType());
                            output.println(gameRooms.get(i).getPlayersInRoom());
                            output.println(gameRooms.get(i).getMaxPlayers());
                            output.flush();
                        }
                    }

                    for (int i = 0; i < user.getNewMessages().size(); i++){
                        output.println("**message\n" +
                                user.getNewMessagesNick().get(i) + "\n" +
                                user.getNewMessagesIcon().get(i) + "\n" +
                                user.getNewMessagesTime().get(i) + "\n" +
                                user.getNewMessages().get(i));
                    }
                    output.flush();

                    // Removes messages
                    user.getNewMessagesNick().clear();
                    user.getNewMessagesIcon().clear();
                    user.getNewMessagesTime().clear();
                    user.getNewMessages().clear();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                    }
                }

            }).start();

            new Thread(() -> {
                while (waiting){
                    String userInput, userInput1, userInput2, userInput3, userInput4;
                    // Checks if the user is still connected
                    try {
                        if (input.ready()){
                            // Checks if the client is sending a message
                            // First input is a command for what they want to do
                            userInput = input.readLine();

                            // If statement for what client wants to do
                            switch (userInput){
                                case "**sendMessage":    // Sending a Message
                                    userInput1 = input.readLine();  // nickname
                                    userInput2 = input.readLine();  // icon
                                    userInput3 = input.readLine();  // timeStamp
                                    userInput4 = input.readLine();  // message

                                    for (int i = 0; i < users.size(); i++){
                                        if (users.get(i) != null && users.get(i) != user){
                                            // Receives and sends the message
                                            users.get(i).getNewMessagesNick().add(userInput1);
                                            users.get(i).getNewMessagesIcon().add(userInput2);
                                            users.get(i).getNewMessagesTime().add(userInput3);
                                            users.get(i).getNewMessages().add(userInput4);
                                        }
                                    }
                                    messagesNick.add(userInput1);
                                    messagesIcon.add(userInput2);
                                    messagesTime.add(userInput3);
                                    messages.add(userInput4);
                                    break;

                                case "**createRoom":
                                    userInput1 = input.readLine();  // gameName
                                    userInput2 = input.readLine();  // gameType

                                    gameRooms.add(new GameRoom(userInput1, userInput2));
                                    break;

                                case "**enterRoomAndPlay":
                                    userInput1 = input.readLine();  // roomName

                                    for (int i = 0; i < gameRooms.size(); i++){
                                        if (gameRooms.get(i).getGameName().equals(userInput1)){
                                            gameRooms.get(i).enterPlayer(user, output);
                                        }
                                    }
                                    break;

                                case "**game":
                                    userInput1 = input.readLine();  // roomName
                                    userInput2 = input.readLine();  // command
                                    userInput3 = input.readLine();  // data

                                    for (GameRoom gameRoom : gameRooms){
                                        if (gameRoom.getGameName().equals(userInput1)){
                                            gameRoom.handleCommand(user, userInput2, userInput3);
                                        }
                                    }
                                    break;

                                case "**leaveRoom":
                                    userInput1 = input.readLine();  // roomName
                                    for (GameRoom gameRoom : gameRooms){
                                        if (gameRoom.getGameName().equals(userInput1)){
                                            gameRoom.removeUser(user);
                                        }
                                    }
                                    break;

                                case "**close":
                                    users.remove(user);
                                    userNum--;
                                    if (userNum == 1){
                                        userLbl.setText("1 user online");
                                    } else {
                                        userLbl.setText(userNum + " users online");
                                    }

                                    break;

                                default:
                                    output.println("Invalid Command Received");
                                    output.flush();
                                    break;
                            }
                        }
                    } catch (IOException e){
                        System.out.println("Failed to receive msg from the client");
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e){
                    }
                }
            }).start();

        }
    }

    /**
     * Server Reader
     * read from users
     * if necessary, can display messages from users
     */
    class ServerBufferedReader extends BufferedReader{
        public ServerBufferedReader(Reader in){
            super(in);
        }

        @Override
        public String readLine() throws IOException{
            String line = super.readLine();
            System.out.println("Client: " + line);
            return line;
        }
    }
}