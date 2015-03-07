package com.gmaur.codejam.problem01;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.gmaur.codejam.problem01.PowerSwapper.SolutionComparator.is;

public class PowerSwapper {

	private final LinkedList<Integer[]> swaps;
	private List<Swap> swapsObjects;

	public PowerSwapper () {
		swaps = new LinkedList<>();
		swapsObjects = new LinkedList<>();
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
		int maxCorrectPositionsFromBefore = getAmount(getIsCorrect(input));
		int maxCorrectPositionsAfterSwap = maxCorrectPositionsFromBefore;

		Integer[] chosenSwap = null;
		Swap chosenSwapObject = null;
		for (int i = 0; i < swapCandidates.size(); i++) {
			Integer[] currentI = swapCandidates.get(i);
			for (int j = 0; j < swapCandidates.size(); j++) {
				Integer[] currentJ = swapCandidates.get(j);
				if (currentI[0] - currentI[1] == currentJ[0] - currentJ[1] && i != j) {
					int correctPositionsAfterThisSwap = getAmount(getIsCorrect(swapWithDebug(input, currentI, currentJ)));
					if (is(maxCorrectPositionsAfterSwap).betterThan(correctPositionsAfterThisSwap)) {
						chosenSwap = new Integer[]{i, j};
						chosenSwapObject = new Swap(i, j);
						maxCorrectPositionsAfterSwap = correctPositionsAfterThisSwap;
					}
				}
			}
		}

		List<Integer> swapped = input;
		int newSize;
		if(null != chosenSwap){
			swaps.add(chosenSwap);
			swapsObjects.add(chosenSwapObject);
			swapped = swapWithDebug(input, swapCandidates, chosenSwapObject);
			newSize = size;
		}else {
			newSize = size / 2;
		}

		getPartsWithLength(swapped, newSize);

	}

	private List<Integer> swapWithDebug (final List<Integer> input, final List<Integer[]> swapCandidates, final Swap chosenSwapObject) {
		return swapWithDebug(input, swapCandidates.get(chosenSwapObject.begin), swapCandidates.get(chosenSwapObject.end));
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

	static class SolutionComparator {
		private final int current;

		public SolutionComparator (final int current) {
			this.current = current;
		}

		public boolean betterThan (final int candidate) {
			return candidate > current;
		}

		public static SolutionComparator is (final int after) {
			return new SolutionComparator(after);
		}
	}

	private class Swap {
		private final int begin;
		private final int end;

		public Swap (final int begin, final int end) {
			this.begin = begin;
			this.end = end;
		}
	}
}
