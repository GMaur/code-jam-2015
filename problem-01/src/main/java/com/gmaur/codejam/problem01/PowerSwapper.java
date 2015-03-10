package com.gmaur.codejam.problem01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.gmaur.codejam.problem01.SolutionComparator.is;

public class PowerSwapper {

	private List<SwapPair> swapPairs;

	public PowerSwapper () {
		swapPairs = new ArrayList<>();
	}

	Integer sort (InputArray input) {
		getPartsWithLength(input, input.halfSize());
		return swapPairs.size();
	}

	private void getPartsWithLength (InputArray input, int size) {
		List<Swap> swapCandidates = generateSwapCandidates(input, size);
		if (swapCandidates.isEmpty()) return;
		int maxCorrectPositionsFromBefore = getAmount(getIsCorrect(input));
		int maxCorrectPositionsAfterSwap = maxCorrectPositionsFromBefore;


		SwapPair chosenSwapPair = null;
		for(SwapPair nextSwap : getValidSwapCandidates(swapCandidates)) {
			final InputArray swapped = swap(input, nextSwap);
			int correctPositionsAfterThisSwap = getAmount(getIsCorrect(swapped));
			if (is(maxCorrectPositionsAfterSwap).betterThan(correctPositionsAfterThisSwap)) {
				chosenSwapPair = nextSwap;
				maxCorrectPositionsAfterSwap = correctPositionsAfterThisSwap;
			}
		}

		InputArray swapped = input;
		int newSize;
		if(chosenSwapPair != null){
			swapPairs.add(chosenSwapPair);
			swapped = swapWithDebug(input, chosenSwapPair);
			newSize = size;
		}else {
			newSize = size / 2;
		}

		getPartsWithLength(swapped, newSize);

	}

	private List<SwapPair> getValidSwapCandidates (final List<Swap> swapCandidates) {
		final List<SwapPair>  result = new ArrayList<>();
		SwapPair chosenSwapPair = null;
		for (int i = 0; i < swapCandidates.size(); i++) {
			for (int j = 0; j < swapCandidates.size(); j++) {
				SwapPair nextSwap = getNextSwapPair(swapCandidates, i, j);
				if (i != j && sameLengthAs(nextSwap)) {
					result.add(nextSwap);
				}
			}
		}
		return result;

	}

	private SwapPair getNextSwapPair (final List<Swap> swapCandidates, final int i, final int j) {
		Swap currentI = swapCandidates.get(i);
		Swap currentJ = swapCandidates.get(j);
		return new SwapPair(currentI, currentJ);
	}

	private boolean sameLengthAs (final SwapPair nextSwap) {
		return nextSwap.l.isSameLengthAs(nextSwap.m);
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

	private InputArray swapWithDebug (final InputArray input, final SwapPair chosenSwapPair) {
//		Integer[] l = swapCandidates.get(swap.begin);
//		Integer[] m = swapCandidates.get(swap.end);

//		System.out.println("input = " + input);
//		System.out.println("l = " + Arrays.asList(l));
//		System.out.println("m = " + Arrays.asList(m));

		final InputArray swapped = swap(input, chosenSwapPair);
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

	private InputArray swap (final InputArray input, final SwapPair chosenSwapPair) {
		return input.swap(chosenSwapPair);
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
