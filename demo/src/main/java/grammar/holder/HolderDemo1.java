package grammar.holder;

import javax.xml.ws.Holder;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time: 17:14
 */
public class HolderDemo1 {
    private static void testHolder(Holder<User> uHolder){
        User user = new User();
        user.setId(uHolder.value.getId());
        user.setName(uHolder.value.getName());
        user.setPassword(uHolder.value.getPassword());
        user.setHomepage("https://github.com/johnnywong233");//newly add

        uHolder.value = user;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setName("test");
        user.setPassword("123456");

        //使用Holder对User进行包装, both OK
		Holder<User> holder = new Holder<>();
		holder.value = user;
//        Holder<User> holder = new Holder<>(user);

        HolderDemo1.testHolder(holder);
        System.out.println("newly added homepage is: " + holder.value.getHomepage());
    }
}
