package com.gwenson.common.utils;

public class NumberUtil {

	public static final int BIT_COUT=64;
	
	/**
	 * 字符串转number,总char的和
	 * @param str
	 * @return
	 */
	public static int getStringSumNumber(final String str){
		char[] charArray = str.toCharArray();
		int l = charArray.length;
		int num = 0; 
		for(int i = 0;i<l;i++){
			char c =charArray[i];
			num = num+c;
		}
		return num;
		
	}
	
	/**
	 * 求字符串的总和的余
	 * @param str
	 * @param bitCount
	 * @return
	 */
	public static int getStringModBitCount(final String str,final int bitCount){
		int string2Number = getStringSumNumber(str);
		int i = string2Number%bitCount;
		return i;
		
	}
	
	/*public static void main(String[] args) {
		String s= "dafdasdf";
		int i = getStringModBitCount(s,64);
		System.out.println(i);
		Set set = new HashSet<>();
		Set set2 = new HashSet<>();
		for(int i1=0;i1<5;i1++){
			set.add(i1);
		}
		
		for(int i2=5;i2<10;i2++){
			set.add(i2);
		}
		set.addAll(set2);
		System.out.println(set);
		List list = new LinkedList<>();
		List index = new ArrayList<>();
		for(int i1 = 0; i1<10; i1++){
			list.add(i1);
			list.remove(0);
			System.out.println(list.size());
		}
		int iN=-1000;
		int n=iN%10;
		System.out.println(n);
	}*/
}
