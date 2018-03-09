package dbtoentity;


import mysql.MySqlUtil;
import org.apache.commons.io.FileUtils;
import string.StringUtil;

import java.io.File;
import java.io.IOException;
import java.sql.*;


/**
 * @author Administrator
 * @create 2018\3\9 0009
 * @since 1.0.0
 */
public class DbToEntityUtil {

    public static final int SQLSERVER = 1;
    public static final int MYSQL = 2;
    public static final int ORACLE = 3;
    private static final String LINE = "\r\n";
    private static final String TAB = "\t";

    /**
     * 用于调试,懒得写相应的参数
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return getConnection("192.168.0.161:1997", "ETForMonitor_V2", "sa",
                "password", DbToEntityUtil.SQLSERVER);
    }

    /**
     *
     * 通过jdbc获取相应的数据库链接connection
     *
     * @param ipport
     *            ip+port ,eg.: 192.168.0.161:1997
     * @param dbName
     *            databaseName ,eg. : ETForMonitor_V2
     * @param username
     *            eg.:sa
     * @param password
     *            eg. :password
     * @param type
     *            请看本类的静态变量
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection(String ipport, String dbName,
                                           String username, String password, int type)
            throws ClassNotFoundException, SQLException {
        String jdbcString = null;
        if (type == SQLSERVER) {
            jdbcString = "jdbc:jtds:sqlserver://" + ipport + ";databaseName="
                    + dbName;
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        } else if (type == MYSQL) {
            jdbcString = "jdbc:mysql://" + ipport + "/" + dbName;
            Class.forName("org.gjt.mm.mysql.Driver");
        } else if (type == ORACLE) {
            jdbcString = "jdbc:oracle:thin:@" + ipport + ":" + dbName;
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }

        Connection connection = null;
        connection = DriverManager
                .getConnection(jdbcString, username, password);
        return connection;
    }

    /**
     * 数据库表生成相应的java类，生成规则
     * 类名=      表名（第一个字母大写）
     * 属性名=   数据库列名
     * get/set方法 = 根据标准生成
     * 其中生成的基本类型均为包装类，例如Integer , Long , Boolean , String
     * @param connection
     * @param tableName
     * @param dbType
     * @param path
     * @param isCreateFile
     * @return
     * @throws SQLException
     */
    public static String table2pojo(Connection connection, String tableName,
                                    int dbType, String path , boolean isCreateFile) throws SQLException {
        String sql = "select * from " + tableName + " where 1 <> 1";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ps = connection.prepareStatement(sql);
        rs = ps.executeQuery();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();

        StringBuffer sb = new StringBuffer();
        tableName = tableName.substring(0, 1).toUpperCase() +tableName.subSequence(1, tableName.length());
        sb.append("public class " + tableName + " {");
        sb.append(LINE);
        for (int i = 1; i <= columnCount; i++) {
            sb.append(TAB);
            DataBaseType.getPojoType("dfd");
            sb.append("private "
                    + DataBaseType.getPojoType(md.getColumnTypeName(i)) + " "
                    + StringUtil.camelCaseName(md.getColumnName(i)) + ";");
            // System.out.println("name : " + md.getColumnName(i) +
            // "   , type :"
            // + md.getColumnTypeName(i));
            sb.append(LINE);
        }

        for (int i = 1; i <= columnCount; i++) {
            sb.append(TAB);

            String pojoType = DataBaseType.getPojoType(md.getColumnTypeName(i));
            String columnName = StringUtil.camelCaseName(md.getColumnName(i));
            String getName = null;
            String setName = null;
            if (columnName.length() > 1) {
                getName = "public "+pojoType+" get" + columnName.substring(0, 1).toUpperCase()
                        + columnName.substring(1, columnName.length()) + "() {";
                setName = "public void set" + columnName.substring(0, 1).toUpperCase()
                        + columnName.substring(1, columnName.length()) + "("
                        + pojoType + " " + columnName + ") {";
            } else {
                getName = "public get" + columnName.toUpperCase() + "() {";
                setName = "public set" + columnName.toUpperCase() + "(" + pojoType
                        + " " + columnName + ") {";
            }

            sb.append(LINE).append(TAB).append(getName);
            sb.append(LINE).append(TAB).append(TAB);
            sb.append("return " + columnName +";");
            sb.append(LINE).append(TAB).append("}");
            sb.append(LINE);
            sb.append(LINE).append(TAB).append(setName);
            sb.append(LINE).append(TAB).append(TAB);
            sb.append("this." +  columnName + " = " + columnName +";" );
            sb.append(LINE).append(TAB).append("}");
            sb.append(LINE);

        }
        sb.append("}");

        System.out.println(sb.toString());

        if(isCreateFile){
            File file = new File("E:\\"+tableName +".java");
            try {
                FileUtils.writeStringToFile(file,sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
           // FileUtils.writeStringToFile();

          //  FileUtils.stringToFile(null,tableName +".java" , sb.toString());
         // FileUtils.writeStringToFile(sb.toString());
        return null;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection con = MySqlUtil.getInstance().getConnection();
        table2pojo(con, "student", DbToEntityUtil.MYSQL, "" , true);

    }


}