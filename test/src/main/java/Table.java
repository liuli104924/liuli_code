import utils.TextUtil;

import java.io.File;
import java.util.Locale;

public class Table {


    private static boolean findTable(File file, String regex) {
        String allText = TextUtil.getAllText(file);
        String allTextToLow = allText.toLowerCase(Locale.ROOT);
        boolean contains = allTextToLow.contains(regex);

        return contains;
    }


    public static void main(String[] args) {
// EXCH_CODE, TRD_CODE, TRD_MKT, SECU_CODE, SECU_NAME, SECU_ID, CHI_NAME, PLEDG_SECU_ID, PLEDG_SECU_NAME
        String path = "D:\\IdeaProjects\\xc-computation-engine\\conf\\sql\\cms\\inceptor\\dml\\level2\\";
//        // 获取当前目录下的所有文件
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (File file1 : tempList) {
            if (findTable(file1, "mid_l1_cal_bond   ".toLowerCase(Locale.ROOT))) {
                System.out.println(file1.getName());
            }
        }
    }
}
