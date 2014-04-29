/*
 * �������ļ�����������Ӧ�õ����ݿ����Ӳ��֡�
 */
var ioc = {
    //begin.�ϴ��ļ�������------
    tmpFilePool : {
        type : 'org.nutz.filepool.NutFilePool',
        // ��ʱ�ļ�������Ϊ 1000 ��
        args : [ "c:/attFiles", 1000 ]
    },
    uploadFileContext : {
        type : 'org.nutz.mvc.upload.UploadingContext',
        singleton : false,
        args : [
            { refer : 'tmpFilePool' }
        ],
        fields : {
            // �Ƿ���Կ��ļ�, Ĭ��Ϊ false
            ignoreNull : true,
            // �����ļ����ߴ�(��Լ��ֵ����λΪ�ֽڣ��� 10485760 Ϊ 10M)
            maxFileSize : 10485760,
            // ������ʽƥ�����֧�ֵ��ļ���
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
    //end.�ϴ��ļ�������------
    /*
     * ���ݿ����ӳأ����� Apache �� dbcp
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
     * ������úܺ���⣬ args ��ʾ��������캯���Ĳ�������Ȼ�������ע�뷽ʽ������ new NutDao(dataSource)
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
