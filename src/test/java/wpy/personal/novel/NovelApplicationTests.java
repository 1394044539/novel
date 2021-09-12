package wpy.personal.novel;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wpy.personal.novel.novel.system.service.SysUserService;
import wpy.personal.novel.pojo.dto.SysUserDto;
import wpy.personal.novel.pojo.entity.SysUser;
import wpy.personal.novel.utils.RedisUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
class NovelApplicationTests {

    @Autowired
    private SysUserService sysUserService;

    @Test
    void contextLoads() {
        RedisUtils.setObject("test",123);
    }

    @Test
    void createSuperAdmin(){

        SysUserDto sysUserDto = new SysUserDto();
        sysUserDto.setAccountName("superadmin");
        sysUserDto.setUserName("超级管理员");
        sysUserDto.setPassword("admin123");

        SysUser sysUser = new SysUser();
        sysUser.setUserId("System");
        sysUserService.addUser(sysUserDto,sysUser);
    }

}
