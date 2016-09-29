import sorts.QuickSort;

public class SortTest {
    private static int[] data;

    private static void createArray() {
        data = new int[10];

        for (int i = 0; i < data.length; i++)
            data[i] = ((int) (Math.random()*100));

        printArray();
    }

    private static void printArray() {
        for (int num : data)
            System.out.println(num);
        System.out.println();
    }

    public static void quickSortTest() {
        createArray();
        QuickSort.sort(data);
        printArray();
    }

    public static void main(String[] args) {
        quickSortTest();
    }
}
