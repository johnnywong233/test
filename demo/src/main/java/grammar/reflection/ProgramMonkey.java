package grammar.reflection;

public class ProgramMonkey extends Person implements ICompany {
    /*
	see https://zhuanlan.zhihu.com/p/21423208
	*/

    String mLanguage = "C#";
    String mCompany = "BBK";

    public ProgramMonkey(String aName, String aSex, int aAge) {
        super(aName, aSex, aAge);
    }

    public ProgramMonkey(String language, String company, String aName, String aSex, int aAge) {
        super(aName, aSex, aAge);
        mLanguage = language;
        mCompany = company;
    }

    public String getmLanguage() {
        return mLanguage;
    }

    public void setmLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }

    private int getSalaryPerMonth() {
        return 12306;
    }

    @Override
    public String getCompany() {
        return mCompany;
    }

}
