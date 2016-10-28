package mail.service;

import java.util.Date;

import mail.dao.UserDao;
import mail.model.User;
import mail.util.MD5Util;
import mail.util.MailUtil;

/**
 * Created by wajian on 2016/8/9.
 */
public class UserService {
    private UserDao dao = null;

    /**
     * handle register
     * @param email
     */
    public void processRegister(String email) {
        User user = new User();
        user.setEmail(email);
        user.setRegisterTime(new Date());
        user.setStatus(0);
        //这里可以将激活码设计的更复杂
        user.setValidateCode(MD5Util.encode2hex(email));

        dao = new UserDao();
        dao.save(user);

        //发送邮件
        StringBuffer sb = new StringBuffer("点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
        sb.append("<a href=\"http://localhost:8080/JavaMailDemo/user?action=activate&email=")
                .append(email)
                .append("&validateCode=")
                .append(user.getValidateCode())
                .append("\">http://localhost:8080/JavaMailDemo/user?action=activate&email=")
                .append(email)
                .append("&validateCode=")
                .append(user.getValidateCode())
                .append("</a>");
        MailUtil.send(email , sb.toString());

    }

    /**
     * handle validate
     * @param email 邮箱地址
     * @param validateCode 验证码
     * @throws ServiceException
     */
    public void processActivate(String email , String validateCode)throws ServiceException {
        dao = new UserDao();
        User user = dao.findByEmail(email);

        //验证用户是否存在
        if(user != null) {
            //验证用户状态
            if(user.getStatus() == 0) {
                Date currentTime = new Date();
                //验证链接是否过期
                if(currentTime.before(user.getLastActivateTime())) {
                    //验证激活码是否正确
                    if(validateCode.equals(user.getValidateCode())) {
                        //激活成功，
                        dao.updateUserStatus(user.getId(), 1);
                    } else {
                        throw new ServiceException("激活码不正确");
                    }
                } else {
                    throw new ServiceException("激活码已过期！");
                }
            } else {
                throw new ServiceException("邮箱已激活，请登录！");
            }
        } else {
            throw new ServiceException("该邮箱未注册（邮箱地址不存在）！");
        }

    }
}
