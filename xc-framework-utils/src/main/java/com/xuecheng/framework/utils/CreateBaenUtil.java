package com.xuecheng.framework.utils;

/**
 * @author 杜承旭
 * @ClassNmae: CreateBaenUtil
 * @Description: TODO
 * @date 2019/10/11 9:40
 * @Version 1.0
 **/
public class CreateBaenUtil {

    public static <T> void getCreateBean(T t){
        if (t == null){
            try {
                t = (T) t.getClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
