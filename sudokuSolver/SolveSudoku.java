//Solves given sudoku if possible, no gui/interface, works
public class SolveSudoku {
   public static void main(String[] args) { 
      int[][] sudoku = { {0, 6, 2, 0, 1, 0, 0, 8, 0},
                         {1, 8, 0, 6, 0, 0, 4, 0, 9},
                         {0, 3, 0, 2, 5, 8, 7, 0, 1},
                         {5, 0, 8, 0, 6, 7, 9, 0, 0},
                         {2, 0, 6, 0, 0, 0, 5, 3, 8}, 
                         {0, 0, 3, 8, 2, 5, 0, 7, 0}, 
                         {6, 2, 0, 0, 0, 9, 0, 5, 3}, 
                         {0, 0, 4, 5, 8, 0, 0, 1, 7}, 
                         {8, 5, 0, 7, 0, 6, 2, 0, 0} };
                         
      if (solve(sudoku)) {
         printMatrix(sudoku);
      } else {
         System.out.println("No possible solution");
      }
   }
   
   // Checks if a number is valid in sudoku
   public static boolean numberWorks (int[][] sudoku, int y, int x, int num) {
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
   
   //solves the thing
   public static boolean solve (int[][] sudoku) {
      //Find first 0/empty value cords
      int y = -1;
      int x = -1;
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            if (x == -1 && sudoku[i][j] == 0) {
               y = i;
               x = j;
            }
         }
      }
      //If there are no spots left, everything is then filled in :)
      if (x == -1) {
         return true;
      }
      
      //Uses backtracking algorathem to solve
      for (int i = 1; i <= 9; i++) {
         if (numberWorks(sudoku, y, x, i)) {
            sudoku[y][x] = i;
            if (solve(sudoku)) {
               return true;
            } else {
               sudoku[y][x] = 0;
            }
         }
      }
      return false;
   }    

   //Basic print matrix function
   public static void printMatrix (int[][] matrix) {
      for (int i = 0; i < matrix.length; i++) {
         for (int j = 0; j < matrix.length; j++) {
            System.out.print(matrix[i][j] + " ");
         }
         System.out.println();
      }
      System.out.println();
   }
}