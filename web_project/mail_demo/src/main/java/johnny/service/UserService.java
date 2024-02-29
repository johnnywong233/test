package johnny.service;

import johnny.dao.UserDao;
import johnny.model.User;
import johnny.util.MD5Util;
import johnny.util.MailUtil;

import java.util.Date;

public class UserService {
    private UserDao dao = null;

    public void processRegister(String email) {
        User user = new User();
        user.setEmail(email);
        user.setRegisterTime(new Date());
        user.setStatus(0);
        //can be replaced with more complicated activate code
        user.setValidateCode(MD5Util.encode2hex(email));

        dao = new UserDao();
        dao.save(user);

        //send mail
        String sb = "点击下面链接激活账号，48小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>" +
                "<a href=\"http://localhost:8080/JavaMailDemo/user?action=activate&email=" + email +
                "&validateCode=" + user.getValidateCode() +
                "\">http://localhost:8080/JavaMailDemo/user?action=activate&email=" + email +
                "&validateCode=" + user.getValidateCode() + "</a>";
        MailUtil.send(email, sb);
    }

    /**
     * process activate
     *
     * @param email        email address
     * @param validateCode validate code
     */
    public void processActivate(String email, String validateCode) throws ServiceException {
        dao = new UserDao();
        User user = dao.findByEmail(email);

        //validate user exists or not
        if (user != null) {
            //validate user status
            if (user.getStatus() == 0) {
                Date currentTime = new Date();
                //validate link expire or not
                if (currentTime.before(user.getLastActivateTime())) {
                    //validate activate code correctness
                    if (validateCode.equals(user.getValidateCode())) {
                        //activate successfully
                        dao.updateUserStatus(user.getId(), 1);
                    } else {
                        throw new ServiceException("activate code not correct.");
                    }
                } else {
                    throw new ServiceException("activate code has expired!");
                }
            } else {
                throw new ServiceException("mail has already activated, please login!");
            }
        } else {
            throw new ServiceException("this mail has not been registered(mail address not exists!)");
        }
    }

}