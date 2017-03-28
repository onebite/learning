package lynn.solution.leetcode;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeetCodeSolution {
	public static final int INTEGER_MAX_LENGTH = String.valueOf(Long.MAX_VALUE).length();

	/**Given an unsorted integer array, find the first missing positive integer.
	 * For example,
	 * Given [1,2,0] return 3,
	 * and [3,4,-1,1] return 2.
	 * Your algorithm should run in O(n) time and uses constant space
	 *
	 * @param nums
	 * @return
	 */
	public int firstMissingPositive(int[] nums) {
		if(nums == null || nums.length == 0)
			return 0;
		int missing = 1;
		for(int i=0;i < nums.length;i++){
			if(nums[i] <= 0)
				continue;

		}

		return missing;
	}
	/**
	 * Given a set of candidate numbers (C) (without duplicates) and a target number (T),
	 * find all unique combinations in C where the candidate numbers sums to T.
	 * @param candidates
	 * @param target
	 * @return
	 */
	public List<List<Integer>> combinationSum(int[] candidates, int target) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if(candidates == null || candidates.length == 0)
			return result;
		Arrays.sort(candidates);
		List<Integer> current = new ArrayList<Integer>();
		backTraceSum(result,current,candidates,target,-1);
		return result;
	}

	public boolean backTraceSum(List<List<Integer>> result,List<Integer> current,int[] candidates,int target,int index){
		if(target == 0){
			List<Integer> temp = new ArrayList<>(current);
			result.add(temp);
			return true;
		}
		int pre = -1;
		for(int i=index+1;i < candidates.length;i++){
			if(target < candidates[i])
				return false;
			if(pre == candidates[i])
				continue;
			current.add(candidates[i]);
			backTraceSum(result,current,candidates,target-candidates[i],i);
			current.remove(current.size()-1);
			pre = candidates[i];
		}

		return true;
	}

	/**
	 * 怎么确定第n行字符串呢？他的这个是有规律的。
	 * n = 1时，打印一个1。
	 * n = 2时，看n=1那一行，念：1个1，所以打印：11。
	 * n = 3时，看n=2那一行，念：2个1，所以打印：21。
	 * n = 4时，看n=3那一行，念：一个2一个1，所以打印：1211。
	 * @param n
	 * @return
	 */
	public String countAndSay(int n) {
		StringBuilder curr = new StringBuilder("1");
		StringBuilder pre;
		int rows;
		char say;
		for(int i=1;i < n;i++){
			pre = curr;
			curr = new StringBuilder();
			rows = 1;
			say = pre.charAt(0);
			for(int j = 1;j < pre.length();j++){
				if(pre.charAt(j) != say){
					curr.append(rows).append(say);
					rows = 1;
					say = pre.charAt(j);
				}
				else
					rows++;
			}
			curr.append(rows).append(say);
		}
		return curr.toString();
	}

	public void solveSudoku(char[][] board) {
		if(board == null || board.length == 0)
			return;
		backTracSolve(board);
	}

	public boolean backTracSolve(char[][] board){
		for(int row = 0;row < board.length;row++){
			for(int column = 0;column < board[0].length;column++){
				if(board[row][column] == '.'){
					for(char num='1';num <= '9';num++){
						board[row][column] = num;
						if(isSudokuValidNumber(board,row,column,num) && backTracSolve(board))
							return true;
						board[row][column] = '.';
					}
					return false;
				}
			}
		}

		return true;
	}

	public boolean isSudokuValidNumber(char[][] board,int row,int column,char c){
		for(int j = 0;j < 9;j++){
			if(j != column && board[row][j] == c)
				return false;
		}

		//check column
		for(int i = 0;i < 9;i++){
			if(i != row && board[i][column] == c)
				return false;
		}

		for(int i = row/3*3;i < row/3*3+3;i++){
			for(int j = column/3*3;j < column/3*3+3;j++){
				if(i !=row && j!= column && board[i][j] == c)
					return false;
			}
		}

		return true;
	}

	/**
	 *
	 * @param board
	 * @return
	 */
	public boolean isValidSudoku(char[][] board) {
		if(board == null || board.length == 0)
			return false;
		for(int i = 0;i < 9;i++){
			boolean[] numUsed = new boolean[9];
			for(int j = 0;j < 9;j++){
				if(isSudokuDuplicate(numUsed,board[i][j]))
					return false;
			}
		}

		for(int i = 0;i < 9;i++){
			boolean[] numUsed = new boolean[9];
			for(int j = 0;j < 9;j++){
				if(isSudokuDuplicate(numUsed,board[j][i]))
					return false;
			}
		}

		for(int i = 0; i < 9;){
			for(int j = 0; j < 9;){
				if(isSudokuDuplicatedBox(board,i,j))
					return false;
				j = j+3;
			}
			i = i + 3;
		}

		return true;
	}

	public boolean isSudokuDuplicatedBox(char[][] board,int x,int y){
		boolean[] numUsed = new boolean[9];
		for(int i = x;i < x+3;i++){
			for(int j = y;j < y+3;j++){
				if(isSudokuDuplicate(numUsed,board[i][j]))
					return true;
			}
		}
		return false;
	}

	public boolean isSudokuDuplicate(boolean[] numUsed,char c){
		if(c == '.')
			return false;
		else if(numUsed[c - '1'])
			return true;
		else
			numUsed[c - '1'] = true;
		return false;
	}
	/**Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.
	 * You may assume no duplicates in the array.
	 * Here are few examples.
	 * [1,3,5,6], 5 → 2
	 * [1,3,5,6], 2 → 1
	 * @param nums
	 * @param target
	 * @return
	 */
	public int searchInsert(int[] nums, int target) {
		if(nums == null || nums.length == 0)
			return 0;
		int left = 0,mid = 0;
		int right = nums.length - 1;
		while(left <= right){
			mid = (left+right)/2;
			if(nums[mid] == target)
				return mid;
			if(nums[mid] > target){
				right = mid - 1;
			}
			if(nums[mid] < target){
				left = mid + 1;
			}
		}
		return left;
	}

	/**
	 * Given an array of integers sorted in ascending order, find the starting and ending position of a given target value.
	 * Your algorithm's runtime complexity must be in the order of O(log n).
	 * If the target is not found in the array, return [-1, -1].
	 * For example,
	 * Given [5, 7, 7, 8, 8, 10] and target value 8,
	 * return [3, 4].
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] searchRange(int[] nums, int target) {
		int[] result = new int[]{-1,-1};
		if(nums == null || nums.length == 0)
			return result;
		int left = 0,mid = 0;
		int right = nums.length - 1;
		while(left <= right){
			mid = (left + right)/2;
			if(nums[mid] == target){
				int offset = 0;
				while(mid-offset >= left && nums[mid-offset] == target){
					offset++;
				}
				result[0] = mid - offset + 1;
				if(!(mid+offset <= right && nums[mid+offset] == target))
					offset = 0;
				while(mid+offset <= right && nums[mid + offset] == target){
					offset++;
				}
				result[1] = mid + offset - 1;
				break;
			}
			if(nums[mid] < target){
				left = mid + 1;
			}
			if(nums[mid] > target)
				right = mid - 1;
		}

		return result;
	}

	/**
	 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
	 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
	 * You are given a target value to search. If found in the array return its index, otherwise return -1.
	 * You may assume no duplicate exists in the array.
	 * @param nums
	 * @param target
	 * @return
	 */
	public int search(int[] nums, int target) {
		if(nums == null || nums.length == 0)
			return -1;
		int left = 0,right = nums.length - 1;
		int mid = 0;
		while(left <= right){
			mid = (left + right)/2;
			if(nums[mid] == target)
				return  mid;
			if(nums[mid] > nums[right]){
				if(nums[left] <= target && target < nums[mid])
					right = mid -1;
				else
					left = mid + 1;
			}
			else{
				if(nums[mid] < target && target <= nums[right])
					left = mid + 1;
				else
					right = mid - 1;
			}
		}

		return -1;
	}

	/**Given a string containing just the characters '(' and ')',
	 * find the length of the longest valid (well-formed) parentheses substring.
	 * For "(()", the longest valid parentheses substring is "()", which has length = 2.
	 * Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4.
	 *
	 * @param s
	 * @return
	 */
	public int longestValidParentheses2(String s) {
		int[] dp = new int[s.length()];
		int max = 0;
		for(int i = s.length()-2;i >= 0;i--){
			if(s.charAt(i) == '('){
				int end = i + dp[i+1] + 1;
				if(end < s.length() && s.charAt(end) == ')'){
					dp[i] = dp[i+1] + 2;
					if(end+1 < s.length())
						dp[i] += dp[end+1];
				}

				max = Math.max(max,dp[i]);
			}
		}
		return max;
	}

	/**Given a string containing just the characters '(' and ')',
	 * find the length of the longest valid (well-formed) parentheses substring.
	 * For "(()", the longest valid parentheses substring is "()", which has length = 2.
	 * Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4.
	 *
	 * @param s
	 * @return
	 */
	public int longestValidParentheses(String s) {
		Stack<Integer> stack = new Stack<>();
		int max = 0,start = 0;
		char ch = '*';
		for(int i = 0;i < s.length();++i){
			ch = s.charAt(i);
			if(ch == '(')
				stack.push(i);
			else if(ch == ')'){
				if(stack.isEmpty())
					start = i + 1;
				else{
					stack.pop();
					max = stack.isEmpty() ? Math.max(max,i-start+1):Math.max(max,i-stack.peek());
				}
			}
		}
		return max;
	}

	public void nextPermutation(int[] nums) {
		int pos = -1;
		for(int i = nums.length - 1;i > 0;i--){
			if(nums[i] > nums[i-1]){
				pos = i - 1;
				break;
			}
		}
		if(pos < 0){
			reverse(nums,0,nums.length-1);
			return;
		}
		for(int i=nums.length -1; i > pos;i--){
			if(nums[i] > nums[pos]){
				int temp = nums[i];
				nums[i] = nums[pos];
				nums[pos] = temp;
				break;
			}
		}
		reverse(nums,pos+1,nums.length-1);
	}

	public void reverse(int[] nums,int start,int end){
		int l = start;
		int r = end;
		while(l<r){
			int temp = nums[l];
			nums[l] = nums[r];
			nums[r] = temp;
			l++;
			r--;
		}
	}

	/**You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.
	 * For example, given:
	 * s: "barfoothefoobarman"
	 * words: ["foo", "bar"]
	 * You should return the indices: [0,9].
	 *
	 * @param s
	 * @param words
	 * @return
	 */
	public List<Integer> findSubstring(String s, String[] words) {
		List<Integer> indexs = new ArrayList<>();
		if(words == null || words.length == 0 || s == null | s.length() == 0)
			return indexs;
		Map<String,Integer> wordCounts = new HashMap<>(words.length);
		int num = words.length;
		int wlen = words[0].length();
		int sublen = num * wlen;
		int index = 0;
		int point = 0;
		for(int i=0;i< words.length;i++){
			if(wordCounts.containsKey(words[i]))
				wordCounts.put(words[i],wordCounts.get(words[i])+1);
			else
				wordCounts.put(words[i],1);
		}

		for(;index < s.length() - sublen + 1;index++) {
			Map<String, Integer> findingMap = new HashMap<>(wordCounts);
			for (point = index; point + wlen <= s.length(); ) {
				String str = s.substring(point, point + wlen);
				if (findingMap.containsKey(str)) {
					int count = findingMap.get(str);
					if (count == 1)
						findingMap.remove(str);
					else
						findingMap.put(str, count - 1);
					if (findingMap.isEmpty()) {
						indexs.add(index);
						break;
					}
				} else {
					break;
				}
				point = point + wlen;
			}
		}
		return indexs;
	}


	/**You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.
	 * For example, given:
	 * s: "barfoothefoobarman"
	 * words: ["foo", "bar"]
	 * You should return the indices: [0,9].
	 *
	 * @param s
	 * @param words
	 * @return
	 */
	public List<Integer> findSubstring2(String s, String[] words) {
		List<Integer> indexs = new ArrayList<>(words.length);
		if(words == null || words.length == 0 || s == null | s.length() == 0)
			return indexs;
		Map<String,Integer> wordCounts = new HashMap<>(words.length);
		int num = words.length;
		int wlen = words[0].length();
		int sublen = num * wlen;
		int index = 0;
		int point = 0;
		boolean unChanged = true;

		for(int i=0;i< words.length;i++){
			if(wordCounts.containsKey(words[i]))
				wordCounts.put(words[i],wordCounts.get(words[i])+1);
			else
				wordCounts.put(words[i],1);
		}

		for(;index < s.length() - sublen + 1;index++){
			if(!unChanged){
				wordCounts.clear();
				for(int i=0;i< words.length;i++){
					if(wordCounts.containsKey(words[i]))
						wordCounts.put(words[i],wordCounts.get(words[i])+1);
					else
						wordCounts.put(words[i],1);
				}
				num = words.length;
				unChanged = true;
			}
			String str = s.substring(index,index+wlen);
			point = index;
			while(wordCounts.containsKey(str) && wordCounts.get(str) != 0 && point+wlen <= s.length()){
				wordCounts.put(str,wordCounts.get(str)-1);
				num--;
				unChanged = false;
				point = point + wlen;
				if(num == 0)
					break;
				str = s.substring(point,point+wlen);
				if(!wordCounts.containsKey(str))
					break;
			}

			if(num == 0){
				indexs.add(index);
			}
		}

		return indexs;
	}

	/**You are given a string, s, and a list of words, words, that are all of the same length. Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once and without any intervening characters.
	 * For example, given:
	 * s: "barfoothefoobarman"
	 * words: ["foo", "bar"]
	 * You should return the indices: [0,9].
	 *
	 * @param s
	 * @param words
	 * @return
	 */
	public List<Integer> findSubstring1(String s, String[] words) {
		List<Integer> indexs = new ArrayList<>();
		if(words == null || words.length == 0 || s == null | s.length() == 0)
			return indexs;
		Map<String,Integer> strMap = new HashMap<>(words.length);
		int index = 0;
		while(index < s.length() - 1){
			int found = 0;
			for(int i = 0; i < words.length;i++){
				if(strMap.containsKey(words[i])){
					found++;
					continue;
				}
				if(i+words[i].length() >= s.length())
					continue;
				String str = s.substring(index,index+words[i].length());
				if(str.equals(words[i]))
					strMap.put(words[i],index);
			}
			if(found == words.length)
				break;
			index++;
		}
		for(Map.Entry<String,Integer> entry:strMap.entrySet())
			indexs.add(entry.getValue());

		return indexs;
	}

	public int divide(int dividend, int divisor) {
		if(divisor == 0)
			return Integer.MAX_VALUE;
		if(dividend == 0)
			return 0;
		boolean neg = (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0);
		long a = Math.abs((long)dividend);
		long b = Math.abs((long)divisor);
		if(b > a || a == 0)
			return 0;
		long lans = ldivide(a,b);
		if(lans > Integer.MAX_VALUE)
			return neg ? Integer.MIN_VALUE:Integer.MAX_VALUE;
		int ans = (int) lans;
		return neg ? -ans:ans;
	}

	public long ldivide(long ldividend,long ldivisor ){
		if(ldividend < ldivisor)
			return 0;
		long multipe = 1;
		long sum = ldivisor;
		while(sum + sum <= ldividend){
			sum += sum;
			multipe += multipe;
		}

		return multipe + ldivide(ldividend-sum,ldivisor);
	}

	public int strStr(String haystack, String needle) {
		if(haystack == null || needle == null ||needle.length() > haystack.length())
			return -1;
		if(needle.length() == 0)
			return 0;

		for(int i=0;i <= haystack.length() - needle.length();i++){
			int p = 0;
			while(haystack.charAt(i+p) == needle.charAt(p)) {
				if(p == needle.length()-1)
					return i;
				p++;
			}
		}

		return -1;
	}


	/**Given an array and a value, remove all instances of that value in place and return the new length.
	 * Do not allocate extra space for another array, you must do this in place with constant memory.
	 * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
	 * Example:
	 * Given input array nums = [3,2,2,3], val = 3
	 * Your function should return length = 2, with the first two elements of nums being 2.
	 *
	 * @param nums
	 * @param val
	 * @return
	 */
	public int removeElement(int[] nums, int val) {
		if(nums.length == 0)
			return 0;
		int i = nums.length - 1;
		for(int j = 0;i >= j;){
			while(i>j && nums[i] == val){
				i--;
			}
			if(nums[j] == val){
				nums[j] = nums[i];
				i--;
			}
			j++;
		}

		return ++i;
	}

	public int removeDuplicates2(int[] nums) {
		if(nums.length == 0)
			return 0;
		int j = 0;
		for(int i=0;i < nums.length;i++){
			if(nums[i] != nums[j])
				nums[++j] = nums[i];
		}

		return ++j;
	}

	/**
	 * [1,1,2]  => [1,2,2] len=2
 	 * @param nums
	 * @return
	 */
	public int removeDuplicates(int[] nums) {
		int len = nums.length;
		int j = 0;
		for(int i=0;i < nums.length - 1;){
			if(nums[i] == nums[i+1]){
				len--;
			}
			else{
				j++;
			}
			i++;

			if(j != i && nums[j] != nums[i]){
				nums[j] = nums[i];
			}
		}

		return len;
	}

	/**
	 *Given this linked list: 1->2->3->4->5
	 * For k = 2, you should return: 2->1->4->3->5
	 * For k = 3, you should return: 3->2->1->4->5
	 * @param head
	 * @param k
	 * @return
	 */
	public ListNode reverseKGroup(ListNode head, int k) {
		if(k < 2 || head == null || head.next == null)
			return  head;
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		ListNode current = dummy;
		while(current.next != null){
			int num = k-2;
			ListNode  first = current.next;
			ListNode second = current.next;
			while(num-- > 0){
				if(second == null||second.next == null)
					return dummy.next;
				second = second.next;
			}
			ListNode pre = second;
			second = second.next;
			ListNode temp = second.next;

			second.next.next = first.next;
			current.next = second.next;
			second.next = first;
			first.next = temp;
			current = first;
		}

		return dummy.next;
	}
    /**
     * Given a linked list, swap every two adjacent nodes and return its head.
     * For example,
     * Given 1->2->3->4, you should return the list as 2->1->4->3.
     * Your algorithm should use only constant space. You may not modify the values in the list, only nodes itself can be changed.
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode pre = null;
        ListNode cur = head;
        ListNode next = head.next;
        while(cur != null && next != null){
			cur.next = next.next;
			next.next = cur;
			if(pre != null){
				pre.next = next;
			}else{
				pre = next;
				head = pre;
			}

			if(cur.next == null || cur.next.next == null)
				break;
			next = cur.next.next;
			pre = cur;
			cur = cur.next;
        }

        return head;
    }

    public String reverse(String s){
    	return new StringBuilder(s).reverse().toString();
	}

	public String reverse2(String s){
    	char[] chs = s.toCharArray();
    	int end = chs.length-1;
    	for(int start=0;start < end;start++,end--){
    		char temp = chs[start];
    		chs[start] = chs[end];
    		chs[end] = temp;
		}

		return new String(chs);
	}

	public String reverse3(String s){
		byte[] sbyte = s.getBytes();
		byte[] dbyte = new byte[sbyte.length];
		for(int i=sbyte.length-1,j=0;i > -1;i--,j++){
			dbyte[j] = sbyte[i];
		}

		return new String(dbyte);
	}

    public ListNode mergeKLists3(ListNode[] lists) {
        if(lists == null || lists.length == 0)
            return null;

        int len = lists.length;
        while(len > 1){
            int mid = (len+1) / 2;
            for(int i=0;i < len/2;i++){
                lists[i] = mergeTwoLists(lists[i],lists[i+mid]);
                lists[i+mid] = null;
            }
            len = mid;
        }

        return lists[0];
    }

    public ListNode mergeKLists2(ListNode[] lists) {
        if(lists == null || lists.length == 0)
            return null;
        if(lists.length == 1)
            return lists[0];

        PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(lists.length, new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                if(o1.val < o2.val)
                    return -1;
                if(o1.val == o2.val)
                    return 0;

                return 1;
            }
        });
        for(int i=0;i < lists.length;i++){
            if(lists[i] != null){
                queue.add(lists[i]);
            }
        }

        ListNode ret = new ListNode(-1);
        ListNode p = ret;
        while(!queue.isEmpty()){
            p.next = queue.poll();
            p = p.next;

            if(p.next != null)
                queue.add(p.next);
        }
        return ret.next;
    }

    /**
     * Merge k sorted linked lists and return it as one sorted list.
     * Analyze and describe its complexity.
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists == null || lists.length == 0)
            return null;
        if(lists.length == 1)
            return lists[0];

        ListNode ret = new ListNode(-1);
        ListNode p0 = ret;
        while(true){
            int index = 0;
            for(int i=0;i < lists.length;i++){
                if(lists[i] == null)
                    continue;
                if(lists[index] == null || lists[i] != null && lists[i].val < lists[index].val){
                    index = i;
                }
            }
            p0.next = lists[index];
            if(lists[index] != null)
                lists[index] = lists[index].next;
            else
                break;
            p0 = p0.next;
        }

        return ret.next;
    }
    /**
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses
     * 针对一个长度为2n的合法排列，第1到2n个位置都满足如下规则：左括号的个数大于等于右括号的个数
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<String>();
        generate(result,"",n+n,n,n);
        return result;
    }

    public void generate(List<String> result,String s, int length,int leftNum,int rightNum){
        if(leftNum == rightNum)
            if(s.length() == length)
                result.add(s);
        if(leftNum > 0){
            generate(result,s+"(",length,leftNum-1,rightNum);
        }
        if(rightNum > 0 && leftNum < rightNum)
            generate(result,s+")",length,leftNum,rightNum-1);
    }
    /**
     * Given a string containing just the characters '(', ')', '{', '}', '[' and ']',
     * determine if the input string is valid.
     * The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if(s == null || s.length() <2)
            return false;

        LinkedList<Character> stack = new LinkedList<Character>();
        Character temp = null;
        for(int i = 0;i < s.length();i++){
            char ch = s.charAt(i);
            if(ch == '(' || ch == '[' || ch == '{')
                stack.push(ch);

            if(stack.isEmpty())
                return false;

            if(ch == '}'){
                temp = stack.pop();
                if(temp != '{')
                    return false;
            }
            if(ch == ')'){
                temp = stack.pop();
                if(temp != '(')
                    return false;
            }
            if(ch == ']'){
                temp = stack.pop();
                if(temp != '[')
                    return false;
            }
        }

        if(stack.isEmpty())
            return true;

        return false;
    }
    /**
     * Given a linked list, remove the nth node from the end of list and return its head.
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if(n <= 0 || head == null || head.next == null)
            return  null ;
        ListNode p = head;
        ListNode nth = p;
        for(int i=0;i < n && p != null;i++)
            p = p.next;
        if(p == null)
            return head.next;
        while(p.next != null){
            nth = nth.next;
            p = p.next;
        }

        nth.next = nth.next.next;
        return head;
    }

	/**
	 * Given an array S of n integers, are there elements a, b, c,
	 * and d in S such that a + b + c + d = target?
	 * Find all unique quadruplets in the array which gives the sum of target.
	 * @param nums
	 * @param target
	 * @return
	 */
	public List<List<Integer>> fourSum(int[] nums, int target) {
		List<List<Integer>> ret = new ArrayList<List<Integer>>();
		if(nums.length < 4)
			return ret;
		Arrays.sort(nums);
		for(int i = 0;i < nums.length-3;i++){
			if(i == 0 || nums[i] > nums[i-1]){
				for(int j = i+1;j < nums.length-2;j++){
					if((j == i+1 || nums[j] > nums[j-1])){
						int l = j + 1;
						int r = nums.length - 1;
						while (l < r){
							int sum = nums[i] + nums[j] + nums[l] + nums[r];
							if(sum == target){
								List<Integer> lst = new ArrayList<Integer>();
								lst.add(nums[i]);
								lst.add(nums[j]);
								lst.add(nums[l]);
								lst.add(nums[r]);
								ret.add(lst);
								l++;
								r--;
								while(l < r && nums[l] == nums[l-1])
									l++;
								while (l < r && nums[r] == nums[r+1])
									r--;
							}

							if(sum < target)
								l++;
							if(sum > target)
								r--;
						}
					}
				}
			}

		}

		return ret;
	}

	/**
	 * Given a digit string, return all possible letter combinations that the number could represent.
	 * A mapping of digit to letters (just like on the telephone buttons) is given below.
	 * @param digits
	 * @return
	 */
	public List<String> letterCombinations(String digits) {
		List<String> ret = new ArrayList<String>();
		if(digits == null || digits.length() == 0)
			return ret;

		String[] dicts = new String[]{"abc","def","ghi","jkl","mno","pqrs", "tuv","wxyz"};
		LinkedList<String> l = new LinkedList<String>();
		l.add("");
		for(int i = 0;i < digits.length();i++){
			int  index = digits.charAt(i) - '2';
			int size = l.size();
			for(int k = 0;k < size;k++){
				String temp = l.pop();
				for(int j=0; j < dicts[index].length();j++)
					l.add(temp+dicts[index].charAt(j));
			}
		}

		ret.addAll(l);
		return ret;
	}

	/**
	 * Given an array S of n integers,
	 * find three integers in S such that the sum is closest to a given number, target.
	 * Return the sum of the three integers. You may assume that each input would have exactly one solution
	 * @param nums
	 * @param target
	 * @return
	 */
	public int threeSumClosest(int[] nums, int target) {
		if(nums.length < 3){
			return 0;
		}
		int closest = target;
		int min = Integer.MAX_VALUE;
		Arrays.sort(nums);
		for(int i=0;i < nums.length - 2;i++){
			if(i == 0 || nums[i] > nums[i-1]){
				int j = i+1;
				int k = nums.length - 1;
				while(j < k){
					int sum = nums[i] + nums[j] + nums[k];
					if(sum == target)
						return sum;
					int diff = Math.abs(target - sum);
					if(min > diff) {
						closest = sum;
						min = diff;
					}
					if(sum < target)
						j++;
					if(sum > target)
						k--;
				}
			}
		}

		return closest;
	}
	/**Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0?
	 * Find all unique triplets in the array which gives the sum of zero.
	 *
	 * @param nums
	 * @return
	 */
	public List<List<Integer>> threeSum2(int[] nums) {
		List<List<Integer>> ret = new ArrayList<List<Integer>>();
		if(nums.length < 3){
			return ret;
		}
		Arrays.sort(nums);
		for(int i=0;i < nums.length - 2;i++){
			if(i == 0 || nums[i] > nums[i-1]){
				int j = i+1;
				int k = nums.length - 1;
				while(j < k){
					if(nums[i] + nums[j] + nums[k] == 0) {
						List<Integer> l = new ArrayList<Integer>();
						l.add(nums[i]);
						l.add(nums[j]);
						l.add(nums[k]);
						ret.add(l);
						j++;
						k--;
						while (j < k && nums[j] == nums[j - 1])
							j++;
						while (j < k && nums[k] == nums[k + 1])
							k--;
					}
					if(nums[i] + nums[j] + nums[k] < 0)
						j++;
					if(nums[i] + nums[j] + nums[k] > 0)
						k--;
				}
			}
		}

		return ret;
	}
	/**
	 *
	 * @param nums
	 * @param start
	 * @param end
	 * @param target
	 * @return
	 */
	public List<int[]> twoSum(int[] nums,int start ,int end, int target){
		Map<Integer,Integer> numbers = new HashMap<Integer,Integer>();
		List<int[]> retList = new ArrayList<int[]>();
		int[] ret = new int[2];
		for(int i=start;i <= end;i++){
			Integer index = numbers.get(nums[i]);
			if(index == null)
				numbers.put(nums[i], i);

			index = numbers.get(target - nums[i]);
			if(index != null && index < i){
				ret[0] = index;
				ret[1] = i;
				retList.add(ret);
			}
		}

		return retList;
	}

    /**Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0?
     * Find all unique triplets in the array which gives the sum of zero.
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
    	List<List<Integer>> ret = new ArrayList<List<Integer>>();
        if(nums.length < 3){
        	return ret;
		}
		for(int i = 0;i < nums.length -2;i++){
        	List<int[]> temp = this.twoSum(nums,i+1,nums.length-1,0-nums[i]);
        	if(temp.size() > 0){
        		for(int[] idxs:temp){
					List<Integer>  vals = new ArrayList<Integer>();
        			vals.add(nums[idxs[0]]);
					vals.add(nums[idxs[1]]);
					vals.add(nums[i]);
					ret.add(vals);
				}
			}
		}

		return  ret;
    }
	/**
	 *
	 * @param strs
	 * @return
	 */
	public String longestCommonPrefix2(String[] strs) {
		if(strs == null || strs.length == 0)
			return "";
		if(strs.length == 1)
			return strs[0];

		int index = 0;
		while (true){
			for(int i=1;i < strs.length;i++){
				if(strs[i].length() <= index || strs[i-1].length() <= index
						|| strs[i-1].charAt(index) != strs[i].charAt(index))
					return index > 0 ? strs[i].substring(0,index):"";
			}
			index++;
		}
	}

	/**
	 *
	 * @param strs
	 * @return
	 */
	public String longestCommonPrefix(String[] strs) {
		if(strs == null || strs.length == 0)
			return "";
		Character ch = null;
		int index = 0;
		while (true){
			for(String str:strs){
				if(ch == null && str.length() > index)
					ch = str.charAt(index);
				if(str.length() <= index || ch != str.charAt(index))
					return index > 0 ? str.substring(0,index):"";
			}
			ch = null;
			index++;
		}
	}

	/**
	 *
	 * @param s
	 * @return
	 */
	public int romanToInt(String s) {
		if(s == null || s.isEmpty())
			return 0;
		Map<Character,Integer> roman = new HashMap<Character,Integer>();
		roman.put('I',1);
		roman.put('V',5);
		roman.put('X',10);
		roman.put('L',50);
		roman.put('C',100);
		roman.put('D',500);
		roman.put('M',1000);
		int ret = roman.get(s.charAt(0));
		for(int i=1;i < s.length();i++){
			if(roman.get(s.charAt(i-1)) < roman.get(s.charAt(i)) ){
				ret += roman.get(s.charAt(i)) - 2 * roman.get(s.charAt(i-1));
			}
			else{
				ret += roman.get(s.charAt(i));
			}
		}

		return ret;
	}
	/**
	 * Given an integer, convert it to a roman numeral.
	 * Input is guaranteed to be within the range from 1 to 3999.
	 * 1~9: {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
	 * 10~90: {"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
	 * 100~900: {"C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
	 * 1000~3000: {"M", "MM", "MMM"}.
	 * @param num
	 * @return
	 */
	public String intToRoman(int num) {
		String[][] roman = {{"","I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
				{"","X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
				{"","C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
				{"","M", "MM", "MMM"}};
		int base = 0;
		String ret = "";
		while(num != 0){
			int remaining = num % 10;
			ret = roman[base][remaining] + ret;
			base++;
			num /= 10;
		}

		return ret;
	}
	/**
	 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together with x-axis forms a container, such that the container contains the most water.
	 * Note: You may not slant the container and n is at least 2.
	 * @param height
	 * @return
	 */
	public int maxArea(int[] height) {
		int maxArea = 0;
		int j = height.length - 1;
		int i = 0;
		if(j <= 0)
			return 0;

		while(i < j){
			int area = Math.min(height[i],height[j])*(j-i);
			if(height[i] < height[j])
				i++;
			else
				j--;

			if(area > maxArea)
				maxArea = area;

		}

		return maxArea;
	}
	/**
	 * Implement regular expression matching with support for '.' and '*'.
	 * '.' Matches any single character.
	 * '*' Matches zero or more of the preceding element.
	 * The matching should cover the entire input string (not partial).
	 * isMatch("aab", "c*a*b") → true
	 * @param s
	 * @param p
	 * @return
	 */
	public boolean isMathWithDp(String s, String p){
		if(s == null || p == null)
			return false;
		int slen = s.length();
		int plen = p.length();
		boolean[][] res = new boolean[slen+1][plen+1];
		res[0][0] =true;
		for(int i=0;i < slen+1;i++){
			for(int j=1;j < plen+1;j++){
				if(p.charAt(j-1) == '*')
					res[i][j] = res[i][j-2] || (i > 0 && (s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.'))
							&&  res[i-1][j];
				else
					res[i][j] = i > 0 && res[i-1][j-1] && (s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '.');
			}
		}

		return res[slen][plen];
	}
	/**
	 * Implement regular expression matching with support for '.' and '*'.
	 * '.' Matches any single character.
	 * '*' Matches zero or more of the preceding element.
	 * The matching should cover the entire input string (not partial).
	 * isMatch("aab", "c*a*b") → true
	 * @param s
	 * @param p
	 * @return
	 */
	public boolean isMatch(String s, String p) {
		if(s == null || p == null)
			return false;
		if(p.isEmpty())
			return s.isEmpty();

		if(p.length() > 1 && p.charAt(1) == '*'){
			return isMatch(s,p.substring(2)) || (!s.isEmpty() && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.')
			&& isMatch(s.substring(1),p));
		}
		else{
			return !s.isEmpty() && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.') && isMatch(s.substring(1),p.substring(1));
		}

	}

	/**
	 * Determine whether an integer is a palindrome. Do this without extra space.
	 * click to show spoilers.
	 * Some hints:
	 * Could negative integers be palindromes? (ie, -1)
	 * If you are thinking of converting the integer to string, note the restriction of using extra space.
	 * You could also try reversing an integer. However, if you have solved the problem "Reverse Integer", you know that the reversed integer might overflow. How would you handle such case?
	 * There is a more generic way of solving this problem.
	 * @param x
	 * @return
	 */
	public boolean isPalindrome(int x) {
		if(x < 0)
			return false;
		if(x <= 9)
			return true;

		int base = 1;
		while(x/base >= 10)
			base *= 10;

		while(x > 0){
			int left = x / base;
			int right = x % 10;
			if(left != right)
				return false;
			x -= left*base;
			base /= 100;
			x /= 10;
		}

		return true;
	}

	public boolean isNumber(String str){
		if(str == null ||str.equals("")  ){
			return false;
		}

		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if(isNum.matches()){
			return true;
		}
		return false;
	}

	public boolean isNumberWithDigit(String str){
		if(str == null || str.isEmpty())
			return false;
		if(str.length() >= INTEGER_MAX_LENGTH){
			try{
				Integer.parseInt(str);
			}catch (Exception e){
				return false;
			}
		}
		else{
			int i = 0;
			if(str.charAt(0) == '-'){
				if(str.length() > 1)
					i++;
				else
					return false;
			}
			for(;i<str.length();i++){
				if(!Character.isDigit(str.charAt(i)))
					return false;
			}
		}

		return true;
	}
	/**
	 *Given a string s, find the longest palindromic substring in s.
	 * You may assume that the maximum length of s is 1000.
	 * @param s
	 * @return
	 */
	public String longestPalindrome(String s) {
		if(s == null || s.isEmpty()||s.length() == 1)
			return s;
		char[] chars = new char[2*s.length()+3];
		chars[0] = '^';
		for(int i=0;i<s.length();i++){
			chars[2*i+1] = '#';
			chars[2*i+2] = s.charAt(i);
		}
		chars[chars.length-2] = '#';
		chars[chars.length-1] = '$';
		int[] p = new int[chars.length];
		String max = "";
		int id = 0;
		int mx = 0;
		for(int i=1;i<chars.length -1;i++){
			if(i < mx)
				p[i] = Math.min(p[2*id-i],mx-i);
			else
				p[i] = 1;

			while (chars[i-p[i]] == chars[i+p[i]])
				p[i]++;
			if(mx < i+p[i]){
				id = i;
				mx = i + p[i];
			}
			if(max.length() < p[i] - 1){
				int start = (i-p[i]+2)/2 - 1;
				int end = (i+p[i])/2 - 1;
				max = s.substring(start,end);
			}

		}
		return max;

	}
	/**
	 * Median of Two Sorted Arrays Add to List
	 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
	 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
	 * @param nums1
	 * @param nums2
	 * @return
	 */
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int lens = nums1.length + nums2.length;
        if(lens == 0)
        	return 0;
        
        int medianIndex = lens / 2;
        double pre = 0;
        double median = 0;
        int i = 0;
        int j1 = 0;
        int j2 = 0;
        
        while(i <= medianIndex){
        	i++;
        	pre = median;
        	if(j1 >= nums1.length && j2 >= nums2.length){
        		break;
        	}
        	if(j1 >= nums1.length ){
        		median = nums2[j2];
        		j2++;
        		continue;
        	}
        	if(j2 >= nums2.length){
        		median = nums1[j1];
        		j1++;
        		continue;
        	}
        	if(nums1[j1] > nums2[j2]){
        		median = nums2[j2];
        		j2++;
        		continue;
        	}
        	
        	median = nums1[j1];
    		j1++;  	
        }
        
        if(lens%2 == 0){
        	return (double) (pre + median)/2;
        }
       
        return median;
    } 
	
	/** ZigZag Conversion
	 * convert("PAYPALISHIRING", 3) should return "PAHNAPLSIIGYIR". 
	 * @param s
	 * @param numRows
	 * @return
	 */
	public String convert(String s, int numRows) {
        if(numRows == 1 || s == null) return s;
        if(numRows >= s.length()) return s;
        
        //String[] res;
        String[] res = new String[numRows];
        int gap = numRows - 2;
        int i = 0;
        int j = 0;
        
        for(int k=0;k<numRows;k++){
        	res[k] = "";
        }
        
        while(i < s.length()){
        	for(j=0;i<s.length()&&j < numRows;++j){
        		res[j] += s.charAt(i++);
        	}
        	for(j=gap;i<s.length()&& j > 0; --j){
        		res[j] += s.charAt(i++);
        	}
        }
        
        String str = "";
        for(i=0;i<numRows;i++){
        	str += res[i];
        }
        
        return str;
    }
	
	public int myAtoi2(String str) {
        if(str == null || str.length() == 0)
        	return 0;
        
        int sign = str.charAt(0) == '-' ? -1:1;
        int cur = 0;
        int result = 0;
        boolean once = false;
        
        while(cur < str.length()){
        	//溢出：(int)(str.charAt(cur) - '0') 有可能为负数
//        	if(result > (Integer.MAX_VALUE - (int)(str.charAt(cur) - '0'))/10)
//        		return 0;
        	
        	if(str.charAt(cur) >= '0' && str.charAt(cur) <= '9'){
        		
        		if(result >= Integer.MAX_VALUE/10 - (int)(str.charAt(cur) - '0')/10 && sign==1){
					return Integer.MAX_VALUE;
				}

				if(result >= Integer.MAX_VALUE/10 && (int)(str.charAt(cur) - '0') >= 8  && sign==-1){
					return Integer.MIN_VALUE;
				}
        		
        		result = result*10 + (int)(str.charAt(cur) - '0');
        	}
        	else{
        		if(once || (str.charAt(cur) != '+' && str.charAt(cur) != '-' && str.charAt(cur) != ' ') || ( str.charAt(cur) == ' '&& result != 0))
        			return sign*result;
        		
        		if(str.charAt(cur) == '+' && !once){
        			sign=1;
        			once = true;
        		}
        		
        		if(str.charAt(cur) == '-' && !once){
        			sign = -1;
        			once = true;
        		}
        	}
        	
        	cur++;
        	
        }
        
        return sign*result;
    }
	
	/**
	 * String to Integer (atoi) 
	 * @param str
	 * @return
	 */
	public int myAtoi(String str) {
        if(str == null || str.length() == 0)
        	return 0;
        
        int sign = 1;
        int cur = 0;
        int result = 0;
        while(cur < str.length()){
        	//remove no digit starting
        	while(str.charAt(cur) <= '0' || str.charAt(cur) > '9'){
        		if(str.charAt(cur) == '-'){
        			sign = -1;
        		}
        		
        		cur++;
        		
        		if(cur >= str.length())
        			return 0;
        	}
        	
        	if(result > (Integer.MAX_VALUE - (int)(str.charAt(cur) - '0'))/10)
        		return 0;
        	
        	if(str.charAt(cur) >= '0' && str.charAt(cur) <= '9'){
        		result = result*10 + (int)(str.charAt(cur) - '0');
        	}
        	
        	cur++;
        	
        }
        
        return sign*result;
    }
	
	/**
	 * reverse integer
	 * Example2: x = -123, return -321 
	 * @param x
	 * @return
	 */
	public int reverse(int x) {
        if(x == Integer.MIN_VALUE)
        	return 0;
        
        int num = Math.abs(x);
        int res = 0;
        while(num != 0){
        	if(res > (Integer.MAX_VALUE - num%10)/10)
        		return 0;
        	
        	res = res*10 + num%10;
        	num /= 10;
        }
        
        return x > 0 ? res:-res;
    }
	
	public int lengthOfLongestSubstring2(String s) {
		if(s == null || s.isEmpty())
			return 0;
		
		if(s.length() == 1){
			return 1;
		}
		
        boolean[] map = new boolean[256];
        int result = 0;
        int start = 0;
        
        for(int i=0;i<s.length();i++){
        	char cur = s.charAt(i);
        	if(map[cur]){
        		result = Math.max(result, i - start);
        		for(int k=start;k<i;k++){
        			if(s.charAt(k) == cur){
        				start = k + 1;
        				break;
        			}
        			
        			map[s.charAt(k)] = false;
        		}
        	}
        	else{
        		map[cur] = true;
        	}
        }
        
        result = Math.max(result, s.length() - start );
        
        return result;
	}
	
	/**two slow
	 * Given "abcabcbb", the answer is "abc", which the length is 3.
	 * Given "bbbbb", the answer is "b", with the length of 1.
	 * Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
	 * @param s
	 * @return
	 */
	public int lengthOfLongestSubstring(String s) {
		if(s == null || s.isEmpty())
			return 0;
		
		if(s.length() == 1){
			return 1;
		}
		
        Map<Character,Integer> visited = new HashMap<Character,Integer>();
        String longestTillNow = s.substring(0, 1);
        int start = 0;
        //String longest = longestTillNow;
        
        for(int i=0;i<s.length();i++){
        	char ch = s.charAt(i);
        	if(visited.containsKey(ch)){
        		if(i - start > longestTillNow.length()){
        			longestTillNow = s.substring(start,i);
        		}
        		
        		i = visited.get(ch) + 1;
    			start = i;
        		visited.clear();
        		visited.put(s.charAt(i), i);
        	}
        	else{
        		visited.put(ch, i);
        	}
        }
        
        
        return longestTillNow.length() > visited.size() ? longestTillNow.length():visited.size();
    }
	
	/**
	 * Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.
	 * @param l1
	 * @param l2
	 * @return
	 */
	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1);
        ListNode point = head;
        while(true){
        	if(l1 == null ){
        		point.next = l2;
        		break;
        	}
        	
        	if(l2 == null){
        		point.next = l1;
        		break;
        	}
        	
        	if(l1.val < l2.val){
        		point.next = l1;
        		l1 = l1.next;
        	}
        	else{
        		point.next = l2;
        		l2 = l2.next;
        	}
        	
        	point = point.next;
        	
        }
        
        return head.next;
    }
	
	/**
	 * Given nums = [2, 7, 11, 15], target = 9,
	 * Because nums[0] + nums[1] = 2 + 7 = 9,
	 * return [0, 1].
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] twoSum(int[] nums,int target){
		Map<Integer,Integer> numbers = new HashMap<Integer,Integer>();
		int[] ret = new int[2];
		for(int i=0;i < nums.length;i++){
//			if(nums[i] > target)
//				continue;
			
			Integer index = numbers.get(nums[i]);
			if(index == null) 
				numbers.put(nums[i], i);
			
			index = numbers.get(target - nums[i]);
			if(index != null && index < i){
				ret[0] = index;
				ret[1] = i;
				
				return ret;
			}
		}
		
		return ret;
	}
	
	/**
	 * You are given two linked lists representing two non-negative numbers.
	 *  The digits are stored in reverse order and each of their nodes contain a single digit. 
	 *  Add the two numbers and return it as a linked list.
	 *  Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
	 *  Output: 7 -> 0 -> 8
	 * @param l1
	 * @param l2
	 * @return
	 */
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		if(l1 == null)
			return l2;
		if(l2 == null)
			return l1;
		
        ListNode ret = new ListNode(-1);
        ListNode eNode = ret;
        ListNode eNode1 = l1;
        ListNode eNode2 = l2;
        
        int carriedNum = 0;
        
        while(eNode1 != null || eNode2 != null){
        	if(eNode2 != null){
        		carriedNum += eNode2.val;
        		eNode2 = eNode2.next;
        	}
        	
        	if(eNode1 != null){
        		carriedNum += eNode1.val;
        		eNode1 = eNode1.next;
        	}
        	
        	
        	eNode.next = new ListNode(carriedNum%10);
        	eNode = eNode.next;
        	
        	carriedNum = carriedNum / 10;
        }
        
        
        if(carriedNum > 0){
        	eNode.next = new ListNode(carriedNum);;
        	eNode = eNode.next;
        }
        
        return ret.next;
    }
	/**
	 * faster
	 * @param l1
	 * @param l2
	 * @param value
	 * @return
	 */
	public ListNode addTwoNumbers(ListNode l1, ListNode l2, int value) {
	    if (l1 != null || l2 != null || value > 0) {
	        int sum = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + value;
	        value = sum / 10;
	        sum = sum % 10;
	        ListNode newNode = new ListNode(sum);
	        newNode.next = addTwoNumbers(l1 != null ? l1.next : null, l2 != null ? l2.next : null, value);
	        return newNode;
	    } else {
	        return null;
	    }
	}
}
