package com.gmaur.codejam.problem01;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PowerSwapper {

	private final LinkedList<Integer[]> swaps;

	public PowerSwapper () {
		swaps = new LinkedList<>();
	}

	Integer sort2 (List<Integer> input) {
		getPartsWithLength(input, half(input));
		return swaps.size();
	}

	private int half (final List<Integer> input) {
		return input.size()/2;
	}

	private void getPartsWithLength (List<Integer> input, int size) {
		if (size == 0) {
			return;
		}
		List<Integer[]> swapCandidates = getSwapCandidates(input, size);
		if(swapCandidates.isEmpty()){
			return;
		}
		int maxBefore = getAmount(getIsCorrect(input));
		int maxAfter = maxBefore;

		Integer[] chosen = null;
		for (int i = 0; i < swapCandidates.size(); i++) {
			Integer[] currentI = swapCandidates.get(i);
			for (int j = 0; j < swapCandidates.size(); j++) {
				Integer[] currentJ = swapCandidates.get(j);
				if (currentI[0] - currentI[1] == currentJ[0] - currentJ[1] && i != j) {
					int after = getAmount(getIsCorrect(swapWithDebug(input, currentI, currentJ)));
					if (after > maxAfter) {
						chosen = new Integer[]{i, j};
						maxAfter = after;
					}
				}
			}
		}
		Integer[] thisLevelSwap= null;
		if(maxAfter > maxBefore && null != chosen) {
			thisLevelSwap = chosen;
		}

		 List<Integer> swapped = input;
		int newSize;
		if(null != thisLevelSwap){
			swaps.add(thisLevelSwap);
			swapped = swapWithDebug(input, swapCandidates.get(thisLevelSwap[0]), swapCandidates.get(thisLevelSwap[1]));
			newSize = size;
		}else {
			newSize = size / 2;
		}

		getPartsWithLength(swapped, newSize);

	}

	private List<Integer[]> getSwapCandidates (final List<Integer> input, final int size) {
		List<Integer[]> list = new LinkedList<>();
		{
			int end, i;
			for (i = 0; i < input.size(); i = end) {
				end = i + (size - 1);

				if (end < input.size() && size > 0) {
					list.add(new Integer[]{
							i,
							end
					});
				}
				end++;
			}
		}
		return list;
	}

	private List<Integer> swapWithDebug (List<Integer> input, Integer[] l, Integer[] m) {
		System.out.println("input = " + input);
		System.out.println("l = " + Arrays.asList(l));
		System.out.println("m = " + Arrays.asList(m));

		final List<Integer> swapped = swap(input, l, m);
		System.out.println("swapped = " + swapped);
		return swapped;
	}

	private List<Integer> swap (final List<Integer> input, final Integer[] l, final Integer[] m) {
		final Integer[] mm = generateIndices(m);
		final Integer[] ll = generateIndices(l);

		Integer[] array = input.toArray(new Integer[0]);
		Integer[] tmp = new Integer[mm.length];

		for (int i = 0; i < mm.length; i++) {
			tmp[i] = array[mm[i]];
		}

		for (int i = 0; i < ll.length; i++) {
			array[mm[i]] = array[ll[i]];
		}
		for (int i = 0; i < tmp.length; i++) {
			array[ll[i]] = tmp[i];
		}
		return Arrays.asList(array);
	}

	private Integer[] generateIndices (final Integer[] array) {
		final int length = array[1] - array[0] + 1;
		Integer[] indexes = new Integer[length];
		int j =0;
		for (int i = array[0]; i <= array[array.length -1]; i++) {
			indexes[j] =i;
			j++;
		}
		return indexes;
	}

	private int getAmount(Boolean[] isCorrect) {
		return (int) Arrays.asList(isCorrect).stream().filter(x->x==true).count();
	}

	Boolean[] getIsCorrect (List<Integer> of) {
		int i = 1;
		Boolean[] isCorrect = new Boolean[of.size()];
		for (Integer current : of) {
			isCorrect[i-1] = i ==current;
			i++;
		}
		return isCorrect;
	}
}
