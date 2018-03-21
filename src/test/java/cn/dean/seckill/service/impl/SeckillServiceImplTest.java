package cn.dean.seckill.service.impl;

import cn.dean.seckill.dto.Exposure;
import cn.dean.seckill.dto.SeckillExecution;
import cn.dean.seckill.entity.Seckill;
import cn.dean.seckill.exception.RepeatSeckillException;
import cn.dean.seckill.exception.SeckillCloseException;
import cn.dean.seckill.exception.SeckillException;
import cn.dean.seckill.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {
    @Autowired
    private SeckillService seckillService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void getById() {
        Seckill seckill = seckillService.getById(1000L);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void getSeckillList() {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("seckillList={}", seckillList);

    }

    @Test
    public void exportSeckillUrl() {
        long seckillId = 1001L;
        Exposure exposure = seckillService.exportSeckillUrl(seckillId);
        if (exposure.isExposed()) {
            logger.info("exposure={}", exposure);
            long userPhone = 18511242886L;
            String md5 = exposure.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
                logger.info("seckillExecution={}", seckillExecution);
            } catch (SeckillCloseException ex1) {
                logger.error(ex1.getMessage());
            } catch (RepeatSeckillException ex2) {
                logger.error(ex2.getMessage());
            } catch (SeckillException ex3) {
                logger.error(ex3.getMessage());
            }
        }

    }
}