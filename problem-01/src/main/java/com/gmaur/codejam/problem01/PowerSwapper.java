package com.gmaur.codejam.problem01;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.gmaur.codejam.problem01.SolutionComparator.is;

public class PowerSwapper {

	private List<SwapPair> swapPairs;

	public PowerSwapper () {
		swapPairs = new ArrayList<>();
	}

	Integer sort (InputArray input) {
		return getPartsWithLength(input, input.halfSize()).size();
	}

	private List<SwapPair> getPartsWithLength (InputArray input, int size) {
		List<Swap> swapCandidates = generateSwapCandidates(input, size);
		if (swapCandidates.isEmpty()) return null;

		Optional<SwapPair> chosenSwapPair = obtainASingleSwapThatImproves(input, swapCandidates);

		return downOrContinue(input, size, chosenSwapPair);
	}

	private List<SwapPair> downOrContinue (final InputArray input, final int size, final Optional<SwapPair> chosenSwapPair) {
		if(chosenSwapPair.isPresent()){
			swapPairs.add(chosenSwapPair.get());
			final InputArray swapped = swap(input, chosenSwapPair.get());
			getPartsWithLength(swapped, size);
		}else {
			getPartsWithLength(input, size / 2);
		}
		return swapPairs;
	}

	private Optional<SwapPair> obtainASingleSwapThatImproves (final InputArray input, final List<Swap> swapCandidates) {
		Optional<SwapPair> chosenSwapPair = Optional.empty();
		for(SwapPair nextSwap : getValidSwapCandidates(swapCandidates)) {
			final InputArray swapped = swap(input, nextSwap);
			if (is(input).betterThan(swapped)) {
				chosenSwapPair = Optional.of(nextSwap);
			}
		}
		return chosenSwapPair;
	}

	private List<SwapPair> getValidSwapCandidates (final List<Swap> swapCandidates) {
		final List<SwapPair>  result = new ArrayList<>();
		for (int i = 0; i < swapCandidates.size(); i++) {
			for (int j = 0; j < swapCandidates.size(); j++) {
				SwapPair nextSwap = getNextSwapPair(swapCandidates, i, j);
				if (i != j && nextSwap.areSameLength()) {
					result.add(nextSwap);
				}
			}
		}
		return result;

	}

	private SwapPair getNextSwapPair (final List<Swap> swapCandidates, final int toBeReplaced, final int replaceFor) {
		Swap past = swapCandidates.get(toBeReplaced);
		Swap future = swapCandidates.get(replaceFor);
		return new SwapPair(past, future);
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

}
