package h2db;

import java.sql.*;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lvxz5
 * @version 1.0
 * @date 2017/11/9
 * @since 1.0
 */
public class DbDao {

    public static void crateTable() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            DatabaseMetaData meta = conn.getMetaData();

            ResultSet rsTables = meta.getTables(null, null, "users",
                    new String[] { "TABLE" });
            if (!rsTables.next()) {
                stmt = conn.createStatement();
                stmt.execute("CREATE TABLE users(name VARCHAR(1024),pwd VARCHAR(1024),status VARCHAR(1024),PRIMARY KEY(name))");
            }
            rsTables.close();
        } finally {
            releaseConnection(conn, stmt, null);
        }
    }

    public static void addInfo(String name, String pwd,
                               String status) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();

            stmt = conn
                    .prepareStatement("INSERT INTO users VALUES(?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, pwd);
            stmt.setString(3, status);
            stmt.execute();

        } finally {
            releaseConnection(conn, stmt, null);
        }
    }

    public static boolean isInfoExits(String name, String pwd)
            throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionPool.getInstance().getConnection();
            stmt = conn
                    .prepareStatement("SELECT name FROM users WHERE name=? AND pwd=?");
            stmt.setString(1, name);
            stmt.setString(2, pwd);
            rs = stmt.executeQuery();
            return rs.next();
        } finally {
            releaseConnection(conn, stmt, rs);
        }
    }

    private static void releaseConnection(Connection conn, Statement stmt,
                                          ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    /**
     jdbc.driverClassName=org.h2.Driver
     jdbc.url=jdbc:h2:~/localh2database;MVCC\=TRUE
     jdbc.username=sa
     jdbc.password=
     */

}
