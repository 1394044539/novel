package wpy.personal.novel;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wpy.personal.novel.utils.RedisUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
class NovelApplicationTests {

    @Test
    void contextLoads() {
        RedisUtils.setObject("test",123);
    }

}
