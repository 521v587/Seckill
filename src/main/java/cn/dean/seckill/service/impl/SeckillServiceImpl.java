package cn.dean.seckill.service.impl;

import cn.dean.seckill.dao.SeckillDao;
import cn.dean.seckill.dao.SuccessKilledDao;
import cn.dean.seckill.dto.Exposure;
import cn.dean.seckill.dto.SeckillExecution;
import cn.dean.seckill.entity.Seckill;
import cn.dean.seckill.entity.SuccessKilled;
import cn.dean.seckill.enums.SeckillStatEnum;
import cn.dean.seckill.exception.RepeatSeckillException;
import cn.dean.seckill.exception.SeckillCloseException;
import cn.dean.seckill.exception.SeckillException;
import cn.dean.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    private final String slat = "asjfsodkfsaeojrewf464fasdfs";

    public Seckill getById(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        return seckill;
    }

    public List<Seckill> getSeckillList() {
        List<Seckill> seckillList = seckillDao.queryAll(0, 4);
        return seckillList;
    }

    /**
     * 秒杀开启时，暴露秒杀地址
     * 秒杀未开始时，输出秒杀开始时间和当前时间
     *
     * @param seckillId
     * @return
     */
    public Exposure exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        //判断seckill是否为空
        if (seckill == null) {
            return new Exposure(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();

        //当前时间
        Date nowTime = new Date();
        //判断是否处在秒杀期间
        if (startTime.getTime() > nowTime.getTime() || endTime.getTime() < nowTime.getTime()) {
            //不在秒杀期间内
            return new Exposure(false, seckillId,
                    startTime.getTime(), endTime.getTime(), nowTime.getTime());
        }
        //获得md5，不可逆
        String md5 = getMD5(seckillId);
        return new Exposure(true, seckillId, md5);
    }

    //获得加密的MD5
    private String getMD5(long seckillId) {
        //防止通过md5算法破解
        String base = seckillId + "/" + slat;
        //Spring框架自带md5加密功能
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatSeckillException
     * @throws SeckillCloseException
     */
    @Transactional
    /**
     * 使用注解控制事务的优点：
     * 1.开发团队达成一致的约定，明确标注事务方法的声明规则；
     * 2.保证事务的执行时间尽可能短，尽量避免使用网络操作HTTP/RPC请求或剥离到事务方法外部；
     * 3.不是所有的方法都需要声明事务，如只有一条修改操作，只读操作等。
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws RepeatSeckillException, SeckillCloseException, SeckillException {
        //判断md5是否正确
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑:减库存+记录用户购买行为
        Date nowTime = new Date();
        try {
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                //秒杀结束
                throw new SeckillCloseException("seckill is closed");
            } else {
                //记录用户购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatSeckillException("seckill repeated");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException ex1) {
            throw ex1;
        } catch (RepeatSeckillException ex2) {
            throw ex2;
        } catch (Exception ex) {
            //logger.error(ex.printStackTrace(),ex);
            //把所有编译时异常转化成运行时异常
            throw new SeckillException("seckill inner error:" + ex.getMessage());
        }
    }
}
