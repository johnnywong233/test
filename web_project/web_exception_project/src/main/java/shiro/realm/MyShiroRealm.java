package shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import util.DecriptUtil;

import java.util.HashSet;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {

    //only one user just for test
    private static final String USER_NAME = "johnny";
    private static final String PASS = "123456";

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<String> roleNames = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        roleNames.add("administrator");//add user
        permissions.add("newPage.jhtml");  //add permission
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    //Authentication for login
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        if (token.getUsername().equals(USER_NAME)) {
            return new SimpleAuthenticationInfo(USER_NAME, DecriptUtil.md5(PASS), getName());
        } else {
            throw new AuthenticationException();
        }
    }

}
