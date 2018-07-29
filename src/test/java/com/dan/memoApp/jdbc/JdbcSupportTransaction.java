package com.dan.memoApp.jdbc;


import com.dan.memoApp.entity.MemoGroup;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcSupportTransaction {

    private static ApplicationContext context;
    private static DataSource dataSource;

    @BeforeClass
    public static void beforeClass() {
        context = new ClassPathXmlApplicationContext("application-context.xml");
        dataSource = (DataSource) context.getBean("dataSource");
    }

    @Test
    public void test_metaData() {
        try {
            Connection connection = dataSource.getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println(databaseMetaData.supportsANSI92FullSQL());
            System.out.println(databaseMetaData.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED));
            System.out.println(databaseMetaData.supportsTransactions());
            Statement statement = connection.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Spring提供的事务模板类   编程式事务管理
     * 通过TransactionTemplate并配合使用事务回调TransactionCall指定的具体的持
     * 久化操作，就可以通过编程式实现事务的管理（即：编程式事务管理），无需关注资源的获取，复用，释
     * 放，事务同步和异常处理等操作
     */
    @Test
    public void test_TransactionTemplate() {
        TransactionTemplate transactionTemplate = (TransactionTemplate) context.getBean("transactionTemplate");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
        transactionTemplate.execute(new TransactionCallback<Object>() {
            public Object doInTransaction(TransactionStatus status) {
                //在此进行数据库操作    此为一组sql语句
                MemoGroup memoGroup = new MemoGroup();
                memoGroup.setName("D");
                memoGroup.setCreateTime(new Date(2018 - 7 - 26));
                String sql = "insert into memo_group ( name, created_time) VALUES  (?,?);";
                jdbcTemplate.update(sql, memoGroup.getName(), memoGroup.getCreateTime());

                memoGroup.setName("D");
                memoGroup.setCreateTime(new Date(2018 - 7 - 26));
                String sql1 = "insert into memo_group ( name, created_time) VALUES  (?,?);";
                jdbcTemplate.update(sql1, memoGroup.getName(), memoGroup.getCreateTime());

                DataSourceUtils.getConnection(dataSource);
                return null;
            }
        });
    }

/**
 * Spring  声明式事务管理是通过Spring的Aop实现的   两种方法  xml配置或者注解
 */

}
