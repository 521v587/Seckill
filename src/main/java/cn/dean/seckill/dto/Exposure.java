package cn.dean.seckill.dto;

import java.util.Date;

public class Exposure {

    //是否开始秒杀，true->开始秒杀，false->未开始或结束秒杀
    private boolean exposed;
    //秒杀商品id
    private long seckillId;
    //加密措施
    private String md5;
    //秒杀开始时间（毫秒）
    private long start;
    //秒杀结束时间（毫秒）
    private long end;
    //当前时间（毫秒）
    private long now;

    public Exposure(boolean exposed, long start, long end, long now) {
        this.exposed = exposed;
        this.start = start;
        this.end = end;
        this.now = now;
    }

    public Exposure(boolean exposed, long seckillId, String md5) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.md5 = md5;
    }

    public Exposure(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }
}
