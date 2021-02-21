package com.xuecheng.ucenter.dao;

import com.xuecheng.framework.domain.ucenter.XcMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 肖宏武
 * @date 2021/2/19 - 19:14
 */
@Component
@Mapper
public interface XcMenuMapper {
    //根据用户id查询用户权限
    List<XcMenu> selectPermissionByUserId(String userId);
}
