package project.daoDemo.dao;

import java.util.List;

import project.daoDemo.vo.Myemp;

public interface IMyempDAO {
	/**
     * 数据的增加操作
     * @param emp 要增加的对象
     * @return 是否增加成功的标记
     * @throws Exception 异常交给被调用处处理
     */
    public boolean doCreate(Myemp emp) throws Exception ;
    /**
     * 根据关键字查询数据
     * @param  keyWord 查询条件
     * @return 查询到的数据集合
     * @throws Exception 异常交给被调用处处理
     */
    public List<Myemp> findAll(String keyWord) throws Exception ;
}
