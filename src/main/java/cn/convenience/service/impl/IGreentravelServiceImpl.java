package cn.convenience.service.impl;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.convenience.bean.ApplyGreenRet;
import cn.convenience.bean.GreenTravelBean;
import cn.convenience.cached.impl.IConvenienceCachedImpl;
import cn.convenience.service.IGreentravelService;
import cn.sdk.bean.BaseBean;
import cn.sdk.webservice.WebServiceClient;

@SuppressWarnings(value="all")
@Service("greentravelService")
public class IGreentravelServiceImpl implements IGreentravelService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private IConvenienceCachedImpl convenienceCache;
	
	/**
	 * 获取发送模板消息的域名地址
	 */
	@Override
	public String getTemplateSendUrl() {
		String url = convenienceCache.getTemplateSendUrl();
		logger.info("获取到的域名地址是：" + url);
		return url;
	}
	
	 /**
		 * 
		 *  @Title: applyDownDate 
		 * @Description: TODO(可申请停驶日期提取) 
		 * @param @param greenTravelBean
		 * @param @return 设定文件 
		 * @return BaseBean 返回类型 
		 * @throws
		 */
	@Override
	public BaseBean applyDownDate(GreenTravelBean greenTravelBean) throws Exception{
        logger.info("【警示通】webService...");
		
		String interfaceNumber = "xjlscx1001";  //接口编号
		BaseBean refBean = new BaseBean();  //创建返回信息
		//拼装xml数据
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request>")
		.append("<hphm>").append(greenTravelBean.getHphm()).append("</hphm>")     //车牌号码(带’粤’)
		.append("<hpzl>").append(greenTravelBean.getHpzl()).append("</hpzl>")  //号牌号码
		.append("<sfzmhm>").append(greenTravelBean.getSfzmhm()).append("</sfzmhm>")    //身份证号码
		.append("<month>").append(greenTravelBean.getMonth()).append("</month>")    //月份
		.append("</request>");
		logger.info("请求参数:"+sb.toString());
		try {
			String string = "";
			@SuppressWarnings("static-access")
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(string), convenienceCache.getMethod(string), 
					interfaceNumber,sb.toString(),convenienceCache.getUserid(string),convenienceCache.getUserpwd(string),convenienceCache.getKey(string));

			refBean.setCode(respStr.get("code").toString());  //返回状态码
			refBean.setMsg(respStr.get("msg").toString());	  //返回消息描述
			if("0000".equals(respStr.get("code").toString())){
				refBean.setData(respStr.get("body"));
			}
			logger.info("【警示通】申请停驾日期提取结果:"+respStr);
		} catch (Exception e) {
			logger.error("【警示通】申请停驾日期提取结果失败！greenTravelBean="+greenTravelBean.toString(),e);
			throw e;
		}
		return refBean;
	}
	
	/**
	 * 
	 *  @Title: downDatedeclare 
	 * @Description: TODO(提交申报停驶日期) 
	 * @param @param greenTravelBean
	 * @param @return 设定文件 
	 * @return BaseBean 返回类型 
	 * @throws
	 */
	@Override
	public BaseBean downDatedeclare(GreenTravelBean greenTravelBean)
			throws Exception {
        logger.info("【警示通】webService...");
		
		String interfaceNumber = " xjlscx1002";  //接口编号
		BaseBean refBean = new BaseBean();  //创建返回信息
		//拼装xml数据
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request><head>")
		.append("<sname>").append(greenTravelBean.getSname()).append("</sname>")     //车主姓名/车主使用人
		.append("<sfzmhm>").append(greenTravelBean.getSfzmhm()).append("</sfzmhm>")    //身份证号码
		.append("<mobile>").append(greenTravelBean.getMobile()).append("</mobile>")    //手机号码
		.append("<hphm>").append(greenTravelBean.getHphm()).append("</hphm>")  //车牌号码(带’粤’)
		.append("<hpzl>").append(greenTravelBean.getHpzl()).append("</hpzl>")    //号牌种类
		.append("<sfbr>").append(greenTravelBean.getSfbr()).append("</sfbr>")    //1是本人，0是非本人
		.append("<lrly>").append(greenTravelBean.getLrly()).append("</lrly></head>")    //申请来源（WX:微信，ZFB支付宝，APP:手机App）
		.append("<body>");
		if(greenTravelBean!=null && greenTravelBean.getApplyGreenRetList()!=null ){
			for (ApplyGreenRet applyGreenRet : greenTravelBean.getApplyGreenRetList()) {
				   sb.append("<ret>")
				    .append("<cdate>").append(applyGreenRet.getCdate()).append("</cdate>")
				    .append("<type>").append(applyGreenRet.getType()).append("</type>")
				   .append("</ret>");	
				}
		}
		sb.append("</body></request>");
		logger.info("请求参数报文:"+sb.toString());
		try {
			String string = "";
			@SuppressWarnings("static-access")
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(string), convenienceCache.getMethod(string), 
					interfaceNumber,sb.toString(),convenienceCache.getUserid(string),convenienceCache.getUserpwd(string),convenienceCache.getKey(string));

			refBean.setCode(respStr.get("code").toString());  //返回状态码
			refBean.setMsg(respStr.get("msg").toString());	  //返回消息描述
			
			logger.info("【警示通】提交申报停驶日期结果:"+respStr);
		} catch (Exception e) {
			logger.error("【警示通】提交申报停驶日期结果失败！greenTravelBean="+greenTravelBean.toString(),e);
			throw e;
		}
		return refBean;
	}
     
	/**
	 * 
	 *  @Title: applyrunningQuery 
	 * @Description: TODO(申请流水查询)
	 * @param @param greenTravelBean
	 * @param @return 设定文件 
	 * @return BaseBean 返回类型 
	 * @throws
	 */
	@Override
	public BaseBean applyrunningQuery(GreenTravelBean greenTravelBean)
			throws Exception {
		     logger.info("【警示通】webService...");
			
			String interfaceNumber = "xjlscx1003";  //接口编号
			BaseBean refBean = new BaseBean();  //创建返回信息
			//拼装xml数据
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request>")
			.append("<hphm>").append(greenTravelBean.getHphm()).append("</hphm>")     //车牌号码(带’粤’)
			.append("<hpzl>").append(greenTravelBean.getHpzl()).append("</hpzl>")  //号牌种类
			.append("<sqrq>").append(greenTravelBean.getSqrq()).append("</sqrq>")    //申请日期
			.append("</request>");
			logger.info("请求参数报文:"+sb.toString());
			try {
				String string = "";
				@SuppressWarnings("static-access")
				JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(string), convenienceCache.getMethod(string), 
						interfaceNumber,sb.toString(),convenienceCache.getUserid(string),convenienceCache.getUserpwd(string),convenienceCache.getKey(string));
                 
				refBean.setCode(respStr.get("code").toString());  //返回状态码
				refBean.setMsg(respStr.get("msg").toString());	  //返回消息描述
				if("0000".equals(respStr.get("code").toString())){
					refBean.setData(respStr.get("body"));
				}
				logger.info("【警示通】申请流水查询结果:"+respStr);
			} catch (Exception e) {
				logger.error("【警示通】申请流水查询结果失败！greenTravelBean="+greenTravelBean.toString(),e);
				throw e;
			}
			return refBean;
	}
	/**
	 * 
	  @Title: applyTotalQuery 
	 * @Description: TODO(车辆停驶情况查询)
	 * @param @param greenTravelBean
	 * @param @return 设定文件 
	 * @return BaseBean 返回类型 
	 * @throws
	 */
	@Override
	public BaseBean applyTotalQuery(GreenTravelBean greenTravelBean)
			throws Exception {
		      logger.info("【警示通】webService...");
			String interfaceNumber = "xjlscx1004";  //接口编号
			BaseBean refBean = new BaseBean();  //创建返回信息
			//拼装xml数据
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request>")
			.append("<hphm>").append(greenTravelBean.getHphm()).append("</hphm>")     //车牌号码(带’粤’)
			.append("<hpzl>").append(greenTravelBean.getHpzl()).append("</hpzl>")  //号牌种类
			.append("</request>");
			logger.info("请求参数报文:"+sb.toString());
			try {
				String string = "";
				@SuppressWarnings("static-access")
				JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(string), convenienceCache.getMethod(string), 
						interfaceNumber,sb.toString(),convenienceCache.getUserid(string),convenienceCache.getUserpwd(string),convenienceCache.getKey(string));
                 
				refBean.setCode(respStr.get("code").toString());  //返回状态码
				refBean.setMsg(respStr.get("msg").toString());	  //返回消息描述
				if("0000".equals(respStr.get("code").toString())){
					refBean.setData(respStr);
				}
				logger.info("【警示通】申请流水查询结果:"+respStr);
			} catch (Exception e) {
				logger.error("【警示通】申请流水查询结果失败！greenTravelBean="+greenTravelBean.toString(),e);
				throw e;
			}
			return refBean;
	}

	 
	
}
