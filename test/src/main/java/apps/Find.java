package apps;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import utils.TextUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static utils.TextUtil.getLines;

public class Find {


    private static void extracted(String sql) {

        String dbType = JdbcConstants.MYSQL;

        String result = SQLUtils.format(sql, dbType);
        System.out.println(result); // 缺省大写格式

        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);

        System.out.println("size is:" + stmtList.size());
        for (int i = 0; i < stmtList.size(); i++) {
            SQLStatement stmt = stmtList.get(i);
            MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
            stmt.accept(visitor);
            //获取表名称
            System.out.println("Tables : " + visitor.getCurrentTable());
            //获取操作方法名称,依赖于表名称
            System.out.println("Manipulation : " + visitor.getTables());
            //获取字段名称
            System.out.println("fields : " + visitor.getColumns());
            System.out.println("Conditions:" + visitor.getConditions());
        }


    }

//    String[] s1 = new String[]{"fin_prd_crse_attr", "fin_prd_valu", "fin_prd_vou_min", "fin_prd_balance",
//            "fin_prd_crse_code_covt", "fin_prd_hld_info", "fin_prd_profit", "fin_prd_astrd_trd", "fin_prd_dpsi_pro", "fin_prd_repo_plge_pro", "fin_prd_dpsi_hld"};
//

    public static void main(String[] args) throws IOException {

        String path = "D:\\IdeaProjects\\xc-computation-engine\\conf\\sql\\cms\\inceptor\\dml\\level1\\";
        // 获取当前目录下的所有文件
        File file = new File(path);
        File[] tempList = file.listFiles();

        // 得到要加密列
        String filePath1 = "D:\\IdeaProjects\\test\\src\\main\\resources\\column.txt";
        List<String> columns = getLines(filePath1);

        Stream<String> distinct = columns.stream().distinct();
        distinct.count();

        // 去重
        LinkedHashSet<String> dstColumns = new LinkedHashSet<>(columns.size());
        dstColumns.addAll(columns);
        System.out.println("加密的字段:" + dstColumns.toString());

        // 对每个文件进行文本处理
        for (File file1 : tempList) {
            Map<String, Integer> linesAndNo = TextUtil.getLinesAndNo(file1.toString());
            Map<String, String> position = new HashMap<String, String>();
            Set<String> lines = linesAndNo.keySet();
            for (String line : lines) {
                for (String column : dstColumns) {
                    column = column.toLowerCase();
                    String regex = column.toLowerCase() + "\\s{0,}(in|not in)\\s{0,}\\(";
                    //  String regex = column.toLowerCase()+" in \\(('.{1,}'),{0}\\)";
                    findRegex(linesAndNo, position, line, column, regex);
                    findRegex(linesAndNo, position, line, column, "\\s{0,}=\\s{0,}'");
                    findRegex(linesAndNo, position, line, column,"'.{1,}'\\s{0,}(as|AS){0,}\\s{0,}");
                }
            }

            ArrayList<String> values = new ArrayList<String>();

            for (String s : position.keySet()) {
                values.add(position.get(s));
            }

            if (!position.isEmpty()) {
                System.out.println(file1.getName() + "需修改" + position.keySet().toString());
            } else {
//                System.out.println(file1.getName() + "不存在加密字段");
            }


            // 得到每个文件的select语句
//            String extracted = extracted(file1, result);
//            for (String column : columns) {
//                if (extracted.contains(column)) {
//                    System.out.println(extracted);
//                    System.out.println(column+"："+extracted.indexOf(column,0));
//                }
//            }
        }
    }

    private static void findRegex(Map<String, Integer> linesAndNo, Map<String, String> position, String line, String column, String s2) {
        String regex = column + s2;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line.toLowerCase());
        if (m.find()) {
            position.put(linesAndNo.get(line) + "", column);
        }
    }
//    }

    private static String extracted(File file1, String result) {
        String regex = "(select)([^;]*)(;)";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(result);
        String value = "";
        if (matcher.find()) {
            value = matcher.group();
            //   System.out.println(file1.getName() + ":中的sql语句：" + value);
        }
        return value;
    }


}


