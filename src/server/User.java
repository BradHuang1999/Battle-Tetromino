package server;

import java.net.Socket;
import java.util.ArrayList;

// User class
class User {
    private String nickName;
    private String userIconPic;

    private ArrayList<String> newMessagesNick = new ArrayList<String>();
    private ArrayList<String> newMessagesIcon = new ArrayList<String>();
    private ArrayList<String> newMessagesTime = new ArrayList<String>();

    private ArrayList<String> newMessages = new ArrayList<String>();
	protected Socket socket;

	// Constructors
	User(Socket s){
		// Setting socket
		this.socket = s;
	}

    public String nickName(){
        return nickName;
    }

    public void nickName(String nickName){
        this.nickName = nickName;
    }

    public String getUserIconPic(){
        return userIconPic;
    }

    public void setUserIconPic(String userIconPic){
        this.userIconPic = userIconPic;
    }

    public ArrayList<String> getNewMessages(){
        return newMessages;
    }

    public ArrayList<String> getNewMessagesNick(){
        return newMessagesNick;
    }

    public ArrayList<String> getNewMessagesIcon(){
        return newMessagesIcon;
    }

    public ArrayList<String> getNewMessagesTime(){
        return newMessagesTime;
    }

    public String getNickName(){
        return nickName;
    }
}
