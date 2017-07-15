package annotation;

import com.sun.tools.javac.model.JavacElements;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Author: Johnny
 * Date: 2016/12/31
 * Time: 15:44
 */
@SupportedAnnotationTypes({"annotation.HereDocument"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HereDocumentProcessor extends AbstractProcessor {
    private JavacElements elementUtils;
    private TreeMaker maker;

    @Override
    public void init(final ProcessingEnvironment procEnv) {
        super.init(procEnv);
        JavacProcessingEnvironment javacProcessingEnv = (JavacProcessingEnvironment) procEnv;
        this.elementUtils = javacProcessingEnv.getElementUtils();
        this.maker = TreeMaker.instance(javacProcessingEnv.getContext());
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        Set<? extends Element> fields = roundEnv.getElementsAnnotatedWith(HereDocument.class);
        for (Element field : fields) {
            String docComment = elementUtils.getDocComment(field);
            if (null != docComment) {
                JCTree.JCVariableDecl fieldNode = (JCTree.JCVariableDecl) elementUtils.getTree(field);
                fieldNode.init = maker.Literal(docComment);
            }
        }

        return true;
    }

}
