package cn.convenience.cached.impl;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.convenience.bean.Token;
import cn.convenience.bean.UserRegInfo;
import cn.convenience.bean.WechatUserInfoBean;
import cn.convenience.cached.ICacheKey;
import cn.convenience.cached.IConvenienceCached;
import cn.convenience.config.IConfig;
import cn.sdk.cache.ICacheManger;
import cn.sdk.serialization.ISerializeManager;



@Service
public class IConvenienceCachedImpl implements IConvenienceCached{
	protected Logger log = Logger.getLogger(this.getClass());
	/**
	 * 用户id
	 */
	@Value("${useridApp}")
    private String useridApp;
	/**
	 * 用户密码
	 */
    @Value("${userpwdApp}")
    private String userpwdApp;
    /**
     * 请求地址
     */
    @Value("${urlApp}")
    private String urlApp;
    /**
     * 方法
     */
    @Value("${methodApp}")
    private String methodApp;
    /**
     * 秘钥
     */
    @Value("${keyApp}")
    private String keyApp;
	/**
	 * 用户id
	 */
	@Value("${userid}")
    private String userid;
	/**
	 * 用户密码
	 */
    @Value("${userpwd}")
    private String userpwd;
    /**
     * 请求地址
     */
    @Value("${url}")
    private String url;
    /**
     * 方法
     */
    @Value("${method}")
    private String method;
    /**
     * 秘钥
     */
    @Value("${key}")
    private String key;
    /**
     * 模板消息URL
     */
    @Value("${templateSendUrl}")
    private String templateSendUrl;
    /**
     * 模板消息URL
     */
    @Value("${templateSendUrl2}")
    private String templateSendUrl2;
    
    
    /**
     * 微信刷脸验证解密秘钥
     */
    @Value("${secretkey}")
    private String secretkey;
    
    /**
     * 微信刷脸验证AES解密秘钥
     */
    @Value("${aeskey}")
    private String aeskey;
    
    /**
     * 微信刷脸验获取用户URL
     */
    @Value("${infoUrl}")
    private String infoUrl;
    
    
    @Value("${useridAlipay}")
    private String useridAlipay;
    
    @Value("${userpwdAlipay}")
    private String userpwdAlipay;
    
    @Value("${urlAlipay}")
    private String urlAlipay;
    
    @Value("${methodAlipay}")
    private String methodAlipay;
    
    @Value("${keyAlipay}")
    private String keyAlipay;
    
    /**
     * 民生警务参数
     */
    @Value("${useridMsjw}")
    private String useridMsjw;
    @Value("${userpwdMsjw}")
    private String userpwdMsjw;
    @Value("${urlMsjw}")
    private String urlMsjw;
    @Value("${methodMsjw}")
    private String methodMsjw;
    @Value("${keyMsjw}")
    private String keyMsjw;
    @Value("${checkuserUrl}")
    private String checkuserUrl;
    @Value("${govnetUri}")
    private String govnetUri;
    @Value("${msjwToken}")
    private String msjwToken;
    
    public String getCheckuserUrl() {
		return checkuserUrl;
	}
	public String getGovnetUri() {
		return govnetUri;
	}
	public String getMsjwToken() {
		return msjwToken;
	}

	public void setMsjwUserInfo(String openId, String userInfo){
		objectcacheManger.set(ICacheKey.MSJW_USER_INFO_KEY + openId, userInfo, ICacheKey.oneDayExpireTime);
	}
	public String getMsjwUserInfo(String openId){
		return (String) objectcacheManger.get(ICacheKey.MSJW_USER_INFO_KEY + openId);
	}
	

	@Value("${urlEbike}")
    private String urlEbike;
    
    public String getUrlEbike() {
		return urlEbike;
	}
	public void setUrlEbike(String urlEbike) {
		this.urlEbike = urlEbike;
	}


	@Value("${refreshTokenTime}")
    private int refreshTokenTime;
    
    @Value("${encyptAccessTokenTime}")
    private int encyptAccessTokenTime;
    
