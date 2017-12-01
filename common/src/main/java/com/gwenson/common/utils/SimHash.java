package com.gwenson.common.utils;

import java.math.BigInteger;
import java.util.List;


/**
 *  SimHash 算法
 * @author gwenson
 *
 */
public class SimHash {

	private static volatile int HASHBITS = 64;
	
	/**
	 * 获取文本的SimHashCode
	 * @param text 文本
	 * @return
	 */
	public static String getSimHashCode(final List<String> text){
		
		int[] v = new int[HASHBITS];
//		List<Word> seg = WordSegmenter.seg(text, SegmentationAlgorithm.BidirectionalMinimumMatching);
        String word = null;
        for(int k = 0;k<text.size();k++){
//          word = lexeme.getLexemeText();
      	  word = text.get(k);
          // 注意停用词会被干掉
          // System.out.println(word);
          // 2、将每一个分词hash为一组固定长度的数列.比如 64bit 的一个整数.
          BigInteger t = hash(word);
          for (int i = 0; i < HASHBITS; i++) {
              BigInteger bitmask = new BigInteger("1").shiftLeft(i);
              // 3、建立一个长度为64的整数数组(假设要生成64位的数字指纹,也可以是其它数字),
              // 对每一个分词hash后的数列进行判断,如果是1000...1,那么数组的第一位和末尾一位加1,
              // 中间的62位减一,也就是说,逢1加1,逢0减1.一直到把所有的分词hash数列全部判断完毕.
              if (t.and(bitmask).signum() != 0) {
                  // 这里是计算整个文档的所有特征的向量和
                  // 这里实际使用中需要 +- 权重，比如词频，而不是简单的 +1/-1，
                  v[i] += 1;
              } else {
                  v[i] -= 1;
              }
          }
       }
//         十进制指纹
//        BigInteger fingerprint = new BigInteger("0");
        /**
         * 二进制指纹
         */
        StringBuffer simHashBuffer = new StringBuffer();
        for (int i = 0; i < HASHBITS; i++) {
            // 4、最后对数组进行判断,大于0的记为1,小于等于0的记为0,得到一个 64bit 的数字指纹/签名.
            if (v[i] >= 0) {
//                fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
                simHashBuffer.append("1");
            } else {
                simHashBuffer.append("0");
            }
        }
        String strSimHash = simHashBuffer.toString();
//        System.out.println("builder : "+ strSimHash + "; length :" + strSimHash.length());
        return strSimHash;  
	}
	
	
    private static BigInteger hash(String source) {
        if (source == null || source.length() == 0) {
            return new BigInteger("0");
        } else {
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(HASHBITS).subtract(new BigInteger("1"));
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }
	
    /**
     * 获得海明距离
     * @param str1
     * @param str2
     * @return
     */
    public static int getDistance(final String simHashcode1,final String simHashcode2) {
        int distance;
        if (simHashcode1.length() != simHashcode2.length()) {
            distance = -1;
        } else {
            distance = 0;
            for (int i = 0; i < simHashcode1.length(); i++) {
                if (simHashcode1.charAt(i) != simHashcode2.charAt(i)) {
                    distance++;
                }
            }
        }
        return distance;
    }
    
    
    
    /**
     * 拆分simhashcode
     * @param simHashCode 
     * @param subNum 拆分次数
     * @return
     */
    public static String[] subSimHashCode(final String simHashCode,final int subNum){
    	String[] code = new String[subNum];
    	int length = simHashCode.length();
    	int y = length%subNum;
    	int d = length/subNum;
    	int begin = 0;
    	int next = 0;
    	for(int i=0;i<subNum;i++){
    		next = next+d;
    		if(i<y){
    			next+=1;
    		}
    		code[i] = simHashCode.substring(begin, next);
    		begin = next;
    	}
		return code;
    	
    }
}
