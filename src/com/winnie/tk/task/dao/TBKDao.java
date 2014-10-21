package com.winnie.tk.task.dao;

import com.winnie.tk.task.vo.*;

import java.util.Map;
import java.util.List;

/**
 * Created by DaoCreator (daoInterface.jt)
 */
public interface TBKDao {
//#appendbegin
    //==============TaskLog==============
    public TaskLog insertTaskLog(TaskLog vo);
    public int updateTaskLog(TaskLog vo);
    public int updateTaskLogIgnoreNull(TaskLog vo);

    public int deleteTaskLog(TaskLog vo);
    public int deleteTaskLogByPrimaryKey(String id );
    public int deleteTaskLogByParam(Object... param);
    public int deleteTaskLogByParam(Map<String, Object> param);

    public TaskLog getTaskLogByPrimaryKey(String id );
    
    public int getTaskLogCountByPrimaryKey(String id );
    public int getTaskLogCountByParam(Object... param);
    public int getTaskLogCountByParam(Map<String, Object> param);

    public List<TaskLog> getTaskLogListByParam(String orderBy, Object... param);
    public List<TaskLog> getTaskLogListByParam(int page, int count, String orderBy, Object... param);
    public List<TaskLog> getTaskLogListByParam(String orderBy, Map<String, Object> param);
    public List<TaskLog> getTaskLogListByParam(String orderBy, Map<String, Object> param, int page, int count);
    public List<TaskLog> getAllTaskLogList(String orderBy);
    //==============end of TaskLog==============	
//#appendend   

    //==============Task==============
    public Task insertTask(Task vo);
    public int updateTask(Task vo);
    public int updateTaskIgnoreNull(Task vo);

    public int deleteTask(Task vo);
    public int deleteTaskByPrimaryKey(String id );
    public int deleteTaskByParam(Object... param);
    public int deleteTaskByParam(Map<String, Object> param);

    public Task getTaskByPrimaryKey(String id );
    
    public int getTaskCountByPrimaryKey(String id );
    public int getTaskCountByParam(Object... param);
    public int getTaskCountByParam(Map<String, Object> param);

    public List<Task> getTaskListByParam(String orderBy, Object... param);
    public List<Task> getTaskListByParam(int page, int count, String orderBy, Object... param);
    public List<Task> getTaskListByParam(String orderBy, Map<String, Object> param);
    public List<Task> getTaskListByParam(String orderBy, Map<String, Object> param, int page, int count);
    public List<Task> getAllTaskList(String orderBy);
    //==============end of Task==============	



    //==============HotKeys==============
    public HotKeys insertHotKeys(HotKeys vo);
    public int updateHotKeys(HotKeys vo);
    public int updateHotKeysIgnoreNull(HotKeys vo);

    public int deleteHotKeys(HotKeys vo);
    public int deleteHotKeysByPrimaryKey(String id );
    public int deleteHotKeysByParam(Object... param);
    public int deleteHotKeysByParam(Map<String, Object> param);

    public HotKeys getHotKeysByPrimaryKey(String id );
    
    public int getHotKeysCountByPrimaryKey(String id );
    public int getHotKeysCountByParam(Object... param);
    public int getHotKeysCountByParam(Map<String, Object> param);

    public List<HotKeys> getHotKeysListByParam(String orderBy, Object... param);
    public List<HotKeys> getHotKeysListByParam(int page, int count, String orderBy, Object... param);
    public List<HotKeys> getHotKeysListByParam(String orderBy, Map<String, Object> param);
    public List<HotKeys> getHotKeysListByParam(String orderBy, Map<String, Object> param, int page, int count);
    public List<HotKeys> getAllHotKeysList(String orderBy);
    //==============end of HotKeys==============	



    //==============P4PKeys==============
    public P4PKeys insertP4PKeys(P4PKeys vo);
    public int updateP4PKeys(P4PKeys vo);
    public int updateP4PKeysIgnoreNull(P4PKeys vo);

