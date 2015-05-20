package com.flyingh;

import org.junit.Test;

import java.util.*;

class Node {
    Object data;
    Node next;

    public Node(Object data, Node next) {
        this.data = data;
        this.next = next;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(data);
        if (next != null) {
            builder.append(next);
        }
        return builder.toString();
    }

}

class TreeNode {
    Object data;
    TreeNode left;
    TreeNode right;
}


public class Demo {


    public void preOrder(TreeNode root, Stack<TreeNode> stack) {

    }

    @Test
    public void test4() {
        Set<Set<String>> sets = powerSet(Arrays.asList("a", "b", "c", "d"));
        System.out.println(sets.size());
        sets.forEach(System.out::println);

    }

    private Set<Set<String>> powerSet(Collection<String> strings) {
        Set<Set<String>> result = new HashSet<>();
        if (strings.isEmpty()) {
            result.add(new TreeSet<>());
            return result;
        }
        TreeSet<String> treeSet = new TreeSet<>(strings);
        String last = treeSet.last();
        for (Set<String> set : powerSet(treeSet.headSet(last))) {
            result.add(set);
            TreeSet<String> newSet = new TreeSet<>();
            newSet.addAll(set);
            newSet.add(last);
            result.add(newSet);
        }
        return result;
    }

    @Test
    public void test3() {
        int[] array = {1, 3, 3, 5, 7, 7, 8, 9};
        BitSet bitSet = new BitSet();
        for (int i : array) {
            bitSet.set(i);
        }
        System.out.println(bitSet);
        for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
            System.out.println(i);
        }
    }

    @Test
    public void test2() {
        int[] arr = {8, 1, 7, 2, 5, 9, 0, 3, 6, 4};
        System.out.println(Arrays.toString(arr));
//        heapSort(arr, arr.length);
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));

    }

    public void quickSort(int[] array, int m, int n) {
        if (m < n) {
            int t = partition(array, m, n);
            quickSort(array, m, t - 1);
            quickSort(array, t + 1, n);
        }
    }

    private int partition(int[] array, int m, int n) {
        int key = array[m];
        while (m < n) {
            while (m < n && array[n] >= key) {
                --n;
            }
            array[m] = array[n];
            while (m < n && array[m] <= key) {
                ++m;
            }
            array[n] = array[m];
        }
        array[m] = key;
        return m;
    }


    private void heapSort(int[] array, int length) {
        buildHeap(array, length);
        if (array[0] > array[length - 1]) {
            swap(array, 0, length - 1);
            heapSort(array, length - 1);
        }
    }

    private void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private void buildHeap(int[] array, int length) {
        for (int i = length / 2 - 1; i >= 0; --i) {
            heapify(array, i, length);
        }
    }

    private void heapify(int[] array, int i, int length) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (right < length) {
            int largest = i;
            if (array[largest] < array[left]) {
                largest = left;
            }
            if (array[largest] < array[right]) {
                largest = right;
            }
            if (largest != i) {
                swap(array, i, largest);
                heapify(array, largest, length);
            }
        }
    }


    @Test
    public void test() {
        Node n3 = new Node("d", null);
        Node n2 = new Node("c", n3);
        Node n1 = new Node("b", n2);
        Node n0 = new Node("a", n1);
        System.out.println(n0);
        System.out.println(reverse(n0));
    }

    public Node reverse(Node node) {
        if (node == null || node.next == null) {
            return node;
        }
        Node n1 = node;
        Node n2 = n1.next;
        while (n2 != null) {
            Node n3 = n2.next;
            n2.next = n1;
            n1 = n2;
            n2 = n3;
        }
        node.next = null;
        return n1;
    }

}
