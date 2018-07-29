package com.dan.memoApp.service;

import com.dan.memoApp.entity.MemoGroup;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface MemoGroupService {

    /**
     * 插入一个便签组
     *
     * @param memoGroup
     * @return
     */
    boolean createMemoGroup(MemoGroup memoGroup);

    /**
     * 修改便签组的名称
     *
     * @param id
     * @param name
     * @return
     */
    boolean updateMemoGroup(int id, String name);

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
    boolean deleteMemoGroup(int id);


}
