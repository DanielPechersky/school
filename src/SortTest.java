import sorts.MergeSort;
import sorts.QuickSort;

public class SortTest {
    private static int[] array = new int[10];

    private static void fillArray() {
        for (int i = 0; i < array.length; i++)
            array[i] = ((int) (Math.random()*100));
    }

    private static void printArray() {
        String arrayString = "";
        for (int num : array)
            arrayString = arrayString.concat(String.valueOf(num) + ",");
        System.out.println(arrayString.substring(0, arrayString.length()-1));
    }

    public static void quickSortTest() {
        fillArray();
        printArray();
        QuickSort.sort(array);
        printArray();
    }

    public static void mergeSortTest() {
        fillArray();
        printArray();
        MergeSort.sort(array);
        printArray();
    }

    public static void main(String[] args) {
        // sort being tested is determined through args[0]
        if (args[0].equals("merge"))
            mergeSortTest();
        else if (args[0].equals("quick"))
            quickSortTest();
    }
}
