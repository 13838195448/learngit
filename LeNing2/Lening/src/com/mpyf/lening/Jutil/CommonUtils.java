package com.mpyf.lening.Jutil;

/**
 * @author Administrator ��ֹ��ε��
 *
 */
public class CommonUtils {  
    private static long lastClickTime;  
    public static boolean isFastDoubleClick() {  
        long time = System.currentTimeMillis();  
        long timeD = time - lastClickTime;  
        if ( 0 < timeD && timeD < 1000) {     
            return true;     
        }     
        lastClickTime = time;     
        return false;     
    }  
}  