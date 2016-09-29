package sorts;

public class QuickSort {
    public static void sort(int[] data) {
        sort(data, 0, data.length);
    }

    public static void sort(int[] data, int fromIndex, int toIndex) {
        int left = fromIndex;
        int right = toIndex-1;
        boolean pivotIsLeft = true;

        while (left != right) {
            if (data[left] > data[right]) {
                int temp = data[left];
                data[left] = data[right];
                data[right] = temp;

                pivotIsLeft ^= true;
            }

            if (!pivotIsLeft)
                left++;
            else
                right--;
        }

        int pivotPosition = pivotIsLeft ? left : right;

        if (pivotPosition-fromIndex > 1)
            sort(data, fromIndex, pivotPosition);
        if (toIndex-(pivotPosition+1) > 1)
            sort(data, pivotPosition+1, toIndex);
    }
}
