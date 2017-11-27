import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class Test_h2 {
    private static final transient Logger log = LoggerFactory.getLogger(Test_h2.class);
    /**
     * 自定义 Realm
     * @param args
     */
    public static void main(String[] args) throws SQLException {
//        DbDao.crateTable();
//        DbDao.addInfo("xigua","000","1");
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-h2-realms.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("xigua", "0500");
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                log.info("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }
        currentUser.logout();
        System.exit(0);
    }
}
