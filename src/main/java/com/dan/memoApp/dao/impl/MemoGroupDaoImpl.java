package com.dan.memoApp.dao.impl;

import com.dan.memoApp.dao.MemoGroupDao;
import com.dan.memoApp.entity.MemoGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class MemoGroupDaoImpl implements MemoGroupDao {

    /**
     * 日志对象
     */
    private  final Logger logger=LoggerFactory.getLogger(MemoGroupDaoImpl.class);
    /**
     * 使用jdbc模板看不到样板代码，只是被隐藏到jdbc模板类里面了
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入便签组
     * @param memoGroup
     * @return
     */
    public int insertMemoGroup(MemoGroup memoGroup) {
        String sql="insert into memo_group ( name, created_time) VALUES  (?,?);";
      int effect= jdbcTemplate.update(sql,memoGroup.getName(),memoGroup.getCreateTime());
      return effect;
    }

    /**
     * 更新便签组
     * @param id
     * @param name
     * @return
     */
    public int updateMemoGroup(int id, String name) {
        String sql="update memo_group set name=? where id=?";
        int effect=jdbcTemplate.update(sql,name,id);
        return effect;
    }

    /**
     * 根据id查询便签组
     * queryForObject方法有三个参数
     * 第一个参数：String类型，要从数据库中查找数据的SQL
     * 第二个参数：RowMapper类型，用来从ResultSet中提取数据并构建成域对象（示例中的Book对象）
     * 第三个参数：可变参数列表，列出要绑定到查询上的索引参数值
     * @param id
     * @return
     */
    public List<MemoGroup> queryMemoGroup(int id) {
       String sql= "select id,  name, created_time, modify_time from memo_group where id = ?";
       List<MemoGroup> memoGroups=jdbcTemplate.query(sql,new Object[]{id},
               new RowMapper<MemoGroup>() {
                   public MemoGroup mapRow(ResultSet resultSet, int i) throws SQLException {
                       MemoGroup memoGroup=new MemoGroup();
                       memoGroup.setId(resultSet.getInt("id"));
                       memoGroup.setCreateTime(resultSet.getTime("created_time"));
                       memoGroup.setModifyTime(resultSet.getTime("modify_time"));
                       memoGroup.setName(resultSet.getString("name"));
                       return memoGroup;
                   }
               }
               );
       return memoGroups;
    }

    /**
     * 在一个时间段内查询所属便签组
     * @param startTime
     * @param endTime
     * @return
     */
    public List<MemoGroup> queryMemoGroupByCreatedTime(LocalDate startTime, LocalDate endTime) {
        String sql = "select id, name,created_time,modify_time from memo_group where created_time between ? and ? ";
        List<MemoGroup> memoGroups=jdbcTemplate.query(sql, new Object[]{startTime, endTime},
                new RowMapper<MemoGroup>() {
                    public MemoGroup mapRow(ResultSet rs, int i) throws SQLException {
                        MemoGroup memoGroup=new MemoGroup();
                        memoGroup.setId(rs.getInt("id"));
                        memoGroup.setCreateTime(rs.getTime("created_time"));
                        memoGroup.setModifyTime(rs.getTime("modify_time"));
                        memoGroup.setName(rs.getString("name"));
                        return memoGroup;
                    }
                }
        );
        return memoGroups;
    }

    /**
     * 根据id删除便签组
     * @param id
     * @return
     */
    public int deleteMemoGroup(int id) {
        String sql="delete from memo_group where id=?;";
        int effect=jdbcTemplate.update(sql,id);
        return effect;
    }

    /**
     * 查询name在数据库中出现了几次
     * @param name
     * @return
     */
    public int queryMemoGroupByNameCount(String name) {
        String sql = "select count(id) from memo_group where name=?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{name}, Integer.class);
        return count;
    }

    /**
     * 查询数据库中是否有此id,id出现了几次
     * @param id
     * @return
     */
    public int queryMemoGroupIdById(int id) {
        String sql="select count(id) from memo_group where id=?;";
       int result= jdbcTemplate.queryForObject(sql,new Object[]{id},Integer.class);
       return result;
    }


}
