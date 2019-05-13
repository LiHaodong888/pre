package com.xd.pre.mapper;

import com.xd.pre.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xd.pre.dto.UserDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Insert("insert into sys_user (username,password,salt,dept_id,phone,email,avatar,lock_flag) values (#{username},#{password},#{salt},#{deptId},#{phone},#{email},#{avatar},#{lockFlag})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    boolean insertUser(SysUser sysUser);

}
