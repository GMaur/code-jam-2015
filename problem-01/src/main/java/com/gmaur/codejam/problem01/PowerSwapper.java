package com.gmaur.codejam.problem01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.gmaur.codejam.problem01.SolutionComparator.is;

public class PowerSwapper {

	private List<Swap> swaps;
	private List<SwapPair> swapPairs;

	public PowerSwapper () {
		swaps = new LinkedList<>();
		swapPairs = new ArrayList<>();
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
		SwapPair chosenSwapPair = null;
		for (int i = 0; i < swapCandidates.size(); i++) {
			for (int j = 0; j < swapCandidates.size(); j++) {
				if(i!=j) {
					Swap currentI = swapCandidates.get(i);
					Swap currentJ = swapCandidates.get(j);
					if (sameLengthAs(currentI, currentJ)) {

						System.out.println("input = " + input);
						System.out.println("l = " + Arrays.asList(currentI));
						System.out.println("m = " + Arrays.asList(currentJ));

						final InputArray swapped = swap(input, currentI, currentJ, new SwapPair(currentI, currentJ));
						System.out.println("swapped = " + swapped);
						int correctPositionsAfterThisSwap = getAmount(getIsCorrect(swapped));
						if (is(maxCorrectPositionsAfterSwap).betterThan(correctPositionsAfterThisSwap)) {
							chosenSwap = new Swap(i, j);
							chosenSwapPair = new SwapPair(currentI, currentJ);
							maxCorrectPositionsAfterSwap = correctPositionsAfterThisSwap;
						}
					}
				}
			}
		}

		InputArray swapped = input;
		int newSize;
		if(null != chosenSwap && chosenSwapPair != null){
			swaps.add(chosenSwap);
			swapPairs.add(chosenSwapPair);
			swapped = swapWithDebug(input, swapCandidates.get(chosenSwap.begin), swapCandidates.get(chosenSwap.end),
					chosenSwapPair);
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

	private InputArray swapWithDebug (final InputArray input, final Swap l, final Swap m, final SwapPair chosenSwapPair) {
//		Integer[] l = swapCandidates.get(swap.begin);
//		Integer[] m = swapCandidates.get(swap.end);

//		System.out.println("input = " + input);
//		System.out.println("l = " + Arrays.asList(l));
//		System.out.println("m = " + Arrays.asList(m));

		final InputArray swapped = swap(input, l, m, chosenSwapPair);
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

	private InputArray swap (final InputArray input, final Swap l, final Swap m, final SwapPair chosenSwapPair) {
		return input.swap(l,m,chosenSwapPair);
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
