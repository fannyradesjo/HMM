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
          Line = Line.trim();
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

  public static int indicator(int O, int k){
    int okay = 0;
    if(O == k){
      okay = 1;
    }
    return okay;
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
      Float[][] beta = new Float[T][N];
      Float sum_alpha = 0f;
      Float[][][] di_gamma = new Float[T-1][N][N];
      Float[][] gamma = new Float[T-1][N];
      Float sum_di_gamma;
      Float sum;
      Float[][] alpha = new Float[T][N];
      Float[][] A_new = new Float[arow][acol];
      Float[][] B_new = new Float[brow][bcol];
      Float[][] pi_new = new Float[1][N];
      Float di_gamma_t_sum = 0f;
      Float gamma_t_sum = 0f;
      Float sum_up;
      Float gamma_t_sum_2 = 0f;

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


      System.out.println("A: ");
      for(int i = 0; i < A.length; i++){
      System.out.println(Arrays.toString(A[i]));}
      System.out.println("B: ");
      for(int i = 0; i < B.length; i++){
      System.out.println(Arrays.toString(B[i]));}
      System.out.println("pi: ");
      System.out.println(Arrays.toString(pi[0]));
      /*System.out.println("seq: ");
      System.out.println(Arrays.toString(seq));*/

      int count = 0;

      A_new = A;
      B_new = B;
      pi_new = pi;

      while(count < 1 ){
        count++;

        A = A_new;
        B = B_new;
        pi = pi_new;

      //Här börjar alpha-pass algoritmen
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

            System.out.println("alpha: ");
            for(int i = 0; i < alpha.length; i++){
            System.out.println(Arrays.toString(alpha[i]));}


// Här börjar beta
      for (int i = 0; i < N; i++){
        beta[T-1][i] = 1f;
      }

    for(int t = T-2; t >= 0; t--){
      for(int i = 0; i < N; i++){
        beta[t][i] = 0f;
        for(int j = 0; j < N; j++){
          beta[t][i] += beta[t+1][j]*B[j][seq[t+1]]*A[i][j];
        }
      }
    }

    /*System.out.println("beta: ");
    for(int i = 0; i < beta.length; i++){
    System.out.println(Arrays.toString(beta[i]));}*/


// Här börjar gamma

  for(int k = 0; k < N; k++){
    sum_alpha += alpha[T-1][k];
  }

  for(int t = 0; t < T-1; t++){
    for(int i = 0; i <N; i++){
      sum_di_gamma = 0f;
      for(int j = 0; j<N; j++){
        di_gamma[t][i][j] = (alpha[t][i]*A[i][j]*B[j][seq[t+1]]*beta[t+1][j])/sum_alpha;
        sum_di_gamma += di_gamma[t][i][j];
      }
      gamma[t][i] = sum_di_gamma;
    }
  }

  System.out.println("sum_alpha: " + sum_alpha);

  /*System.out.println("gamma: ");
  for(int i = 0; i < gamma.length; i++){
  System.out.println(Arrays.toString(gamma[i]));}*/

  for(int i = 0; i <N; i++){
    for(int j = 0; j <N; j++){
      di_gamma_t_sum = 0f;
      gamma_t_sum = 0f;
      for(int t = 0; t < T-1; t++){
        di_gamma_t_sum += di_gamma[t][i][j];
        gamma_t_sum += gamma[t][i];
      }
      A_new[i][j] = di_gamma_t_sum/gamma_t_sum;
    }
  }

  System.out.println("gamma_t_sum: " + gamma_t_sum);

    for(int j = 0; j < N; j++){
      for(int k = 0; k < N; k++){
        gamma_t_sum_2 = 0f;
        sum_up = 0f;
        for(int t = 0; t < T-1; t++){
          sum_up += indicator(seq[t], k)*gamma[t][j];
          gamma_t_sum_2 += gamma[t][j];
        }
        B_new[j][k] = sum_up/gamma_t_sum_2;
      }
    }

      System.out.println("gamma_t_sum_2: " + gamma_t_sum_2);

    pi_new[0] = gamma[0];

    System.out.println("A_new: ");
    for(int i = 0; i < A_new.length; i++){
    System.out.println(Arrays.toString(A[i]));}
    System.out.println("B_new: ");
    for(int i = 0; i < B.length; i++){
    System.out.println(Arrays.toString(B_new[i]));}
    System.out.println("pi_new: ");
    System.out.println(Arrays.toString(pi_new[0]));

  }


    }

}
