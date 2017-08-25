package com.utils;

import java.io.*;

/**
 * @author 李世成
 * @email: lishicheng@innjia.com
 * @date: 2017/8/25
 */
public class ConstUtils {
    /**
     * 对象深层复制，写的不是很好，待优化
     * @param obj
     * @return
     */
    public static Object deepClone(Object obj) {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = null;
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        Object object = null;
        try {
            oo=new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bi=new ByteArrayInputStream(bo.toByteArray());
            oi=new ObjectInputStream(bi);
            object = oi.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return object;
    }
}
