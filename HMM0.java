import java.util.*;
import java.lang.Math;
import java.lang.StringBuilder;

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


      public static String fixOutput(Float[][] ans){
        StringBuilder builder = new StringBuilder();
        builder.append("1");
        builder.append(" ");
        builder.append(Integer.toString(ans[0].length));
        for(Float e: ans[0]) {
          builder.append(" ");
          builder.append(Float.toString(e));
        }
        //builder.setLength(builder.length() - 1);
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

      /*
      System.out.println("A: ");
      for(int i = 0; i < A.length; i++){
      System.out.println(Arrays.toString(A[i]));}
      System.out.println("B: ");
      for(int i = 0; i < B.length; i++){
      System.out.println(Arrays.toString(B[i]));}
      System.out.println("pi: ");
      System.out.println(Arrays.toString(pi[0]));*/

      Float[][] C = matrixMult(pi, A);
      Float[][] ans = matrixMult(C, B);

      /*
      System.out.println("C: ");
      for(int i = 0; i < C.length; i++){
      System.out.println(Arrays.toString(C[i]));}
      System.out.println("ans: ");
      for(int i = 0; i < ans.length; i++){
      System.out.println(Arrays.toString(ans[i]));}*/

      String output = fixOutput(ans);
      System.out.println(output);



    }

}
