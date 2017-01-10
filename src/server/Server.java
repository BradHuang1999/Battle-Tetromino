package server;

/**
 * @author Dyno Zheng
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
     * @param args parameters from command line
     */
    public static void main(String[] args) throws IOException{
        new Server().go(); //start the server
    }

    // Server Socket
    private ServerSocket serverSock;

    // Boolean for accepting client connections
    private boolean running = true;

    // ArrayLists of user objects and threads for clients
    private ArrayList<User> clients = new ArrayList<User>();
    private ArrayList<Thread> clientThreads = new ArrayList<Thread>();

    // File and PrintWriter
    private File users = new File("users.txt");
    private PrintWriter usersFile = new PrintWriter(new FileWriter(users, true));

    // number of clients
    private int numClients = 0;
    private int userNum = 0;
    private int clientNum = 0;

    // socket port number
    public static final String IP_ADDRESS = "127.0.0.1";
    public static final int PORT_NUM = 3672;

    JFrame serverFrame;
    JLabel titleLbl;
    JLabel userLbl;
    JButton terminateBtn;

    public Server() throws IOException{

        // Variable for total number of clients
        try {
            // Assigns port to server
            serverSock = new ServerSocket(PORT_NUM);
            // Read users from text file
            Scanner usersReader = new Scanner(users);
            while (usersReader.hasNextLine()){
                clients.add(new User());
                clients.get(userNum).setUsername(usersReader.nextLine());
                clients.get(userNum).setNickname(usersReader.nextLine());
                clients.get(userNum).setPassword(usersReader.nextLine());
                clients.get(userNum).setSignature(usersReader.nextLine());
                userNum++;
            }
            usersReader.close();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Closing");
                usersFile.close();

                try {
                    PrintWriter usersFilePw = new PrintWriter(users);
                    for (int i = 0; i < clients.size(); i++){
                        if (clients.get(i).getUsername() != null){
                            usersFilePw.println(clients.get(i).getUsername());
                            usersFilePw.println(clients.get(i).getNickname());
                            usersFilePw.println(clients.get(i).getPassword());
                            usersFilePw.println(clients.get(i).getSignature());
                        }
                    }
                    usersFilePw.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }, "Shutdown-thread"));

            System.out.println("Waiting for client connection..");

        } catch (IOException e){
            System.out.println("Initiation Failed");
            System.exit(-1);
        }

        serverFrame = new JFrame("ChatServ Server");
        serverFrame.setSize(300, 300);
        serverFrame.setResizable(false);
        serverFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        serverFrame.getContentPane().setLayout(null);
        serverFrame.setAlwaysOnTop(true);

        titleLbl = new JLabel("ChatServ");
        titleLbl.setFont(new Font("Trebuchet MS", Font.PLAIN, 40));
        titleLbl.setBounds(69, 60, 160, 45);
        serverFrame.getContentPane().add(titleLbl);

        userLbl = new JLabel("0 User Online");
        userLbl.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
        userLbl.setBounds(90, 135, 160, 20);
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
        // Continually loops to accept all clients
        Socket tempSocket;
        try {
            while (running){
                // Adds user to arraylist
                tempSocket = serverSock.accept();
                clients.add(new User());  //wait for connection
                clients.get(userNum).setSocket(tempSocket);

                // Creates a separate thread for each user
                clientThreads.add(new Thread(new ConnectionHandler(clients.get(userNum))));
                clientThreads.get(clientNum).start(); //start the new thread
                userNum++;
                clientNum++;

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
        private PrintWriter output; //assign printwriter to network stream
        private ServerBufferedReader input; //Stream for network input
        private Socket client;  //keeps track of the client socket
        private User user;
        private String inputMessage;

        /* ConnectionHandler
		 * Constructor
		 * @param User object with client information
		 */
        ConnectionHandler(User user){
            this.user = user;
            // Assigns socket variable
            this.client = user.getSocket();
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


        /* run
		 * executed on start of thread
		 */
        public void run(){
            boolean waiting = true;

            while (waiting){
                // Checks username and password
                try {
                    if (input.ready()){
                        inputMessage = input.readLine();

                        // If the user is making a new account
                        if (inputMessage.equals("CreateUser")){
                            // Setting the username, nickname, and password
                            inputMessage = input.readLine();

                            boolean usernameInUse = false;

                            // Checks if the username is in use
                            for (int i = 0; i < clients.size() - 1; i++){
                                if (clients.get(i).getUsername() != null){
                                    if (clients.get(i).getUsername().equals(inputMessage)){
                                        inputMessage = input.readLine();
                                        inputMessage = input.readLine();
                                        inputMessage = input.readLine();
                                        output.println("Username already in use");
                                        output.flush();
                                        usernameInUse = true;
                                        break;
                                    }
                                }
                            }

                            if (!usernameInUse){
                                // Sets Username
                                user.setUsername(inputMessage);
                                // Sets Nickname
                                inputMessage = input.readLine();
                                user.setNickname(inputMessage);
                                // Sets and checks passwords
                                inputMessage = input.readLine();
                                user.setPassword(inputMessage);

                                user.setSignature("Hello World!");

                                // Confirmation Password
                                inputMessage = input.readLine();

                                if (user.getPassword().equals(inputMessage)){
                                    // Updates textfile with users
                                    usersFile.println(user.getUsername());
                                    usersFile.println(user.getNickname());
                                    usersFile.println(user.getPassword());
                                    usersFile.println(user.getSignature());
                                    // Confirms user creation
                                    output.println("User created");
                                    output.flush();
                                } else {
                                    // If passwords don't match
                                    output.println("Passwords do not match");
                                    output.flush();
                                }
                            }

                        } else if (inputMessage.equals("Login")){
                            // Takes in the username
                            inputMessage = input.readLine();
                            // Searches for username
                            for (int i = 0; i < clients.size(); i++){
                                if (clients.get(i).getUsername() != null){
                                    // If username matches a known user
                                    if (inputMessage.equals(clients.get(i).getUsername())){
                                        // Checks for password
                                        inputMessage = input.readLine();
                                        if (inputMessage.equals(clients.get(i).getPassword())){
                                            // Accepts user if passwords match
                                            user = clients.get(i);
                                            clients.remove(i);
                                            clients.add(user);
                                            output.println("Successful");
                                            user.setStatus("Online");
                                            output.println(user.getUsername() + "\n" + user.getNickname() + "\n" + user.getSignature());
                                            output.flush();

                                            numClients++;
                                            if (numClients == 1){
                                                userLbl.setText("1 user online");
                                            } else {
                                                userLbl.setText(numClients + " users online");
                                            }

                                            waiting = false;
                                            break;
                                        } else {
                                            output.println("Unsuccessful");
                                            output.flush();
                                        }
                                    }
                                }
                            }
                            // If username is not found in the database
                            if (user == null){
                                output.println("Unsuccessful");
                                output.flush();
                            }
                            // If client doesn't send valid username command
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

            // Loop to keep receiving messages from the client
            while (waiting){
                // Sends all the friends list info to the client
                output.println("Friends List Info");
                for (int i = 0; i < clients.size(); i++){
                    if (clients.get(i).getUsername() != null){
                        output.println(clients.get(i).getUsername());
                        output.println(clients.get(i).getNickname());
                        output.println(clients.get(i).getSignature());
                        output.println(clients.get(i).getStatus());
                    }
                }
                // Signals end of friends list info
                output.println("End of Friends List");
                output.flush();

                for (int i = 0; i < user.messages.size(); i++){
                    // send a message
                    output.println("Message");
                    // Username of person messaging
                    output.println(user.messageSenders.get(i));
                    // Nickname of person messaging
                    output.println(user.messageSendersNick.get(i));
                    // Timestamp of message
                    output.println(user.messageTime.get(i));
                    // Actual message
                    output.println(user.messages.get(i));
                    // Signals end of new message
                    output.println("End of Message");
                    // Sends message
                    output.flush();
                }

                // Removes messages
                user.messages.clear();
                user.messageTime.clear();
                user.messageSenders.clear();
                user.messageSendersNick.clear();

                // Checks if the user is still connected
                try {
                    if (input.ready()){
                        // Checks if the client is sending a message
                        // First input is a command for what they want to do
                        user.setMessagingUser(input.readLine());

                        // If statement for what client wants to do
                        // Sending a Message
                        if (user.getMessagingUser().equals("SendMessage")){
                            // Searches for the user in the arraylist
                            user.setMessagingUser(input.readLine());
                            for (int i = 0; i < clients.size(); i++){
                                if (clients.get(i).getUsername() != null){
                                    if (clients.get(i).getUsername().equals(user.getMessagingUser())){
                                        // Receives and sends the message
                                        clients.get(i).messageSenders.add(user.getUsername());
                                        clients.get(i).messageSendersNick.add(user.getNickname());

                                        user.sendMessage = input.readLine();
                                        clients.get(i).messageTime.add(user.sendMessage);

                                        user.sendMessage = input.readLine();
                                        clients.get(i).messages.add(user.sendMessage);

                                        System.out.println("msg from " + user.getUsername() + " to " + clients.get(i).getUsername() + ": " + user.sendMessage);
                                    }
                                }
                            }

                        } else if (user.getMessagingUser().equals("ChangeSignature")){
                            inputMessage = input.readLine();
                            user.setSignature(inputMessage);

                            // Setting Status To Busy
                        } else if (user.getMessagingUser().equals("Busy")){
                            user.setStatus("Busy");

                            // Setting Status to Online
                        } else if (user.getMessagingUser().equals("Online")){
                            user.setStatus("Online");

                        } else if (user.getMessagingUser().equals("Offline")){
                            user.setStatus("Offline");

                        } else if (user.getMessagingUser().equals("Close")){
                            numClients--;
                            if (numClients == 1){
                                userLbl.setText("1 user online");
                            } else {
                                userLbl.setText(numClients + " users online");
                            }

                            user.setStatus("Offline");
                            System.out.println("User " + user.getUsername() + " has disconnected");
                            waiting = false;

                        } else {
                            output.println("Invalid Command Received");
                            output.flush();
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
        }
    }

    /**
     * Server Reader
     * read from clients
     * if necessary, can display messages from clients
     */
    class ServerBufferedReader extends BufferedReader{
        public ServerBufferedReader(Reader in){
            super(in);
        }

//		@Override
//		public String readLine() throws IOException{
//			String line = super.readLine();
//			System.out.println("Client: " + line);
//			return line;
//		}
    }
}