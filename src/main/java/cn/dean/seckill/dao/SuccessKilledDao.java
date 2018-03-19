package cn.dean.seckill.dao;

import cn.dean.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

public interface SuccessKilledDao {

    /**
     * 插入购买明细，根据联合主键seckillId和userPhone可以过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     * 插入的行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据id查询秒杀的商品并携带商品信息
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
