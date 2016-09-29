import sorts.MergeSort;
import sorts.QuickSort;

import static com.oracle.javafx.jmx.json.JSONDocument.createArray;

public class SortTest {
    private static int[] array = new int[10];

    private static void fillArray() {
        for (int i = 0; i < array.length; i++)
            array[i] = ((int) (Math.random()*100));
    }

    private static void printArray() {
        for (int num : array)
            System.out.println(num);
        System.out.println();
    }

    public static void quickSortTest() {
        fillArray();
        printArray();
        QuickSort.sort(array);
        printArray();
    }

    public static void mergeSortTest() {
        createArray();
        printArray();
        MergeSort.sort(array);
        printArray();
    }

    public static void main(String[] args) {
        quickSortTest();
    }
}
