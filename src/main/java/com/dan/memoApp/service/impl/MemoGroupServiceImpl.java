package com.dan.memoApp.service.impl;

import com.dan.memoApp.dao.MemoGroupDao;
import com.dan.memoApp.entity.MemoGroup;
import com.dan.memoApp.service.MemoGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional//类级注解，适用于类中所有public的方法
public class MemoGroupServiceImpl implements MemoGroupService {

    /**
     * 数据层接口，尽量不要传实现类
     */
    @Autowired
    private MemoGroupDao memoGroupDao;

    /**
     * 插入一个便签组
     *
     * @param memoGroup
     * @return
     */
    public boolean createMemoGroup(MemoGroup memoGroup) {
        if (memoGroup == null) {
            throw new IllegalArgumentException("memoGroup arguments should not null");
        }
        if (StringUtils.isEmpty(memoGroup.getName())) {
            throw new IllegalArgumentException("memoGroup arguments name is IllegalArgument");
        }
        if (StringUtils.isEmpty(memoGroup.getCreateTime())) {
            throw new IllegalArgumentException("memoGroup arguments createTime is IllegalArgument");
        }
        int count = memoGroupDao.queryMemoGroupByNameCount(memoGroup.getName());
        if (count > 0) {
            throw new RuntimeException("MemoGroup name already exists..");
        } else {
            int effect = memoGroupDao.insertMemoGroup(memoGroup);
           return effect==1;
        }
    }

    /**
     * 根据id更新便签组
     *
     * @param id
     * @param name
     * @return
     */
    public boolean updateMemoGroup(int id, String name) {
        if (name == null || name.length() < 1) {
            throw new IllegalArgumentException("name argument should not null");
        }
        int count = memoGroupDao.queryMemoGroupIdById(id);
        if (count == 0) {
            throw new IllegalArgumentException("id argument is not exist");
        }
        if (memoGroupDao.queryMemoGroupByNameCount(name) > 1) {
            throw new RuntimeException("argument name already exists..");
        } else {
            int effect = memoGroupDao.updateMemoGroup(id, name);
           return effect==1;
        }
    }

    /**
     * 根据id查询便签组
     *
     * @param id
     * @return
     */
    @Transactional(transactionManager = "transactionManager",readOnly = true)
    public List<MemoGroup> queryMemoGroup(int id) {
        int count = memoGroupDao.queryMemoGroupIdById(id);
        if (id < 0) {
            throw new IllegalArgumentException("arguments id is IllegalArgument..");
        }
        if (count == 0) {
            throw new IllegalArgumentException("id argument is not exist");
        } else {
            List<MemoGroup> memoGroups = memoGroupDao.queryMemoGroup(id);
            return memoGroups;
        }
    }

    /**
     * 查询一个时间段创建的便签组
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<MemoGroup> queryMemoGroupByCreatedTime(LocalDate startTime, LocalDate endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("argument should not null");
        } else {
            List<MemoGroup> memoGroups = memoGroupDao.queryMemoGroupByCreatedTime(startTime, endTime);
            return memoGroups;
        }
    }

    /**
     * 根据id删除便签组
     * @param id
     * @return
     */
    public boolean deleteMemoGroup(int id) {
        //判断id是否是有效id
        int count = memoGroupDao.queryMemoGroupIdById(id);
        if (id < 0) {
            throw new IllegalArgumentException("arguments id is  IllegalArgument..");
        }
        if (count == 0) {
            throw new IllegalArgumentException("id argument is not exist");
        }else {
            int ret = memoGroupDao.deleteMemoGroup(id);
            return ret==1;
        }
    }
}
