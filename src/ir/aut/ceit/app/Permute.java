package ir.aut.ceit.app;

import java.util.ArrayList;

public class Permute {

    static ArrayList<int[]> permutations = new ArrayList<>();

    public static void permute(ArrayList<Integer> arr) {
        permuteHelper(arr, 1);
    }

    private static void permuteHelper(ArrayList<Integer> arr, int index) {

        if (index >= arr.size() - 1) {
            int[] temp = new int[arr.size()];
            for (int i = 0; i < arr.size() - 1; i++) {
                temp[i] = arr.get(i);
            }
            temp[arr.size() - 1] = arr.get(arr.size() - 1);
            permutations.add(temp);
            return;
        }

        for (int i = index; i < arr.size(); i++) {

            int t = arr.get(index);
            arr.set(index, arr.get(i));
            arr.set(i, t);

            permuteHelper(arr, index + 1);

            t = arr.get(index);
            arr.set(index, arr.get(i));
            arr.set(i, t);
        }
    }
}