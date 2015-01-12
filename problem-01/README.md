Problem 01: Mini-Power Swapper
----

(Inspiration: https://code.google.com/codejam/contest/7214486/dashboard#s=p1)

###Problem

In a parallel universe, people only want sorted lists of elements from 1 to 2^k.

Now, old software systems generate lists of elements who are within the same range (1 to 2^k).

There is a machine who swaps numbers in this fashion: 
  * A range of numbers to be swapped is valid if and only if it is a range of adjacent numbers of size 2^k
  * A valid swap operation of size-k is defined by swapping two distinct, valid ranges of numbers, each of size 2^k.

It is your assignment, shall you decide to take it, to estimate the number of swaps necessary to sort the list (and therefore be able to sell it to the public)

###Example 1

For example, given the permutation [3, 6, 1, 2, 7, 8, 5, 4] (range 1..2^3), can be sorted as follows:
input: 3, 6, 1, 2, 7, 8, 5, 4

debug output:
was = [3, 6, 1, 2, 7, 8, 5, 4], swap [0, 3] => [4, 7] :  now = [7, 8, 5, 4, 3, 6, 1, 2]
was = [7, 8, 5, 4, 3, 6, 1, 2], swap [0, 1] => [6, 7] :  now = [1, 2, 5, 4, 3, 6, 7, 8]
was = [1, 2, 5, 4, 3, 6, 7, 8], swap [2, 2] => [4, 4] :  now = [1, 2, 3, 4, 5, 6, 7, 8]

Result: 3

###Example 2
input: 3, 4, 1 ,2

debug output: 
was = [3, 4, 1, 2], swap [0, 1] => [2, 3] :  now = [1, 2, 3, 4]

output: 1
