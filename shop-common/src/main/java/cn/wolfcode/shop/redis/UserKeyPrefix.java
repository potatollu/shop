package cn.wolfcode.shop.redis;

public class UserKeyPrefix extends AbstractKeyPrefix{

    /**
     *
     */
    public static final UserKeyPrefix USER_TOKEN_KEY_PREFIX = new UserKeyPrefix("tokens",1000*60*60*24L);


    public UserKeyPrefix(String prefix) {
        this(prefix,0L);
    }

    public UserKeyPrefix(String prefix, Long expire) {
       super(prefix,expire);
    }

}
