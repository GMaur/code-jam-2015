package com.gmaur.codejam.problem01;

import java.util.Arrays;
import java.util.List;

/**
* Created by alvaro on 10/03/15.
*/
class SolutionComparator {
	private final InputArray current;

	public SolutionComparator (final InputArray current) {
		this.current = current;
	}

	public boolean betterThan (final InputArray candidate) {
		return getAmount(getIsCorrect(candidate)) > getAmount(getIsCorrect(current));
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

	public static SolutionComparator is (final InputArray after) {
		return new SolutionComparator(after);
	}
}
