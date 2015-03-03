package com.gmaur.codejam.problem01;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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

		assertThat(getIsCorrect(Arrays.asList(1,2,3,4)), equalTo(new Boolean[]{true, true, true, true}));
		assertThat(getIsCorrect(Arrays.asList(1,1,3,4)), equalTo(new Boolean[]{true, false, true, true}));
		assertThat(getIsCorrect(Arrays.asList(1,1,1,4)), equalTo(new Boolean[]{true, false, false, true}));
	}

	private Integer sort2(List<Integer> input) {
		final LinkedList<Integer[]> swaps = new LinkedList<>();
		getPartsWithLength(input, input.size()/2, swaps);
		return swaps.size();
	}

	private void getPartsWithLength(List<Integer> input, int size, LinkedList<Integer[]> swaps) {
		if (size == 0) {
			return;
		}
		List<Integer[]> list = new LinkedList<>();
		{
			int end, i;
			for (i = 0; i < input.size(); i = end) {
				end = i + (size - 1);

				if (end < input.size() && size > 0) {
					list.add(new Integer[]{
							i,
							end
					});
				}
				end++;
			}
		}
		if(list.isEmpty()){
			return;
		}
		int maxBefore = getAmount(getIsCorrect(input));
		int maxAfter = maxBefore;

		Integer[] chosen = null;
		for (int i = 0; i < list.size(); i++) {
			Integer[] currentI = list.get(i);
			for (int j = 0; j < list.size(); j++) {
				Integer[] currentJ = list.get(j);
				if (currentI[0] - currentI[1] == currentJ[0] - currentJ[1] && i != j) {
					int after = getAmount(getIsCorrect(swap(input, currentI, currentJ, false)));
					if (after > maxAfter) {
						chosen = new Integer[]{i, j};
						maxAfter = after;
					}
				}
			}
		}
		Integer[] thisLevelSwap= null;
		if(maxAfter > maxBefore && null != chosen) {
			thisLevelSwap = chosen;
		}

		 List<Integer> swapped = input;
		int newSize;
		if(null != thisLevelSwap){
			swaps.add(thisLevelSwap);
			swapped = swap(input, list.get(thisLevelSwap[0]), list.get(thisLevelSwap[1]), true);
			newSize = size;
		}else {
			newSize = size / 2;
		}

		getPartsWithLength(swapped, newSize, swaps);

	}

	private List<Integer> swap(List<Integer> input, Integer[] l, Integer[] m, boolean debugOutput) {
		if(debugOutput) {
			System.out.println("input = " + input);
			System.out.println("l = " + Arrays.asList(l));
			System.out.println("m = " + Arrays.asList(m));
		}

		Integer[] ll = new Integer[l[1] - l[0] + 1];
		Integer[] mm = new Integer[ll.length];

		swapPart(m, mm);
		swapPart(l, ll);

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
		final List<Integer> swapped = Arrays.asList(array);
		if(debugOutput) {
			System.out.println("swapped = " + swapped);
		}
		return swapped;
	}

	private void swapPart (final Integer[] m, final Integer[] mm) {
		int j =0;
		for (int i = m[0]; i <= m[m.length -1]; i++) {
			mm[j] =i;
			j++;
		}
	}

	private int getAmount(Boolean[] isCorrect) {

		int correct = 0;
//		System.out.println(Arrays.asList(isCorrect));
		for (Boolean current : isCorrect) {
			if(current) {
				correct++;
			}
		}

		return correct;
	}

	private Boolean[] getIsCorrect(List<Integer> of) {
		List<Integer> correctOrder = new ArrayList<>(of.size());
		int i = 1;
		Boolean[] isCorrect = new Boolean[of.size()];
		for (Integer current : of) {
			correctOrder.add(i);
			isCorrect[i-1] = i ==current;
					i++;
		}
		return isCorrect;
	}
}
