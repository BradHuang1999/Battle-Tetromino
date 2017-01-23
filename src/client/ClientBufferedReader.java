package client;

/**
 * @author Brad Huang
 * @date Dec 18, 2016
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Client Reader
 * read from server and display messages from server
 */
class ClientBufferedReader extends BufferedReader{
    public ClientBufferedReader(Reader in){
        super(in);
    }

    @Override
    public String readLine() throws IOException{
        String line = super.readLine();
        System.out.println("Server: " + line);      // read line
        return line;
    }
}