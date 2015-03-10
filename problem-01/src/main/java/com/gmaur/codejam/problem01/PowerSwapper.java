package com.gmaur.codejam.problem01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.gmaur.codejam.problem01.SolutionComparator.is;

public class PowerSwapper {

	private List<Swap> swaps;

	public PowerSwapper () {
		swaps = new LinkedList<>();
	}

	Integer sort (InputArray input) {
		getPartsWithLength(input, input.halfSize());
		return swaps.size();
	}



	private void getPartsWithLength (InputArray input, int size) {
		List<Swap> swapCandidates = generateSwapCandidates(input, size);
		if (swapCandidates.isEmpty()) return;
		int maxCorrectPositionsFromBefore = getAmount(getIsCorrect(input));
		int maxCorrectPositionsAfterSwap = maxCorrectPositionsFromBefore;

		Swap chosenSwap = null;
		for (int i = 0; i < swapCandidates.size(); i++) {
			Swap currentI = swapCandidates.get(i);
			for (int j = 0; j < swapCandidates.size(); j++) {
				Swap currentJ = swapCandidates.get(j);
				if (sameLengthAs(currentI, currentJ) && i != j) {

					System.out.println("input = " + input);
					System.out.println("l = " + Arrays.asList(currentI));
					System.out.println("m = " + Arrays.asList(currentJ));

					final InputArray swapped = swap(input, currentI, currentJ);
					System.out.println("swapped = " + swapped);
					int correctPositionsAfterThisSwap = getAmount(getIsCorrect(swapped));
					if (is(maxCorrectPositionsAfterSwap).betterThan(correctPositionsAfterThisSwap)) {
						chosenSwap = new Swap(i, j);
						maxCorrectPositionsAfterSwap = correctPositionsAfterThisSwap;
					}
				}
			}
		}

		InputArray swapped = input;
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

	private boolean sameLengthAs (final Swap currentI, final Swap currentJ) {
		return currentI.isSameLengthAs(currentJ);
	}

	private List<Swap> generateSwapCandidates (final InputArray input, final int size) {

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

	private InputArray swapWithDebug (final InputArray input, final List<Swap> swapCandidates, final Swap swap) {
//		Integer[] l = swapCandidates.get(swap.begin);
//		Integer[] m = swapCandidates.get(swap.end);

//		System.out.println("input = " + input);
//		System.out.println("l = " + Arrays.asList(l));
//		System.out.println("m = " + Arrays.asList(m));

		final InputArray swapped = swap(input, swapCandidates.get(swap.begin), swapCandidates.get(swap.end));
		System.out.println("swapped = " + swapped);
		return swapped;
	}

	private List<Swap> getSwapCandidates (final InputArray inputArray, final int size) {
		final List<Integer> input = inputArray.get();
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

	private InputArray swap (final InputArray input, final Swap l, final Swap m) {

		final List<Integer> inputList = input.get();

		final Integer[] mm = generateIndices(swapToIntegerArray(m));
		final Integer[] ll = generateIndices(swapToIntegerArray(l));

		Integer[] array = inputList.toArray(new Integer[0]);
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
		return new InputArray(Arrays.asList(array));
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

	Boolean[] getIsCorrect (InputArray of) {
		final List<Integer> input = of.get();
		int i = 1;
		Boolean[] isCorrect = new Boolean[input.size()];
		for (Integer current : input) {
			isCorrect[i-1] = i ==current;
			i++;
		}
		return isCorrect;
	}

}
