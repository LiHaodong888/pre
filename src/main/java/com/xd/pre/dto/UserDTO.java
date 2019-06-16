package com.xd.pre.dto;

import lombok.Data;

import java.util.List;

/**
 * @Classname UserDTO
 * @Description 用户Dto
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-23 21:26
 * @Version 1.0
 */
@Data
public class UserDTO {

    private Integer userId;
    private String username;
    private String password;
    private Integer deptId;
    private Integer jobId;
    private String phone;
    private String email;
    private String avatar;
    private String lockFlag;
    private String delFlag;
    private List<Integer> roleList;
    private List<Integer> deptList;
    /**
     * 新密码
     */
    private String newPassword;
}
