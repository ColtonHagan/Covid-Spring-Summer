import java.awt.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;
import javax.swing.*;
import java.lang.Math;

public class TicTacToe extends JPanel {
   private static final int BOARDSIZE = 9;
   private static final int WINDOWSize = 300;
   private static final Color BOARDCOLOR = Color.WHITE;
   private static final Color CLICKEDCOLOR = Color.WHITE;
   
   private JButton[] board; 
   private int player;
   private int gridSize;
   private boolean[] playable = {true, true, true, true, true, true, true, true, true};
   private int version;
   private int difficulty;
   private int AiPlayer;
   private int turn;
   private String AiSide;
   private String PlayerSide;
   private boolean centerPicked;
   private int ogDiff;
   private int[][][] poss = {{ {6, 3}, {2, 1}, {4, 8} }, { {7, 4}, {0, 2} }, { {1, 0}, {5, 8}, {4, 6} }, { {4, 5}, {0, 6} }, { {3, 5}, {0, 8}, {2, 6}, {1, 7} }, { {2, 8}, {3, 4} }, { {0, 3}, {7, 8}, {4, 2} }, { {1, 4}, {6, 8} }, { {2, 5}, {6, 7}, {0, 4}}};
   

   public static void main(String[] args) {
      int mode = -1;
      int diff = -1;
      int start = -1;
      
      JFrame frame = new JFrame("StartQuestions");  
      Object[] modes = {"Human", "AI"};
      Object[] difficulty = {"Easy", "Unbeatable"};
      Object[] side = {"X (first)", "O (second)"};
      mode = JOptionPane.showOptionDialog(frame, "Who would you like to play agianst", "Mode", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, modes, modes[0]);
      if (mode == 1) {
         diff = JOptionPane.showOptionDialog(frame, "What do you want difficulty to be?", "difficulty", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, difficulty, difficulty[0]);
         start = JOptionPane.showOptionDialog(frame, "What do you want to be?", "Pick side", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, side, side[0]);
      } else if (mode == 0) {
         diff = 3;
         start = 3;
      }
      if (mode != -1 & diff != -1 & start != -1) {
         JFrame window = new JFrame("Tic-Tac-Toe");  
         window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         window.getContentPane().add(new TicTacToe(mode,diff,start));
         window.setBounds(WINDOWSize,WINDOWSize,WINDOWSize,WINDOWSize);
         window.setVisible(true);
      } else {
         System.exit(0);
      }
   }
   
   public TicTacToe(int mode, int diff, int start) {
      version = mode;
      difficulty = diff;
      ogDiff = diff;
      if(start == 0) {
         AiPlayer = 2;
         AiSide = "O";
         PlayerSide = "X";
      } else if (start == 1) {
         AiPlayer = 1;
         AiSide = "X";
         PlayerSide = "O";
      }
      player = 1;
      turn = 1;

      board = new JButton[BOARDSIZE]; 
      gridSize = (int) Math.sqrt(BOARDSIZE);
      setLayout(new GridLayout(gridSize, gridSize));
      CreateBoard(); 
      AiTurnCheck();
   }
   
   private void CreateBoard() {
      for (int i = 0; i < BOARDSIZE; i++) {
         board[i] = new JButton();
         board[i].setBackground(BOARDCOLOR); 
         board[i].setFont(new Font("Ink Free", Font.PLAIN, 80));
         board[i].setText("");
         board[i].addActionListener(new spotClicked());
         //adds to Jpanel
         add(board[i]); 
      }
   }
   
   private boolean FullBoard() {
      for (int i = 0; i < board.length; i++) {
         if (board[i].getText().equals("")) {
            return false;
         }
      }
      return true;
   }
   
   private void SwitchPlayer() {
      if (player == 1) {
         player = 2;
      } else if (player == 2) {
         player = 1;
      } else {
         System.out.println("ERROR");
      }
      turn++;
      AiTurnCheck();
   } 
   
