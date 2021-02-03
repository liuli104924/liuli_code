package apps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import static utils.TextUtil.getLines;

public class ddl {

    private void RemoveFile(String fileName, String destinationFloderUrl) {
        File file = new File(fileName);
        File destFloder = new File(destinationFloderUrl);
        boolean b = file.renameTo(destFloder);


//        //检查目标路径是否合法
//        if(destFloder.exists())
//        {
//            if(destFloder.isFile())
//            {
//                System.out.println("目标路径是个文件，请检查目标路径！");
//                return false;
//            }
//        }else
//        {
//            if(!destFloder.mkdirs())
//            {
//                System.out.println("目标文件夹不存在，创建失败！");
//                return false;
//            }
//        }
//        //检查源文件是否合法
//        if(file.isFile() &&file.exists())
//        {
//            String destinationFile = destinationFloderUrl+"/"+file.getName();
//            if(!file.renameTo(new File(destinationFile)))
//            {
//                System.out.println("移动文件失败！");
//                return false;
//            }
//        }else
//        {
//            System.out.println("要备份的文件路径不正确，移动失败！");
//            return false;
//        }
//        System.out.println("已成功移动文件"+file.getName()+"到"+destinationFloderUrl);
//        return true;
    }


    public static void main(String[] args) {

        // 得到要加密列
        String filePath1 = "D:\\IdeaProjects\\test\\src\\main\\resources\\ddl.txt";
        List<String> columns = getLines(filePath1);
        String inceptorSql;
        String oracleSql;
        String dbName;
        String updatePath = "D:\\IdeaProjects\\xc-computation-engine\\conf\\sql\\cms\\tag\\update\\inceptor\\ddl\\";
        String outPutfileName = null;
        String outPutfileOralceName = null;
        ArrayList<String> inceptprSqls = new ArrayList<>();
        ArrayList<String> oracleSqls = new ArrayList<>();

        for (String column : columns) {
            String[] split = column.split("\t");
            outPutfileName = split[0].toLowerCase(Locale.ROOT) + ".sql";
            if (split.length != 4) {
                System.out.println("输入参数不合理" + "\n" + "检查改行：" + column);
                return;
            }

            // 判断库名 插入路径
            if (split[0].toLowerCase(Locale.ROOT).contains("l2")) {
                dbName = "kbs";
                updatePath += "leve2\\";
                outPutfileOralceName=split[0].toLowerCase(Locale.ROOT)+"+oralce" + ".sql";
            } else if (split[0].toLowerCase(Locale.ROOT).contains("l1")) {
                dbName = "mod";
                updatePath += "leve1\\";
            } else {
                dbName = "agg";
                updatePath += "leve0\\";
            }

            // 生成 update sql 语句
            inceptorSql = "alter table " + dbName + "." + split[0] + " add columns( " + split[2] + " " + split[3] + " comment " + "'" + split[1] + "');".toLowerCase(Locale.ROOT);
            inceptprSqls.add(inceptorSql);
            System.out.println(inceptorSql);

            oracleSql = ("alter table " + split[0] + " add(" + split[2] + " " + split[3] + " );\n" + "comment on column " + split[0] + "." + split[2] + " is " + "'" + split[1] + "';").toLowerCase(Locale.ROOT);
            oracleSqls.add(oracleSql);
            System.out.println(oracleSql);
            //    String fileName = updatePath + split[0].toLowerCase(Locale.ROOT) + ".sql";

        }

        sqlTofile(outPutfileName, inceptprSqls);

        sqlTofile(outPutfileOralceName, oracleSqls);


    }

    private static void sqlTofile(String outPutfileName, ArrayList<String> inceptprSqls) {
        File testFile = new File("C:\\Users\\admin\\Desktop\\" + outPutfileName);
        // 将updatesql插入文件.
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(testFile);
            PrintStream ps = new PrintStream(fileOutputStream);
            for (String s : inceptprSqls) {
                ps.println(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
