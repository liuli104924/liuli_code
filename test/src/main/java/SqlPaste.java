//import com.alibaba.druid.sql.SQLUtils;
//import com.alibaba.druid.sql.ast.SQLStatement;
//import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
//import com.alibaba.druid.util.JdbcConstants;
//
//import java.util.List;
//
//public class SqlPaste {
//
//    public static void main(String[] args) {
//
//
//        String sql =
//                "select\n" +
//                "  cast('${hivevar:tradingday}' as bigint) as busi_date,    --业务日期\n" +
//                "  cast('${hivevar:tradingday}' as bigint) as t_date,    --计算日期\n" +
//                "  secu_id,    --证券编码\n" +
//                "  null as upd_time,    --源数据修改时间\n" +
//                "  'prc_ast_prd_secu_real_inm_secuid' as task_rs_id,    --任务来源标识\n" +
//                "  'dws_smr.mid_l1_cal_prd_dtl' as dm_src_info,\t    --来源信息\n" +
//                "  current_timestamp() as dm_created_time,    --数据中台创建时间\n" +
//                "  'OUTLINE' as dm_created_by,    --数据中台创建者\n" +
//                "  current_timestamp() as dm_updated_time,    --数据中台更新时间\n" +
//                "  'OUTLINE' as dm_updated_by,    --数据中台更新者\n" +
//                "  '${hivevar:tradingday}' as p_dt\n" +
//                "  from\n" +
//                "  dws_smr.mid_l1_cal_prd_dtl \n" +
//                "  where t_date= '${hivevar:tradingday}'      group by secu_id ;";
//        extracted(sql);
//
//    }
//
//    private static void extracted(String sql) {
//
//        String dbType = JdbcConstants.HIVE;
//
//        String result = SQLUtils.format(sql, dbType);
//        System.out.println(result); // 缺省大写格式
//
//        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
//
//        System.out.println("size is:" + stmtList.size());
//        for (int i = 0; i < stmtList.size(); i++) {
//
//            SQLStatement stmt = stmtList.get(i);
//            MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
//            stmt.accept(visitor);
//            //获取表名称
//            System.out.println("Tables : " + visitor.getCurrentTable());
//            //获取操作方法名称,依赖于表名称
//            System.out.println("Manipulation : " + visitor.getTables());
//            //获取字段名称
//            System.out.println("fields : " + visitor.getColumns());
//            System.out.println("Conditions:"+visitor.getConditions());
//        }
//    }
//
//}
