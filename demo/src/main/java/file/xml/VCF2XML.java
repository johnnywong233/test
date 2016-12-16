package file.xml;

//参考的实例说不支持中文，把源码进行改写，加入编码分析；但是我引用源码时没有发现中午乱码的问题，也许是jar包已经升级
//import com.net.sourceforge.cardme.engine.VCardEngine;

import net.sourceforge.cardme.engine.VCardEngine;
import net.sourceforge.cardme.vcard.VCard;
import net.sourceforge.cardme.vcard.exceptions.VCardParseException;
import net.sourceforge.cardme.vcard.types.NType;
import net.sourceforge.cardme.vcard.types.TelType;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by wajian on 2016/8/6.
 */
public class VCF2XML {
	
	public static void main(String args[]) {
		
		String filePath = "C:\\work\\test\\src\\main\\resources\\联系人.vcf";
		File file = new File(filePath);
		VCardEngine vce = new VCardEngine();
		try {
			//to parse more than one contractor, use parseMultiple(filename)
			//only one contractor, use parse(filename)
			//a VCard class correspond to a contractor, usually a VCF file has many contractors, use List to store
			List<VCard> vcards = vce.parseMultiple(file);
			for (VCard vCard : vcards) {
				// NType is the class that store info of the contractor
				NType nt = vCard.getN();
				String fname = ""; //family name
				String mname = ""; //middle name
				String gname = ""; //given name
				if (nt != null) {
					// the foreign name format: FamilyName + GivenName, this is also applied to Chinese names
					fname = fname + nt.getFamilyName();
					//defined as AdditionalNames in List<String> , not middle name
					mname = mname + nt.getAdditionalNames().get(0);//add get(0) to discard []
					gname = gname + nt.getGivenName();
				}
				System.out.println(fname + mname + gname);
				
				//use list to store more than one contractor
				List<TelType> teltype = vCard.getTels();
				
				// only print those contractors with the phone number
				if (teltype != null && teltype.size() != 0) {
					for (TelType telType2 : teltype) {
						// output the phone number of contractors
						System.out.println(telType2.getTelephone());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (VCardParseException e) {
			e.printStackTrace();
		}
	}
}
