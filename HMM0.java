import java.util.*;
import java.lang.Math;

public class HMM0 {

      public static String[][] takeInput(){
        Scanner scan = new Scanner(System.in);
        String[][] data = new String[3][];
        String Line;

        for(int line = 0; line < 3; line++){
          Line = scan.nextLine();
          String[] LineArray = Line.split(" ");
          data[line] = LineArray;
        }
        return data;
      }



      public static void main(String[] args) {

      String[][] data = takeInput();
      int arow = Integer.parseInt(data[0][0]);
      int acol = Integer.parseInt(data[0][1]);
      int brow = Integer.parseInt(data[0][1]);
      int bcol =Integer.parseInt(data[1][1]);
      Float[][] A = new Float[arow][acol];
      Float[][] B = new Float[brow][bcol];
      Float[] pi = new Float[data[2].length-2];

      int w = 2;
      for(int i = 0; i < arow; i++){
        for(int j = 0; j < acol; j++){
          A[i][j] = Float.parseFloat(data[0][w]);
          w++;
        }
      }

      w = 2;
      for(int i = 0; i < brow; i++){
        for(int j = 0; j < bcol; j++){
          B[i][j] = Float.parseFloat(data[1][w]);
          w++;
        }
      }

      for(int i = 0; i < Integer.parseInt(data[2][1]); i++){
        pi[i] = Float.parseFloat(data[2][i+2]);
      }

      System.out.println("A: ");
      for(int i = 0; i < A.length; i++){
      System.out.println(Arrays.toString(A[i]));}
      System.out.println("B: ");
      for(int i = 0; i < B.length; i++){
      System.out.println(Arrays.toString(B[i]));}
      System.out.println("pi: ");
      System.out.println(Arrays.toString(pi));

    }

}
