package cn.convenience.service.impl;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import cn.convenience.cached.impl.IConvenienceCachedImpl;
import cn.convenience.service.IFaceautonymService;
import cn.convenience.utils.AES;
import cn.convenience.utils.HttpRequest;
import cn.convenience.utils.Util;
import cn.sdk.bean.BaseBean;
import cn.sdk.util.MsgCode;
import cn.sdk.util.StringUtil;

/**
 * 获取用户信息实现类
 * @author Maibenben
 *
 */
@SuppressWarnings(value="all")
@Service("faceautonymService")
public class IFaceautonymServiceImpl implements IFaceautonymService {
    
private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private IConvenienceCachedImpl convenienceCache;
	
	
	/**
	 * 获取用户信息
	 * @throws Exception 
	 */
	@Override
	public BaseBean getdetectinfo(String appid, String token) throws Exception {
		BaseBean baen=new BaseBean();
		String plainText = "a="+appid+"&m=getdetectinfo&t="+Util.getCurrentTime()+"&e="+600;
		String url=convenienceCache.getInfoUrl();
		String secretkey=convenienceCache.getSecretkey();
		String sig = Util.getSig(new String[]{token,appid});
		String signature = Util.getSignature(secretkey, plainText);
		System.out.println("key:"+secretkey);
		String ret = HttpRequest.sendPost(url, "token="+token+"&appid="+appid+"&sig="+sig,signature,"application/x-www-form-urlencoded");
		logger.info("获取用户得到响应:"+ret);
		if(StringUtil.isBlank(ret)){
		     baen.setCode(MsgCode.businessError);
		     baen.setMsg("获取用户基本信息失败");
		     return baen;
		}
		JSONObject respStr=JSONObject.fromObject(ret);
		if("0".equals(respStr.get("errorcode").toString())){
			 String data=respStr.get("data").toString();
			 byte[] by=AES.decrypt(Base64.decodeBase64(data),convenienceCache.getAeskey().getBytes("UTF-8"));
			 String str=new String(by, "UTF-8");
			 logger.info("data解密数据:"+str);
			 JSONObject json=JSONObject.fromObject(str);
			 baen.setCode(MsgCode.success);
			 baen.setMsg(respStr.getString("errormsg"));
			 baen.setData(json);
		}
		return baen;
	}

}
