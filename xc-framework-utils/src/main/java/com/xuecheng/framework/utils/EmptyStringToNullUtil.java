package com.xuecheng.framework.utils;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author 杜承旭
 * @ClassNmae: EmptyStringToNullUtil
 * @Description: TODO
 * @date 2019/10/11 15:32
 * @Version 1.0
 **/
public class EmptyStringToNullUtil {

    public static <T> T emptyStringToNullUtil(T t){
        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).forEach(s ->{
            Class<?> type = s.getType();
            String typeName = type.getTypeName();
            if ("java.lang.String".equals(typeName)){
                s.setAccessible(true);
                try {
                    String str = (String)s.get(t);
                    if (str == ""){
                        s.set(t,null);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return t;
    }

}
