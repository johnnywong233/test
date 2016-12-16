package project.mvcDemo.action;

public enum ResultType {
	//重定向,转发,异步请求,数据流,跳转到向下一个控制器,重定向到下一个控制器
	Redirect, Forward, Ajax,Stream, Chain, RedirectChain
}
