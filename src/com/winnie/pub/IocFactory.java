package com.winnie.pub;

import com.winnie.pub.file.dao.FileDao;
import com.winnie.pub.acl.dao.AclDao;
import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;

import javax.sql.DataSource;
import com.winnie.test.dao.ComputerDao;
import com.winnie.tk.task.dao.TBKDao;


/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-9-11
 * Time: ÉÏÎç1:49
 * To change this template use File | Settings | File Templates.
 */
public class IocFactory {
    private static Ioc ioc;

    public static Ioc getIoc() {
        return ioc;
    }

    protected static void setIoc(Ioc ioc) {
        IocFactory.ioc = ioc;
    }

    public static DataSource getDataSource() {
        return ioc.get(DataSource.class, "dataSource");
    }

    public static Dao getDao() {
        return ioc.get(Dao.class, "dao");
    }

    public static AclDao getAclDao() {
        return ioc.get(AclDao.class, "AclDao");
    }

    public static FileDao getFileDao() {
        return ioc.get(FileDao.class, "FileDao");
    }

	public static ComputerDao getComputerDao() {
		//Created by FileOperator addDaoFactoryMethod
		return ioc.get(ComputerDao.class, "ComputerDao");
	}



	public static TBKDao getTBKDao() {
		//Created by FileOperator addIocFactoryMethod 
		return ioc.get(TBKDao.class, "TBKDao");
	}

}
