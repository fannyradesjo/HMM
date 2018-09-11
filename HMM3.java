import java.util.*;
import java.lang.Math;
import java.lang.StringBuilder;


public class HMM3 {

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
      Float[][] alpha = new Float[seq.length][pi[0].length];
      Float[][] beta = new Float[T][N];
      Float[][] gamma = new Float[T][N];
      Float sum = 0f;
      int[] ProbSeq = new int[T];

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
        pi[0][i] = Float.parseFloat(data[2][i+2]);
      }

      for(int i = 0; i < Integer.parseInt(data[3][0]); i++){
        seq[i] = Integer.parseInt(data[3][i+1]);
      }


      /*System.out.println("A: ");
      for(int i = 0; i < A.length; i++){
      System.out.println(Arrays.toString(A[i]));}
      System.out.println("B: ");
      for(int i = 0; i < B.length; i++){
      System.out.println(Arrays.toString(B[i]));}
      System.out.println("pi: ");
      System.out.println(Arrays.toString(pi[0]));
      System.out.println("seq: ");
      System.out.println(Arrays.toString(seq));*/

      for(int i = 0; i < N; i++){
        alpha[0][i] = pi[0][i]*B[i][seq[0]];
    }

    for(int t = 1; t < T; t++){
      for(int i = 0; i < N; i++){
        sum = 0f;
        for(int j = 0; j < N; j++){
          sum += alpha[t-1][j]*A[j][i];
        }
        alpha[t][i] = sum*B[i][seq[t]];
      }
    }

      for(int i = 0; i < N; i++){
        beta[T-1][i] = 1f;
      }

      for(int t = T-2; t >= 0; t--){
        for(int i = 0; i < N; i++){
          sum = 0f;
          for(int j = 0; j < N; j++){
            sum += A[i][j]*B[j][seq[t+1]]*beta[t+1][j];
          }
          beta[t][i] = sum;
        }
      }

      float sumAlpha = 0f;
      for(int i = 0; i < N; i++){
        sumAlpha += alpha[T-1][i];
      }

      for(int t = 0; t < T; t++){
        for(int i = 0; i < N; i++){
          gamma[t][i] = (alpha[t][i]*beta[t][i])/sumAlpha;
        }
      }

      for(int t = 0; t < T; t++){
        ProbSeq[t] = 0;
        Float largest = 0f;
        for(int i = 0; i < N; i++){
          if(gamma[t][i] > largest){
            largest = gamma[t][i];
            ProbSeq[t] = i;
          }
        }
      }

    /*  System.out.println("alpha: ");
      for(int i = 0; i < alpha.length; i++){
      System.out.println(Arrays.toString(alpha[i]));}
      System.out.println("beta: ");
      for(int i = 0; i < beta.length; i++){
      System.out.println(Arrays.toString(beta[i]));}
      System.out.println("gamma: ");
      for(int i = 0; i < gamma.length; i++){
      System.out.println(Arrays.toString(gamma[i]));}*/


      String output = fixOutput(ProbSeq);
      System.out.println(output);
    }

}
