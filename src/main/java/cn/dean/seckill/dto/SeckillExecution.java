package cn.dean.seckill.dto;

import cn.dean.seckill.entity.SuccessKilled;

public class SeckillExecution {
    //秒杀商品id
    private long seckillId;
    //秒杀执行结果状态
    private int state;
    //秒杀执行结果状态描述
    private String stateInfo;
    //秒杀成功对象
    private SuccessKilled successKilled;

    public SeckillExecution(long seckillId, int state, String stateInfo) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public SeckillExecution(long seckillId, int state, String stateInfo, SuccessKilled successKilled) {
        this.seckillId = seckillId;

        this.state = state;
        this.stateInfo = stateInfo;
        this.successKilled = successKilled;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
