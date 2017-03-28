import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    // Significantly faster than the Scanner.
    // I will be using a BufferedReader, in order to read the user input, from the console.
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));;

    public static void main(String[] args) {

        // Read the input from the console, and split it by space (" ") delimiter.
        String[] inputArray = readLine().split(" ");

        // Pass the array to a method which will parse it to an integer array
        // and store the result a variable.
        int[] parsedArray = parseInt(inputArray);

        // Pass the parsed array to the sorting method, and store the result in a variable.
        int[] sortedArray = mergeSort(parsedArray);

        // Print the sorted array by passing it to a method, which will print it.
        printArray(sortedArray);
    }

    // Just a simple method for parsing an array of string to an array of integers.
    private static int[] parseInt(String[] inputArray) {
        int[] parsedArray = new int[inputArray.length];

        for (int i = 0; i < inputArray.length; i++) {
            parsedArray[i] = Integer.parseInt(inputArray[i]);
        }

        return parsedArray;
    }

    // Just a simple method that prints an array of integers.
    // The elements are printed, with a space (" ") delimiter.
    private static void printArray(int[] array) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            if(i != array.length - 1) {
                result.append(array[i] + " ");
            } else {
                result.append(array[i]);
            }
        }

        System.out.println(result);
    }

    // The main algorithm method.
    // The merge sort method.
    private static int[] mergeSort(int[] array) {

        // If we have reached the bottom of the recursion (all elements have been split into partitions of 1),
        // we return the whole array (1 element).
        if(array.length == 1) {
            return array;
        }

        // Extract the index, at the middle of the array.
        int halfIndex = array.length / 2;

        // The lengths of the two partitions of the array.
        int firstArrayLength = halfIndex;
        int secondArrayLength = array.length - halfIndex;

        // The two partitions of the array.
        int[] firstPartition = new int[firstArrayLength];
        int[] secondPartition = new int[secondArrayLength];

        // Filling the first partition with values from the main array.
        for (int i = 0; i < firstArrayLength; i++) {
            firstPartition[i] = array[i];
        }

        // Filling the second partition with values from the main array.
        for (int i = firstArrayLength; i < firstArrayLength + secondArrayLength; i++) {
            secondPartition[i - firstArrayLength] = array[i];
        }

        // Recursively, do the same for each of the two partitions of the array.
        // Each partitions gets split into two, until you reach partitions of one element.
        firstPartition = mergeSort(firstPartition);
        secondPartition = mergeSort(secondPartition);

        // From here on, it's the backtrack of the recursion.
        // The main logic after the split of the partitions.

        // This is the main index, which will be used to follow the progress on the main array.
        int mainIndex = 0;

        // These are the indexes for the two partitions, which will be used to follow the progress on them.
        int firstPartitionIndex = 0;
        int secondPartitionIndex = 0;

        // The main comparing algorithm.
        // The loop's condition consists of both indexes, being compared to their corresponding partition lengths.
        // Both partitions's indexes will be increased, until one of the arrays is expired.
        // In other words... This loop will go through both partitions, simultaneously, and will finish
        // only when, one of the two indexes, reaches its partition's length.
        while(firstPartitionIndex < firstArrayLength && secondPartitionIndex < secondArrayLength) {
            // Here is the comparison part.
            // We compare the CURRENT element from the first partition, with the CURRENT element from the second partition.
            // In case the first partition's CURRENT element is lower by comparison, it will be put on the CURRENT position
            // of the MAIN array.
            // If that is NOT the case, the second partition's CURRENT element, will be be put on the CURRENT position
            // of the MAIN array.
            // If you switch the comparing symbol, you might achieve a descending order in the sort.
            // Currently the algorithm sorts in ASCENDING order.
            if(firstPartition[firstPartitionIndex] < secondPartition[secondPartitionIndex]) {
                array[mainIndex] = firstPartition[firstPartitionIndex];

                mainIndex++;
                firstPartitionIndex++;
            } else {
                array[mainIndex] = secondPartition[secondPartitionIndex];

                mainIndex++;
                secondPartitionIndex++;
            }
        }

        // When the loop finishes, naturally, one of the two partitions, should be expired.
        // In other words... One of the two partitions' values, have been traversed totally.
        // That would mean that, the other array would have some leftover values, which is why
        // we need to store even them.
        // Due to the fact we have nothing to compare them with, we just store them in the remaining
        // positions of the main array.

        // This loop will go through the remaining elements from the first partition, if there are such.
        while(firstPartitionIndex < firstArrayLength) {
            array[mainIndex] = firstPartition[firstPartitionIndex];

            mainIndex++;
            firstPartitionIndex++;
        }

        // This loop will go through the remaining elements from the first partition, if there are such.
        while(secondPartitionIndex < secondArrayLength) {
            array[mainIndex] = secondPartition[secondPartitionIndex];

            mainIndex++;
            secondPartitionIndex++;
        }

        // The other exit point of the recursive algorithm.
        // Return the processed array.
        return array;
    }

    // The BufferedReader is fast, but one of its cons is, that it may throw an IOException
    // And Java does NOT let you pass an unhandled potential exception throw.
    // Putting this in the main() method, plainly, would result in the need to add "throws ..."
    // signature to the method, which is kind of ... Not, good-looking.
    // That is why I wrapped the logic in a method, with a try / catch block inside it.
    private static String readLine() {
        String result = null;

        try {
            result = reader.readLine();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