    @Value("${accessTokentime}")
    private int accessTokenTime;
    
	@Autowired
	@Qualifier("jedisCacheManagerImpl")
	private ICacheManger<String> cacheManger;
	
	@Autowired
	@Qualifier("jedisCacheManagerImpl")
	private ICacheManger<Object> objectcacheManger;
	
	@Autowired
	private ISerializeManager< Map<String, String> > serializeManager;
	
    public static final String arrayToString(byte[] bytes)
    {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < bytes.length; i++)
        {
            buff.append(bytes[i] + " ");
        }
        return buff.toString();
    }
    
    
    @Override
    public boolean setWechatUserInfoBean(long id, WechatUserInfoBean wechatUserInfoBean){
    	String userRedisKey = USER_WECHAT_INFO_REDIS_KEY + id;
		return objectcacheManger.setByKryo(userRedisKey, wechatUserInfoBean, exprieTime);
    }
	
    @Override
	public WechatUserInfoBean getWechatUserInfoBean(long id){
    	String userRedisKey = USER_WECHAT_INFO_REDIS_KEY + id;
    	return (WechatUserInfoBean) objectcacheManger.getByKryo(userRedisKey, WechatUserInfoBean.class);
	}
	
	

	@Override
	public boolean setUser(long userId, UserRegInfo user) {
		String userRedisKey = USER_REDIS_KEY + userId;
		return objectcacheManger.setByKryo(userRedisKey, user, exprieTime);
	}

	@Override
	public UserRegInfo getUser(long userId) {
		String userRedisKey = USER_REDIS_KEY + userId;
		return (UserRegInfo) objectcacheManger.getByKryo(userRedisKey, UserRegInfo.class);
	}
	
	public Token insertToken(Token token) {
	    cacheManger.set(IConfig.USER_ACCOUNT_ACCESS_TOKEN_REDIS + token.getUserId(), token.getAccessToken(), accessTokenTime);
        cacheManger.set(IConfig.USER_ACCOUNT_REFRESH_TOKEN_REDIS + token.getUserId(), token.getRefreshToken(), refreshTokenTime);
        return token;
    }
    
    public String deleteToken(String userId) {
        cacheManger.del(IConfig.USER_ACCOUNT_ACCESS_TOKEN_REDIS + userId);
        cacheManger.del(IConfig.USER_ACCOUNT_REFRESH_TOKEN_REDIS + userId);
        return "success";
    }   
    
    public Token getToken(String userId)
    {   
        Token token = new Token();
        String accessToken = cacheManger.get(IConfig.USER_ACCOUNT_ACCESS_TOKEN_REDIS + userId);
        String refreshToken = cacheManger.get(IConfig.USER_ACCOUNT_REFRESH_TOKEN_REDIS + userId);
        token.setUserId(userId);
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        return token;
    }
    
    public Token updateAllToken(String userId)
    {
        Token token = getToken(userId);
        cacheManger.set(IConfig.USER_ACCOUNT_ACCESS_TOKEN_REDIS + token.getUserId(), token.getAccessToken(), accessTokenTime);
        cacheManger.set(IConfig.USER_ACCOUNT_REFRESH_TOKEN_REDIS + token.getUserId(), token.getRefreshToken(), refreshTokenTime);      
        return token;
    }
    
    public void updateAccessToken(String userId, String accessToken)
    {
        cacheManger.set(IConfig.USER_ACCOUNT_ACCESS_TOKEN_REDIS + userId, accessToken, accessTokenTime);
    }
    public void updateRefreshToken(String userId, String refreshToken)
    {
        cacheManger.set(IConfig.USER_ACCOUNT_REFRESH_TOKEN_REDIS + userId, refreshToken, refreshTokenTime);        
    }
    
    @Override
    public void insertEncyptAccessToken(String encyptAccessToken, String AccessToken) {
        if(StringUtils.isNotBlank(encyptAccessToken) && StringUtils.isNotBlank(AccessToken)){
            cacheManger.set(String.format(IConfig.ENCYPT_ACCESS_TOKEN_REDIS_KEY, encyptAccessToken), AccessToken, encyptAccessTokenTime);          
        }
    }

    @Override
    public String getAccessTokenFromEncypt(String encyptAccessToken) {
        if(StringUtils.isNotBlank(encyptAccessToken)){
            return cacheManger.get(String.format(IConfig.ENCYPT_ACCESS_TOKEN_REDIS_KEY, encyptAccessToken), encyptAccessTokenTime);            
        }else{
            return null;
        }
    }

    public String getUserid(String sourceOfCertification) {
		String string = "";
		if("C".equals(sourceOfCertification)){
			string = userid;
		}else if("Z".equals(sourceOfCertification)){
			string = useridAlipay;
		}else if("A".equals(sourceOfCertification)){
			string = useridApp;
		}else if("M".equals(sourceOfCertification)){
			string = useridMsjw;
		}else {
			string = userid;
		}
		return string;
	}


	public String getUserpwd(String sourceOfCertification) {
		String string = "";
		if("C".equals(sourceOfCertification)){
			string = userpwd;
		}else if("Z".equals(sourceOfCertification)){
			string = userpwdAlipay;
		}else if("A".equals(sourceOfCertification)){
			string = userpwdApp;
		}else if("M".equals(sourceOfCertification)){
			string = userpwdMsjw;
		}else {
			string = userpwd;
		}
		return string;
	}


	public String getUrl(String sourceOfCertification) {
		String string = "";
		if("C".equals(sourceOfCertification)){
			string = url;
		}else if("Z".equals(sourceOfCertification)){
			string = urlAlipay;
		}else if("A".equals(sourceOfCertification)){
			string = urlApp;
		}else if("M".equals(sourceOfCertification)){
			string = urlMsjw;
		}else {
			string = url;
		}
		return string;
	}

	public String getMethod(String sourceOfCertification) {
		String string = "";
		if("C".equals(sourceOfCertification)){
			string = method;
		}else  if("Z".equals(sourceOfCertification)){
			string = methodAlipay;
		}else  if("A".equals(sourceOfCertification)){
			string = methodApp;
		}else  if("M".equals(sourceOfCertification)){
			string = methodMsjw;
		}else {
			string = method;
		}
		return string;
	}


	public String getKey(String sourceOfCertification) {
		String string = "";
		if("C".equals(sourceOfCertification)){
			string = key;
		}else if("Z".equals(sourceOfCertification)){
			string = keyAlipay;
		}else if("A".equals(sourceOfCertification)){
			string = keyApp;
		}else if("M".equals(sourceOfCertification)){
			string = keyMsjw;
		}else {
			string = key;
		}
		return string;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setKey(String key) {
		this.key = key;
	}


	public String getTemplateSendUrl() {
		return templateSendUrl;
	}


	public void setTemplateSendUrl(String templateSendUrl) {
		this.templateSendUrl = templateSendUrl;
	}
	public String getSecretkey() {
		return secretkey;
	}
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}
	public String getAeskey() {
		return aeskey;
	}
	public void setAeskey(String aeskey) {
		this.aeskey = aeskey;
	}
	public String getInfoUrl() {
		return infoUrl;
	}
	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}
	public String getTemplateSendUrl2() {
		return templateSendUrl2;
	}
	public void setTemplateSendUrl2(String templateSendUrl2) {
		this.templateSendUrl2 = templateSendUrl2;
	}
	public void setKey(String key, Object value) {
		objectcacheManger.set(key, value);
	}
	public void expire(String key, int seconds) {
		objectcacheManger.expire(key, seconds);
		
	}
	public boolean exists(String key) {
		return objectcacheManger.exists(key);
	}
	public Object get(String key) {
		return objectcacheManger.get(key);
	}
    public Object getFront15(){
    	return objectcacheManger.get(RANK15_VOTE_LIST_KEY);
    }
    public void setFront15(Object list){
    	objectcacheManger.set(RANK15_VOTE_LIST_KEY, list, 60);
    }
}
