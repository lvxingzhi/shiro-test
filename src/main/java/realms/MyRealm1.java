package realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

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
public class MyRealm1 implements Realm{
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
        //  验证用户密码
        if(!"xigua".equals(name)){
            throw new UnknownAccountException(); //如果用户名错误
        }
        if(!"000".equals(pwd)) {
            throw new IncorrectCredentialsException(); //如果密码错误
        }
        return new SimpleAuthenticationInfo(name,pwd,name);
    }
}
