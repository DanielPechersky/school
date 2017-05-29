import java.util.Arrays;

/**
 * Created by dannyp on 2017-05-08.
 */
public class Transpose {
    public static void main(String[] args) {
        int[][] array = new int[10][10];
        for (int[] col : array)
            for (int i=0; i<col.length; i++)
                col[i] = (int)(Math.random()*255);
        System.out.println(Arrays.deepToString(array).replace("], ", "],\n"));
        System.out.println();
        for (int i=0; i<array.length-1; i++)
            for (int o=1+i; o<array[0].length; o++) {
                int temp = array[i][o];
                array[i][o] = array[o][i];
                array[o][i] = temp;
            }
        System.out.println(Arrays.deepToString(array).replace("], ", "],\n"));
    }
}
