package com.xd.pre.modules.sys.controller;


import com.xd.pre.common.utils.R;
import com.xd.pre.modules.sys.util.RedisUtil;
import com.xd.pre.modules.sys.vo.RedisVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * redis缓存管理 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-27
 */
@RestController
@RequestMapping("/redis")
public class SysRedisController {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取所有key
     * @param pageable
     * @return
     */
    @GetMapping
    public R getAllByPage(Pageable pageable) {
        List<RedisVo> redisList = redisUtil.getAll();
        int totalElements = redisList.size();
        if (pageable == null) {
            pageable = PageRequest.of(0, 10);
        }
        int fromIndex = pageable.getPageSize() * pageable.getPageNumber();
        int toIndex = pageable.getPageSize() * (pageable.getPageNumber() + 1);
        if (toIndex > totalElements) {
            toIndex = totalElements;
        }
        List<RedisVo> indexObjects = redisList.subList(fromIndex, toIndex);
        Page<RedisVo> page = new PageImpl<>(indexObjects, pageable, totalElements);
        return R.ok(page);
    }

    /**
     * 批量删除
     * @param keys
     * @return
     */
    @DeleteMapping("/delKeys")
    public R delByKeys(@RequestBody List<String> keys) {
        return R.ok(redisUtil.removeKey(keys));
    }

}

