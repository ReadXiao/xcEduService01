package com.xuecheng.ucenter;

import com.xuecheng.framework.domain.ucenter.XcMenu;
import com.xuecheng.ucenter.dao.XcMenuMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 肖宏武
 * @date 2021/2/19 - 19:50
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class servcieTest {
    @Resource
    private XcMenuMapper xcMenuMapper;

    @Test
    public void xcMenuMapperTest(){
        String id = "49";
        List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserId(id);
        System.out.println(xcMenus);
    }
}
