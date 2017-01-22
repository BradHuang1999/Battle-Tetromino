package client;

/**
 * Created by Brad Huang on 1/21/2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Client Reader
 * read from server
 * if necessary, can display messages from server
 */
class ClientBufferedReader extends BufferedReader{
    public ClientBufferedReader(Reader in){
        super(in);
    }

    @Override
    public String readLine() throws IOException{
        String line = super.readLine();
        System.out.println("Server: " + line);
        return line;
    }
}