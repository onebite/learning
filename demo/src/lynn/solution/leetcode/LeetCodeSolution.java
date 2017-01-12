package lynn.solution.leetcode;


import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeetCodeSolution {
	public static final int INTEGER_MAX_LENGTH = String.valueOf(Long.MAX_VALUE).length();

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
