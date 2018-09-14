import java.util.*;
import java.lang.Math;
import java.lang.StringBuilder;


public class HMM2 {

      public static String[][] takeInput(){
        Scanner scan = new Scanner(System.in);
        String[][] data = new String[4][];
        String Line;

        for(int line = 0; line < 4; line++){
          Line = scan.nextLine();
          String[] LineArray = Line.split(" ");
          data[line] = LineArray;
        }
        return data;
      }

      public static Float[][] matrixMult(Float[][] a, Float[][] b){
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        Float[][] c = new Float[m1][n2];
        for (int i = 0; i < m1; i++){
          for (int j = 0; j < n2; j++){
            c[i][j] = 0f;
            for (int k = 0; k < n1; k++){
            c[i][j] += a[i][k] * b[k][j];}}}
            return c;
      }

      public static String fixOutput(int[] ans){
        StringBuilder builder = new StringBuilder();
        for(int e: ans) {
          builder.append(Integer.toString(e));
          builder.append(" ");
        }
        builder.setLength(builder.length() - 1);
        String str = builder.toString();
        return str;

  }


      public static void main(String[] args) {

      String[][] data = takeInput();
      int arow = Integer.parseInt(data[0][0]);
      int acol = Integer.parseInt(data[0][1]);
      int brow = Integer.parseInt(data[0][1]);
      int bcol =Integer.parseInt(data[1][1]);
      Float[][] A = new Float[arow][acol];
      Float[][] B = new Float[brow][bcol];
      Float[][] pi = new Float[1][data[2].length-2];
      int[] seq = new int[data[3].length-1];
      int N = pi[0].length;
      int T = seq.length;
      Float[][] delta = new Float[T][N];
      int[] ProbSeq = new int[T];
      Float possibledelta;
      Float largest;
      int[][] delta_idx =  new int[T][N];

      //Create A from input data
      int w = 2;
      for(int i = 0; i < arow; i++){
        for(int j = 0; j < acol; j++){
          A[i][j] = Float.parseFloat(data[0][w]);
          w++;
        }
      }

      //Create B from input data
      w = 2;
      for(int i = 0; i < brow; i++){
        for(int j = 0; j < bcol; j++){
          B[i][j] = Float.parseFloat(data[1][w]);
          w++;
        }
      }

      //Create Pi from input data
      for(int i = 0; i < Integer.parseInt(data[2][1]); i++){
        pi[0][i] = Float.parseFloat(data[2][i+2]);
      }

      //Create Emission/Observation sequence from input data
      for(int i = 0; i < Integer.parseInt(data[3][0]); i++){
        seq[i] = Integer.parseInt(data[3][i+1]);
      }


    /*  System.out.println("A: ");
      for(int i = 0; i < A.length; i++){
      System.out.println(Arrays.toString(A[i]));}
      System.out.println("B: ");
      for(int i = 0; i < B.length; i++){
      System.out.println(Arrays.toString(B[i]));}
      System.out.println("pi: ");
      System.out.println(Arrays.toString(pi[0]));
      System.out.println("seq: ");
      System.out.println(Arrays.toString(seq));*/

// create delta and delta_idx
      for(int i = 0; i < N; i++){
        delta[0][i] = pi[0][i]*B[i][seq[0]];
    }

      for(int t = 1; t < T; t++){
        for(int i = 0; i < N; i++){
          largest = 0f;
          for(int j = 0; j < N; j++){
            possibledelta = A[j][i]*delta[t-1][j]*B[i][seq[t]];
            if(possibledelta >= largest){
              largest = possibledelta;
              delta_idx[t][i] = j;
            }
          }
          delta[t][i] = largest;
        }
      }

// initiate ProbSeq
        largest = 0f;
    for(int j = 0; j < N; j++){
      if(largest <= delta[T-1][j]){
        largest = delta[T-1][j];
        ProbSeq[T-1] = j;
     }
    }

//bactracking
    for(int t = T-2; t >= 0; t--){
      ProbSeq[t] = delta_idx[t+1][ProbSeq[t+1]];
    }

      String output = fixOutput(ProbSeq);
      System.out.println(output);
    }

}
