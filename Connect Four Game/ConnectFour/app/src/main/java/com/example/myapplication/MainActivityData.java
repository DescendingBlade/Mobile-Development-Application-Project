package com.example.myapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;

public class MainActivityData extends ViewModel {
    // Navigation
    public MutableLiveData<Integer> clickedValue;
    public MutableLiveData<Integer> previousClickedValue;

    // Player One data
    public MutableLiveData<String> playerOneName;
    public MutableLiveData<Integer> pOneAvatar;
    public MutableLiveData<Integer> pOneColour;
    public int wins;
    public int lose;
    public int draw;
    public int tGP; // Total Games Played

    // Player Two data
    public MutableLiveData<String> playerTwoName;
    public MutableLiveData<Integer> pTwoAvatar;
    public MutableLiveData<Integer> pTwoColour;

    // Computer player data
    public String computer;
    int computerAvatar;

    // Game state
    private MutableLiveData<int[][]> board;
    private boolean isPlayer1Turn;
    private MutableLiveData<Integer> movesCount;
    private MutableLiveData<Integer> numberMoves;
    private boolean gameOver;
    private MutableLiveData<LinkedList<Integer>> moveHistory;

    // Game result
    public String winner;
    public boolean pvp; // Player vs Player mode
    public boolean pOneSelect; // Player One's turn to select
    public int gameRow;
    public int gameCol;

    /**
     * Constructor to initialize all the data fields
     */
    public MainActivityData() {

        // Initialize navigation values
        clickedValue = new MutableLiveData<Integer>();
        clickedValue.setValue(0);
        previousClickedValue = new MutableLiveData<>();

        // Initialize Player One data
        playerOneName = new MutableLiveData<String>();
        pOneAvatar = new MutableLiveData<Integer>();
        pOneColour = new MutableLiveData<Integer>();
        playerOneName.setValue("Player One");
        pOneAvatar.setValue(R.drawable.avatar_default);
        pOneColour.setValue(R.drawable.token_red);

        // Initialize Player Two data
        playerTwoName = new MutableLiveData<String>();
        pTwoAvatar = new MutableLiveData<Integer>();
        pTwoColour = new MutableLiveData<Integer>();
        playerTwoName.setValue("Player Two");
        pTwoAvatar.setValue(R.drawable.avatar_default);
        pTwoColour.setValue(R.drawable.token_yellow);

        // Initialize computer player data
        computer = "Computer";
        computerAvatar = R.drawable.avatar_comp;

        // Initialize game state
        board = new MutableLiveData<int[][]>();
        movesCount = new MutableLiveData<Integer>();
        moveHistory = new MutableLiveData<LinkedList<Integer>>();
        numberMoves = new MutableLiveData<Integer>();
        isPlayer1Turn = true;
        gameOver = false;

        // Initialize game settings and statistics
        winner = "";
        pvp = false;
        pOneSelect=false;
        gameRow=6;
        gameCol=5;
        wins=0;
        lose=0;
        draw=0;
        tGP=0;
    }

    /**
     * Sets the current clicked value and updates the previous clicked value
     */
    public void setClickedValue(int clicked) {
        previousClickedValue.setValue(getClickedValue());
        clickedValue.setValue(clicked);
    }
    /**
     * Gets the previous clicked value
     */
    public int getPreviousClickedValue()
    {
        return previousClickedValue.getValue();
    }

    /**
     * Gets the current clicked value
     */
    public int getClickedValue() {
        return clickedValue.getValue();
    }

    /**
     * Sets the winner of the game
     */
    public void setWinner(String inWinner)
    {
        winner = inWinner;
    }

    /**
     * Gets the winner of the game
     */
    public String getWinner()
    {
        return winner;
    }

    /**
     * Enables Player vs Player mode
     */
    public void setPvpOn() {
        pvp = true;
    }

    /**
     * Disables Player vs Player mode (enables Player vs Computer)
     */
    public void setPvpOff() {
        pvp = false;
    }

    /**
     * Gets the current game mode (true for PvP, false for PvC)
     */
    public boolean getGameMode() {
        return pvp;
    }

    /**
     * Sets Player One's turn to select
     */
    public void setPOneSelect() {
        pOneSelect = true;
    }

    /**
     * Sets Player Two's turn to select
     */
    public void setPTwoSelect() {
        pOneSelect = false;
    }

    /**
     * Gets which player's turn it is to select
     */
    public boolean getSelect() {
        return pOneSelect;
    }

    // getter and setter methods for player names, avatars, colors
    public void setPlayerOne(String newUserName) {
        playerOneName.setValue(newUserName);
    }
    public String getPlayerOneName() {
        return playerOneName.getValue();
    }
    public int getPOneAvatar() {
        return pOneAvatar.getValue();
    }
    public int getPOneColour() {
        return pOneColour.getValue();
    }
    public void setPlayerTwo(String newUserName) {
        playerTwoName.setValue(newUserName);
    }
    public String getPlayerTwoName() {
        return playerTwoName.getValue();
    }
    public int getPTwoAvatar() {
        return pTwoAvatar.getValue();
    }
    public int getPTwoColour() {
        return pTwoColour.getValue();
    }
    public String getComp() {
        return computer;
    }
    public int getCompAvatar() {
        return computerAvatar;
    }

    /**
     * Sets the number of rows in the game board
     */
    public void setRow(int nRow)
    {
        gameRow=nRow;
    }

    /**
     * Gets the number of rows in the game board
     */
    public int getRow()
    {
        return gameRow;
    }

    /**
     * Sets the number of columns in the game board
     */
    public void setCol(int nCol)
    {
        gameCol = nCol;
    }

    /**
     * Gets the number of columns in the game board
     */
    public int getCol()
    {
        return gameCol;
    }

    //getter and setter methods for wins, losses, draws, and total games played
    public int getWins()
    {
        return wins;
    }
    public void setWins(int inWins)
    {
        wins = wins + inWins;
    }
    public int getLose()
    {
        return lose;
    }
    public void setLose(int inLose)
    {
        lose = lose + inLose;
    }
    public int getDraw()
    {
        return draw;
    }
    public void setDraw(int inDraw)
    {
        draw = draw + inDraw;
    }
    public int getTGP()
    {
        return tGP;
    }
    public void setTGP(int inTGP)
    {
        tGP = tGP + inTGP;
    }

    /**
     * Gets the current game board state
     */
    public int[][] getBoard() {
        return board.getValue();
    }

    /**
     * Sets the current game board state
     */
    public void setBoard(int[][] board) {
        this.board.setValue(board);
    }

    /**
     * Checks if it's Player One's turn
     */
    public boolean getIsPlayer1Turn() {
        return isPlayer1Turn;
    }

    /**
     * Sets whether it's Player One's turn
     */
    public void setPlayer1Turn(boolean player1Turn) {
        isPlayer1Turn = player1Turn;
    }

    //getter and setter methods for moves count, number of moves, game over state, and move history
    public int getMovesCount() {
        return movesCount.getValue();
    }
    public void setMovesCount(int movesCount) {
        this.movesCount.setValue(movesCount);
    }
    public void setNumberMoves(int inMoves)
    {
        numberMoves.setValue(inMoves);
    }
    public int getNumberMoves()
    {
        return numberMoves.getValue();
    }
    public boolean getIsGameOver() {
        return gameOver;
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    public LinkedList<Integer> getMoveHistory() {
        return moveHistory.getValue();
    }
    public void setMoveHistory(LinkedList<Integer> moveHistory) {
        this.moveHistory.setValue(moveHistory);
    }
}
