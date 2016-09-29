package Sorts;

public class MergeSort {
    public static void sort(int[] data) {
        sort(data, 0, data.length);
    }

    public static void sort(int[] data, int fromIndex, int toIndex) {
        int dataLength = toIndex-fromIndex;
        if (dataLength > 1) {
            int leftListStart = fromIndex;
            int leftListEnd = dataLength/2+fromIndex;

            int rightListStart = leftListEnd;
            int rightListEnd = toIndex;

            sort(data, leftListStart, leftListEnd);
            sort(data, rightListStart, rightListEnd);

            int[] unsortedData = data.clone();

            for (int dataIndex = fromIndex, leftListIndex = leftListStart, rightListIndex = rightListStart; dataIndex < dataLength; dataIndex++)
                if (leftListIndex < leftListEnd && (rightListIndex >= rightListEnd || unsortedData[leftListIndex] < unsortedData[rightListIndex])) {
                    data[dataIndex] = unsortedData[leftListIndex];
                    leftListIndex++;
                } else {
                    data[dataIndex] = unsortedData[rightListIndex];
                    rightListIndex++;
                }
        }
    }
}
