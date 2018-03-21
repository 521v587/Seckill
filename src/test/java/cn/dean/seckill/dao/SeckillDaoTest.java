package cn.dean.seckill.dao;

import cn.dean.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * junit与Spring整合，类加载时启动实现SpringIOC注入
 * spring-test，junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
/**
 * 告诉junit加载spring配置文件
 */
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() {
        long id = 1000L;
        int number = seckillDao.reduceNumber(id, new Date());
        System.out.println(number);

    }

    @Test
    public void queryById() {
        long id = 1000L;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill);

    }

    @Test
    public void queryAll() {
        List<Seckill> seckillList = seckillDao.queryAll(0, 4);
        for (Seckill seckill : seckillList) {
            System.out.println(seckill);
        }
    }
}