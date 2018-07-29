package com.dan.memoApp.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
public class MemoGroup {
    private Integer id;
    private String name;
    private Date createTime;
    private Date modifyTime;
}
