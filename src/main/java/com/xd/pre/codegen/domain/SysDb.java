package com.xd.pre.codegen.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据库管理
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class SysDb implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据库类型
     */
    private String dbType;

    /**
     * 数据库地址
     */
    private Long host;

    /**
     * 数据库端口
     */
    private Integer port;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    /**
     * @param host
     * @param port
     * @param dbName
     * @param userName
     * @param password
     */
    public SysDb(String dbType, Long host, int port, String dbName, String userName, String password) {
        super();
        this.dbType = dbType;
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.username = userName;
        this.password = password;
    }


}
