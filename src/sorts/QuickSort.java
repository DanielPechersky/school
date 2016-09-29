package sorts;

public class QuickSort {
    public static void sort(int[] array) {
        sort(array, 0, array.length);
    }

    public static void sort(int[] array, int fromIndex, int toIndex) {
        int left = fromIndex;
        int right = toIndex-1;
        boolean pivotIsLeft = true;

        while (left != right) {
            if (array[left] > array[right]) {
                int temp = array[left];
                array[left] = array[right];
                array[right] = temp;

                pivotIsLeft ^= true;
            }

            if (!pivotIsLeft)
                left++;
            else
                right--;
        }

        int pivotPosition = pivotIsLeft ? left : right;

        if (pivotPosition-fromIndex > 1)
            sort(array, fromIndex, pivotPosition);
        if (toIndex-(pivotPosition+1) > 1)
            sort(array, pivotPosition+1, toIndex);
    }
}
