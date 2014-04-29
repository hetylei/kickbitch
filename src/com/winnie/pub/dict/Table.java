package com.winnie.pub.dict;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: r
 * Date: 2005-5-9
 * Time: 21:21:37
 * To change this template use File | Settings | File Templates.
 */
public class Table {
    private String name;
    private String pojoName;
    private String remark;
    private List<Column> columns;     // Column
    private PrimaryKey primaryKey;
    private List<Unique> uniques;     // Unique
    private List<Index> indexs;      // Index
    private List<ForeignKey> foreignKeys; // ForeignKey
    private List<String> descriptions;

    public Table(String name) {
        this.name = name;
        this.pojoName = name;
    }

    public Table pojoName(String pojoName){
        this.pojoName = pojoName;
        return this;
    }

    public Table remark(String remark){
        this.remark = remark;
        return this;
    }

    public Table column(Column c)  {
        if (this.columns == null) this.columns = new ArrayList<Column>();
        if (this.hasColumn(c.getName()))
            throw new DictException("列名字有重复("+c.getName()+")");
        this.columns.add(c);
        return this;
    }

    /**
     * 添加主键
     * 多主键使用 PrimaryKey.and
     * @param pk
     * @return
     */
    public Table primarykey(PrimaryKey pk)  {
        for (PrimaryKeyIterator it = pk.getColumnsIterator();  it.hasNext(); ){
            String col = it.next();
            if (!this.hasColumn(col))
                throw new DictException("没有找到字段定义("+col+")");
        }
        this.primaryKey = pk;
        return this;
    }

    /**
     * 创建外键
     * @param fk
     * @return
     */
    public Table foreignkey(ForeignKey fk) {
        if (this.foreignKeys == null) this.foreignKeys = new ArrayList<ForeignKey>();

        Table t = Database.instance().getTable(fk.getForeignTable());
        if (t == null)
            throw new DictException("没有找到表哥定义("+fk.getForeignTable()+")");
        for (ForeignKeyIterator it = fk.getReferenceIterator(); it.hasNext();){
            Reference r = it.next();
            if (!t.hasColumn(r.getForeign()))
                throw new DictException("没有找到外键字段定义("+r.getForeign()+")");
        }

        for (ForeignKeyIterator it = fk.getReferenceIterator(); it.hasNext();){
            Reference r = it.next();
            if (!this.hasColumn(r.getLocal()))
                throw new DictException("没有找到字段定义("+r.getLocal()+")");
        }
        this.foreignKeys.add(fk);
        return this;
    }

    public Table unique(Unique uk)  {
        if (this.uniques == null) this.uniques = new ArrayList<Unique>();
        for (UniqueIterator it = uk.getColumnsIterator();  it.hasNext(); ){
            String col = it.next();
            if (!this.hasColumn(col))
                throw new DictException("没有找到字段定义("+col+")");
        }
        this.uniques.add(uk);
        return this;
    }


    public Table index(Index idx)  {
        if (this.indexs == null) this.indexs = new ArrayList<Index>();
        for (IndexIterator it = idx.getColumnsIterator();  it.hasNext(); ){
            String col = it.next();
            if (!this.hasColumn(col))
                throw new DictException("没有找到字段定义("+col+")");
        }
        this.indexs.add(idx);
        return this;
    }

    public Table description(String s){
        if (this.descriptions == null) this.descriptions = new ArrayList<String>();
        this.descriptions.add(s);
        return this;
    }

    public List getDescriptions(){
        return this.descriptions;
    }

