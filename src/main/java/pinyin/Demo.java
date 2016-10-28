package pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Demo {
	//http://blog.csdn.net/xiao__gui/article/details/8558620
	public static void main(String args[]){
		
		//只能获取单个汉字的拼音，但是不能获取一个包含多音字的词的拼音。
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();   
		  
		// UPPERCASE ZHONG
		// LOWERCASE zhong
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
		  
		// WITHOUT_TONE：无音标  (zhong)  
		// WITH_TONE_NUMBER：use number 1-4 to signify tone (zhong4)  
		// WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常）  (zhòng)  
		format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);  
		  
		// WITH_V：用v表示ü  (nv)  
		// WITH_U_AND_COLON：用"u:"表示ü  (nu:)  
		// WITH_U_UNICODE：直接用ü (nü)  
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);  
		          
		try {
			String[] pinyin = PinyinHelper.toHanyuPinyinStringArray('重', format);
			System.out.println(pinyin[0] + " " + pinyin[1]);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
	}
}
