package sorts;

public class MergeSort {
    public static void sort(int[] array) {
        sort(array, 0, array.length);
    }

    public static void sort(int[] array, int fromIndex, int toIndex) {
        int dataLength = toIndex-fromIndex;
        if (dataLength > 1) {
            int leftListStart = fromIndex;
            int leftListEnd = dataLength/2+fromIndex;

            int rightListStart = leftListEnd;
            int rightListEnd = toIndex;

            sort(array, leftListStart, leftListEnd);
            sort(array, rightListStart, rightListEnd);

            int[] unsortedData = array.clone();

            for (int dataIndex = fromIndex, leftListIndex = leftListStart, rightListIndex = rightListStart; dataIndex < toIndex; dataIndex++)
                if (leftListIndex < leftListEnd && (rightListIndex >= rightListEnd || unsortedData[leftListIndex] < unsortedData[rightListIndex])) {
                    array[dataIndex] = unsortedData[leftListIndex];
                    leftListIndex++;
                } else {
                    array[dataIndex] = unsortedData[rightListIndex];
                    rightListIndex++;
                }
        }
    }
}
