package com.flyingh;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DDD {
    public static final int ROW_COUNT = 6;
    public static final int COL_COUNT = 5;
    private static final int[] ARRAY = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};

    @Test
    public void output() {
        int[][] array = convert(ARRAY);
        System.out.println(getResult(array));
    }

    private List<Integer> getResult(int[][] array) {
        List<Integer> list = new ArrayList<>();
        int row = -1, col = -1, count = Math.min(ROW_COUNT, COL_COUNT);
        while (count > 0) {
            for (int i = 0; i < count; i++) {
                list.add(array[++row][++col]);
            }
            --count;
            for (int i = 0; i < count; i++) {
                list.add(array[row][--col]);
            }
            --count;
            for (int i = 0; i < count; i++) {
                list.add(array[--row][col]);
            }
            --count;
        }
        return list;
    }

    private int[][] convert(int[] array) {
        int m = 0;
        int[][] ints = new int[ROW_COUNT][COL_COUNT];
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COL_COUNT; j++) {
                ints[i][j] = array[m++];
            }
        }
        return ints;
    }
}
