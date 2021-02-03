package utils;


import java.util.*;

public class ConfigUtil {

    /**
     * 得到配置文章的所有的value
     *
     * @param propertyName
     * @return
     */
    public static List<String> getAllMessage(String propertyName) {
        // 获得资源包
        ResourceBundle rb = ResourceBundle.getBundle(propertyName.trim());
        // 通过资源包拿到所有的key
        Enumeration<String> allKey = rb.getKeys();
        // 遍历key 得到 value
        List<String> valList = new ArrayList<String>();
        while (allKey.hasMoreElements()) {
            String key = allKey.nextElement();
            String value = (String) rb.getString(key);
            valList.add(value);
        }
        return valList;
    }


    public static String get(String key) {
        // 获得资源包
        ResourceBundle rb = ResourceBundle.getBundle("other".trim());
        String s1 = rb.getString(key);
        if(s1.isEmpty()){
            return null;
        }
        return s1;
    }

    public static String get(String propertyName,String key) {
        // 获得资源包
        ResourceBundle rb = ResourceBundle.getBundle(propertyName.trim());
        String s1 = rb.getString(key);
        if(s1.isEmpty()){
            return null;
        }
        return s1;
    }





}
