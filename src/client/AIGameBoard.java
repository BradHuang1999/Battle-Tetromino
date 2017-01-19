package client;

/**
 * Created by Brad Huang on 1/18/2017.
 */
public class AIGameBoard extends GameBoard{

    public int getSpikeyness(){
        int spikeyness = 0;
        boolean currentPiece;
        for (int y = 1; y < 21; y++){
            for (int x = 1; x < 13; x++){
                currentPiece = super.map[x][y];
                if (currentPiece != map[x + 1][y]){
                    spikeyness++;
                }
                if (currentPiece != map[x - 1][y]){
                    spikeyness++;
                }
                if (currentPiece != map[x][y + 1]){
                    spikeyness++;
                }
                if (currentPiece != map[x][y - 1]){
                    spikeyness++;
                }
            }
        }
        return spikeyness;
    }
}
