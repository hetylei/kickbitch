package com.winnie.pub.utils;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 11-12-21
 * Time: 下午12:45
 * To change this template use File | Settings | File Templates.
 */
public class Pager {
    private int page;
    private int linesPerPage;
    private int pageCount;
    private int recorderCount;
    private String query;
    private String orderBy;

    public Pager(){

    }

    public Pager(int page, int linesPerPage, int recorderCount) {
        if (page < 1) {
            this.page = 1;
        } else {
            this.page = page;
        }
        if (linesPerPage < 1) {
            this.linesPerPage = 10;
        } else {
            this.linesPerPage = linesPerPage;
        }
        this.recorderCount = recorderCount;
        this.pageCount = (recorderCount + (linesPerPage - 1)) / linesPerPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLinesPerPage() {
        return linesPerPage;
    }

    public void setLinesPerPage(int linesPerPage) {
        this.linesPerPage = linesPerPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getRecorderCount() {
        return recorderCount;
    }

    public void setRecorderCount(int recorderCount) {
        this.recorderCount = recorderCount;
        //动些手脚 ，做些小处理
        if (page < 1) {
            this.page = 1;
        }
        if (linesPerPage < 1) {
            this.linesPerPage = 10;
        }
        this.recorderCount = recorderCount;
        this.pageCount = (recorderCount + (linesPerPage - 1)) / linesPerPage;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    
    public static String getQueryStringFromMap(Map m) {
        if (m != null) {
            StringBuilder sb = new StringBuilder();
            for (Object o:m.keySet()) {
                sb.append(o.toString()).append("=");
                if (m.get(o) != null) sb.append(m.get(o).toString());
                sb.append("&");
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    public static String getQueryString(Object... param) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0 ; i<param.length; i += 2) {
            try {
                sb.append(param[i].toString()).append("=").append(param[i + 1].toString()).append("&");
            } catch (Exception ex) {
                //参数传的不对，不是偶数, 或者有null了
                ex.printStackTrace();
            }
        }
        return sb.toString();
    }
}
