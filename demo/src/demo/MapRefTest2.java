package demo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import junit.framework.TestCase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class MapRefTest2 extends TestCase {

    public void test_0() throws Exception {
        String text;
        {
            Map<String, Object> map = new HashMap<String, Object>();

            USER user = new USER();
            user.setId(123);
            user.setName("wenshao");
            
            map.put("u1", user);
            map.put("u2", user);
            
            System.out.println(JSON.toJSONString(user));
            USER user2 = JSON.parseObject("{\"ID\":123,\"b\":{\"nameq\":13},\"name\":\"wen\"}",USER.class);
            System.out.println("user id=" + user2.id + " name=" + user2.name);
            text = JSON.toJSONString(map, SerializerFeature.WriteClassName);
        }
        
        System.out.println(text);
        Map<String, Object> map = JSON.parseObject(text, new TypeReference<Map<String, Object>>() {});
        //Assert.assertEquals(map, map.get("this"));
        Assert.assertEquals(map.get("u1"), map.get("u2"));
    }

    public static class USER {

        private int    id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int i) {
            this.id = i;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
