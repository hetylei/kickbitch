package com.winnie.pub.dict;



public enum DataType {
    Varchar,Smallint,Date,Time,Double,Float,Numeric,
    DateTime,Int,Blob,Bool,Char,Text;
    /*public static final int Varchar  = 0;
    public static final int Smallint = 1;
    public static final int Date     = 2;
    public static final int Time     = 3;
    public static final int Double   = 4;
    public static final int Float    = 5;
    public static final int Numeric  = 6;
    public static final int DateTime     = 7;
    public static final int Int = 8;
    public static final int Blob     = 9;
    public static final int Bool     = 10;
    */

    public static DataType getDataType(int datatype,DBType dbtype) throws DictException {
        if (dbtype == DBType.MySQL){
            //空空如也
            throw new DictException("字段数据类型错误");
        }

        if (dbtype == DBType.Oracle){
            switch (datatype){
                case 12:return DataType.Varchar;
                case 91    :return DataType.Date;
                case 3  :return  DataType.Numeric;
                case 2004  :return  DataType.Blob;
                case 1  :return  DataType.Char;
            }
            throw new DictException("字段数据类型错误");
        }

        throw new DictException("字段数据类型错误");
    }

    public static String getTypeDefine(DataType datatype,DBType dbtype) throws DictException {
        if (dbtype == DBType.MySQL){
            switch (datatype){
                case Varchar:return "varchar";
                case Smallint:return "int";
                case Int:return "int";
                case Date    :return "date";
                case Time    :return "time";
                case Double  :return "double";
                case Float  :return  "double";
                case Numeric  :return  "double";
                case DateTime  :return  "datetime";
                case Blob  :return  "blob";
                case Bool  :return  "int";
            }
            throw new DictException("字段数据类型错误");
        }

        if (dbtype == DBType.Oracle){
            switch (datatype){
                case Varchar:return "varchar2";
                case Smallint:return "number(8,0)";
                case Int     :return "number(15,0)";
                case Date    :return "date";
                case Time    :return "date";
                case Double  :return "number";
                case Float  :return  "number";
                case Numeric  :return  "number";
                case DateTime    :return "date";
                case Blob  :return  "blob";
                case Bool  :return  "number(8,0)";
                case Char  :return  "char";
            }
            throw new DictException("字段数据类型错误");
        }

        if (dbtype == DBType.SQLServer){
            switch (datatype){
                case Varchar:return "varchar";
                case Smallint:return "int";
                case Int     :return "int";
                case Date    :return "datetime";
                case Time    :return "datetime";
                case Double  :return "float";
                case Float  :return  "float";
                case Numeric  :return  "float";
                case DateTime    :return "datetime";
                case Blob  :return  "image";
                case Bool  :return  "bit";
                case Char  :return  "char";
                case Text  :return  "text";
            }
            throw new DictException("字段数据类型错误");
        }

        throw new DictException("字段数据类型错误");
    }

    public static String getJavaDefine(DataType datatype) throws DictException {
        switch (datatype){
            case Varchar:return "String";
            case Smallint:return "int";
            case Int:return "int";
            case Date    :return "Date";
            case Time    :return "Date";
            case Double  :return "double";
            case Float  :return  "double";
            case Numeric  :return  "BigDecimal";
            case DateTime  :return  "Date";
            case Blob  :return  "BLOB!!";
            case Bool  :return  "boolean";
            case Char  :return  "String";
            case Text  :return  "String";
        }
        throw new DictException("字段数据类型错误");
    }

    public static String getJdbcDefine(DataType datatype) throws DictException {
        switch (datatype){
            case Varchar:return "getString";
            case Smallint:return "getInt";
            case Int:return "getInt";
            case Date    :return "getDate";
            case Time    :return "getDate";
            case Double  :return "getDouble";
            case Float  :return  "getDouble";
            case Numeric  :return  "getBigDecimal";
            case DateTime  :return  "getTimestamp";
            case Text  :return  "getString";
            case Bool  :return  "getBoolean";
            case Char  :return  "getString";
        }
        throw new DictException("字段数据类型错误");
    }
}

