package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.ArrayList;


public class IoUtil {


    public static void mkdir() {
        ArrayList<File> files = new ArrayList<>();
        File f1 = new File("./conf/sql/cms/tag/update/inceptor/ddl/level1/");
        File f2 = new File("./conf/sql/cms/tag/update/inceptor/ddl/level2/");
        File f3 = new File("./conf/sql/cms/tag/update/inceptor/ddl/level3/");
        File f4 = new File("./conf/sql/cms/tag/update/inceptor/dml/level1/");
        File f5 = new File("./conf/sql/cms/tag/update/inceptor/dml/level2/");
        files.add(f1);
        files.add(f2);
        files.add(f3);
        files.add(f4);
        files.add(f5);

        for (File file : files) {
            if (!file.exists()) {
                file.mkdirs();
                System.out.println("创建成功");
            }
        }
    }


    public static  void mvFile(){
        File file = new File("./conf/sql/cms/tag/update/inceptor/dml/level2/prc_mid_l2_basi.sql");
        File file2 = new File("./conf/sql/cms/tag/update/inceptor/ddl/level2/prc_mid_l2_basi.sql");
        if(file2.exists()){
            file2.delete();
        }

        try {
            Files.copy(file.toPath(),file2.toPath());
        } catch (IOException e) {
            System.out.println("io err");
        }


    }


    public static void main(String[] args) {//主程序，程序入口
        mkdir();

//        File file=new File("D:\\Qiju_Li");
//        if(!file.exists()){//如果文件夹不存在
//            file.mkdir();//创建文件夹
//        }
//        try{//异常处理
//            //如果Qiju_Li文件夹下没有Qiju_Li.txt就会创建该文件
//            BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\Qiju_Li\\Qiju_Li.txt"));
//            bw.write("Hello I/O!");//在创建好的文件中写入"Hello I/O"
//            bw.close();//一定要关闭文件
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }
    }
}