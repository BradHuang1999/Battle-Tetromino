package server;

import java.net.Socket;
import java.util.ArrayList;

// User class
class User {
	// User Variables
	public ArrayList<String> messages = new ArrayList<String>();
    public ArrayList<String> messageSenders = new ArrayList<String>();
	public ArrayList<String> messageSendersNick = new ArrayList<String>();
	public ArrayList<String> messageTime = new ArrayList<String>();

    public String sendMessage;

	private String username;
	private String password;
	private String nickname;
	private String signature;

	private Socket socket;
	private String messagingUser;
	private String status;

	// Constructors
	User(){
		this.status = "Offline";
	}

	User(Socket s){
		// Online status by default
		this.status = "Online";
		this.signature = "Status Message Here";
		// Setting socket
		this.socket = s;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the messageOfDay
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * @param messageOfDay the messageOfDay to set
	 */
	public void setSignature(String messageOfDay) {
		this.signature = messageOfDay;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @param socket the socket to set
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * @return the messagingUser
	 */
	public String getMessagingUser() {
		return messagingUser;
	}

	/**
	 * @param messagingUser the messagingUser to set
	 */
	public void setMessagingUser(String messagingUser) {
		this.messagingUser = messagingUser;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