    public boolean hasColumn(String col){
        if (this.columns == null)
            return false;

        String colname = col.toUpperCase();
        for (Column c : this.columns) {
            if (c.getName().equals(colname)) return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String getPojoName() {
        return pojoName;
    }

    public String getRemark() {
        return remark;
    }

    public boolean hasForeignKey(){
        return this.foreignKeys != null && this.foreignKeys.size() != 0;
    }

    public boolean foreignKeyReferTable(String name) {
        if (this.foreignKeys == null) return false;
        if (this.foreignKeys.size() == 0) return false;

        for (ForeignKey c : this.foreignKeys) {
            if (c.getForeignTable().equals(name)) return true;
        }

        return false;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public PrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public ColumnIterator getColumnIterator(){
        return new ColumnIterator(this.columns.iterator());
    }

    public String getDropDDL(){
        StringBuffer sb = new StringBuffer();
        sb.append("drop table ").append(this.getName()).append(" cascade constraints");
        return sb.toString();
    }

    public String getSelectDDL(){
        StringBuffer sb = new StringBuffer();
        sb.append("select ");
        int i=0;
        for (Column c : this.columns) {
            if (i > 0) sb.append(",");
            i++;
            sb.append(c.getName());
        }

        sb.append(" from ").append(this.getName());
        return sb.toString();
    }

    public String getInsertDDL(){
        StringBuffer sb = new StringBuffer();
        sb.append("insert into ").append(this.getName()).append(" (");
        int i=0;
        for (Column c : this.columns) {
            if (i > 0) sb.append(",");
            i++;
            sb.append(c.getName());
        }

        sb.append(") values (");
        i=0;
        for (Column c : this.columns) {
            if (i > 0) sb.append(",");
            i++;
            sb.append("?");
        }
        sb.append(")");
        return sb.toString();
    }

    public String getInsertSqlMap(){
        StringBuffer sb = new StringBuffer();
        sb.append("insert into ").append(this.getName()).append(" (\n");
        int i=0;
        for (Column c : this.columns) {
            if (i > 0) sb.append(",\n");
            i++;
            sb.append("  ").append(c.getName());
        }

        sb.append("\n) values (\n");
        i=0;
        for (Column c : this.columns) {
            if (i > 0) sb.append(",\n");
            i++;
            sb.append("  #").append(c.getProperty()).append("#");
        }
        sb.append("\n)");
        return sb.toString();
    }

    public String getInsertSpringJdbc(){
        StringBuffer sb = new StringBuffer();
        sb.append("insert into ").append(this.getName()).append(" (\n");
        int i=0;
        for (Column c : this.columns) {
            if (i > 0) sb.append(",\n");
            i++;
            sb.append("  ").append(c.getName());
        }

        sb.append("\n) values (\n");
        i=0;
        for (Column c : this.columns) {
            if (i > 0) sb.append(",\n");
            i++;
            sb.append(" :").append(c.getProperty());
        }
        sb.append("\n)");
        return sb.toString();
    }

    /**
     * 创建建表DDL语句
     * @param dbtype  数据库类型
     * @return
     */
    public List<String> getCreateDDL(DBType dbtype)  {
        if (dbtype.equals(DBType.MySQL)) {
            return getCreateMysqlDDL();
        } else {
            return getCreateOracleDDL();
        }
    }

    /**
     *

        CREATE TABLE `computer` (
          `id` int(11) NOT NULL AUTO_INCREMENT,
          `cpu` varchar(45) DEFAULT '123',
          `madetime` datetime DEFAULT NULL,
          `core` int(11) DEFAULT NULL,
          `price` double DEFAULT NULL,
          PRIMARY KEY (`id`)
        ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8$$


     * @return
     */
    public List<String> getCreateMysqlDDL() {
        List<StringBuffer> lt = new ArrayList<StringBuffer>();
        StringBuffer sb = new StringBuffer();
        lt.add(sb);
        sb.append("CREATE TABLE `").append(this.getName()).append("` (\n");

        int count = 0;
        for (Column c : this.columns) {
            if (count > 0) sb.append(",\n");
            count++;
            sb.append("  `").append(c.getName()).append("` ").append(c.getTypeCaption(DBType.MySQL))
                    .append(c.getLenCaption());
            if (c.isDefault()) sb.append(" DEFAULT \"").append(c.getDefaultv()).append("\"");
            if (c.isRequired()) sb.append(" NOT NULL");
        }

        if (this.primaryKey == null) throw new DictException("表没有主键("+this.getName()+")");
        if (count>0) sb.append(",\n");count++;
        sb.append(" PRIMARY KEY (");
        int j=0;
        for (PrimaryKeyIterator it = this.primaryKey.getColumnsIterator(); it.hasNext();){
            String pk = it.next();
            if (j>0) sb.append(",");j++;
            sb.append("`").append(pk).append("`");
        }
        sb.append("))");


        List<String> lt1 = new ArrayList<String>();
        for (StringBuffer s : lt) {
            lt1.add(s.toString());
        }
        return lt1;
    }

    public List<String> getCreateOracleDDL() {
        List<StringBuffer> lt = new ArrayList<StringBuffer>();
        StringBuffer sb = new StringBuffer();
        lt.add(sb);
        sb.append("CREATE TABLE ").append(this.getName()).append(" (\n");

        int count = 0;
        for (Column c : this.columns) {
            if (count > 0) sb.append(",\n");
            count++;
            sb.append("  ").append(c.getName()).append(" ").append(c.getTypeCaption(DBType.Oracle))
                    .append(c.getLenCaption());
            if (c.isDefault()) sb.append(" default '").append(c.getDefaultv()).append("'");
            if (c.isRequired()) sb.append(" not null");
        }

        if (this.primaryKey == null) throw new DictException("表没有主键("+this.getName()+")");
        if (count>0) sb.append(",\n");count++;
        sb.append("  CONSTRAINT PK_").append(this.getName()).append(" PRIMARY KEY (");
        int j=0;
        for (PrimaryKeyIterator it = this.primaryKey.getColumnsIterator(); it.hasNext();){
            String pk = it.next();
            if (j>0) sb.append(",");j++;
            sb.append(pk);
        }
        sb.append(")");

        if (this.uniques != null){
            int x=0;
            for (Unique c : this.uniques) {
                if (count > 0) sb.append(",\n");
                count++;
                //CONSTRAINT UK_EN_USER UNIQUE (LOGINNAME)
                sb.append("  CONSTRAINT UK_").append(this.getName()).append("_")
                        .append(x).append(" UNIQUE (");
                x++;
                j = 0;
                for (UniqueIterator it1 = c.getColumnsIterator(); it1.hasNext();) {
                    String uk = it1.next();
                    if (j > 0) sb.append(",");
                    j++;
                    sb.append(uk);
                }
                sb.append(")");
            }
        }

        if (this.indexs != null){
            int x=0;
            for (Index c : this.indexs) {
                StringBuffer sb1 = new StringBuffer();
                lt.add(sb1);
                //if (count>0) sb1.append(",\n");count++;
                //INDEX IK_EN_USER_0 (LOGINNAME)
                sb1.append("CREATE INDEX IK_").append(this.getName()).append("_")
                        .append(x).append(" ON ").append(this.getName()).append("(");
                x++;
                j = 0;
                for (IndexIterator it1 = c.getColumnsIterator(); it1.hasNext();) {
                    String uk = it1.next();
                    if (j > 0) sb1.append(",");
                    j++;
                    sb1.append(uk);
                }
                sb1.append(")");
            }
        }

        if (this.foreignKeys != null){
            int x=0;
            for (ForeignKey c : this.foreignKeys) {
                if (count > 0) sb.append(",\n");
                count++;
                //CONSTRAINT FK_EM_URO_RO FOREIGN KEY (ROLESN,OPID)   REFERENCES EM_ROLE_OP (ROLESN,OPID)
                sb.append("  CONSTRAINT FK_").append(this.getName()).append("_")
                        .append(x).append(" FOREIGN KEY (");
                x++;
                j = 0;
                for (ForeignKeyIterator it1 = c.getReferenceIterator(); it1.hasNext();) {
                    Reference ref = it1.next();
                    if (j > 0) sb.append(",");
                    j++;
                    sb.append(ref.getLocal());
                }
                sb.append(")");

                sb.append(" REFERENCES ").append(c.getForeignTable()).append("(");
                j = 0;
                for (ForeignKeyIterator it1 = c.getReferenceIterator(); it1.hasNext();) {
                    Reference ref = it1.next();
                    if (j > 0) sb.append(",");
                    j++;
                    sb.append(ref.getForeign());
                }
                sb.append(")");
            }
        }

        sb.append("\n)");

        List<String> lt1 = new ArrayList<String>();
        for (StringBuffer s : lt) {
            lt1.add(s.toString());
        }
        return lt1;
    }

    /**
     *

     CREATE TABLE [dbo].[Table_1](
     [id] [int] IDENTITY(1,1) NOT NULL,
     [test] [varchar](50) NULL,
     [fffe] [char](10) NOT NULL CONSTRAINT [DF_Table_1_fffe]  DEFAULT ((123)),
     CONSTRAINT [PK_Table_1] PRIMARY KEY CLUSTERED
     (
     [id] ASC
     )WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
     ) ON [PRIMARY]

     * @return
     */
    public List<String> getCreateSQLServerDDL() {
        List<StringBuffer> lt = new ArrayList<StringBuffer>();
        StringBuffer sb = new StringBuffer();
        lt.add(sb);
        sb.append("CREATE TABLE [dbo].[").append(this.getName()).append("] (\n");

        int count = 0;
        for (Column c : this.columns) {
            if (count > 0) sb.append(",\n");
            count++;
            sb.append("  [").append(c.getName()).append("] [").append(c.getTypeCaption(DBType.SQLServer)).append("] ")
                    .append(c.getLenCaption());
            if (c.isRequired()) sb.append(" NOT NULL");
            else sb.append(" NULL");  
            
            if (c.isDefault()) sb.append(" CONSTRAINT [df_").append(this.getName()).append("_").append(c.getName())
                    .append("] DEFAULT ((").append(c.getDefaultv()).append("))");
            
        }

        if (this.primaryKey == null) throw new DictException("表没有主键("+this.getName()+")");
        if (count>0) sb.append(",\n");
        sb.append("CONSTRAINT [PK_").append(this.getName()).append("] PRIMARY KEY CLUSTERED (");
        int j=0;
        for (PrimaryKeyIterator it = this.primaryKey.getColumnsIterator(); it.hasNext();){
            String pk = it.next();
            if (j>0) sb.append(",");j++;
            sb.append("[").append(pk).append("] asc");
        }
        sb.append(")WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, " +
                "IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]\n" +
                "     ) ON [PRIMARY]");


        List<String> lt1 = new ArrayList<String>();
        for (StringBuffer s : lt) {
            lt1.add(s.toString());
        }
        return lt1;
    }

    public String getUpdateSqlMap()  {
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ").append(this.getName()).append(" set\n");

        if (this.primaryKey == null) throw new DictException("表没有主键("+this.getName()+")");

        int count = 0;
        for (Column c : this.columns) {
            boolean ispk = false;
            for (PrimaryKeyIterator it1 = this.primaryKey.getColumnsIterator(); it1.hasNext();) {
                String pk = it1.next();
                if (c.getName().toUpperCase().equals(pk.toUpperCase())) {
                    ispk = true;
                    break;
                }
            }
            if (ispk) continue;

            if (count > 0) sb.append(",\n");
            count++;
            sb.append("  ").append(c.getName()).append("=#").append(c.getProperty())
                    .append("#");
        }

        sb.append("\nwhere\n");
        int j=0;
        for (PrimaryKeyIterator it1 = this.primaryKey.getColumnsIterator(); it1.hasNext();){
            String pk = it1.next();
            if (j>0) sb.append("\n  and ");
            else sb.append("  ");
            j++;
            sb.append(pk).append(" = #");
            for (Column c : this.columns) {
                if (c.getName().toUpperCase().equals(pk.toUpperCase())) {
                    sb.append(c.getProperty());
                    break;
                }
            }
            sb.append("#");
        }
        sb.append("\n");

        return sb.toString();
    }

    public String getUpdateSpringJdbc()  {
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ").append(this.getName()).append(" set\n");

        if (this.primaryKey == null) throw new DictException("表没有主键("+this.getName()+")");

        int count = 0;
        for (Column c : this.columns) {
            boolean ispk = false;
            for (PrimaryKeyIterator it1 = this.primaryKey.getColumnsIterator(); it1.hasNext();) {
                String pk = it1.next();
                if (c.getName().toUpperCase().equals(pk.toUpperCase())) {
                    ispk = true;
                    break;
                }
            }
            if (ispk) continue;

            if (count > 0) sb.append(",\n");
            count++;
            sb.append("  ").append(c.getName()).append("=:").append(c.getProperty());
        }

        sb.append("\nwhere\n");
        int j=0;
        for (PrimaryKeyIterator it1 = this.primaryKey.getColumnsIterator(); it1.hasNext();){
            String pk = it1.next();
            if (j>0) sb.append("\n  and ");
            else sb.append("  ");
            j++;
            sb.append(pk).append(" = :");
            for (Column c : this.columns) {
                if (c.getName().toUpperCase().equals(pk.toUpperCase())) {
                    sb.append(c.getProperty());
                    break;
                }
            }
        }
        sb.append("\n");

        return sb.toString();
    }

    public String getUpdateDDL()  {
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ").append(this.getName()).append(" set\n");

        if (this.primaryKey == null) throw new DictException("表没有主键("+this.getName()+")");

        int count = 0;
        for (Column c : this.columns) {
            boolean ispk = false;
            for (PrimaryKeyIterator it1 = this.primaryKey.getColumnsIterator(); it1.hasNext();) {
                String pk = it1.next();
                if (c.getName().toUpperCase().equals(pk.toUpperCase())) {
                    ispk = true;
                    break;
                }
            }
            if (ispk) continue;

            if (count > 0) sb.append(",\n");
            count++;
            sb.append("  ").append(c.getName()).append("=?");
        }

        sb.append("\nwhere\n");
        int j=0;
        for (PrimaryKeyIterator it1 = this.primaryKey.getColumnsIterator(); it1.hasNext();){
            String pk = it1.next();
            if (j>0) sb.append(",\n  and ");
            else sb.append("  ");
            j++;
            sb.append(pk).append(" = ?");
        }
        sb.append("\n");

        return sb.toString();
    }

    public int getColumnCount() {
        return this.columns.size();
    }

    public void generateHtmlDocument(String fname,DBType dbtype)  {
        String sp1 = "<table border=0  width=100%><tr bgcolor=#111111><td height=1></td></tr></table>\n";
        String sp2 = "<table border=0  width=100%><tr><td height=1></td></tr></table>\n";
        String sp3 = "<table border=0  width=100%><tr><td height=5></td></tr></table>\n";

        StringBuffer sb = new StringBuffer();
        sb.append("<span style=\"font-size:20pt;\">表名：").append(this.getName()).append(" - ").append(this.getRemark());
        sb.append("</span>\n");
        sb.append(sp1).append(sp3);

        sb.append("<span style=\"font-size:14pt;\">描述</span>\n");
        //sb.append(sp2);
        if (this.descriptions!=null){
            for (String s : this.descriptions) {
                sb.append("<p>");
                sb.append(s);
                sb.append("</p>\n");
            }
        }
        sb.append(sp3);

        sb.append("<span style=\"font-size:14pt;\">字段表</span>\n");
        sb.append(sp2);
        sb.append("<table style=\"font-size:9pt\" border=1><tr bgcolor=#cccccc>");
        sb.append("<td>字段名</td>");
        sb.append("<td>数据类型</td>");
        sb.append("<td>长度</td>");
        sb.append("<td>缺省值</td>");
        sb.append("<td>是否必需</td>");
        sb.append("<td>说明</td>");
        sb.append("<td>IbatisProperty</td>");
        sb.append("</tr>\n");

        for (Column c : this.columns) {
            sb.append("<tr>");
            sb.append("<td>").append(c.getName()).append("</td>");
            sb.append("<td>").append(c.getTypeCaption(dbtype)).append("</td>");
            sb.append("<td>");
            if (c.getLenCaption() == null || c.getLenCaption().trim().equals(""))
                sb.append("&nbsp;");
            else sb.append(c.getLenCaption());
            sb.append("</td>");
            sb.append("<td>").append(c.isDefault() ? c.getDefaultv() : "&nbsp;").append("</td>");
            sb.append("<td>").append(c.isRequired() ? "必需" : "&nbsp;").append("</td>");
            sb.append("<td>").append(c.getRemark() == null ? "&nbsp;" : c.getRemark()).append("</td>");
            sb.append("<td>").append(c.getProperty()).append("</td>");
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        sb.append(sp3);

        if (this.primaryKey == null) throw new DictException("表没有主键("+this.getName()+")");

        sb.append("<span style=\"font-size:14pt;\">主键</span>\n");
        sb.append(sp2);
        sb.append("<table style=\"font-size:9pt\" border=1><tr bgcolor=#cccccc>");
        sb.append("<td>名称</td>");
        sb.append("<td>字段</td>");
        sb.append("</tr>\n");

        sb.append("<tr>");
        sb.append("<td>PK_").append(this.getName()).append("</td>");

        int j=0;
        sb.append("<td>");
        for (PrimaryKeyIterator it = this.primaryKey.getColumnsIterator(); it.hasNext();){
            String pk = it.next();
            if (j>0) sb.append(",");j++;
            sb.append(pk);
        }
        sb.append("</td>");
        sb.append("</tr>\n");
        sb.append("</table>\n");
        sb.append(sp3);


        if (this.uniques != null){
            sb.append("<span style=\"font-size:14pt;\">唯一索引</span>\n");
            sb.append(sp2);

            sb.append("<table style=\"font-size:9pt\" border=1><tr bgcolor=#cccccc>");
            sb.append("<td>名称</td>");
            sb.append("<td>字段</td>");
            sb.append("</tr>\n");

            int x=0;
            for (Unique c : this.uniques) {
                sb.append("<tr>");
                sb.append("<td>UK_").append(this.getName()).append("_").append(x).append("</td>");
                x++;

                j = 0;
                sb.append("<td>");
                for (UniqueIterator it1 = c.getColumnsIterator(); it1.hasNext();) {
                    String uk = it1.next();
                    if (j > 0) sb.append(",");
                    j++;
                    sb.append(uk);
                }
                sb.append("</td>");
                sb.append("</tr>\n");
            }
            sb.append("</table>\n");
            sb.append(sp3);
        }

        if (this.indexs != null){
            sb.append("<span style=\"font-size:14pt;\">普通索引</span>\n");
            sb.append(sp2);

            sb.append("<table style=\"font-size:9pt\" border=1><tr bgcolor=#cccccc>");
            sb.append("<td>名称</td>");
            sb.append("<td>字段</td>");
            sb.append("</tr>\n");

            int x=0;
            for (Index c : this.indexs) {
                sb.append("<tr>");
                sb.append("<td>IK_").append(this.getName()).append("_").append(x).append("</td>");
                x++;

                j = 0;
                sb.append("<td>");
                for (IndexIterator it1 = c.getColumnsIterator(); it1.hasNext();) {
                    String uk = it1.next();
                    if (j > 0) sb.append(",");
                    j++;
                    sb.append(uk);
                }
                sb.append("</td>");
                sb.append("</tr>\n");
            }
            sb.append("</table>\n");
            sb.append(sp3);
        }

        if (this.foreignKeys != null){
            sb.append("<span style=\"font-size:14pt;\">外键</span>\n");
            sb.append(sp2);

            sb.append("<table style=\"font-size:9pt\" border=1><tr bgcolor=#cccccc>");
            sb.append("<td>名称</td>");
            sb.append("<td>字段</td>");
            sb.append("<td>引用表</td>");
            sb.append("<td>引用字段</td>");
            sb.append("</tr>\n");

            int x=0;
            for (ForeignKey c : this.foreignKeys) {
                sb.append("<tr>");
                sb.append("<td>FK_").append(this.getName()).append("_").append(x).append("</td>");
                x++;

                j = 0;
                sb.append("<td>");
                for (ForeignKeyIterator it1 = c.getReferenceIterator(); it1.hasNext();) {
                    Reference ref = it1.next();
                    if (j > 0) sb.append(",");
                    j++;
                    sb.append(ref.getLocal());
                }
                sb.append("</td>");

                sb.append("<td>").append(c.getForeignTable()).append("</td>");
                j = 0;
                sb.append("<td>");
                for (ForeignKeyIterator it1 = c.getReferenceIterator(); it1.hasNext();) {
                    Reference ref = it1.next();
                    if (j > 0) sb.append(",");
                    j++;
                    sb.append(ref.getForeign());
                }
                sb.append("</td>");

                sb.append("</tr>\n");
            }
            sb.append("</table>\n");
            sb.append(sp3);
        }
        sb.append("<br><br><br><br>");

        try {
            FileWriter f = new FileWriter(fname);
            f.write(sb.toString());
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getBeanProperties(){
        StringBuffer sb = new StringBuffer();
        for (Column c : this.columns) {
            sb.append("\tprivate ").append(c.getJavaCaption())
                    .append(" ").append(c.getProperty()).append(";\t")
                    .append("//").append(c.getRemark()).append("\n");
        }
        return sb.toString();
    }

    public String getIBatisMap(){
        StringBuffer sb = new StringBuffer();
        for (Column c : this.columns) {
            //<result property="org0acc" column="ORG0ACC"/>
            sb.append("  <result property=\"").append(c.getProperty())
                    .append("\" column=\"").append(c.getName())
                    .append("\"/>\n");
        }
        return sb.toString();
    }
    private static String getSetterName(String property){
        if (property!=null && !property.equals("")){
            return "set"+property.toUpperCase().charAt(0)+property.substring(1);
        } else {
            return "";
        }
    }

    public String getJdbcMap()  {
        StringBuffer sb = new StringBuffer();
        sb.append("    private ParameterizedRowMapper<YOURVO> item_YOUR_ROW=new  ParameterizedRowMapper<YOURVO>(){\n" +
                "        public YOURVO mapRow(ResultSet rs, int rowNum) throws SQLException {\n" +
                "            YOURVO i=new YOURVO();\n");
        for (Column c : this.columns){

            sb.append("            i.").append(getSetterName(c.getProperty())).append("(rs.").
                    append(DataType.getJdbcDefine(c.getDataType()))
                    .append("(\"").append(c.getName()).append("\"));\n");
        }
        sb.append("            return i;\n        }\n    };\n");
        return sb.toString();
    }
    public String getFieldNames(){
        StringBuffer sb = new StringBuffer();
        int count=0;
        for (Column c : this.columns) {
            if (count > 0) sb.append("^");
            count++;
            sb.append(c.getProperty());
        }
        return sb.toString();
    }

    public String getFieldCount(){
        return String.valueOf(this.columns.size());
    }
}

