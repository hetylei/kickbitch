package com.winnie.test.dao;

import com.winnie.test.vo.*;

import java.util.Map;
import java.util.List;

/**
 * Created by DaoCreator (daoInterface.jt)
 */
public interface ComputerDao {
//#appendbegin
    //==============Computer==============
    public Computer insertComputer(Computer vo);
    public int updateComputer(Computer vo);
    public int updateComputerIgnoreNull(Computer vo);

    public int deleteComputer(Computer vo);
    public int deleteComputerByPrimaryKey(String id );
    public int deleteComputerByParam(Object... param);
    public int deleteComputerByParam(Map<String, Object> param);

    public Computer getComputerByPrimaryKey(String id );
    
    public int getComputerCountByPrimaryKey(String id );
    public int getComputerCountByParam(Object... param);
    public int getComputerCountByParam(Map<String, Object> param);

    public List<Computer> getComputerListByParam(String orderBy, Object... param);
    public List<Computer> getComputerListByParam(int page, int count, String orderBy, Object... param);
    public List<Computer> getComputerListByParam(String orderBy, Map<String, Object> param);
    public List<Computer> getComputerListByParam(String orderBy, Map<String, Object> param, int page, int count);
    public List<Computer> getAllComputerList(String orderBy);
    //==============end of Computer==============	
//#appendend   
}
