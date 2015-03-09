package com.gmaur.codejam.problem01;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class PowerSwapperShould {

	@Test
	public void sort_the_array_with_4() {
		assertThat(sort2(Arrays.asList(3, 4, 1, 2)), is(1));
		assertThat(sort2(Arrays.asList(3, 6, 1, 2, 7, 8, 5, 4)), is(3));
		assertThat(sort2(Arrays.asList(5,2,7,8,1,6,3,4)), is(2));
		assertThat(sort2(Arrays.asList(3,2,7,8,1,6,5,4)), is(4));
	}

	@Test
	public void calculate_correctness() {

		assertThat(getIsCorrect(Arrays.asList(1, 2, 3, 4)), equalTo(new Boolean[]{true, true, true, true}));
		assertThat(getIsCorrect(Arrays.asList(1, 1, 3, 4)), equalTo(new Boolean[]{true, false, true, true}));
		assertThat(getIsCorrect(Arrays.asList(1, 1, 1, 4)), equalTo(new Boolean[]{true, false, false, true}));
	}

	private Integer sort2(List<Integer> input) {
		return new PowerSwapper().sort(new InputArray(input));
	}

	//TODO AGB this needs to be extracted to a collaborator 
	private Boolean[] getIsCorrect(List<Integer> of) {
		return new PowerSwapper().getIsCorrect(new InputArray(of));
	}

}
