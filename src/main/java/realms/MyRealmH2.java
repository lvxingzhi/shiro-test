package realms;

import h2db.DbDao;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

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
public class MyRealmH2 implements Realm{
    @Override
    public String getName() {
        return "MyRealm1";
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String name = (String) authenticationToken.getPrincipal();
        String pwd = new String((char[])authenticationToken.getCredentials());
        // 验证用户密码
        try {
            boolean exits = DbDao.isInfoExits(name,pwd);
            if(!exits){
                throw new UnknownAccountException(); //如果用户名错误
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new SimpleAuthenticationInfo(name,pwd,name);
    }
}
