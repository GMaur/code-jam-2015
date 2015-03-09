package com.gmaur.codejam.problem01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.gmaur.codejam.problem01.PowerSwapper.SolutionComparator.is;

public class PowerSwapper {

	private List<Swap> swaps;

	public PowerSwapper () {
		swaps = new LinkedList<>();
	}

	Integer sort2 (InputArray input) {
		getPartsWithLength(input.get(), input.halfSize());
		return swaps.size();
	}



	private void getPartsWithLength (List<Integer> input, int size) {
		List<Swap> swapCandidates = generateSwapCandidates(input, size);
		if (swapCandidates.isEmpty()) return;
		int maxCorrectPositionsFromBefore = getAmount(getIsCorrect(input));
		int maxCorrectPositionsAfterSwap = maxCorrectPositionsFromBefore;

		Swap chosenSwap = null;
		for (int i = 0; i < swapCandidates.size(); i++) {
			Swap currentI = swapCandidates.get(i);
			for (int j = 0; j < swapCandidates.size(); j++) {
				Swap currentJ = swapCandidates.get(j);
				if (currentI.begin - currentI.end == currentJ.begin - currentJ.end && i != j) {

					System.out.println("input = " + input);
					System.out.println("l = " + Arrays.asList(currentI));
					System.out.println("m = " + Arrays.asList(currentJ));

					final List<Integer> swapped = swap(input, currentI, currentJ);
					System.out.println("swapped = " + swapped);
					int correctPositionsAfterThisSwap = getAmount(getIsCorrect(swapped));
					if (is(maxCorrectPositionsAfterSwap).betterThan(correctPositionsAfterThisSwap)) {
						chosenSwap = new Swap(i, j);
						maxCorrectPositionsAfterSwap = correctPositionsAfterThisSwap;
					}
				}
			}
		}

		List<Integer> swapped = input;
		int newSize;
		if(null != chosenSwap){
			swaps.add(chosenSwap);
			swapped = swapWithDebug(input, swapCandidates, chosenSwap);
			newSize = size;
		}else {
			newSize = size / 2;
		}

		getPartsWithLength(swapped, newSize);

	}

	private List<Swap> generateSwapCandidates (final List<Integer> input, final int size) {

		List<Swap> swapCandidates = new ArrayList<>();
		if(size == 0){
			return swapCandidates;
		}
		swapCandidates = getSwapCandidates(input, size);
		if(swapCandidates.isEmpty()){
			return swapCandidates;
		}
		return swapCandidates;
	}

	private List<Integer> swapWithDebug (final List<Integer> input, final List<Swap> swapCandidates, final Swap swap) {
//		Integer[] l = swapCandidates.get(swap.begin);
//		Integer[] m = swapCandidates.get(swap.end);

//		System.out.println("input = " + input);
//		System.out.println("l = " + Arrays.asList(l));
//		System.out.println("m = " + Arrays.asList(m));

		final List<Integer> swapped = swap(input, swapCandidates.get(swap.begin), swapCandidates.get(swap.end));
		System.out.println("swapped = " + swapped);
		return swapped;
	}

	private List<Swap> getSwapCandidates (final List<Integer> input, final int size) {
		List<Swap> list = new LinkedList<>();
		int end, i;
		for (i = 0; i < input.size(); i = end) {
			end = i + (size - 1);

			if (end < input.size() && size > 0) {
				list.add(new Swap(i, end));
			}
			end++;
		}
		return list;
	}

	private List<Integer> swap (final List<Integer> input, final Swap l, final Swap m) {

		final Integer[] mm = generateIndices(swapToIntegerArray(m));
		final Integer[] ll = generateIndices(swapToIntegerArray(l));

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

	private Integer[] swapToIntegerArray (final Swap m) {
		return new Integer[]{m.begin, m.end};
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
