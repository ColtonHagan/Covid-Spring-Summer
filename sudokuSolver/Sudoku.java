//From https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaGame_Sudoku.html, with slight modifications
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Sudoku extends JFrame {
   public static final int GRID_SIZE = 9;
   public static final int SUBGRID_SIZE = 3;
   public static final int CELL_SIZE = 60;
   public static final int CANVAS_WIDTH  = CELL_SIZE * GRID_SIZE;
   public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE;
   
   public static final Color OPEN_CELL_BGCOLOR = Color.YELLOW;
   public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0);
   public static final Color OPEN_CELL_TEXT_NO = Color.RED;
   public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240);
   public static final Color CLOSED_CELL_TEXT = Color.BLACK;
   public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
   
   private JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];
                            
   private int[][] originalOLD = { {5, 3, 4, 6, 7, 0, 9, 1, 2},
                            {6, 7, 2, 1, 9, 5, 3, 4, 0},
                            {1, 9, 8, 3, 4, 2, 5, 6, 7},
                            {8, 5, 9, 7, 6, 1, 4, 2, 3},
                            {4, 2, 6, 8, 5, 3, 7, 9, 1},
                            {7, 1, 3, 9, 2, 4, 8, 5, 6},
                            {9, 6, 1, 5, 3, 7, 2, 8, 4},
                            {2, 8, 7, 4, 1, 9, 6, 3, 5},
                            {3, 4, 5, 2, 8, 6, 1, 7, 9}};
   private int[][] originalMid = { {0, 6, 2, 0, 1, 0, 0, 8, 0},
                         {1, 8, 0, 6, 0, 0, 4, 0, 9},
                         {0, 3, 0, 2, 5, 8, 7, 0, 1},
                         {5, 0, 8, 0, 6, 7, 9, 0, 0},
                         {2, 0, 6, 0, 0, 0, 5, 3, 8}, 
                         {0, 0, 3, 8, 2, 5, 0, 7, 0}, 
                         {6, 2, 0, 0, 0, 9, 0, 5, 3}, 
                         {0, 0, 4, 5, 8, 0, 0, 1, 7}, 
                         {8, 5, 0, 7, 0, 6, 2, 0, 0} };                            
   private int[][] original = { {8, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 3, 6, 0, 0, 0, 0, 0},
                           {0, 7, 0, 0, 9, 0, 2, 0, 0},
                           {0, 5, 0, 0, 0, 7, 0, 0, 0},
                           {0, 0, 0, 0, 4, 5, 7, 0, 0},
                           {0, 0, 0, 1, 0, 0, 0, 3, 0},
                           {0, 0, 1, 0, 0, 0, 0, 6, 8},
                           {0, 0, 8, 5, 0, 0, 0, 1, 0},
                           {0, 9, 0, 0, 0, 0, 4, 0, 0} }; 
   private int[][] testingRandom = { {0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 1, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 2, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0} }; 
                             
   private int[][] current;
                            
   public static void main(String[] args)   { 
      Sudoku test = new Sudoku();
   }
   
   public Sudoku()  {
      current = testingRandom;
      Container cp = getContentPane();
      cp.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
      InputListener listener = new InputListener();
      
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            tfCells[row][col] = new JTextField();
            cp.add(tfCells[row][col]);
            if (current[row][col] == 0) {
               tfCells[row][col].setText(""); 
               tfCells[row][col].setEditable(true);
               tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
               tfCells[row][col].addActionListener(listener);
            } else {
               tfCells[row][col].setText(current[row][col] + "");
               tfCells[row][col].setEditable(false);
               tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
               tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
            }
            tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
            tfCells[row][col].setFont(FONT_NUMBERS);
         }
      }
      
      cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
      pack();
 
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("Sudoku");
      setVisible(true);
  }
  
  private class InputListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e)  {
         int rowSelected = -1;
         int colSelected = -1;
 
         JTextField source = (JTextField)e.getSource();
         //edit into find first 0
         boolean found = false;
         for (int row = 0; row < GRID_SIZE && !found; ++row) {
            for (int col = 0; col < GRID_SIZE && !found; ++col) {
               if (tfCells[row][col] == source) {
                  rowSelected = row;
                  colSelected = col;
                  found = true;
               }
            }
         }
               
         int inputedText = Integer.parseInt(tfCells[rowSelected][colSelected].getText());
         if (inputedText == -1) {
            current[rowSelected][colSelected] = 0;
            solve(current);
         } else {
         if (inputedText >= 1 && inputedText <= 9 && numberWorks(current, rowSelected, colSelected, inputedText)) {
           tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_YES);
           current[rowSelected][colSelected] = inputedText;
           if (findFirstZero()[0] == -1) {
               JOptionPane.showMessageDialog(null, "Congratulation!");
           }
         } else {
           current[rowSelected][colSelected] = 0;
           tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
         }
      }}
  }
   public boolean numberWorks (int[][] sudoku, int y, int x, int num)   {
      //Checks current row/column
      for (int i = 0; i < 9; i++) {
         if (sudoku[y][i] == num || sudoku[i][x] == num) {
            return false;
         }
      }
      //checks box
      int boxXstart = x - x % 3;
      int boxYstart = y - y % 3;
      for (int i = boxXstart; i < boxXstart + 3; i++) {
         for (int j = boxYstart; j < boxYstart + 3; j++) {
            if (sudoku[j][i] == num) {
               return false;
            }
         }
      }
      return true;
   }
   public int[] findFirstZero()   {
      int[] location = {-1, -1};
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            if (location[0] == -1 && current[i][j] == 0) {
               location[0] = i;
               location[1] = j;
            }
         }
      }
      return location;
   }
   //for testing only
   public static void printMatrix (int[][] matrix)  {
      for (int i = 0; i < matrix.length; i++) {
         for (int j = 0; j < matrix.length; j++) {
            System.out.print(matrix[i][j] + " ");
         }
         System.out.println();
      }
   }
   //Solves the thing
   public boolean solve (int[][] sudoku)  {  
      
      int[] location = findFirstZero();
      int y = location[0];
      int x = location[1];
      //-1 if there was no first zero found
      if (x == -1) {
         return true;
      }
      //Uses backtracking algorathem to solve
      for (int i = 1; i <= 9; i++) {
         if (numberWorks(sudoku, y, x, i)) {
            sudoku[y][x] = i;
            tfCells[y][x].setText(i + "");
            tfCells[y][x].setBackground(Color.GREEN);
               
            if (solve(sudoku)) {
               return true;
            } else {
               sudoku[y][x] = 0;
               tfCells[y][x].setBackground(Color.RED);
               tfCells[y][x].setText(i + "");
            }
         }
      }
      return false;
   }
   public static void wait(int ms){
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}