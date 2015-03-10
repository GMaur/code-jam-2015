package com.gmaur.codejam.problem01;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
* Created by alvaro on 09/03/15.
*/
class InputArray {
	private final List<Integer> input;

	public InputArray (final List<Integer> input) {
		this.input = input;
	}

	public int halfSize () {
		return input.size() / 2;
	}

	public List<Integer> get () {
		return Collections.unmodifiableList(input);
	}

	public InputArray swap (final SwapPair chosenSwapPair) {

		final Swap m = chosenSwapPair.future;
		final Swap l = chosenSwapPair.past;
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
		return new InputArray(Arrays.asList(array));
	}

	private Integer[] generateIndices (final Swap l) {
		final int length = l.length();
		Integer[] indexes = new Integer[length];
		int j =0;
		for (int i = l.begin; i <= l.end; i++) {
			indexes[j] =i;
			j++;
		}
		return indexes;
	}
}
