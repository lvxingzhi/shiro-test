package authenticator;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.apache.shiro.util.CollectionUtils;

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
public class MyAuthenticator extends AbstractAuthenticationStrategy {

    public MyAuthenticator() {
    }

    @Override
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        if(aggregate != null && !CollectionUtils.isEmpty(aggregate.getPrincipals())) {
            return aggregate;
        } else {
            throw new AuthenticationException("Authentication token of type [" + token.getClass() + "] could not be authenticated by any configured realms.  Please ensure that at least one realm can authenticate these tokens.");
        }
    }

}
