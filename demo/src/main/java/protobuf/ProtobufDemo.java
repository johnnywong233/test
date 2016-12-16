package protobuf;

import com.google.protobuf.ExtensionRegistry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2016/10/16
 * Time: 2:23
 */
public class ProtobufDemo {
    //http://blog.csdn.net/xiao__gui/article/details/36643949
    public static void main(String[] args) throws IOException {
        main1();
        main2();
        main3();
    }

    private static void main1() throws IOException {
        //according to the defined data structure to create person
        PersonMsg.Person.Builder personBuilder = PersonMsg.Person.newBuilder();
        personBuilder.setId(1);
        personBuilder.setName("johnny");
        personBuilder.setEmail("xxg@163.com");
        personBuilder.addFriends("movie");
        personBuilder.addFriends("riding");
        PersonMsg.Person xxg = personBuilder.build();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        xxg.writeTo(output);

        //above is sender, send data after serialize it
        byte[] byteArray = output.toByteArray();

        //below is receiver，de-serialize it after receiving it
        ByteArrayInputStream input = new ByteArrayInputStream(byteArray);

        //de-serialize
        PersonMsg.Person xxg2 = PersonMsg.Person.parseFrom(input);
        System.out.println("ID:" + xxg2.getId());
        System.out.println("name:" + xxg2.getName());
        System.out.println("email:" + xxg2.getEmail());
        System.out.println("friend:");
        List<String> friends = xxg2.getFriendsList();
        friends.forEach(System.out::println);
    }

    private static void main2() throws IOException {
        Example.BaseData.Builder baseBuilder = Example.BaseData.newBuilder();
        baseBuilder.setCode(123);
        baseBuilder.setExtension(Example.extendData, "xxg");
        Example.BaseData baseData = baseBuilder.build();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        baseData.writeTo(output);
        byte[] byteArray = output.toByteArray();
        ByteArrayInputStream input = new ByteArrayInputStream(byteArray);

        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(Example.extendData); //or Example.registerAllExtensions(registry);
        Example.BaseData receiveBaseData = Example.BaseData.parseFrom(input, registry);
        System.out.println(receiveBaseData.getCode());
        System.out.println(receiveBaseData.getExtension(Example.extendData));
    }

    private static void main3() throws IOException {
        Example1.Data.Builder dataBuilder = Example1.Data.newBuilder();
        dataBuilder.setMsg("xxg");
        Example1.Data data = dataBuilder.build();

        //create message BaseData object，add Data into BaseData through setExtension method
        Example1.BaseData.Builder baseBuilder = Example1.BaseData.newBuilder();
        baseBuilder.setCode(123);
        baseBuilder.setExtension(Example1.Data.extendData, data);
        Example1.BaseData baseData = baseBuilder.build();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        baseData.writeTo(output);
        byte[] byteArray = output.toByteArray();
        ByteArrayInputStream input = new ByteArrayInputStream(byteArray);

        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(Example1.Data.extendData); //or Example.registerAllExtensions(registry);
        Example1.BaseData receiveBaseData = Example1.BaseData.parseFrom(input, registry);
        System.out.println(receiveBaseData.getCode());
        System.out.println(receiveBaseData.getExtension(Example1.Data.extendData).getMsg());
    }

}