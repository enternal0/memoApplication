package com.dan.memoApp.dao;

import com.dan.memoApp.entity.MemoGroup;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface MemoGroupDao {

    /**
     * 插入一个便签组
     *
     * @param memoGroup
     * @return
     */
    int insertMemoGroup(MemoGroup memoGroup);

    /**
     * 修改便签组的名称
     *
     * @param id
     * @param name
     * @return
     */
    int updateMemoGroup(int id, String name);

    /**
     * 根据Id查询便签组信息
     *
     * @param id
     * @return
     */
    List<MemoGroup> queryMemoGroup(int id);

    /**
     * 根据时间范围查询便签信息
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<MemoGroup> queryMemoGroupByCreatedTime(LocalDate startTime, LocalDate endTime);

    /**
     * 根据Id删除便签组
     *
     * @param id
     * @return
     */
    int deleteMemoGroup(int id);

    /**
     * 查询便签名字是否重复
     * @param name
     * @return
     */
    int queryMemoGroupByNameCount(String name);

    /**
     * 查询数据库中是否有此id
     * @param id
     * @return
     */
    int queryMemoGroupIdById(int id);
}