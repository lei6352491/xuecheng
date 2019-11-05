package com.xuecheng.framework.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 杜承旭
 * @ClassNmae: PagingUtil
 * @Description: TODO
 * @date 2019/10/11 8:48
 * @Version 1.0
 **/
public class PagingUtil {

    //分页初始化
    public static Integer[] pagingInitialization(Integer page,Integer size){
        Integer[] array = new Integer[4];
        if (page == null || page <= 0){
            page = 1;
        }
        //适应mongodb的分页从第0页开始
        page = page - 1;
        if (size == null || size <= 0){
            size = 20;
        }
        array[0] = page;
        array[1] = size;
        return array;
    }
}
