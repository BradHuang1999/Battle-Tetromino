package game.games;

import javax.swing.*;
import java.io.PrintWriter;

/**
 * Created by bradh on 1/18/2017.
 */
public class OnlineBattleGame extends DoublePlayerGame{
    public OnlineBattleGame(String nickName, String iconPath, PrintWriter output){
        super(nickName, iconPath, output);
        //setupOpponentGameOverScreen();
        //setupMyReadyScreen();
        //setupCountDownScreen();
    }

    public void getOpponent(String nickName, String iconPath){
        opponentIcon.setText("nickName");
        opponentIcon.setIcon(new ImageIcon("iconPath"));
    }

    @Override
    void setupOpponentReadyScreen(){

    }

    @Override
    void setupOpponentGameOverScreen(){

    }

    @Override
    void setupRewardModeScreen(int myLevelScore, int opponentLevelScore){

    }
}
