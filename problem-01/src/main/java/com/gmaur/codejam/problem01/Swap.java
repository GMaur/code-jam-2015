package com.gmaur.codejam.problem01;

/**
* Created by alvaro on 10/03/15.
*/
class Swap {
	public final int begin;
	public final int end;

	public Swap (final int begin, final int end) {
		this.begin = begin;
		this.end = end;
	}

	public boolean isSameLengthAs (final Swap currentJ) {
		final int myLength = begin - end;
		final int otherLength = currentJ.begin - currentJ.end;
		return myLength == otherLength;
	}

	public int length () {
		return end - begin + 1;
	}
}
