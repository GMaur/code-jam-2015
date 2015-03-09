package com.gmaur.codejam.problem01;

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
}
