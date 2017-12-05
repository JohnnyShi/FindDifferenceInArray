# AMNE Coding Challenge by Yuan
Programming Language: Java 8
Deliverables: README.md, DiffOfIncAndDec.java, input.txt, test_highVolumn.txt, test_highVolumn2.txt
Usage: Simply use terminal to 'javac DiffOfIncAndDec.java', then use 'java DiffOfIncAndDec'

## Requirments:
1. Given an array of N days and a window size K, find difference of number of increasing subarray and decreasing subarray, and print the answer, which should be exactly N-K+1 times.
2. 1 ≤ N ≤ 200,000, 1 ≤ K ≤ N, and the input file should always be valid
3. Solution should run in less than 30 seconds and use less than 50MB of memory

## Algorithm Design
### Consideration on constraints
Firstly, to satisfy the space constraint, let's say there are at most 200,000 numbers and 100,001 result, so if I use a list of size k to dynamically manage my window, it's possible that I need to hold 200,000 Integer object, which costs (16+4+4) = 24 bytes, per Integer object, times 200,000, which should be 4,800,000, about 5MB. So apparently it may exceed the constraint, so I use an int array to store these numbers, which is at most 24+4*200,000 = 800,024 bytes, way less than 5MB.
And in my second method, I also use an int array to store the factor of each index in the window, which makes another int array of size (n-k+1). In a word, using static int array to store numbers should satisfy the constraint.
Also, the garbage collection of java can clean the unused elements when the heap is full.

### Method 1: Naive Brute Force
The first method comes to my mind, is simply for each window, calculate the number of increasing subarray and the number of decreasing subarray seperately, then get the result.
So for each window, with a start index and an end index, for example, a window 'nums' of [1,2,3,2,1], starts from index 0 and ends at index 4, let variable i start from index 1. If nums[i] > nums[i-1], which means it is ascending, then a while loop keeps increasing i when ascending, which stops at index 3. This shows me that '1,2,3' is increasing, so 'i' has moved two steps, and there are [[1,2], [2,3], [1,2,3]] 3 increasing subarrays, which is 1 + 2 + ... + (steps of i). Suppose 'i' moves n steps, then there are (1+n)*n/2 increasing subarrays. Then start from index 3 we find the decreasing amounts and the logic is similar.
This algorithm can give the correct output, but it doesn't meet the time constraint for high volumn data like 200,000 numbers and window size of 100,000. After optimization, the time for high volumn can be at most 35 seconds, but still exceed the limit.
Time complexity: O(k * (n-k+1))

### Method 2: Cache Status
To have a faster approach, I find that actually I can store every factor of a number in the window to avoid repetitive calculation.
For example, I have [1,2,3,4,5] 5 numbers, and window size is 4, so first I calculate an initial result for window(1,2,3,4), then I delete 1 and add 5. Since '2,3,4' are still in the window, instead of calculating the entire window again, maybe I can minus the effect of 1 and add the effect of 5 on the result. The effect here means how many increasing subarray this number is in, minus the number of decreasing subarray with this number.
So for example [1,2,3,4,5] here, the '1' in is [[1,2], [1,2,3], [1,2,3,4]] 3 increasing subarrays and 0 decreasing subarrays, so remove '1' loses 3 ascending subarrays. Then a 5 will bring [[4,5], [3,4,5], [2,3,4,5]], also 3 ascending subarrays, which makes the total stay the same.
To do so, I use an int array 'dp', to store the current effect for this element. Everytime I add or delete an element in the window, it will generate a new dp value for the added one, and based on the effect of the add and delete, other elements' status in this window will also be updated.
Time complexity: For a worst case of [1,2,...200000] and window size 100000, it should be (k * (n-k+1)), but it saves time for other cases by avoiding a whole calculation for the window.
The worst case running time is 19 seconds. The time saved here is because of simpler incrementation instead of multiplication.

## Test Cases
- input.txt Test the standard example input
- test_highVolumn.txt Test high volumn input
- test_highVolumn2.txt Test an ordered high volumn input
