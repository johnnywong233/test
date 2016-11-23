package file.jacob;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/*
 * from 《你必须知道的261个Java语言问题》4-31.
 */
public class WordBean {
    //TODO
    private ActiveXComponent msWordApp = null;
    private Dispatch document = null;

    //open word doc
    private void openWord(boolean makeVisible) {
        if (msWordApp == null) {
            msWordApp = new ActiveXComponent("Word.Application");
        }
        //set as visible
        Dispatch.put(msWordApp, "Visible", new Variant(makeVisible));
    }

    //create new word doc
    private void createNewDocument() {
        //get doc lists
        Dispatch documents = Dispatch.get(msWordApp, "Documents").toDispatch();
        document = Dispatch.call(documents, "Add").toDispatch();
    }

    //insert string into a word doc
    private void insertText(String textToInsert) {
        //获取当前执行写入的位置，如果是新的文档则从开始处写入
        Dispatch selection = Dispatch.get(msWordApp, "Selection").toDispatch();
        Dispatch.put(selection, "Text", textToInsert);
    }

    public void saveFileAs(String filename) {
//		Dispatch.call(document, "SaveAs", filename);
        Dispatch.call(document, filename);
    }

    private void printFile() {
        //use the default printer
        Dispatch.call(document, "PrintOut");
    }

    private void closeDocument() {
        //0 not save
        //-1 save
        //-2 prompt and require a confirmation(yes/no)
        Dispatch.call(document, "Close", new Variant(-2));
        document = null;
    }

    private void closeWord() {
        Dispatch.call(msWordApp, "Quit");
        msWordApp = null;
        document = null;
    }

    public static void main(String[] args) {
        WordBean word = new WordBean();
        word.openWord(true);
        word.createNewDocument();
        word.insertText("Hello");
        //by default save to \Documents
        word.saveFileAs("test.doc");
        word.printFile();
        word.closeDocument();
        word.closeWord();
    }

}