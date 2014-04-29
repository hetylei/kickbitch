/*
 * 本配置文件声明了整个应用的数据库连接部分。
 */
var ioc = {
    //begin.上传文件的配置------
    tmpFilePool : {
        type : 'org.nutz.filepool.NutFilePool',
        // 临时文件最大个数为 1000 个
        args : [ "c:/attFiles", 1000 ]
    },
    uploadFileContext : {
        type : 'org.nutz.mvc.upload.UploadingContext',
        singleton : false,
        args : [
            { refer : 'tmpFilePool' }
        ],
        fields : {
            // 是否忽略空文件, 默认为 false
            ignoreNull : true,
            // 单个文件最大尺寸(大约的值，单位为字节，即 10485760 为 10M)
            maxFileSize : 10485760,
            // 正则表达式匹配可以支持的文件名
            nameFilter : '^(.+[.])(gif|jpg|png|xls|txt)$'
        }
    },
    tmpFileUpload : {
        type : 'org.nutz.mvc.upload.UploadAdaptor',
        singleton : false,
        args : [
            { refer : 'uploadFileContext' }
        ]
    },
    //end.上传文件的配置------
    /*
     * 数据库连接池，采用 Apache 的 dbcp
     */
    dataSource : {
        type : "org.apache.commons.dbcp.BasicDataSource",
        events : {
            depose : 'close'
        },
        fields : {
            driverClassName : 'com.mysql.jdbc.Driver',
            url : 'jdbc:mysql://localhost:3306/tbk?autoReconnect=true&characterEncoding=utf-8',
            username : 'root',
            password : 'ioriiori'
        }
    },
    /*
     * 这个配置很好理解， args 表示这个对象构造函数的参数。显然，下面的注入方式将调用 new NutDao(dataSource)
     */
    dao : { type : "org.nutz.dao.impl.NutDao", args : [
        { refer : "dataSource" }
    ] },
    AclDao : { type : "com.winnie.pub.acl.dao.AclJdbcDao", args : [
        { refer : "dao" }
    ] },
    FileDao : { type : "com.winnie.pub.file.dao.FileJdbcDao", args : [
        { refer : "dao" }
    ]}
    ,ComputerDao : { type : "com.winnie.test.dao.ComputerJdbcDao", args : [
        { refer : "dao" }
    ] }

    ,TBKDao : { type : "com.winnie.tk.task.dao.TBKJdbcDao", args : [
        { refer : "dao" }
    ] }

// ..............................................................End Ioc
};
