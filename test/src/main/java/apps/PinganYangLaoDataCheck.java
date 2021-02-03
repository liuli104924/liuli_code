package apps;

import utils.ConfigUtil;
import utils.TextUtil;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PinganYangLaoDataCheck {


    // 需加密字段: EXCH_CODE, TRD_CODE, TRD_MKT, SECU_CODE, SECU_NAME, SECU_ID, CHI_NAME, PLEDG_SECU_ID, PLEDG_SECU_NAME
    public static void main(String[] args) {
        // 得到l1的脚本
        String path = ConfigUtil.get("dml-l1-path");
        File file = new File(path);
        File[] tempList = file.listFiles();

        // 拿到包含加密列的表名
        File file2 = new File("./src/main/resources/table.txt");
        List<String> lines = TextUtil.getLines(file2.toString());
        Stream<String> distinct = lines.stream().distinct();
        List<String> tables = distinct.collect(Collectors.toList());

        // 不包含加密列的l1的表的集合
        List<File> files = new ArrayList<>();
        // 包含加密列的l1的表的集合
        List<File> files2 = new ArrayList<>();

        // 筛选出L1不包含需要加密列的脚本
        for (File file1 : tempList) {
            int i = 0;
            for (String table : tables) {
                if (TextUtil.findSomeThingFromTable(file1, table.toLowerCase(Locale.ROOT))) {
                    i++;
                }
            }
            if (i == 0) {
                files.add(file1);
            } else {
                files2.add(file1);
            }
        }

        // 得到L2的脚本
        String path1 = ConfigUtil.get("dml-l2-path");

        File file4 = new File(path1);
        File[] l2Files = file4.listFiles();
        for (File l2file : l2Files) {
            for (File file1 : files) {
                String table = getTableName(file1);
                for (File file3 : files2) {
                    String allText = TextUtil.getAllText(l2file);
                    String table1 = getTableName(file3);
                    if (allText.contains(table) && allText.contains(table1)) {
                        System.out.println(l2file.getName() + " 包含: " + "(不含加密列)" + table + " ---- " + "(含加密列)" + table1);
                    }
                }
            }
        }
    }


    private static String getTableName(File file1) {
        return file1.getName().replace("prc_", "").replace(".sql", "");
    }
}