    public int deleteP4PKeys(P4PKeys vo);
    public int deleteP4PKeysByPrimaryKey(String id );
    public int deleteP4PKeysByParam(Object... param);
    public int deleteP4PKeysByParam(Map<String, Object> param);

    public P4PKeys getP4PKeysByPrimaryKey(String id );
    
    public int getP4PKeysCountByPrimaryKey(String id );
    public int getP4PKeysCountByParam(Object... param);
    public int getP4PKeysCountByParam(Map<String, Object> param);

    public List<P4PKeys> getP4PKeysListByParam(String orderBy, Object... param);
    public List<P4PKeys> getP4PKeysListByParam(int page, int count, String orderBy, Object... param);
    public List<P4PKeys> getP4PKeysListByParam(String orderBy, Map<String, Object> param);
    public List<P4PKeys> getP4PKeysListByParam(String orderBy, Map<String, Object> param, int page, int count);
    public List<P4PKeys> getAllP4PKeysList(String orderBy);
    //==============end of P4PKeys==============	



    //==============Shop==============
    public Shop insertShop(Shop vo);
    public int updateShop(Shop vo);
    public int updateShopIgnoreNull(Shop vo);

    public int deleteShop(Shop vo);
    public int deleteShopByPrimaryKey(String id );
    public int deleteShopByParam(Object... param);
    public int deleteShopByParam(Map<String, Object> param);

    public Shop getShopByPrimaryKey(String id );
    
    public int getShopCountByPrimaryKey(String id );
    public int getShopCountByParam(Object... param);
    public int getShopCountByParam(Map<String, Object> param);

    public List<Shop> getShopListByParam(String orderBy, Object... param);
    public List<Shop> getShopListByParam(int page, int count, String orderBy, Object... param);
    public List<Shop> getShopListByParam(String orderBy, Map<String, Object> param);
    public List<Shop> getShopListByParam(String orderBy, Map<String, Object> param, int page, int count);
    public List<Shop> getAllShopList(String orderBy);
    //==============end of Shop==============	



    //==============ShopProduct==============
    public ShopProduct insertShopProduct(ShopProduct vo);
    public int updateShopProduct(ShopProduct vo);
    public int updateShopProductIgnoreNull(ShopProduct vo);

    public int deleteShopProduct(ShopProduct vo);
    public int deleteShopProductByPrimaryKey(String id );
    public int deleteShopProductByParam(Object... param);
    public int deleteShopProductByParam(Map<String, Object> param);

    public ShopProduct getShopProductByPrimaryKey(String id );
    
    public int getShopProductCountByPrimaryKey(String id );
    public int getShopProductCountByParam(Object... param);
    public int getShopProductCountByParam(Map<String, Object> param);

    public List<ShopProduct> getShopProductListByParam(String orderBy, Object... param);
    public List<ShopProduct> getShopProductListByParam(int page, int count, String orderBy, Object... param);
    public List<ShopProduct> getShopProductListByParam(String orderBy, Map<String, Object> param);
    public List<ShopProduct> getShopProductListByParam(String orderBy, Map<String, Object> param, int page, int count);
    public List<ShopProduct> getAllShopProductList(String orderBy);
    //==============end of ShopProduct==============	



    //==============ShopProductTrade==============
    public ShopProductTrade insertShopProductTrade(ShopProductTrade vo);
    public int updateShopProductTrade(ShopProductTrade vo);
    public int updateShopProductTradeIgnoreNull(ShopProductTrade vo);

    public int deleteShopProductTrade(ShopProductTrade vo);
    public int deleteShopProductTradeByPrimaryKey(String id );
    public int deleteShopProductTradeByParam(Object... param);
    public int deleteShopProductTradeByParam(Map<String, Object> param);

    public ShopProductTrade getShopProductTradeByPrimaryKey(String id );
    
    public int getShopProductTradeCountByPrimaryKey(String id );
    public int getShopProductTradeCountByParam(Object... param);
    public int getShopProductTradeCountByParam(Map<String, Object> param);

