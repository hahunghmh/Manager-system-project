/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/25/2023 - 1:02 PM
 */


package mockproject.backend.utils.tools;

import java.util.ArrayList;
import java.util.Arrays;

public class PermissionExtraction {
    //    main for build test
    public static void main(String[] args) {
        Integer[] input1 = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0};
        Integer[] input2 = {0, 0, 0, 0, 0};
        Integer[] input3 = {1, 1, 1, 1, 1};

        Object[] output1 = preprocess(input1);
        // Print the outputs
        System.out.println("Output1: " + output1);

    }

    public static Integer[] preprocess(Integer[] input) {
        int subarrayLength = 5;
        int numOfSubarrays = (int) Math.ceil((double) input.length / subarrayLength);
        Integer[][] output = new Integer[numOfSubarrays][];

        for (int i = 0; i < numOfSubarrays; i++) {
            int startIdx = i * subarrayLength;
            int endIdx = Math.min((i + 1) * subarrayLength, input.length);
            output[i] = Arrays.copyOfRange(input, startIdx, endIdx);
        }

        ArrayList<Integer> var1 = new ArrayList<>();
        for (Integer[] ints : output) {
            var1.add(extractValue(ints));
        }
        return var1.toArray(new Integer[numOfSubarrays]);
    }

    private static int extractValue(Integer[] input) {
        Integer[][] matrixPermission = {
                {0, 0, 0, 0, 0},//deny all
                {0, 1, 0, 0, 0},//read only
                {0, 1, 1, 1, 0},//modify = read + update + delete
                {1, 0, 0, 0, 0},//create only
                {1, 1, 1, 1, 1}// full
        };

        for (int i = 0; i < 5; i++) {
            boolean matched = true;
            for (int j = 0; j < 5; j++) {
                if (input[j] != matrixPermission[i][j]) {
                    matched = false;
                    break;
                }
            }
            if (matched) {
                return i;
            }
        }
        return -1;
    }
}
