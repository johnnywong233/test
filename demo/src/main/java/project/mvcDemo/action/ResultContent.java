package project.mvcDemo.action;

import com.google.gson.Gson; 

public class ResultContent {
	private String url;
	private Object obj;
	
	public ResultContent(String url) {
		this.url = url;
	}
	
	public ResultContent(Object obj) {
		this.obj = obj;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getJson() {
		return new Gson().toJson(obj);// 这里使用Google的JSON工具类gson
	}
}