    public List<ShopProductTrade> getShopProductTradeListByParam(String orderBy, Object... param);
    public List<ShopProductTrade> getShopProductTradeListByParam(int page, int count, String orderBy, Object... param);
    public List<ShopProductTrade> getShopProductTradeListByParam(String orderBy, Map<String, Object> param);
    public List<ShopProductTrade> getShopProductTradeListByParam(String orderBy, Map<String, Object> param, int page, int count);
    public List<ShopProductTrade> getAllShopProductTradeList(String orderBy);
    //==============end of ShopProductTrade==============	



    //==============ShopRate==============
    public ShopRate insertShopRate(ShopRate vo);
    public int updateShopRate(ShopRate vo);
    public int updateShopRateIgnoreNull(ShopRate vo);

    public int deleteShopRate(ShopRate vo);
    public int deleteShopRateByPrimaryKey(String id );
    public int deleteShopRateByParam(Object... param);
    public int deleteShopRateByParam(Map<String, Object> param);

    public ShopRate getShopRateByPrimaryKey(String id );
    
    public int getShopRateCountByPrimaryKey(String id );
    public int getShopRateCountByParam(Object... param);
    public int getShopRateCountByParam(Map<String, Object> param);

    public List<ShopRate> getShopRateListByParam(String orderBy, Object... param);
    public List<ShopRate> getShopRateListByParam(int page, int count, String orderBy, Object... param);
    public List<ShopRate> getShopRateListByParam(String orderBy, Map<String, Object> param);
    public List<ShopRate> getShopRateListByParam(String orderBy, Map<String, Object> param, int page, int count);
    public List<ShopRate> getAllShopRateList(String orderBy);
    //==============end of ShopRate==============	



    //==============ShopProductRate==============
    public ShopProductRate insertShopProductRate(ShopProductRate vo);
    public int updateShopProductRate(ShopProductRate vo);
    public int updateShopProductRateIgnoreNull(ShopProductRate vo);

    public int deleteShopProductRate(ShopProductRate vo);
    public int deleteShopProductRateByPrimaryKey(String id );
    public int deleteShopProductRateByParam(Object... param);
    public int deleteShopProductRateByParam(Map<String, Object> param);

    public ShopProductRate getShopProductRateByPrimaryKey(String id );
    
    public int getShopProductRateCountByPrimaryKey(String id );
    public int getShopProductRateCountByParam(Object... param);
    public int getShopProductRateCountByParam(Map<String, Object> param);

    public List<ShopProductRate> getShopProductRateListByParam(String orderBy, Object... param);
    public List<ShopProductRate> getShopProductRateListByParam(int page, int count, String orderBy, Object... param);
    public List<ShopProductRate> getShopProductRateListByParam(String orderBy, Map<String, Object> param);
    public List<ShopProductRate> getShopProductRateListByParam(String orderBy, Map<String, Object> param, int page, int count);
    public List<ShopProductRate> getAllShopProductRateList(String orderBy);
    //==============end of ShopProductRate==============	



    //==============PublicLog==============
    public PublicLog insertPublicLog(PublicLog vo);
    public int updatePublicLog(PublicLog vo);
    public int updatePublicLogIgnoreNull(PublicLog vo);

    public int deletePublicLog(PublicLog vo);
    public int deletePublicLogByPrimaryKey(String id );
    public int deletePublicLogByParam(Object... param);
    public int deletePublicLogByParam(Map<String, Object> param);

    public PublicLog getPublicLogByPrimaryKey(String id );
    
    public int getPublicLogCountByPrimaryKey(String id );
    public int getPublicLogCountByParam(Object... param);
    public int getPublicLogCountByParam(Map<String, Object> param);

    public List<PublicLog> getPublicLogListByParam(String orderBy, Object... param);
    public List<PublicLog> getPublicLogListByParam(int page, int count, String orderBy, Object... param);
    public List<PublicLog> getPublicLogListByParam(String orderBy, Map<String, Object> param);
    public List<PublicLog> getPublicLogListByParam(String orderBy, Map<String, Object> param, int page, int count);
    public List<PublicLog> getAllPublicLogList(String orderBy);
    //==============end of PublicLog==============	


}
