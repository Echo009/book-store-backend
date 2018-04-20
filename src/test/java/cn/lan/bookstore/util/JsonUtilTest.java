package cn.lan.bookstore.util;

import cn.lan.bookstore.dto.UserBaseInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author: Ech0
 * Email  : ech0.extreme@foxmail.com
 * Time   : 04/20/2018 01:16 PM
 */
@Slf4j
public class JsonUtilTest {

    String jsonString = "{\n" +
            "  \"userName\": \"Echo009\",\n" +
            "  \"phone\": 17673207341,\n" +
            "  \"role_code\": 1\n" +
            "}";
    @Test
    public void getPropertyValueTest(){
        log.info(JsonUtil.getValue(jsonString,"userName"));
    }

    @Test
    public void formObjectTest(){
        log.info("{}",JsonUtil.toBean(jsonString, UserBaseInfoDTO.class));
    }
}
