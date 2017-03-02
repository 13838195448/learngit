package com.mpyf.lening.interfaces.http;



import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mpyf.lening.interfaces.bean.Parame.ParameBase;


/**
 * Created by Administrator on 2015/12/23.
 */
public class MessageHandle {

    static String formatParame(Map<String, Object> dic) throws  Exception{
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        dic.put("time", time);
        dic.put("sourcefrom", 3);

        String parame = "";
        String str = "";

        Collection<String> keyset = dic.keySet();
        List<String> list = new ArrayList<String>(keyset);

        Collections.sort(list, new Comparator<String>() {

            public int compare(String arg0, String arg1) {

                return arg0.toLowerCase().compareTo(arg1.toLowerCase());
            }
        });

        for (int i = 0; i < list.size(); i++) {
            String key = list.get(i);
            Object value = dic.get(key);
            if (value == null) {
                value = "";
            }
            parame += String.format("%s=%s&", key, URLEncoder.encode(value.toString(), "utf-8"));
            str += value + "";
        }
        String token = MD5.getMD5(str + Setting.apiKey);
        parame += String.format("%s=%s", "token", token);
        return parame;
    }

    static Field[] concat(Field[] a, Field[] b) {
        Field[] c= new Field[a.length+b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    static String formatParame(ParameBase msg) throws Exception {
        msg.setSourcefrom("3");
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        msg.setTime(time);
        Class _class=msg.getClass();
        Field[] fields =_class.getDeclaredFields();
        
        if (_class.getGenericSuperclass() != null) {
            Class superClass = _class.getSuperclass();
            Field[] fieldsSuper =superClass.getDeclaredFields();
            fields=concat(fields, fieldsSuper);
        }
        List<Field> fields2 = Arrays.asList(fields);
        Collections.sort(fields2, new Comparator<Field>() {
            public int compare(Field arg0, Field arg1) {

                return arg0.getName().toLowerCase().compareTo(arg1.getName().toLowerCase());
            }
        });
        String str = "";
        
        for (Field f : fields2) {
            String name = f.getName();
            //f先判断是否是集合，如果是集合，则跳过

            if (name.toLowerCase() .equals("token") ||name.toLowerCase() .equals("photo")||name.toLowerCase() .equals("touser")||f.getClass().isArray()||name.toLowerCase().equals("markingscore")) {
                continue;
            	
            }else{
            	String metodName="get" + toUpperCaseFirstOne(name);
                Method m = msg.getClass().getMethod(metodName);
                Object value = m.invoke(msg);
                if (value == null) {
                    value = "";
                }
                else if(f.getType()==Boolean.class)
                {
                	Boolean v2=(Boolean)value;
                	value=v2?1:0;
                }
                str += value + "";
            }
            
        }
        
        //序列化后向接口提交的字符串
        String token = MD5.getMD5(str + Setting.apiKey);
        msg.setToken(token);
        return JsonUtils.serialize(msg);
    }

    public static void getMessage(Map<String, Object> map,  String api,RequestCallBack callBack){
    	
        String parame ;
        try {
            parame = formatParame(map);
            
        }
        catch (Exception ero) {
            DealResult dealResult = new DealResult();
            dealResult.setResult(false);
            dealResult.setMessage("签名时发生错误:" + ero.getMessage());
           // callBack.onCall(dealResult.getResult(),dealResult.getMessage(),null);
            callBack.onCall(dealResult);
            return;
        }
        String url = String.format("%s%s?%s", Setting.apiUrl, api, parame);
       // String url = Setting.apiUrl + path + "!" + method+ ".action?" + parame;
        HttpRequest request = new HttpRequest();
        request.setCookie("user=" + Setting.apiUser);
        //request.setRequestProperty("accept", "*/*");
        //request.setRequestProperty("connection", "Keep-Alive");
        request.sendGet(url, callBack);
        request.execute();
    }

    public static void postMessage(ParameBase msg, String api,RequestCallBack callBack) {

        String parame;
        try {
            parame = formatParame(msg);
        }
        catch (Exception ero) {
            DealResult dealResult = new DealResult();
            dealResult.setResult(false);
            dealResult.setMessage("签名时发生错误:" + ero.getMessage());
           // callBack.onCall(dealResult.getResult(),dealResult.getMessage(),null);
            callBack.onCall(dealResult);
            return;
        }
        String url = String.format("%s%s", Setting.apiUrl, api);
        HttpRequest request = new HttpRequest();
        //set head
        //request.set
//        request.getCookies();
        request.setCookie("user=" + Setting.apiUser);
        request.setRequestProperty("Content-Type", "application/json");
        request.setRequestProperty("Charset", "utf-8");
        
        
        request.sendPost(url, parame, callBack);
        request.execute();
    }

	

}
