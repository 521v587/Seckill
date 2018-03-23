package cn.dean.seckill.dto;

public class SeckillResult<T> {

    private boolean success;

    private String error;

    private T data;

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getT() {
        return data;
    }

    public void setT(T data) {
        this.data = data;
    }
}
