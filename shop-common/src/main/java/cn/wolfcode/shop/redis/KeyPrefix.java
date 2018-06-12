package cn.wolfcode.shop.redis;

/**
 * redis kry 前缀的规范
 */
public interface KeyPrefix {
    /**
     * 获取前缀
     * @return
     */
    String getPrefix();

    /**
     * 过期时间
     * @return
     */
    Long getExpire();
}
