package utils;

import java.sql.*;
/**
 * Created by 10412 on 2016/12/27.
 * JDBC的六大步骤
 * JAVA连接Oracle的三种方式
 */
public class JdbcTest
{

        Connection ct = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

	public JdbcTest() {
            try {
                // 1.加载驱动
                Class.forName("oracle.jdbc.driver.OracleDriver");
                // 2.得到连接
                ct = DriverManager.getConnection(
                        "jdbc:oracle:thin:@127.0.0.1:1521:Switch", "scott",
                        "123456");
                // 3.创建PreparedStatement
                ps = ct.prepareStatement("select * from emp");
                // 4.执行SQL
                rs = ps.executeQuery();
                // 5.对获取的数据进行操作
                while (rs.next()) {
                    System.out.println("员工名：" + rs.getString("ename"));
                }
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                    if (ct != null) {
                        ct.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }






}