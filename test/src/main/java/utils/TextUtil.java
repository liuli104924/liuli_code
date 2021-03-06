package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {


    /**
     * 得到文本中每一行的数据,返后一个字符串数组
     * @param filePath 文件路径
     * @return 字符串数组
     */
    public static List<String> getLines(String filePath) {
        // String filePath = "D:\\IdeaProjects\\test\\src\\main\\resources\\column.txt";
        Path path = Paths.get(filePath);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("io err ");
            e.printStackTrace();
        }
        return lines;
    }


    /**
     *  得到文本中的每一行和行号，返回一个map
     * @param filePath 文件路径
     * @return
     */
    public static Map<String, Integer> getLinesAndNo(String filePath) {
        // String filePath = "D:\\IdeaProjects\\test\\src\\main\\resources\\column.txt";
        HashMap<String, Integer> map = new HashMap<>();
        int count = 1;
        File file = new File(filePath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("file not find err ");
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(fis);
        while (scanner.hasNextLine()) {
            String newLine = scanner.nextLine().toLowerCase();
            map.put(newLine, count);
            count++;
        }
        return map;
    }


    /**
     * 得到文本中的内容饭后一个字符串
     * @param file1
     * @return
     */
    public static String getAllText(File file1) {

        Path filePath = Paths.get(file1.toString());
        byte[] data = new byte[0];
        try {
            data = Files.readAllBytes(filePath);
        } catch (IOException e) {
            System.out.println("io err");
            e.printStackTrace();
        }
        String result = null;
        try {
            result = new String(data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     *  判断文本中是否包含某个值
     * @param file  文件路径
     * @param regex  匹配值
     * @return
     */
    public static boolean findSomeThingFromTable(File file, String regex) {
        String allText = TextUtil.getAllText(file);
        String allTextToLow = allText.toLowerCase(Locale.ROOT);
        boolean contains = allTextToLow.contains(regex);

        return contains;
    }



    public static void main(String[] args) {
        String s1 = "'应收基金红利' CHI_NAME";
        Pattern p = Pattern.compile("'.{1,}'\\s{0,}(as|AS){0,}\\s{0,}" + "CHI_NAME");
        Matcher m = p.matcher(s1);
        if (m.find()) {
            System.out.println(m.group());
        } else {
            System.out.println(m.find());
        }


    }


}
