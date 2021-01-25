import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class test1 {

    public static void main(String[] args) {


        File testFile = new File("C:\\Users\\admin\\Desktop\\mid_l2_bond+oralce.sql");

        String fromPath = "C:\\Users\\admin\\Desktop\\mid_l2_bond+oralce.sql";
        String toPath = "C:\\Users\\admin\\Desktop\\mid_l2_bond+oralce.sql1";

        Path fromPath1 = Paths.get(fromPath);
        Path toPath1 = Paths.get(toPath);

        File file = new File(toPath);
        if (file.exists()) {
            file.delete();
            System.out.println(toPath + "----目标文件存在" + "已删除");
        }

        try {
            Files.copy(fromPath1, toPath1);
            System.out.println(fromPath + "   复制到：   " + toPath + "  成功!!!");
        } catch (IOException e) {
            System.out.println("io err !!! cp 失败!!!");
        }

    }
}
