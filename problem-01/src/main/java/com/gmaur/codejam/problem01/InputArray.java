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

	public InputArray swap (final Swap l, final Swap m) {

		final List<Integer> inputList = input;

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
}
