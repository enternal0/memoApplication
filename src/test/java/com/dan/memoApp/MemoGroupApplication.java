package com.dan.memoApp;

import com.dan.memoApp.entity.MemoGroup;
import com.dan.memoApp.service.MemoGroupService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.CallableStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MemoGroupApplication {

    private static ApplicationContext context;
    private static MemoGroupService memoGroupService;
    public static Logger logger=LoggerFactory.getLogger(MemoGroupApplication.class);

    @BeforeClass
    public static void classBefore(){
        context=new ClassPathXmlApplicationContext("application-context.xml");
        memoGroupService= context.getBean(MemoGroupService.class);
    }

       /**
         * 测试插入一个便签组
        */
       @Test
    public void test_createMemoGroup() throws ParseException {
//        MemoGroup memoGroup=new MemoGroup();
//        memoGroup.setName("G");
//        memoGroup.setCreateTime(new Date());
//        memoGroup.setModifyTime(new Date());
//        boolean res=memoGroupService.createMemoGroup(memoGroup);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2018-8-1");
        System.out.println(date);
//        Assert.assertTrue(res);

    }

    /**
     * 测试根据id更新便签组
     */
    @Test
    public void test_updateMemoGroup(){
      boolean res=  memoGroupService.updateMemoGroup(3,"French");
       Assert.assertTrue(res);
    }

    /**
     * 测试根据id查询便签组
     */
    @Test
    public void test_queryMemoGroup(){
     List<MemoGroup> memoGroups= memoGroupService.queryMemoGroup(-1);
        Assert.assertNotNull(memoGroups);
        logger.debug("query memoGroup  detail id={} value={}", -1, memoGroups);
    }

    /**
     * 测试根据id删除便签组
     */
    @Test
    public void test_deleteMemoGroup(){
       boolean ret= memoGroupService.deleteMemoGroup(16);
        memoGroupService.deleteMemoGroup(17);
        memoGroupService.deleteMemoGroup(18);
       Assert.assertTrue(ret);
    }

    @Test
    public void test_queryMemoGroupByCreatedTime() {
        LocalDate startTime = LocalDate.of(2018, 7, 3);
        LocalDate endTime = LocalDate.of(2018, 7, 12);
        List<MemoGroup> memoGroups = memoGroupService.queryMemoGroupByCreatedTime(startTime, endTime);
        if (memoGroups == null) {
            logger.debug("memoGroups is null,query is not exist");
        } else {
            logger.debug("queryMemoGroup result details startTime={} endTime={} memoGroups={} ", startTime, endTime, memoGroups);
        }
    }




}
