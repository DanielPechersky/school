package sorts;

public class MergeSort {
    public static void sort(int[] array) {
        sort(array, 0, array.length);
    }

    // fromIndex: inclusive, toIndex: exclusive
    public static void sort(int[] array, int fromIndex, int toIndex) {
        int dataLength = toIndex-fromIndex;  // length of range
        if (dataLength > 1) {
            // get the splitting point to split the array in half with
            int split = dataLength/2+fromIndex;

            // sort each of the two ranges
            sort(array, fromIndex, split);
            sort(array, split, toIndex);

            int[] unsortedData = array.clone();

            // perform merge by separating unsortedData into the two ranges
            for (int dataIndex = fromIndex, leftListIndex = fromIndex, rightListIndex = split; dataIndex < toIndex; dataIndex++)
                if (leftListIndex < split && (rightListIndex >= toIndex || unsortedData[leftListIndex] < unsortedData[rightListIndex])) {
                    array[dataIndex] = unsortedData[leftListIndex];
                    leftListIndex++;
                } else {
                    array[dataIndex] = unsortedData[rightListIndex];
                    rightListIndex++;
                }
        }
    }
}
