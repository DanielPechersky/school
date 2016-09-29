import Sorts.QuickSort;

public class SortTest {
    public static void main(String[] args) {
        int[] data = new int[10];

        for (int i = 0; i < data.length; i++)
            data[i] = ((int) (Math.random()*50));

        for (int num : data)
            System.out.println(num);
        System.out.println();
        QuickSort.sort(data);
        for (int num : data)
            System.out.println(num);

    }
}
