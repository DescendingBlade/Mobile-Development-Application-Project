package com.example.myapplication;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Game extends Fragment {
    //Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //Rename and change the types of the parameters
    private String mParam1;
    private String mParam2;

    private MainActivityData mainActivityData;
    private TextView inGamePOneName;
    private TextView inGamePTwoName;
    private ImageView inGamePOneAvatar;
    private ImageView inGamePTwoAvatar;
    private ImageView settingsButton;
    private ImageView undoButton;
    private GridLayout gameBoard;
    private TextView movesTextView;
    private TextView movesAvailable;

    private final static int[] ALL_COLORS = {R.drawable.token_blue, R.drawable.token_red, R.drawable.token_yellow, R.drawable.token_pink, R.drawable.token_purple, R.drawable.token_green};
    private LinkedList<Integer> moveHistory;
    private int[][] board;
    private boolean isPlayer1Turn;
    private int movesCount;
    private int nMoves;
    private boolean gameOver;

    /**
     * Creates a new instance of the Game fragment.
     * @param param1 First parameter (unused in this implementation)
     * @param param2 Second parameter (unused in this implementation)
     * @return A new instance of fragment Game
     */
    public static Game newInstance(String param1, String param2) {
        Game fragment = new Game();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when the fragment is being created.
     * Retrieves arguments if any.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     * Initializes game state, UI elements, and sets up event listeners.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game, container, false);
        mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        inGamePOneName = view.findViewById(R.id.inGamePOneName);
        inGamePTwoName = view.findViewById(R.id.inGamePTwoName);
        inGamePOneAvatar = view.findViewById(R.id.inGamePOneAvatar);
        inGamePTwoAvatar = view.findViewById(R.id.inGamePTwoAvatar);
        settingsButton = view.findViewById(R.id.settingsButton);
        gameBoard = view.findViewById(R.id.gameBoard);
        movesTextView = view.findViewById(R.id.moves);
        undoButton = view.findViewById(R.id.undoButton);
        movesAvailable = view.findViewById(R.id.movesAvailable);

        inGamePOneName.setText(mainActivityData.getPlayerOneName());
        inGamePOneAvatar.setImageResource(mainActivityData.getPOneAvatar());


        if(mainActivityData.getBoard() == null || mainActivityData.getIsGameOver())
        {
            board = new int[mainActivityData.getRow()][mainActivityData.getCol()];
            for (int row = 0; row < mainActivityData.getRow(); row++)
            {
                for (int col = 0; col < mainActivityData.getCol(); col++)
                {
                    board[row][col] = 0;
                }
            }
            mainActivityData.setBoard(board);
            moveHistory = new LinkedList<>();
            mainActivityData.setMoveHistory(moveHistory);
            isPlayer1Turn = true;
            movesCount = 0;
            nMoves = mainActivityData.getRow() * mainActivityData.getCol();
            updateMovesDisplay();
            gameOver = false;;

            //saving the data to the viewmodel
            mainActivityData.setBoard(board);
            mainActivityData.setMoveHistory(moveHistory);
            mainActivityData.setMovesCount(movesCount);
            mainActivityData.setNumberMoves(nMoves);
            mainActivityData.setGameOver(gameOver);
            mainActivityData.setPlayer1Turn(isPlayer1Turn);
        }
        else
        {
            board = mainActivityData.getBoard();
            isPlayer1Turn = mainActivityData.getIsPlayer1Turn();
            movesCount = mainActivityData.getMovesCount();
            nMoves = mainActivityData.getNumberMoves();
            updateMovesDisplay();
            gameOver = mainActivityData.getIsGameOver();
            moveHistory = mainActivityData.getMoveHistory();
        }

        if (mainActivityData.getGameMode())
        {
            inGamePTwoName.setText(mainActivityData.getPlayerTwoName());
            inGamePTwoAvatar.setImageResource(mainActivityData.getPTwoAvatar());
        }
        else
        {
            inGamePTwoName.setText(mainActivityData.getComp());
            inGamePTwoAvatar.setImageResource(mainActivityData.getCompAvatar());
            mainActivityData.pTwoColour.setValue(nCompColour());
        }

        initializeBoard();
        updatePlayerTurnIndicator();

        undoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                undoMove();
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener()
        {
                @Override
                public void onClick (View view){
                    mainActivityData.setClickedValue(5);
                }
            });
        return view;
    }

    /**
     * Initializes the game board UI.
     * Creates and sets up the grid of cells for the game board.
     */
    private void initializeBoard() {
        gameBoard.removeAllViews();
        gameBoard.setColumnCount(mainActivityData.getCol());
        gameBoard.setRowCount(mainActivityData.getRow());

        for (int row = 0; row < mainActivityData.getRow(); row++)
        {
            for (int col = 0; col < mainActivityData.getCol(); col++)
            {
                ImageView cell = new ImageView(getContext());
                cell.setImageResource(R.drawable.token_empty);
                cell.setScaleType(ImageView.ScaleType.FIT_XY);
                cell.setAdjustViewBounds(true);
                if(board[row][col] == 1)
                {
                    cell.setImageResource(mainActivityData.getPOneColour());
                }
                else if(board[row][col] == 2)
                {
                    cell.setImageResource(mainActivityData.getPTwoColour());
                }
                final int column = col;
                cell.setOnClickListener(v -> dropPiece(column));
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;
                params.columnSpec = GridLayout.spec(col, 1f);
                params.rowSpec = GridLayout.spec(row, 1f);
                cell.setLayoutParams(params);
                gameBoard.addView(cell);
            }
        }
    }

    /**
     * Handles the logic for dropping a piece in a column.
     * Updates the game state, checks for win conditions, and handles turn switching.
     * @param column The column where the piece is dropped
     */
    private void dropPiece(int column)
    {
        boolean piecePlaced = false;
        int row = mainActivityData.getRow() - 1;

        while (!piecePlaced && row >= 0)
        {
            if (board[row][column] == 0)
            {
                board[row][column] = isPlayer1Turn ? 1 : 2;
                updateCellUI(row, column);
                moveHistory.addLast(row * 1000 + column);
                movesCount++;
                nMoves--;
                updateMovesDisplay();
                piecePlaced = true;
            }
            else
            {
                row--;
            }
        }

        if (piecePlaced)
        {
            if (checkWin(row, column))
            {
                String winner;
                if (isPlayer1Turn)
                {
                    winner = mainActivityData.getPlayerOneName();
                }
                else if (mainActivityData.getGameMode())
                {
                    winner = mainActivityData.getPlayerTwoName();
                }
                else
                {
                    winner = mainActivityData.getComp();
                }
                endGame(winner);
            }
            else if (movesCount == mainActivityData.getRow() * mainActivityData.getCol())
            {
                endGame(null); // Draw
            } else
            {
                isPlayer1Turn = !isPlayer1Turn;
                updatePlayerTurnIndicator();
                if (!mainActivityData.getGameMode() && !isPlayer1Turn)
                {
                    makeAIMove();
                }
            }
        }
        //saving the data to the viewmodel
        mainActivityData.setBoard(board);
        mainActivityData.setMoveHistory(moveHistory);
        mainActivityData.setMovesCount(movesCount);
        mainActivityData.setNumberMoves(nMoves);
        mainActivityData.setGameOver(gameOver);
        mainActivityData.setPlayer1Turn(isPlayer1Turn);
    }

    /**
     * Undoes the last move made in the game.
     * Removes the last piece placed and updates the game state accordingly.
     */
    private void undoMove() {
        if (!moveHistory.isEmpty() && !gameOver)
        {
            int lastMove = moveHistory.removeLast();
            int row = lastMove / 1000;
            int column = lastMove % 1000;

            board[row][column] = 0;
            updateCellUI(row, column);
            isPlayer1Turn = !isPlayer1Turn; // Switch back to the previous player
            movesCount--;
            nMoves++;
            updateMovesDisplay();
            updatePlayerTurnIndicator();

            // If playing against AI and we've undone the player's move, also undo the AI's move
            if (!mainActivityData.getGameMode() && !isPlayer1Turn)
            {
                undoMove();
            }
        }
        //saving the data to the viewmodel
        mainActivityData.setBoard(board);
        mainActivityData.setMoveHistory(moveHistory);
        mainActivityData.setMovesCount(movesCount);
        mainActivityData.setNumberMoves(nMoves);
        mainActivityData.setGameOver(gameOver);
        mainActivityData.setPlayer1Turn(isPlayer1Turn);
    }

    /**
     * Updates the UI for a specific cell on the game board.
     * @param row The row of the cell to update
     * @param column The column of the cell to update
     */
    private void updateCellUI(int row, int column)
    {
        ImageView cell = (ImageView) gameBoard.getChildAt(row * mainActivityData.gameCol + column);
        if (board[row][column] == 2) {
            cell.setImageResource(mainActivityData.getPTwoColour());
        }
        else if(board[row][column] == 1)
        {
            cell.setImageResource(mainActivityData.getPOneColour());
        }
        else
        {
            cell.setImageResource(R.drawable.token_empty);
        }
    }

    /**
     * Checks if the last move resulted in a win.
     * Checks for four consecutive pieces in all directions.
     * @param row The row of the last move
     * @param column The column of the last move
     * @return true if the move resulted in a win, false otherwise
     */
    private boolean checkWin(int row, int column)
    {
        int player;
        boolean win = false;

        if(isPlayer1Turn)
        {
            player = 1;
        }
        else
        {
            player=2;
        }

        // Check horizontal
        int horizontalCount = checkDirection(row, column, 0, 1, player);
        if (horizontalCount >= 4)
        {
            win = true;
        }

        // Check vertical
        if (!win)
        {
            int verticalCount = checkDirection(row, column, 1, 0, player);
            if (verticalCount >= 4)
            {
                win = true;
            }
        }

        // Check diagonal (top-left to bottom-right)
        if (!win)
        {
            int diagonalCount1 = checkDirection(row, column, 1, 1, player);
            if (diagonalCount1 >= 4)
            {
                win = true;
            }
        }

        // Check diagonal (top-right to bottom-left)
        if (!win)
        {
            int diagonalCount2 = checkDirection(row, column, 1, -1, player);
            if (diagonalCount2 >= 4)
            {
                win = true;
            }
        }
        return win;
    }

    /**
     * Checks for consecutive pieces in a specific direction.
     * @param row Starting row
     * @param column Starting column
     * @param dRow Row direction (-1, 0, or 1)
     * @param dCol Column direction (-1, 0, or 1)
     * @param player Player to check for (1 or 2)
     * @return Count of consecutive pieces
     */
    private int checkDirection(int row, int column, int dRow, int dCol, int player)
    {
        int count = 1;
        count += countConsecutive(row, column, dRow, dCol, player);
        count += countConsecutive(row, column, -dRow, -dCol, player);
        return count;
    }

    /**
     * Counts consecutive pieces in a specific direction.
     * @param row Starting row
     * @param column Starting column
     * @param dRow Row direction (-1, 0, or 1)
     * @param dCol Column direction (-1, 0, or 1)
     * @param player Player to check for (1 or 2)
     * @return Count of consecutive pieces
     */
    private int countConsecutive(int row, int column, int dRow, int dCol, int player)
    {
        int count = 0;
        int i = 1;
        boolean continueChecking = true;

        while (continueChecking && i < 4)
        {
            int newRow = row + i * dRow;
            int newCol = column + i * dCol;

            if (newRow < 0 || newRow >= mainActivityData.getRow() || newCol < 0 || newCol >= mainActivityData.getCol())
            {
                continueChecking = false;
            }
            else if (board[newRow][newCol] == player)
            {
                count++;
                i++;
            }
            else
            {
                continueChecking = false;
            }
        }
        return count;
    }

    /**
     * Handles the end of the game.
     * Updates game state and statistics based on the game outcome.
     * @param winner The name of the winning player, or null if it's a draw
     */
    private void endGame(String winner)
    {
        gameOver = true;
        mainActivityData.setGameOver(gameOver);
        if(winner == null)
        {
            mainActivityData.setDraw(1);
            mainActivityData.setTGP(1);
        }
        else if(winner.equals(mainActivityData.getPlayerOneName()))
        {
            mainActivityData.setWins(1);
            mainActivityData.setTGP(1);
        }
        else if(winner.equals(mainActivityData.getPlayerTwoName()))
        {
            mainActivityData.setTGP(1);
            mainActivityData.setLose(1);
        }
        else if(winner.equals(mainActivityData.getComp()))
        {
            mainActivityData.setTGP(1);
            mainActivityData.setLose(1);
        }
        mainActivityData.setWinner(winner);
        mainActivityData.setClickedValue(8);
    }

    /**
     * Updates the UI to indicate whose turn it is.
     * Changes the text color of the player names to highlight the current player.
     */
    private void updatePlayerTurnIndicator()
    {
        if (isPlayer1Turn)
        {
            inGamePOneName.setTextColor(getResources().getColor(android.R.color.holo_red_light));
            inGamePTwoName.setTextColor(getResources().getColor(android.R.color.black));
        } else
        {
            inGamePOneName.setTextColor(getResources().getColor(android.R.color.black));
            inGamePTwoName.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
    }

    /**
     * Makes a move for the AI player.
     * Randomly selects a valid column and drops a piece.
     */
    private void makeAIMove()
    {
        if (!gameOver)
        {
            Random random = new Random();
            int column;
            do {
                column = random.nextInt(mainActivityData.getCol());
            } while (board[0][column] != 0);
            dropPiece(column);
        }
    }

    /**
     * Updates the display of move counts.
     * Updates the UI to show the number of moves made and remaining.
     */
    private void updateMovesDisplay()
    {
        movesTextView.setText("Number of moves: " + movesCount);
        movesAvailable.setText("Moves Available: "+nMoves);
    }

    /**
     * Selects a random color for the computer player.
     * Ensures the computer's color is different from player one's color.
     * @return Resource ID of the selected color
     */
    private int nCompColour()
    {
        List<Integer> availableColors = new ArrayList<>();
        int colour = R.drawable.token_yellow;
        for (int color : ALL_COLORS) {
            if (color != mainActivityData.getPOneColour()) {
                availableColors.add(color);
            }
        }
        Random random = new Random();
        int randomIndex = random.nextInt(availableColors.size());
        colour = availableColors.get(randomIndex);
        return colour;
    }
}
