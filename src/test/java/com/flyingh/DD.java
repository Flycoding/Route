package com.flyingh;

import org.junit.Test;

public class DD {

    private static final int[] a = {2, 2, 2, 3, 7, 1, 3, 4, 1, 5, 6};
    private static final int[] b = {2, 3, 7, 6, 1, 3, 4, 1, 5, 7};

    @Test
    public void test() {
        System.out.println(f(a, b));
    }


    public int f(int[] a, int[] b) {
        int max = 0;
        for (int i = 0; i < b.length; i++) {
            int tmpi = i, maxLength = 0;
            int j = 0, count = 0;
            while (j < a.length) {
                while (i < b.length && j < a.length && b[i] == a[j]) {
                    ++i;
                    ++j;
                    ++maxLength;
                }
                max = max > maxLength ? max : maxLength;
                j = ++count;
                maxLength = 0;
                i = tmpi;
            }
        }
        return max;
    }
}
