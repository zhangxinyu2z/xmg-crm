package com._520it.crm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.Date;


@Getter
@Setter
@Alias("DiskFile")
@ToString(exclude = {"user"})
public class NetworkDisk {

    private Long id;

    private String name;

    private String path;

    private boolean dir;

    private Employee user;

    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private Date uploadtime;

    private Long parentId;

    private String type;

    private boolean pub;


}