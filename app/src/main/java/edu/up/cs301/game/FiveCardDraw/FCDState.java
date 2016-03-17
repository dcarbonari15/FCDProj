package edu.up.cs301.game.FiveCardDraw;

import java.util.ArrayList;
import java.util.Arrays;

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
    private ArrayList<Card> deck = new ArrayList<Card>();
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
                Card card = new Card(cardVals[j], cardSuits[i]);
                deck.add(card);
                cardCount++;
            }
        }
        gameStage = 0;
        pot = 0;
    }

    /**
     * Creates a gamestate with the given inputs
     *
     * @param activePlayer --> the player's number whose turn it is
     * @param gameStage --> what point in the game the gamestate is at
     * @param pot --> The total amount of money that is a sum of all the bets and ante's made by the
     *            players
     * @param player1Bet --> the amount that the first player bets to be added to the pot
     * @param player2Bet --> the amount that the second player bets to be added to the pot
     * @param player1Money --> the amount of money that the first player has available to place a bet.
     * @param player2Money --> the amount of money that the second player has available to place a bet.
     * @param player1Fold --> is true depending on if the first player is still playing or not
     * @param player2Fold --> is true depending on if the second player is still playing or not
     */

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


    /**
     * This method takes in the input of the players hand and determines the numerical value of the
     * hand.
     *
     * @param playersHand --> an array for cards that are currently in the player's hand
     *
     * @return --> returns the 0-9 integer value of the players hand based on the following hierarchy
     * 0 --> High Card
     * 1 --> Single Pair
     * 2 --> Two Pair
     * 3 --> Three of a kind
     * 4 --> Straight
     * 5 --> Flush
     * 6 --> Full house
     * 7 --> 4 of a kind
     * 8 --> Straight Flush
     * 9 --> Royal Flush
     *
     */
    public int handValue(Card[] playersHand){
        Rank[] cardVals = new Rank[5];
        Suit[] cardSuit = new Suit[5];
        int handVal = 0;
        //separates the ranks and suits of the cards into separate arrays
        for(int i = 0; i < 5; i++){
            cardVals[i] = playersHand[i].getRank();
            cardSuit[i] = playersHand[i].getSuit();
        }
        int matchCount = 0;
        //counts the amount of pairs there are in the hand
        for(int i = 1; i < 5; i++){
            //the cards that match with the first card in the hand
            if (cardVals[0].equals(cardVals[i])) {
                matchCount++;
            }
            //the cards that match with the second card in the hand
            if(i > 1){
                if(cardVals[1].equals(cardVals[i])){
                    matchCount++;
                }
            }
            //the cards that match with the third card in the hand
            if(i > 2){
                if(cardVals[2].equals(cardVals[i])){
                    matchCount++;
                }
            }
            //the cards that match with the fourth card in the hand
            if(i > 3){
                if(cardVals[3].equals(cardVals[i])){
                    matchCount++;
                }
            }
        }
        //determines which hand the player has and sets the value based on
        //if they have a single pair
        if (matchCount == 1){
            handVal = 1;
        //if they have two pair
        }else if (matchCount == 2){
            handVal = 2;
        //if they have three of a kind
        }else if (matchCount == 3){
            handVal = 3;
        //if they have a full house
        }else if (matchCount == 4){
            handVal = 6;
        //if they have 4 of a kind
        }else if (matchCount == 6){
            handVal = 7;
        }
        //if they have a flush
        if(allSameSuit(cardSuit)){
            //if their flush is a royal flush
            if(Royalty(cardVals)){
                handVal = 9;
            //if their hand is a straight flush
            }else if(isStraight(cardVals)){
                handVal = 8;
            }
            handVal = 5;
        }
        //if their hand is a straight
        if(isStraight(cardVals)){
            handVal = 4;
        }
        return handVal;
    }

    /**
     * This method is a helper method to the handValue method that return whether the hand is a
     * royal flush or not
     *
     * @param ranks --> the Rank values of the cards in the players hand
     *
     * @return
     * returns true when the player has the highest value straight
     */
    private boolean Royalty(Rank[] ranks){
        int[] sorted = orderHand(ranks);
        return isStraight(ranks) && sorted[0] == 10;
    }

    /**
     * This method is a helper method to the handValue method that returns wherther the hand is a
     * straight or not
     *
     * @param ranks --> the Rank values of the cards in the players hand
     *
     * @return
     * returns true when the player has a straight
     */

    private boolean isStraight(Rank[] ranks){
        int[] sorted = orderHand(ranks);
        int count = 0;
        for(int i = 1; i < 5; i++){
            if(sorted[0] == sorted[i] - i){
                count++;
            }
            if((sorted[0] == 10) && (i == 4) ){
                if(sorted[i] == 1){
                    count++;
                }
            }
        }
        return count == 4;
    }

    /**
     * This method is a helper method to the isStraight method. It takes an array of ranks and then
     * converts them to numerical values and return them in ascending order.
     *
     * @param ranks --> the Rank values of the cards in the players hand
     *
     * @return
     * returns an array of integers in ascending order that represent the ranks in the player's hand
     */

    private int[] orderHand(Rank[] ranks){
        int[] handVals = new int[5];
        for(int i = 0; i < 5; i++){
            handVals[i] = ranksToInts(ranks[i]);
        }
        Arrays.sort(handVals);
        return handVals;
    }

    /**
     * This method is a helper method that changed the ranks into numerical values
     *
     * @param rank --> the Rank values of the cards in the players hand
     *
     * @return
     * returns the integer value for the corresponding rank
     */

    private int ranksToInts(Rank rank){
        if(rank.equals(Rank.ACE)){
            return 1;
        }else if(rank.equals(Rank.TWO)){
            return 2;
        }else if(rank.equals(Rank.THREE)){
            return 3;
        }else if(rank.equals(Rank.FOUR)){
            return 4;
        }else if(rank.equals(Rank.FIVE)){
            return 5;
        }else if(rank.equals(Rank.SIX)){
            return 6;
        }else if(rank.equals(Rank.SEVEN)){
            return 7;
        }else if(rank.equals(Rank.EIGHT)){
            return 8;
        }else if(rank.equals(Rank.NINE)){
            return 9;
        }else if(rank.equals(Rank.TEN)){
            return 10;
        }else if(rank.equals(Rank.JACK)){
            return 11;
        }else if(rank.equals(Rank.QUEEN)){
            return 12;
        }else{
            return 13;
        }
    }

    /**
     * This method is a helper method that checks if all the suits are the same
     *
     * @param suits --> the Suit values of the cards in the players hand
     *
     * @return
     * returns true if the players hand consists of all the same suit
     */
    private boolean allSameSuit(Suit[] suits){
        int sameSuitCount = 0;
        for(int i = 1; i < 5; i++){
            if(suits[0].equals(suits[i])){
                sameSuitCount++;
            }
        }
        if(sameSuitCount == 4){
            return true;
        }else{
            return false;
        }
    }

    public int subHandValue(Card[] cards){
        int handVal = handValue(cards);
        if(handVal == 0){
            int highestCard = 0;
            for(int i = 0; i < 5; i++){
                if(highestCard < ranksToInts(cards[i].getRank())){
                    highestCard = ranksToInts(cards[i].getRank());
                }
            }
            return highestCard;
        }else if(handVal == 1){

        }else if(handVal == 2){

        }else if(handVal == 3){

        }else if(handVal == 4){
            Rank[] ranks = new Rank[5];
            for(int i = 0; i < 5; i++){
                ranks[i] = cards[i].getRank();
            }
            int[] sorted = orderHand(ranks);
            return sorted[0];
        }else if(handVal == 5){
            int highestCard = 0;
            for(int i = 0; i < 5; i++){
                if(highestCard < ranksToInts(cards[i].getRank())){
                    highestCard = ranksToInts(cards[i].getRank());
                }
            }
            return highestCard;
        }else if(handVal == 6){

        }else if(handVal == 7){

        }else if(handVal == 8){
            Rank[] ranks = new Rank[5];
            for(int i = 0; i < 5; i++){
                ranks[i] = cards[i].getRank();
            }
            int[] sorted = orderHand(ranks);
            return sorted[0];
        }
        return 0;
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
