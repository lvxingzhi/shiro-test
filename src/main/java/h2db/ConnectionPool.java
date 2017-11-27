package h2db;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

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
public class ConnectionPool {
    private static ConnectionPool cp = null;
    private JdbcConnectionPool jdbcCP = null;

    private ConnectionPool() {
        String dbPath ="E://H2/config/test";
        jdbcCP = JdbcConnectionPool.create("jdbc:h2:" + dbPath, "sa", "");
        jdbcCP.setMaxConnections(50);
    }

    public static ConnectionPool getInstance() {
        if (cp == null) {
            cp = new ConnectionPool();
        }
        return cp;
    }

    public Connection getConnection() throws SQLException {
        return jdbcCP.getConnection();
    }
}
