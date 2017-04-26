package cn.convenience.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.convenience.bean.ConvenienceBean;
import cn.convenience.bean.WechatUserInfoBean;
import cn.convenience.cached.impl.IConvenienceCachedImpl;
import cn.convenience.dao.IConvenienceDao;
import cn.convenience.service.IConvenienceService;
import cn.sdk.bean.BaseBean;
import cn.sdk.util.MsgCode;
import cn.sdk.webservice.WebServiceClient;

@Service("convenienceService")
public class IConvenienceServiceImpl implements IConvenienceService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IConvenienceDao convenienceDao;

	@Autowired
	private IConvenienceCachedImpl convenienceCache;
	
	
	@Override
	public int insertWechatUserInfo(WechatUserInfoBean wechatUserInfo) {
		int result = 0;
		
		try {
			result = convenienceDao.insertWechatUserInfo(wechatUserInfo);
		} catch (Exception e) {
			logger.error("插入wechatUserInfo失败，错误 ＝ ", e);
			throw e;
		}
		
		return result;
	}
	
	/**
	 * @Title: equipmentDamageReport 
	 * @Description: TODO(设备损坏通报) 
	 * @param @param convenienceBean
	 * @param @return 设定文件 
	 * @return BaseBean 返回类型 
	 * @throws
	 */
	@Override
	public BaseBean equipmentDamageReport(ConvenienceBean convenienceBean) {
		logger.info("【民意云】设备损坏通报信息采集webService...");
		
		String interfaceNumber = "HM1002";  //接口编号
		BaseBean refBean = new BaseBean();  //创建返回信息
		
		//拼装xml数据
		StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request>")
			.append("<fxrxm>").append(convenienceBean.getUserName()).append("</fxrxm>")     //发现人姓名
			.append("<fxrdh>").append(convenienceBean.getMobilephone()).append("</fxrdh>")  //发现人电话
			.append("<fxsj>").append(convenienceBean.getStartTime()).append("</fxsj>")    //发现时间
			.append("<dz_qu>").append(convenienceBean.getAddressRegion()).append("</dz_qu>")    //区域
			.append("<dz_jd>").append(convenienceBean.getAddressStreet()).append("</dz_jd>")    //街道
			.append("<dz_zd>").append(convenienceBean.getAddressSite()).append("</dz_zd>")    //站点
			.append("<dz_xxdz>").append(convenienceBean.getDetailAddress()).append("</dz_xxdz>")    //详细地址
			.append("<jjcd>").append(convenienceBean.getEmergency()).append("</jjcd>")    //紧急程度
			.append("<yhgzlx>").append("2").append("</yhgzlx>")    					//故障报障
			.append("<sslx1>").append(convenienceBean.getSelectTypeId()).append("</sslx1>")    //申诉类型 例如（142）
			.append("<sslxms1>").append(convenienceBean.getSelectType()).append("</sslxms1>")    //申诉类型描述 例如（交通信号灯）
			.append("<sslx2>").append(convenienceBean.getSubTypeId()).append("</sslx2>")    //申诉类型子级
			.append("<sslxms2>").append(convenienceBean.getSubType()).append("</sslxms2>")    //申诉类型子级描述 
			.append("<xxms>").append(convenienceBean.getDescription()).append("</xxms>")    //故障现象描述
			.append("<zp>").append(convenienceBean.getSceneImg()).append("</zp>")    //现场照片 （base64）
			.append("<ssly>").append("C").append("</ssly>")    //申诉来源  A移动APP C微信Z支付宝E邮政
//			.append("<xjyhid>").append(convenienceBean.getUserId()).append("</xjyhid>")    //星级用户id  可不传
			.append("<sfzmhm>").append(convenienceBean.getIdentityCard()).append("</sfzmhm>")    //星际用户身份证号码
			.append("</request>");
		
		try {
			@SuppressWarnings("static-access")
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(), convenienceCache.getMethod(), 
					interfaceNumber,sb.toString(),convenienceCache.getUserid(),convenienceCache.getUserpwd(),convenienceCache.getKey());
			
			refBean.setCode(respStr.get("CODE").toString());  //返回状态码
			refBean.setMsg(respStr.get("MSG").toString());	  //返回消息描述
			
			logger.info("【民意云】设备损坏通报信息采集结果:"+respStr);
		} catch (Exception e) {
			logger.error("【民意云】设备损坏通报信息采集失败！convenienceBean="+net.sf.json.JSONObject.fromObject(convenienceBean),e);
			
			refBean.setCode(MsgCode.exception);  //返回状态码  系统返回错误
			refBean.setMsg("服务器繁忙！");	  //返回消息描述
		}
		return refBean;
	}

	
	/**
	 * @Title: safeHiddenDanger 
	 * @Description: TODO(安全隐患通报) 
	 * @param @param convenienceBean
	 * @param @return 设定文件 
	 * @return BaseBean 返回类型 
	 * @throws
	 */
	@Override
	public BaseBean safeHiddenDanger(ConvenienceBean convenienceBean) {
		logger.info("【民意云】安全隐患通报信息采集webService...");
		
		String interfaceNumber = "HM1001";  //接口编号
		BaseBean refBean = new BaseBean();  //创建返回信息
		
		//拼装xml数据
		StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request>")
			.append("<fxrxm>").append(convenienceBean.getUserName()).append("</fxrxm>")     //发现人姓名
			.append("<fxrdh>").append(convenienceBean.getMobilephone()).append("</fxrdh>")  //发现人电话
			.append("<fxsj>").append(convenienceBean.getStartTime()).append("</fxsj>")    //发现时间
			.append("<dz_qu>").append(convenienceBean.getAddressRegion()).append("</dz_qu>")    //区域
			.append("<dz_jd>").append(convenienceBean.getAddressStreet()).append("</dz_jd>")    //街道
			.append("<dz_zd>").append(convenienceBean.getAddressSite()).append("</dz_zd>")    //站点
			.append("<dz_xxdz>").append(convenienceBean.getDetailAddress()).append("</dz_xxdz>")    //详细地址
			.append("<jjcd>").append(convenienceBean.getEmergency()).append("</jjcd>")    //紧急程度
			.append("<yhgzlx>").append("1").append("</yhgzlx>")    					//隐患意见
			.append("<sslx1>").append(convenienceBean.getSelectTypeId()).append("</sslx1>")    //申诉类型 例如（142）
			.append("<sslxms1>").append(convenienceBean.getSelectType()).append("</sslxms1>")    //申诉类型描述 例如（交通信号灯）
			.append("<sslx2>").append(convenienceBean.getSubTypeId()).append("</sslx2>")    //申诉类型子级
			.append("<sslxms2>").append(convenienceBean.getSubType()).append("</sslxms2>")    //申诉类型子级描述 
			.append("<xxms>").append(convenienceBean.getDescription()).append("</xxms>")    //隐患现象描述
			.append("<zp>").append(convenienceBean.getSceneImg()).append("</zp>")    //现场照片 （base64）
			.append("<ssly>").append("C").append("</ssly>")    //采集来源  A移动APP C微信Z支付宝E邮政
//			.append("<xjyhid>").append(convenienceBean.getUserId()).append("</xjyhid>")    //星级用户id  可不传
			.append("<sfzmhm>").append(convenienceBean.getIdentityCard()).append("</sfzmhm>")    //星际用户身份证号码
			.append("</request>");
		
		try {
			@SuppressWarnings("static-access")
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(), convenienceCache.getMethod(), 
					interfaceNumber,sb.toString(),convenienceCache.getUserid(),convenienceCache.getUserpwd(),convenienceCache.getKey());
			
			refBean.setCode(respStr.get("CODE").toString());  //返回状态码
			refBean.setMsg(respStr.get("MSG").toString());	  //返回消息描述
			
			logger.info("【民意云】安全隐患通报信息采集结果:"+respStr);
		} catch (Exception e) {
			logger.error("【民意云】安全隐患通报信息采集失败！convenienceBean="+net.sf.json.JSONObject.fromObject(convenienceBean),e);
			
			refBean.setCode(MsgCode.exception);  //返回状态码  系统返回错误
			refBean.setMsg("服务器繁忙！");	  //返回消息描述
		}
		return refBean;
	}

	
	/**
	 * @Title: trafficCongestion 
	 * @Description: TODO(交通拥堵通报) 
	 * @param @param convenienceBean
	 * @param @return 设定文件 
	 * @return BaseBean 返回类型 
	 * @throws
	 */
	@Override
	public BaseBean trafficCongestion(ConvenienceBean convenienceBean) {
		logger.info("【民意云】交通拥堵通报信息采集webService...");
		
		String interfaceNumber = "myzdtp";  //接口编号
		BaseBean refBean = new BaseBean();  //创建返回信息
		
		//拼装xml数据
		StringBuffer sbf = new StringBuffer();
			sbf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request><head>")
			 .append("<sfzmhm>").append(convenienceBean.getIdentityCard()).append("</sfzmhm>")  //身份证
			 //.append("<xm>").append("张三").append("</xm>")
			 .append("<sjhm>").append(convenienceBean.getMobilephone()).append("</sjhm>")		//手机号码
			 .append("<ip>").append(convenienceBean.getIp()).append("</ip>")		 //ip地址
			 .append("<yhly>C</yhly>")											//用户来源  微信
			 .append("<ztxh>").append("02").append("</ztxh>")					 //主题序号
			 .append("<ztnr>").append("互联网+民意治堵投票").append("</ztnr>")		 //主题内容
			 .append("<zt_time>").append(convenienceBean.getStartTime()+"-"+convenienceBean.getEndTiem()).append("</zt_time>")//主题时间段 00：00  00：30
			 .append("<zt_address>").append(convenienceBean.getAddressCode()).append("</zt_address>")		//主题地点代码  经纬度
			 .append("<zt_address_ms>").append(convenienceBean.getAddress()).append("</zt_address_ms>")		//主题地点描述  
			 .append("<fx>").append(convenienceBean.getDirection()).append("</fx>")		//拥堵 方向
			 .append("<jtfs>").append("").append("</jtfs>")				//交通方式  默认为空
			 .append("<sfcycxfs>").append("").append("</sfcycxfs>")		//是否为常用出行方式  默认为空
			 .append("<ydlx>").append(convenienceBean.getCongestionType()).append("</ydlx>")  //拥堵类型
			 .append("<yddj>").append(convenienceBean.getCongestionGrade()).append("</yddj>")	 //拥堵等级
			 .append("<ldfwsp>").append(convenienceBean.getRoadServiceLevel()).append("</ldfwsp>")  //路段服务水平
			 .append("<ydyy>").append(convenienceBean.getCongestionReason()).append("</ydyy>")		//拥堵成因
			 .append("<jygsfx>").append(convenienceBean.getImproveAdvice()).append("</jygsfx>")		//建议改善方向
			 .append("</head></request>");
				
			
			try {
			@SuppressWarnings("static-access")
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(), convenienceCache.getMethod(), 
					interfaceNumber,sbf.toString(),convenienceCache.getUserid(),convenienceCache.getUserpwd(),convenienceCache.getKey());
			
			refBean.setCode(respStr.getJSONObject("head").get("fhz").toString());  //返回状态码
			refBean.setMsg(respStr.getJSONObject("head").get("fhz-msg").toString());	  //返回消息描述
			
			logger.info("【民意云】交通拥堵通报信息采集结果:"+respStr);
		} catch (Exception e) {
			logger.error("【民意云】交通拥堵通报信息采集失败！convenienceBean="+net.sf.json.JSONObject.fromObject(convenienceBean),e);
			
			refBean.setCode(MsgCode.exception);  //返回状态码  系统返回错误
			refBean.setMsg("服务器繁忙！");	  //返回消息描述
		}
		return refBean;
	}

	
	/**
	 * @Title: sequenceChaos 
	 * @Description: TODO(秩序混乱通报) 
	 * @param @param convenienceBean
	 * @param @return 设定文件 
	 * @return BaseBean 返回类型 
	 * @throws
	 */
	@Override
	public BaseBean sequenceChaos(ConvenienceBean convenienceBean) {
		logger.info("【民意云】秩序混乱通报信息采集webService...");
		
		String interfaceNumber = "jtztp";  //接口编号
		BaseBean refBean = new BaseBean();  //创建返回信息
			
		//拼装xml数据
		StringBuffer sbf = new StringBuffer();
			sbf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request><head>")
			 .append("<sfzmhm>").append(convenienceBean.getIdentityCard()).append("</sfzmhm>")  //身份证
			 .append("<sjhm>").append(convenienceBean.getMobilephone()).append("</sjhm>")		//手机号码
			 .append("<ip>").append(convenienceBean.getIp()).append("</ip>")		 //ip地址
			 .append("<yhly>C</yhly>")											//用户来源  微信
			 .append("<ztxh>").append("01").append("</ztxh>")					 //主题序号
			 .append("<ztnr>").append("互联网+交通整治投票").append("</ztnr>")		 //主题内容
			 .append("<zt_time>").append(convenienceBean.getStartTime()+"-"+convenienceBean.getEndTiem()).append("</zt_time>")//主题时间段 00：00  00：30
			 .append("<zt_address>").append(convenienceBean.getAddressCode()).append("</zt_address>")		//主题地点代码
			 .append("<zt_address_ms>").append(convenienceBean.getAddress()).append("</zt_address_ms>")		//主题地点描述
			 .append("<zt_wfxw>").append(convenienceBean.getCongestionCode()).append("</zt_wfxw>")    //主题违法行为代码  拥堵类型id
			 .append("<zt_wfxw_ms>").append(convenienceBean.getCongestionType()).append("</zt_wfxw_ms>")    //主题违法行为描述   拥堵类型描述
			 .append("<jygsfx>").append(convenienceBean.getImproveAdvice()).append("</jygsfx>")		//建议改善方向
			 .append("</head></request>");
				
		try {
			@SuppressWarnings("static-access")
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(), convenienceCache.getMethod(), 
					interfaceNumber,sbf.toString(),convenienceCache.getUserid(),convenienceCache.getUserpwd(),convenienceCache.getKey());
			
			refBean.setCode(respStr.getJSONObject("head").get("fhz").toString());  //返回状态码
			refBean.setMsg(respStr.getJSONObject("head").get("fhz-msg").toString());	  //返回消息描述
			
			logger.info("【民意云】秩序混乱通报信息采集结果:"+respStr);
		} catch (Exception e) {
			logger.error("【民意云】秩序混乱通报信息采集失败！convenienceBean="+net.sf.json.JSONObject.fromObject(convenienceBean),e);
			
			refBean.setCode(MsgCode.exception);  //返回状态码  系统返回错误
			refBean.setMsg("服务器繁忙！");	  //返回消息描述
		}
		return refBean;
	}

	
	/**
	 * @Title: oneKeyDodgen 
	 * @Description: TODO(一键挪车) 
	 * @param @param convenienceBean
	 * @param @return 设定文件 
	 * @return BaseBean 返回类型 
	 * @throws
	 */
	@Override
	public BaseBean oneKeyDodgen(ConvenienceBean convenienceBean) {
		logger.info("【民意云】一键挪车信息采集webService...");
		
		String interfaceNumber = "HM1004";  //接口编号
		BaseBean refBean = new BaseBean();  //创建返回信息
			
		//拼装xml数据
		StringBuffer sbf = new StringBuffer();
			sbf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request>")
			.append("<hphm>").append(convenienceBean.getAbbreviation()+""+convenienceBean.getNumberPlate()).append("</hphm>")   //号牌号码
			.append("<hpzl>").append(convenienceBean.getCarType()).append("</hpzl>")  		//号牌种类
			.append("<qqly>").append("C").append("</qqly>") 							    //请求来源
			.append("<qqrsfzmhm>").append(convenienceBean.getIdentityCard()).append("</qqrsfzmhm>")  //请求人身份证明号码
			.append("<lcdz>").append(convenienceBean.getDoodgenAddress()).append("</lcdz>")		//挪车地址
			.append("</request>");
			
				
		try {
			@SuppressWarnings("static-access")
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(), convenienceCache.getMethod(), 
					interfaceNumber,sbf.toString(),convenienceCache.getUserid(),convenienceCache.getUserpwd(),convenienceCache.getKey());
			
			refBean.setCode(respStr.get("CODE").toString());  //返回状态码
			refBean.setMsg(respStr.get("MSG").toString());	  //返回消息描述
			
			logger.info("【民意云】一键挪车信息采集结果:"+respStr);
		} catch (Exception e) {
			logger.error("【民意云】一键挪车信息采集失败！convenienceBean="+net.sf.json.JSONObject.fromObject(convenienceBean),e);
			
			refBean.setCode(MsgCode.exception);  //返回状态码  系统返回错误
			refBean.setMsg("服务器繁忙！");	  //返回消息描述
		}
		return refBean;
	}
}