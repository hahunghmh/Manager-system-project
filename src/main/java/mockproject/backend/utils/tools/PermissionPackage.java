/*
 *@project : BackEnd
 *@Create by: HCM23_FRF_FJB_04_GROUP02
 *@Create time: 11/25/2023 - 10:08 AM
 */


package mockproject.backend.utils.tools;

import java.util.ArrayList;

public class PermissionPackage {
//    ok
    public static void main(String[] args) {
        ArrayList<Integer[]> result = processPermission(1, new Integer[]{0, 0, 0});
        System.out.println(result);
    }

    public static ArrayList<Integer[]> processPermission(int role, Integer[] entityCode) {
        final int[][] matrixPermission = {
                {2},
                {2, 3, 4},
                {1},
                {1, 2, 3, 4, 5}
        };

        final int[][] permissionCodeList = {
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20}
        };


        // Extract values based on entityCode indices with permissionCodeList
        int[][] output = new int[entityCode.length][];

        for (int i = 0; i < entityCode.length; i++) {
            int code = entityCode[i];

            // Check if the code is a valid index in matrixPermission
            if (code - 1 >= 0 && code - 1 < matrixPermission.length) {
                int[] row = matrixPermission[code - 1];
                // Initialize the output row with zeros
                output[i] = new int[permissionCodeList[i].length];

                // Fill in the values from permissionCodeList to the output row
                for (int j = 0; j < row.length; j++) {
                    int permissionIndex = row[j] - 1;
                    if (permissionIndex >= 0 && permissionIndex < permissionCodeList[i].length) {
                        output[i][j] = permissionCodeList[i][permissionIndex];
                    }
                }
            }
        }
        ArrayList<Integer> var11 = new ArrayList<>();
        // Print the output
//        if(output.l)
        for (int[] row : output) {
            if (row != null)
                for (int i : row) {
                    if (i != 0) var11.add(i);
                }
        }

        ArrayList<Integer[]> p1 = new ArrayList<>();
        for (int i = 0; i < var11.size(); i++) {
            p1.add(new Integer[]{role, var11.get(i)});
        }
//        add default permission for Entity user
        if (role == 1) {
            p1.add(new Integer[]{role, 1});
            p1.add(new Integer[]{role, 2});
            p1.add(new Integer[]{role, 3});
            p1.add(new Integer[]{role, 4});
            p1.add(new Integer[]{role, 5});
        } else if (role == 2) {
            p1.add(new Integer[]{role, 1});
        } else {
            p1.add(new Integer[]{role, 2});
        }

        return p1;
    }
}
