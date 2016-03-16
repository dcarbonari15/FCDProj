package edu.up.cs301.game.FiveCardDraw;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by carbonar19 on 3/16/2016.
 */
public class FCDLocal extends LocalGame implements FCDGame {

    FCDState state;

    public FCDLocal(){
        state = new FCDState();
    }

    @Override
    protected String checkIfGameOver() {
        if(!(state.getGameStage() == 6)){
            return null;
        }else{

        }

    }


    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {

    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }



    @Override
    protected boolean makeMove(GameAction action) {
        return false;
    }
}
