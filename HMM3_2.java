import java.util.*;
import java.lang.StringBuilder;
import java.lang.Math;

public class HMM3_2{

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

  public static int indicator(int O, int k){
    int okay = 0;
    if(O == k){
      okay = 1;
    }
    return okay;
  }

  public static void printMatrix(String name, Float[][] matrix){
    System.out.println(name + ": ");
    for(int i = 0; i < matrix.length; i++){
    System.out.println(Arrays.toString(matrix[i]));}
  }

  public static Float[][] getAlpha(Float[][] A, Float[][] B, Float[][] pi,int[] O, int T, int N){
    Float[][] alpha = new Float[T][N];
    Float sum;

    for(int i = 0; i < N; i++){
      alpha[0][i] = pi[0][i]*B[i][O[0]];
    }

    for(int t = 1; t < T; t++){
      for(int i = 0; i < N; i++){
        sum = 0f;
        for(int j = 0; j < N; j++){
          sum += alpha[t-1][j]*A[j][i];
        }
        alpha[t][i] = sum + B[i][O[t]];
      }
    }

    return alpha;

  }

  public static Float[][] getBeta(Float[][] A, Float[][] B, int[] O, int T, int N){
    Float[][] beta = new Float[T][N];

    for(int i = 0; i < N; i++){
      beta[T-1][i] = 1f;
    }

    for(int t = T-2; t >= 0; t--){
      for(int i = 0; i < N; i++){
        beta[t][i] = 0f;
        for(int j = 0; j < N; j++){
          beta[t][i] += A[i][j]*B[j][O[t+1]]*beta[t+1][j];
        }
      }
    }
    return beta;
  }

  public static Float getP(Float[][] alpha, int T, int N){
    Float P = 0f;

    for(int i = 0; i < N; i++){
      P += alpha[T-1][i];
    }
    return P;
  }

  public static Float[][][] getDiGamma(Float[][] A, Float[][] B, int[] O, Float[][] alpha, Float[][] beta, int T, int N){
    Float[][][] di_gamma = new Float[T-1][N][N];
    Float P = getP(alpha, T, N);

    for(int t = 0; t < T-1; t++){
      for(int i = 0; i < N; i++){
        for(int j = 0; j < N; j++){
          di_gamma[t][i][j] = alpha[t][i]*A[i][j]*B[j][O[t+1]]*beta[t+1][j]/P;
        }
      }
    }

    return di_gamma;
  }

  public static Float[][] getGamma(Float[][][] di_gamma, int T, int N){
    Float[][] gamma = new Float[T-1][N];

    for(int t = 0; t < T-1; t++){
      for(int i = 0; i < N; i++){
        gamma[t][i] = 0f;
        for(int j = 0; j < N; j++){
          gamma[t][i] += di_gamma[t][i][j];
        }
      }
    }
    return gamma;
  }

  public static Float[][] setPi(Float[][] gamma, int N){
    Float[][] pi = new Float[1][N];

    for(int i = 0; i < N; i++){
      pi[0][i] = gamma[0][i];
    }
    return pi;
  }

  public static Float[][] setA(Float[][][] di_gamma, Float[][] gamma, int T, int N){
    Float sum_up;
    Float sum_down;
    Float[][] A = new Float[N][N];

    for(int i = 0; i < N; i++){
      for(int j = 0; j < N; j++){
        sum_up = 0f;
        sum_down = 0f;
        for(int t = 0; t < T-1; t++){
          sum_up += di_gamma[t][i][j];
          sum_down += gamma[t][i];
        }
        A[i][j] = sum_up/sum_down;
      }
    }
    return A;
  }

  public static Float[][] setB(Float[][] gamma, int[] O, int T, int N, int M){
    Float[][] B = new Float[N][M];
    Float[] sum_up = new Float[N];
    Float[] sum_down = new Float[N];

    for(int k = 0; k < M; k++){
      for(int j = 0; j < N; j++){
        sum_up[j] = 0f;
        sum_down[j] = 0f;
        for(int t = 0; t < T-1; t++){
          sum_up[j] += indicator(O[t], k)*gamma[t][j];
          sum_down[j] += gamma[t][j];
        }
        B[j][k] = sum_up[j]/sum_down[j];
      }
    }
    return B;
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
        Float[][] A_new;
        Float[][] B_new;
        Float[][] pi_new;
        int[] O = new int[data[3].length-1];
        int T = O.length;
        int N = brow;
        int M = bcol;
        Float P_old;
        Float P_new;

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
          O[i] = Integer.parseInt(data[3][i+1]);
        }

      P_new = 10f;
      P_old = 0f;

while( Math.abs(P_new - P_old) > 0.0001){

        Float[][] alpha = getAlpha(A,B,pi,O,T,N);

      // printMatrix("alpha", alpha);

        Float[][] beta = getBeta(A,B,O,T,N);

      // printMatrix("beta", beta);

        Float[][][] di_gamma = getDiGamma(A, B, O, alpha, beta, T, N);

        Float[][] gamma = getGamma(di_gamma, T, N);

      //  printMatrix("gamma", gamma);

        pi = setPi(gamma, N);

        A = setA(di_gamma, gamma, T, N);

        B = setB(gamma, O, T, N, M);

        P_old = P_new;
        P_new = getP(alpha, T, N);

        System.out.println("P: " + P_new);
      }

      printMatrix("A", A);
      printMatrix("B", B);

      }
}
