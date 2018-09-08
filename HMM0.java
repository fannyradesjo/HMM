import java.util.*;
import java.io.IOException;

public class HMM0 {

      public static String[] takeInput(){
        Scanner scan = new Scanner(System.in);
        List<String> data = new ArrayList<String>();
        String Line;

        while(scan.hasNext()){
          Line = scan.nextLine();
          String[] LineArray = Line.split(" ");

          for(String e: Line.split(" ")){
              data.add(e);
          }

        }

      String[] dataArray = new String[data.size()];
        for(int i = 0; i < data.size(); i++){
          dataArray[i] = data.get(i);
      }
      return dataArray;
      }

      public static void main(String[] args) {

      String[] data = takeInput();

      System.out.println(Arrays.toString(data));
    }

}
