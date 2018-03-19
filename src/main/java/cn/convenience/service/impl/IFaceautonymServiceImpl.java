package cn.convenience.service.impl;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import cn.convenience.bean.SzjjToken;
import cn.convenience.cached.impl.IConvenienceCachedImpl;
import cn.convenience.dao.ISzjjTokenDao;
import cn.convenience.service.IFaceautonymService;
import cn.convenience.utils.AES;
import cn.convenience.utils.HttpRequest;
import cn.convenience.utils.Util;
import cn.sdk.bean.BaseBean;
import cn.sdk.util.HttpClientUtil;
import cn.sdk.util.HttpUtils;
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
	
	@Autowired
	private ISzjjTokenDao szjjTokenDao;
	/**
	 * 获取用户信息
	 * @throws Exception 
	 */
	@Override
	public BaseBean getdetectinfo(String appid, String token) throws Exception {
		BaseBean baen=new BaseBean();
		String plainText = "a="+appid+"&m=getdetectinfo&t="+Util.getCurrentTime()+"&e="+600;
		logger.info("请求参数:"+plainText);
		String url=convenienceCache.getInfoUrl();
//		String url = "https://iauth.wecity.qq.com/new/cgi-bin/getdetectinfo.php";
		String secretkey=convenienceCache.getSecretkey();
//		String secretkey="9828577231bdc6d01754e292023cdbb8";
		String sig = Util.getSig(new String[]{token,appid});
		String signature = Util.getSignature(secretkey, plainText);
		String ret = HttpRequest.sendPost(url, "token="+token+"&appid="+appid+"&sig="+sig,signature,"application/x-www-form-urlencoded");
		if(StringUtil.isBlank(ret)){
		     baen.setCode(MsgCode.businessError);
		     baen.setMsg("获取用户基本信息失败");
		     return baen;
		}
		JSONObject respStr=JSONObject.fromObject(ret);
		if("0".equals(respStr.get("errorcode").toString())){
			 String data=respStr.get("data").toString();
			 byte[] by=AES.decrypt(Base64.decodeBase64(data),convenienceCache.getAeskey().getBytes("UTF-8"));
//			 byte[] by=AES.decrypt(Base64.decodeBase64(data),"26cb3f325891d42bec10efdeec9a4f95".getBytes("UTF-8"));
			 String str=new String(by, "UTF-8");
			 JSONObject json=JSONObject.fromObject(str);
			 logger.info("获取用户信息:"+json.getString("ID")+":"+json.getString("name"));
			 baen.setCode(MsgCode.success);
			 baen.setMsg(respStr.getString("errormsg"));
			 baen.setData(json);
		}
		return baen;
	}


	@Override
	public int insertSzjjToken(SzjjToken szjjToken) throws Exception {
		return szjjTokenDao.insert(szjjToken);
	}


	@Override
	public SzjjToken querySzjjToken(String identityCard) throws Exception {
		return szjjTokenDao.selectByIdentityCard(identityCard);
	}

}
