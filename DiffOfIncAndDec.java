import java.io.File;
import java.util.Scanner;

public class DiffOfIncAndDec {
	private static int[] nums; // store numbers
	private static int[] dp; // store status for each number
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		File f = new File("input.txt");
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (Exception e) {
		}
		int i = 0, n = 0, k = 0; // i -> current index, n -> nums size, k -> window size
		long result = 0;
		// get n and initialize nums
		if (scan.hasNext()) {
			n = Integer.parseInt(scan.next());
			nums = new int[n];
		}
		// get k and initialize dp
        if (scan.hasNext()){
            k = Integer.parseInt(scan.next());
            dp = new int[n-k+1];
        }
		while (scan.hasNext()) {
			nums[i] = Integer.parseInt(scan.next());
			// new window
			if (i < k-1) result += addNum(0,i);
			else if (i == k-1) {
				result += addNum(i-k+1, i);
				System.out.println(result); // result of first window
			}
			else {
				result += addNum(i-k+1, i);
				result -= removeNum(i-k, i-1);
				System.out.println(result);
			}
			++i;
		}
		scan.close();
		long t2 = System.currentTimeMillis();
		System.out.println("Program has executed for: " + (int)((t2 - t1) / 1000) + "    seconds    " + ((t2-t1) % 1000) + "    micro seconds");
	}
	private static int addNum(int start, int index) {
		int result = 0;
		if (index == start) return result;
		int i = index; // from the new add index to trace backward
		if (nums[i] > nums[i-1]) { // ascending
			while (i > start && nums[i] > nums[i-1]) {
				result++;
				i--;
			}
			for(int j = i; i < index && i < dp.length; i++) { // affect previous status
				dp[i] += (i-j+1);
			}
		}
		else if (nums[i] < nums[i-1]) { //descending
			while (i > start && nums[i] < nums[i-1]) {
				result--;
				i--;
			}
			for (int j = i; i < index && i < dp.length; i++) { // affect previous status
				dp[i] -= (i-j+1);
			}
		}
		if (index < dp.length) dp[index] = result;
		return result;
	}
	private static int removeNum(int index, int end) {
		int i = index+1; // from the first element in the new window forward
		if (nums[i] > nums[i-1]) {
			while (i <= end && nums[i] > nums[i-1]) {
				i++;
			}
			for (int j = index+1; j < i && j < dp.length; j++) {
				dp[j] -= (i-j);
			}
		}
		else if (nums[i] < nums[i-1]) {
			while (i <= end && nums[i] < nums[i-1]) {
				i++;
			}
			for (int j = index+1; j < i && j < dp.length; j++) {
				dp[j] += (i-j);
			}
		}
		return dp[index];
	}
}

