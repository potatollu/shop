package cn.wolfcode.shop.redis;

abstract public class AbstractKeyPrefix implements KeyPrefix{
    /**
     * 前缀
     * @return
     */
    private String prefix;
    /**
     * 过期时间:单位(毫秒
     * @return
     */
    private Long expire;
    protected AbstractKeyPrefix(String prefix){
        this(prefix,0L);
    }
    protected AbstractKeyPrefix(String prefix,Long expore){
        this.prefix = prefix;
        this.expire = expore;
    }
    public String getPrefix() {
        return this.getClass().getSimpleName() + ":" + prefix;
    }

    public Long getExpire() {
        return expire;
    }

}
