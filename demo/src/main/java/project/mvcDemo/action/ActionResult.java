package project.mvcDemo.action;

public class ActionResult {
    private final ResultContent resultContent;
    private final ResultType resultType;

    public ActionResult(ResultContent resultContent) {
        this(resultContent, ResultType.Forward);
    }

    public ActionResult(ResultContent resultContent, ResultType type) {
        this.resultContent = resultContent;
        this.resultType = type;
    }

    /**
     * 获得执行结果的内容
     */
    public ResultContent getResultContent() {
        return resultContent;
    }

    /**
     * 获得执行结果的类型
     */
    public ResultType getResultType() {
        return resultType;
    }

}
