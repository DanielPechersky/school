package sorts;

public class QuickSort {
    public static void sort(int[] array) {
        sort(array, 0, array.length);
    }

    // fromIndex: inclusive, toIndex: exclusive
    public static void sort(int[] array, int fromIndex, int toIndex) {
        int left = fromIndex;
        int right = toIndex-1;
        // pivot will always either be equal to left or right, which can be expressed with boolean
        // having pivot as a boolean also means pivot does not have to be set when positions are switched
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

        // get whichever pointer represents the pivot
        int pivotPosition = pivotIsLeft ? left : right;

        // sort the ranges to the left and right of the pivot if they are greater than 1
        if (pivotPosition-fromIndex > 1)
            sort(array, fromIndex, pivotPosition);
        if (toIndex-(pivotPosition+1) > 1)
            sort(array, pivotPosition+1, toIndex);
    }
}
