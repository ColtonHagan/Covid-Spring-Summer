import java.io.*;
import java.util.*;
public class Main {
	public static void main(String[] args) throws IOException {
      printStandings(badSort(overall(combine("spring"), combine("summer")),  combine("summer")));
	}
   
   public static void printStandings(String[] overall) {
      System.out.println("Playoff Standings:");
      for(int i = 0; i < 12; i = i + 2) {
         String output = overall[i];
         for(int j = 0; j < (8 - overall[i].length()); j++) {
            output += " ";
         }
         output += overall[i + 1];
         System.out.println(output);
      }
   }
   
   public static String[] badSort(String[] overall, String[] summer) {
      for(int i = 0; i < 20; i = i + 2) {
         if(i != 0) {
            if(Integer.parseInt(overall[i+1]) > Integer.parseInt(overall[i-1])) {
               swap(overall, i);
               i = 0;
            //tiebreaker LEC
            } else if (Integer.parseInt(overall[i+1]) == Integer.parseInt(overall[i-1])) {
               int summerPoints1 = findPoint(summer, overall, i);
               int summerPoints2 = findPoint(summer, overall, i - 2);
               if(summerPoints1 > summerPoints2) {
                  swap(overall, i);
                  i = 0;
               }
            }
         }
      }
      return overall;
   }
   
   public static void swap(String[] overall, int i) {
      String tempTeam = overall[i];
      String tempPoint = overall[i+1];
      overall[i] = overall[i-2];
      overall[i+1] = overall[i-1];
      overall[i-2] = tempTeam;
      overall[i-1] = tempPoint;
   }
   
   public static int findPoint(String[] summer, String[] overall, int i) {
      for(int j = 0; j < 20; j = j + 2) {
         if(overall[i].equals(summer[j])) {
            return Integer.parseInt(summer[j+1]);
         }
      }
      return -1;
   }
   
   public static String[] overall (String[] spring, String[] summer) {
      for (int i = 0; i < 20; i = i + 2) {
         for (int j = 0; j < 20; j = j + 2) {
            if(spring[i].equals(summer[j])) {
               spring[i+1] = "" + (Integer.parseInt(summer[j+1]) + Integer.parseInt(spring[i+1]));
            }
         }
      }
      return spring;
   }
   
   public static String[] combine(String split) throws IOException {
      String[] output = new String[20];
      String[] standings = fileReader("Files/" + split + "Standings.txt");
      String[] points = fileReader("Files/" + split + "Points.txt");
      for(int i = 0; i < 20; i = i + 2) {
         output[i] = standings[i/2];
         output[i+1] = points[i/2];
      }
      return output;
   }
   
   public static String[] fileReader(String filename) throws IOException {
      String[] output = new String[10];
      int i = 0;
      String line;
      File file = new File(filename);
      BufferedReader fileReader = new BufferedReader(new FileReader(file));
      while ((line = fileReader.readLine()) != null) {
        output[i] = line.trim();
        i++;
      }
      return output;
   }
}
