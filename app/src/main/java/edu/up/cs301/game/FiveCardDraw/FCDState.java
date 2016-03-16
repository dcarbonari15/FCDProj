package edu.up.cs301.game.FiveCardDraw;

import edu.up.cs301.card.Card;
import edu.up.cs301.card.Rank;
import edu.up.cs301.card.Suit;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * Created by carbonar19 on 3/14/2016.
 */
public class FCDState extends GameState {
    //instance Variables

    private Card[] player1Hand = new Card[5];
    private Card[] player2Hand = new Card[5];
    private Card[] deck = new Card[52];
    private Card cardBack;

    private int player1Money;
    private int player2Money;
    private int pot;
    private int handValue;
    private int subHandValue;
    private int activePlayer;
    private int player1Bet;
    private int player2Bet;
    private int gameStage;
    private int targetPlayer;

    private Rank[] cardVals= {Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX, Rank.SEVEN,
                                Rank.EIGHT, Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING};
    private Suit[] cardSuits = {Suit.Club, Suit.Diamond, Suit.Heart, Suit.Spade};

    private boolean player1Fold;
    private boolean player2Fold;

    /*
     *Creates the default GameState
     */

    public FCDState(){
        player1Money = 500;
        player2Money = 500;
        int cardCount = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 13; j++){
                deck[cardCount] =  new Card(cardVals[j], cardSuits[i]);
                cardCount++;
            }
        }
        gameStage = 0;
        pot = 0;
    }

    public FCDState(int activePlayer, int gameStage, int pot, int player1Bet, int player2Bet, int player1Money,
                    int player2Money, boolean player1Fold, boolean player2Fold){
        this.activePlayer = activePlayer;
        this.gameStage = gameStage;
        this.pot = pot;
        this.player1Bet = player1Bet;
        this.player2Bet = player2Bet;
        this.player1Money = player1Money;
        this.player2Money = player2Money;
        this.player1Fold = player1Fold;
        this.player2Fold = player2Fold;
        if(activePlayer == 1){
            this.targetPlayer = 2;
        }else if (activePlayer == 2){
            this.targetPlayer = 1;
        }
    }

    public int handValue(Card[] playersHand){

    }



    public int getPlayer1Money() {
        return player1Money;
    }

    public int getPlayer2Money() {
        return player2Money;
    }

    public Card getCardBack() {
        return cardBack;
    }

    public Card[] getDeck() {
        return deck;
    }

    public Card[] getPlayer1Hand() {
        return player1Hand;
    }

    public Card[] getPlayer2Hand() {
        return player2Hand;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public int getGameStage() {
        return gameStage;
    }

    public int getHandValue() {
        return handValue;
    }

    public int getPlayer1Bet() {
        return player1Bet;
    }

    public int getPlayer2Bet() {
        return player2Bet;
    }

    public int getPot() {
        return pot;
    }

    public int getSubHandValue() {
        return subHandValue;
    }

    public int getTargetPlayer() {
        return targetPlayer;
    }

    public Rank[] getCardVals() {
        return cardVals;
    }

    public Suit[] getCardSuits() {
        return cardSuits;
    }

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void setCardBack(Card cardBack) {
        this.cardBack = cardBack;
    }

    public void setCardSuits(Suit[] cardSuits) {
        this.cardSuits = cardSuits;
    }

    public void setCardVals(Rank[] cardVals) {
        this.cardVals = cardVals;
    }

    public void setDeck(Card[] deck) {
        this.deck = deck;
    }

    public void setGameStage(int gameStage) {
        this.gameStage = gameStage;
    }

    public void setHandValue(int handValue) {
        this.handValue = handValue;
    }

    public void setPlayer1Bet(int player1Bet) {
        this.player1Bet = player1Bet;
    }

    public void setPlayer1Fold(boolean player1Fold) {
        this.player1Fold = player1Fold;
    }

    public void setPlayer1Hand(Card[] player1Hand) {
        this.player1Hand = player1Hand;
    }

    public void setPlayer1Money(int player1Money) {
        this.player1Money = player1Money;
    }

    public void setPlayer2Bet(int player2Bet) {
        this.player2Bet = player2Bet;
    }

    public void setPlayer2Fold(boolean player2Fold) {
        this.player2Fold = player2Fold;
    }

    public void setPlayer2Hand(Card[] player2Hand) {
        this.player2Hand = player2Hand;
    }

    public void setPlayer2Money(int player2Money) {
        this.player2Money = player2Money;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public void setSubHandValue(int subHandValue) {
        this.subHandValue = subHandValue;
    }

    public void setTargetPlayer(int targetPlayer) {
        this.targetPlayer = targetPlayer;
    }


}
