package eecs2030.lab7;

import java.util.ArrayList;
import java.util.List;

/*
 * Requirement:
 * You are required to implement all methods recursively.
 * You receive a zero if there is any occurrence of a loop (e.g., for, while).
 */
public class RecursiveMethods {

	public static void permute(int n) {
		String str = "ABCDEFGHIJ";
		str = str.substring(0, n);

		permuteHelper(str, 0, n - 1);
	}

	private static void permuteHelper(String str, int l, int r) {
		if (l == r) {
			System.out.println(str);
		} else {
			generatePermutations(str, l, r, l);
		}
	}

	private static String swap(String a, int i, int j) {
		char[] charArray = a.toCharArray();
		char temp = charArray[i];
		charArray[i] = charArray[j];
		charArray[j] = temp;

		return String.valueOf(charArray);
	}

	private static void generatePermutations(String str, int l, int r, int iterator) {
		if (iterator > r) {
			return;
		}

		str = swap(str, l, iterator);
		permuteHelper(str, l + 1, r);
		str = swap(str, l, iterator);

		generatePermutations(str, l, r, iterator + 1);
	}

	public static double parseDouble(String s) {
		double finalNum = 0.0;

		// getting the location of the decimal if any
		int decimalIndex = s.indexOf(".");

		boolean isNegative = false;

		if (s.indexOf("-") >= 0) {
			isNegative = true;
		}

		if (decimalIndex == -1) {
			finalNum += parsePart1(s, s.length() - 1, 0, isNegative);
		} else {
			if (decimalIndex != 0) {
				finalNum += parsePart1(s, decimalIndex - 1, 0, isNegative);
			}
			finalNum += parsePart2(s, decimalIndex + 1, 1, isNegative);
		}

		return finalNum;
	}

	private static double parsePart1(String num, int n, int exp, boolean negative) {
		double valueAtN;

		if (negative == false) {
			if (n == 0) {
				// System.out.println("YEET");
				valueAtN = (num.charAt(0) - '0') * Math.pow(10, exp);
				return valueAtN;
			} else {
				valueAtN = (num.charAt(n) - '0') * Math.pow(10, exp);
				return valueAtN + parsePart1(num, n - 1, exp + 1, negative);
			}
		} else {
			if (n == 1) {
				// System.out.println("YEET");
				valueAtN = -(num.charAt(1) - '0') * Math.pow(10, exp);
				return valueAtN;
			} else {
				valueAtN = -(num.charAt(n) - '0') * Math.pow(10, exp);
				return valueAtN + parsePart1(num, n - 1, exp + 1, negative);
			}
		}
	}

	private static double parsePart2(String num, int n, int exp, boolean negative) {
		double valueAtN;

		if (!negative) {
			// base case at end of string
			if (n == num.length() - 1) {
				double multiplier = Math.round(Math.pow(0.1, exp) * Math.pow(10, exp)) / Math.pow(10, exp);
				valueAtN = (num.charAt(num.length() - 1) - '0') * multiplier;
				return valueAtN;
			} else {
				double multiplier = Math.round(Math.pow(0.1, exp) * Math.pow(10, exp)) / Math.pow(10, exp);
				valueAtN = (num.charAt(n) - '0') * multiplier;
				return valueAtN + parsePart2(num, n + 1, exp + 1, negative);
			}
		} else {
			// base case at end of string
			if (n == num.length() - 1) {
				double multiplier = -1 * Math.round(Math.pow(0.1, exp) * Math.pow(10, exp)) / Math.pow(10, exp);
				valueAtN = (num.charAt(num.length() - 1) - '0') * multiplier;
				return valueAtN;
			} else {
				double multiplier = -1 * Math.round(Math.pow(0.1, exp) * Math.pow(10, exp)) / Math.pow(10, exp);
				valueAtN = (num.charAt(n) - '0') * multiplier;
				return valueAtN + parsePart2(num, n + 1, exp + 1, negative);
			}
		}
	}

	public static int lenLongSubList(List<Integer> list) {
		return lenLongSubListHelper(list, 0);
	}

	private static int lenLongSubListHelper(List<Integer> list, int length) {

		if (list.size() == 2) {
			if (list.get(0) < list.get(1)) {
				return length + 2;
			} else if (list.get(0) >= list.get(1)) {
				return length;
			}
		} else {
			if (list.get(0) < list.get(1)) {
				list.remove(0);
				return lenLongSubListHelper(list, length + 1);
			} else if (list.get(0) >= list.get(1)) {
				list.remove(0);
				return lenLongSubListHelper(list, length);
			}
		}
		return 0;
	}

	public static int lenLongSubArr(int[] arr) {
		return lenLongSubArrHelper(arr, 0, 0);
	}

	private static int lenLongSubArrHelper(int[] array, int index1, int length) {
		if (index1 == array.length - 2) {
			if (array[index1] < array[index1 + 1]) {
				return length + 2;
			} else if (array[index1] >= array[index1 + 1]) {
				return length;
			}
		} else {
			if (array[index1] < array[index1 + 1]) {
				return lenLongSubArrHelper(array, index1 + 1, length + 1);
			} else if (array[index1] >= array[index1 + 1]) {
				return lenLongSubArrHelper(array, index1 + 1, length);
			}
		}

		return 0;
	}

	public static int[] mergeSortedArrays(int[] left, int[] right) {
		return getNewArray(left, right, new int[left.length + right.length], 0, 0);
	}

	/**
	 * Helper for mergeSortedArrays method
	 * 
	 * @param arr1     sorted array 1
	 * @param arr2     sorted array 2
	 * @param finalArr the sorted array that is to be outputted
	 * @param index1   the current index of the arr1
	 * @param index2   the current index of the arr2
	 * @return sorted array from the sorted arrays arr1, arr2
	 * @pre both arr1 and arr2 are sorted
	 */
	private static int[] getNewArray(int[] arr1, int[] arr2, int[] finalArr, int index1, int index2) {
		if (index1 + index2 == finalArr.length - 1) {
			if (index1 == arr1.length) {
				finalArr[index1 + index2] = arr2[index2];

			} else if (index2 == arr2.length) {
				finalArr[index1 + index2] = arr1[index1];
			}

			return finalArr;
		} else {
			// System.out.println("INDEX1===>" + index1);
			// System.out.println("INDEX2===>" + index2);
			if (arr1[index1] <= arr2[index2]) {
				finalArr[index1 + index2] = arr1[index1];
				return getNewArray(arr1, arr2, finalArr, index1 + 1, index2);
			} else {
				finalArr[index1 + index2] = arr2[index2];
				return getNewArray(arr1, arr2, finalArr, index1, index2 + 1);
			}
		}
	}

	public static List<Integer> mergeSortedLists(List<Integer> left, List<Integer> right) {
		return getNewList(left, right, new ArrayList<Integer>());
	}

	private static List<Integer> getNewList(List<Integer> list1, List<Integer> list2, List<Integer> finalList) {
		if (list1.isEmpty()) {
			finalList.addAll(list2);
			return finalList;
		} else if (list2.isEmpty()) {
			finalList.addAll(list1);
			return finalList;
		} else {
			// System.out.println("INDEX1===>" + index1);
			// System.out.println("INDEX2===>" + index2);
			if (list1.get(0) <= list2.get(0)) {
				finalList.add(list1.get(0));
				list1.remove(0);
				return getNewList(list1, list2, finalList);
			} else {
				finalList.add(list2.get(0));
				list2.remove(0);
				return getNewList(list1, list2, finalList);
			}
		}
	}

	public static void main(String[] args) {

		permute(4);

	}
}
