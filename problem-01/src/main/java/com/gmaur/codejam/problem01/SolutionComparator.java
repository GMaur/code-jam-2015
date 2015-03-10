package com.gmaur.codejam.problem01;

/**
* Created by alvaro on 10/03/15.
*/
class SolutionComparator {
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
