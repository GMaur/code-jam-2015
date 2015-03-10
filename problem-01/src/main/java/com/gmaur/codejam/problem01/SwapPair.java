package com.gmaur.codejam.problem01;

/**
 * Created by alvaro on 10/03/15.
 */
public class SwapPair {
	final Swap past;
	final Swap future;

	public SwapPair (final Swap past, final Swap future) {
		this.past = past;
		this.future = future;
	}

	public boolean areSameLength () {
		return past.isSameLengthAs(future);
	}
}
