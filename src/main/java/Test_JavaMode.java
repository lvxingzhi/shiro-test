import org.apache.commons.dbcp.BasicDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.Factory;

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
 * @date 2017/11/16
 * @since 1.0
 */
public class Test_JavaMode {

    /**
     * Java 编码方式初始化shiro组件
     * @param args
     */
    public static void main(String[] args) {
        // shiro容器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        // shiro认证校验器
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);

        // shiro授权校验器
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(authorizer);

        // 配置数据源连接池
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.h2.Driver");
        basicDataSource.setUrl("jdbc:h2:~/localh2database;MVCC\\=TRUE");
        basicDataSource.setUsername("sa");
        basicDataSource.setPassword("");

        // shiro基础数据来源(用户认证信息,用户授权信息)
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(basicDataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);
        securityManager.setRealms(CollectionUtils.asList((Realm)jdbcRealm));

        // 将容器设置为全局变量,方便使用
        SecurityUtils.setSecurityManager(securityManager);

        //  shiroAPI
        Subject subject = SecurityUtils.getSubject();
        // 构造认证对象
        UsernamePasswordToken token = new UsernamePasswordToken("用户名","密码");
        // 认证
        subject.login(token);
        Assert.isTrue(subject.isAuthenticated());// 认证通过


        /**
         * 配置文件初始化方式
         * [main]
         #authenticator
         authenticator=org.apache.shiro.authc.pam.ModularRealmAuthenticator
         authenticationStrategy=org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy
         authenticator.authenticationStrategy=$authenticationStrategy
         securityManager.authenticator=$authenticator

         #authorizer
         authorizer=org.apache.shiro.authz.ModularRealmAuthorizer
         permissionResolver=org.apache.shiro.authz.permission.WildcardPermissionResolver
         authorizer.permissionResolver=$permissionResolver
         securityManager.authorizer=$authorizer

         #realm
         dataSource=com.alibaba.druid.pool.DruidDataSource
         dataSource.driverClassName=com.mysql.jdbc.Driver
         dataSource.url=jdbc:mysql://localhost:3306/shiro
         dataSource.username=root
         #dataSource.password=
         jdbcRealm=org.apache.shiro.realm.jdbc.JdbcRealm
         jdbcRealm.dataSource=$dataSource
         jdbcRealm.permissionsLookupEnabled=true
         securityManager.realms=$jdbcRealm
         */

        // 创建SecurityManager的工厂类
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro-config.ini");

        org.apache.shiro.mgt.SecurityManager s = factory.getInstance();
        SecurityUtils.setSecurityManager(s);
        Subject sj = SecurityUtils.getSubject();
        UsernamePasswordToken t = new UsernamePasswordToken("zhang", "123");
        subject.login(t);

        Assert.isTrue(sj.isAuthenticated());

    }
}
