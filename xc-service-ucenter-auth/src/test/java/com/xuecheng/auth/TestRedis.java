package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //创建jwt令牌
    @Test
    public void testRedis(){
        //定义key
        String key = "user_token:4003adb2-7797-478e-8d1b-4be51c822e38";
        //定义value
        Map<String,String> value = new HashMap<>();
        value.put("jwt","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6Iml0Y2FzdCIsInNjb3BlIjpbImFwcCJdLCJuYW1lIjpudWxsLCJ1dHlwZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTYxMjg4ODE3OSwianRpIjoiNDAwM2FkYjItNzc5Ny00NzhlLThkMWItNGJlNTFjODIyZTM4IiwiY2xpZW50X2lkIjoiWGNXZWJBcHAifQ.ewJBmCdgRrLrkESdFBhwQG3mKxEXFoeXJ59zSdV2JRAqYFDmZ6G4IqfoiKSniDmimq91vu0KQZadfan9RA1Bih-vJPeWSxK5i48BwhWaW8y4SepRcGHTGXrgySnt_o_HqVUPq60vYKzH8verZG9_ZsQx8J-577I-MmwZLPKb_FTDNLAdPtaKaYEVJ6dNU3TMYGxchlIJxhVts7HfKwfX0w5fEJ9tF15yDYC7_rx3qT6_JEjuASPBp1k5v8tLkNvLZX6DMkNno_n0NFNlNIF8jh9s5nhagBmY614GcrMumMGYqQa1NoRvKp4VRD76dN9Y5aAfkVdFovFvIyEnUgr5fA");
        value.put("refresh_token","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6Iml0Y2FzdCIsInNjb3BlIjpbImFwcCJdLCJhdGkiOiI0MDAzYWRiMi03Nzk3LTQ3OGUtOGQxYi00YmU1MWM4MjJlMzgiLCJuYW1lIjpudWxsLCJ1dHlwZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTYxMjg4ODE3OSwianRpIjoiNGY3MWZmZWMtZWQwNi00OWI3LTkxNjgtM2I4MzMyNTQ4OGQwIiwiY2xpZW50X2lkIjoiWGNXZWJBcHAifQ.EC8U0Mc0HWbC_Xg3JYM6gwKqJJlN9frHHqVlULySWSl-oHHWiIRGWT339b-sJBJL8FjhTQMOtq5_zGYdwskI-5iVHulmj0EYXMG_amhw3NrrO29k6E5p5g29tHZEfIxJaYCBJJjfpKTw9Y029v_vhhhjMWQT2vs2tgpQ1Yjh3Aclsvu9iGbI3B_MqRu6zC3-0bRR1eBLQZO8KKP4w5lXyFU3ZxNw4n_k1cPG7AW7eFtDHPBoMe1anp0CjBO4NswAvF2ZRXJtzcpx89jzCI68F1WLTRGwSDFB-IzFxX2XYFADDRlXeBMzNlYjp0UczL-6AZ_DtraPAcADVt2NB21rbw");
        String jsonString = JSON.toJSONString(value);
        //校验key是否存在，如果不存在则返回-2
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        System.out.println(expire);
        //存储数据
        stringRedisTemplate.boundValueOps(key).set(jsonString,30, TimeUnit.SECONDS);
        //获取数据
        String string = stringRedisTemplate.opsForValue().get(key);
        System.out.println(string);


    }


}
