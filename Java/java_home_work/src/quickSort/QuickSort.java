package quickSort;

import java.util.*;

public class QuickSort {
    public static void main(String[] args) {
        List<Integer> listToSort = new ArrayList<>(Arrays.asList(7, 3, 2, 5, 5, 6, 4, 8, 1));

        System.out.println(listToSort);

        List<Integer> sortedList =  sortList(listToSort);

        System.out.println(sortedList);
    }

    private static List<Integer> sortList(List<Integer> list) {

        return sortListQuickly(list, 0, list.size() - 1);
    }

    private static List<Integer> sortListQuickly(List<Integer> listToSort, int leftIndex, int rightIndex) {

        if (leftIndex >= rightIndex) {
            return listToSort;
        }

        List<Integer> list = listToSort;

        int pivotIndex = choosePivot(list.subList(leftIndex, rightIndex)) + leftIndex;

        Collections.swap(list, rightIndex, pivotIndex);

        int leftWall = leftIndex;
        int rightWall = rightIndex - 1;

        while (leftWall <= rightWall) {
            if (list.get(leftWall) > list.get(rightIndex)) {
                if (list.get(rightWall) < list.get(rightIndex)) {
                    Collections.swap(list, leftWall, rightWall);
                } else rightWall--;
            } else leftWall++;
        }

        Collections.swap(list, rightIndex, leftWall);

        list = sortListQuickly(list, leftIndex, leftWall - 1);
        list = sortListQuickly(list, leftWall + 1, rightIndex);

        return list;
    }

    private static Integer choosePivot(List<Integer> list) {
        int first = list.getFirst();
        int middle = list.get(list.size() / 2);
        int last = list.getLast();

        int medianOfThree;

        if ((first < last && first > middle) || (first > last && first < middle)) {
            medianOfThree = first;
        } else if ((middle < last && middle > first) || (middle > last && middle < first)) {
            medianOfThree = middle;
        } else {
            medianOfThree = last;
        }

        return list.indexOf(medianOfThree);
    }
}