   private int[] checkWin() {
      int[] winningSet = {-1, -1, -1};
      if (checkSame(0, 1, 2)) {
         winningSet = new int[] {0, 1, 2};
      } else if (checkSame(3, 4, 5)) {
         winningSet = new int[] {3, 4, 5};
      } else if (checkSame(6, 7, 8)) {
         winningSet = new int[] {6, 7, 8};
      } else if (checkSame(0, 3, 6)) {
         winningSet = new int[] {0, 3, 6};
      } else if (checkSame(1, 4, 7)) {
         winningSet = new int[] {1, 4, 7};
      } else if (checkSame(2, 5, 8)) {
         winningSet = new int[] {2, 5, 8};
      } else if (checkSame(0, 4, 8)) {
         winningSet = new int[] {0, 4, 8};
      } else if (checkSame(2, 4, 6)) {
         winningSet = new int[]{2, 4, 6};
      }
      return winningSet;
   }
   private boolean checkSame(int a, int b, int c) {
      return (board[a].getText() == "X" && board[b].getText() == "X" && board[c].getText() == "X") ||
             (board[a].getText() == "O" && board[b].getText() == "O" && board[c].getText() == "O");
   }
   private void reset() {
      System.out.println("-----||Next Game||-----");
      for(int i = 0; i <= 8; i++) {
            board[i].setText("");
            board[i].setForeground(Color.BLACK);
      }
      difficulty = ogDiff;
      turn = 1;
      player = 1;
      centerPicked = false;
      for(int i = 0; i < 9; i++) {
         playable[i] = true;
      }
      System.out.println("player's turn: " + player);
      System.out.println("ai's turn " + AiPlayer);
      System.out.println("turn# " + turn);
      AiTurnCheck();  
   }
   private int openWin(int space, String side) {
      if (board[space].getText().equals(side)) {
         int l = nestedLength(poss, space);
         for (int i = 0; i < l; i++) {
            if (board[poss[space][i][0]].getText().equals("") && board[poss[space][i][1]].getText().equals(side)) {
               return poss[space][i][0];
            } else if (board[poss[space][i][0]].getText().equals(side) && board[poss[space][i][1]].getText().equals("")) {
               return poss[space][i][1];
            }
         }
      }
      return -1;
   }
   private int nestedLength(int[][][] nest, int index) {
      int l = 0;
      for (int i = 0; i < 4; i++) {
         try {
            int w = nest[index][i][0];
            l++;
         } catch (Exception e) {
            break;
         }
      }
      return l;
   }
   private void AiTurnCheck() {
      if(player == AiPlayer) {
         basicAI();
         checkWinAI();
      }
   }
   private void checkWinAI() {
      int[] checkWin = checkWin();
      if (checkWin[0] != -1) {
         for(int i = 0; i < checkWin.length; i++) {
            board[checkWin[i]].setForeground(Color.GREEN);
         }
      }
      if (checkWin[0] != -1 || FullBoard()) {
         int playAgain = JOptionPane.showConfirmDialog(null, "Play Again?");
            if(playAgain == 0) {
               reset();
            } else if (playAgain == 1) {
               System.exit(0);
            }
         } else {
            SwitchPlayer();
         }  
   }
   private void unbeatableAI() {
      if (board[4].getText().equals("")) { //if center is open pick it
         board[4].setText(AiSide);
         playable[4] = false;
         centerPicked = true;
      }
      if (centerPicked && (turn != 1 && turn != 2)) { //if you get center play for win
         if ((board[1].getText().equals(PlayerSide)  || board[3].getText().equals(PlayerSide)) && turn == 3) {
            board[6].setText(AiSide);
            playable[6] = false;
         } else if ((board[5].getText().equals(PlayerSide) && board[8].getText().equals("")) && (turn == 4 || turn == 6)) {
            board[8].setText(AiSide);
            playable[8] = false;
         } else if ((board[3].getText().equals(PlayerSide) && board[0].getText().equals("")) && (turn == 4 || turn == 6)) {
            board[0].setText(AiSide);
            playable[0] = false;
         } else if ((board[5].getText().equals(PlayerSide) ||  board[7].getText().equals(PlayerSide)) && turn == 3) {
            board[8].setText(AiSide);
            playable[8] = false;
         } else if (board[1].getText().equals(PlayerSide) && board[6].getText().equals(AiSide) && turn == 5) {
            board[0].setText(AiSide);
            playable[0] = false;
            difficulty = 0; 
         } else if (board[3].getText().equals(PlayerSide) && board[6].getText().equals(AiSide) && turn == 5) {
            board[8].setText(AiSide);
            playable[8] = false;
            difficulty = 0; 
         } else if (board[5].getText().equals(PlayerSide) && board[8].getText().equals(AiSide) && turn == 5) {
            board[6].setText(AiSide);
            playable[6] = false;
            difficulty = 0; 
         } else if (board[7].getText().equals(PlayerSide) && board[8].getText().equals(AiSide) && turn == 5) {
            board[2].setText(AiSide);
            playable[2] = false;
            difficulty = 0; 
         } else if (board[0].getText().equals(PlayerSide) && turn == 3) {
            board[8].setText(AiSide);
            playable[8] = false;
         } else if (board[2].getText().equals(PlayerSide) && turn == 3) {
            board[6].setText(AiSide);
            playable[6] = false;
         } else if (board[6].getText().equals(PlayerSide) && turn == 3) {
            board[2].setText(AiSide);
            playable[2] = false;
         } else if (board[8].getText().equals(PlayerSide) && turn == 3) {
            board[0].setText(AiSide);
            playable[0] = false;
         } else if (((board[1].getText().equals(PlayerSide) && board[2].getText().equals(AiSide)) || (board[3].getText().equals(PlayerSide) && board[6].getText().equals(AiSide)) ) && turn == 5) {
            board[8].setText(AiSide);
            playable[8] = false;
            difficulty = 0;
         } else if (((board[5].getText().equals(PlayerSide) && board[2].getText().equals(AiSide)) || (board[7].getText().equals(PlayerSide) && board[0].getText().equals(AiSide)) ) && turn == 5) {
            board[0].setText(AiSide);
            playable[0] = false;
            difficulty = 0;
         } else if (((board[3].getText().equals(PlayerSide) && board[0].getText().equals(AiSide)) || (board[7].getText().equals(PlayerSide) && board[8].getText().equals(AiSide)) ) && turn == 5) {
            board[2].setText(AiSide);
            playable[4] = false;
            difficulty = 0;
         } else if (((board[2].getText().equals(PlayerSide) && board[0].getText().equals(AiSide)) || (board[5].getText().equals(PlayerSide) && board[8].getText().equals(AiSide)) ) && turn == 5) {
            board[6].setText(AiSide);
            playable[6] = false;
            difficulty = 0;
         } else {
            System.out.println("Will be tie");
            difficulty = 0;
         }
      } else if (!centerPicked) { //if center is not picked
         if (turn == 2) {
            playable[0] = false;
            board[0].setText(AiSide);
         } else if (turn > 2 && board[6].getText().equals("") ) {
            playable[6] = false;
            board[6].setText(AiSide);
         } else if (turn > 2 && board[6].getText().equals(PlayerSide) ) {
            playable[2] = false;
            board[2].setText(AiSide);
         } else {
            System.out.println("Will be tie");
            difficulty = 0;
         }
      }
   }
   private void basicAI() {
      //find move
      boolean moved = false;
      for (int i = 0; i < 8; i++) {
         int aboutToWin = openWin(i, AiSide);
         if (aboutToWin != -1 && !moved) {
            System.out.println("about-to-win " + aboutToWin);
            board[aboutToWin].setText(AiSide);
            playable[aboutToWin] = false;
            moved = true;
            break;
         }
      } 
      for (int i = 0; i < 8; i++) {
         int aboutToLose = openWin(i, PlayerSide);
         if (aboutToLose != -1 && !moved) {
            System.out.println("about-to-lose " + aboutToLose);
            board[aboutToLose].setText(AiSide);
            playable[aboutToLose] = false;
            moved = true;
            break;
         }
      }
      
      if (!moved && difficulty != 0) {
         unbeatableAI();
      }
      if (difficulty == 0 && !moved) {
         while(moved == false) {
            int rand = (int) (Math.random() * 8 + 1);
            if(board[rand].getText().equals("")) {
               board[rand].setText(AiSide);
               moved = true;
            }
         }
      }
   }
   private boolean validOpen(int n) {
      return (n >= 0 && n <= 8); 
   }
   private class spotClicked implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         JButton spotPicked = (JButton)e.getSource();
         int spotIndex = -1;
         for (int i = 0; i < 9; i++) {
            if(spotPicked == board[i]) {
               spotIndex = i;
            }
         }
         if (playable[spotIndex]) {
            if (spotPicked.getText() == "") {
               if(player == 1) {
                  spotPicked.setText("X");
               } else {
                  spotPicked.setText("O");
               }
               playable[spotIndex] = false;
            }  
            int[] checkWin = checkWin();
            if (checkWin[0] != -1) {
               for(int i = 0; i < checkWin.length; i++) {
                  board[checkWin[i]].setForeground(Color.GREEN);
               }
            }
            if (checkWin[0] != -1 || FullBoard()) {
               int playAgain = JOptionPane.showConfirmDialog(null, "Play Again?");
               if(playAgain == 0) {
                  reset();
               } else if (playAgain == 1) {
                  System.exit(0);
               }
            } else {
               SwitchPlayer();
            } 
         } 
      }
   }
}