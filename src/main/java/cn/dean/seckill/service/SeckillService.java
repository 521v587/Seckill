package cn.dean.seckill.service;

import cn.dean.seckill.dto.Exposure;
import cn.dean.seckill.dto.SeckillExecution;
import cn.dean.seckill.entity.Seckill;
import cn.dean.seckill.exception.RepeatSeckillException;
import cn.dean.seckill.exception.SeckillCloseException;
import cn.dean.seckill.exception.SeckillException;

import java.util.List;

/**
 * 站在用户的角度去设计接口
 * 注意三点，粒度，参数，返回值
 */
public interface SeckillService {

    /**
     * 查询单个秒杀商品
     *
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 查询秒杀商品列表
     *
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 秒杀开启输出秒杀地址
     * 秒杀未开启输出系统时间和秒杀时间
     *
     * @param seckillId
     * @return
     */
    Exposure exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException, RepeatSeckillException, SeckillCloseException;
}
