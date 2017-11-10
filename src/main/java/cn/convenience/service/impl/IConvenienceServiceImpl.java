package cn.convenience.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.convenience.bean.ApplyForPAGoodCarOwners;
import cn.convenience.bean.ConvenienceBean;
import cn.convenience.bean.EbikeInfoBean;
import cn.convenience.bean.FeedbackResultBean;
import cn.convenience.bean.UserInfoBean;
import cn.convenience.bean.WechatUserInfoBean;
import cn.convenience.cached.impl.IConvenienceCachedImpl;
import cn.convenience.dao.IConvenienceDao;
import cn.convenience.service.IConvenienceService;
import cn.sdk.bean.BaseBean;
import cn.sdk.exception.WebServiceException;
import cn.sdk.util.DateUtil;
import cn.sdk.util.HttpClientUtil;
import cn.sdk.util.MsgCode;
import cn.sdk.webservice.WebServiceClient;

@Service("convenienceService")
public class IConvenienceServiceImpl implements IConvenienceService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IConvenienceDao convenienceDao;

	@Autowired
	private IConvenienceCachedImpl convenienceCache;
	
	/**
	 * 获取发送模板消息的域名地址
	 */
	@Override
	public String getTemplateSendUrl2() {
		String url = convenienceCache.getTemplateSendUrl2();
		logger.info("获取到的域名地址是：" + url);
		return url;
	}
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
	public BaseBean equipmentDamageReport(ConvenienceBean convenienceBean) throws Exception{
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
			.append("<ssly>").append(convenienceBean.getSourceOfCertification()).append("</ssly>")    //申诉来源  A移动APP C微信Z支付宝E邮政
//			.append("<xjyhid>").append(convenienceBean.getUserId()).append("</xjyhid>")    //星级用户id  可不传
			.append("<sfzmhm>").append(convenienceBean.getIdentityCard()).append("</sfzmhm>")    //星际用户身份证号码
			.append("</request>");
		
		try {
			@SuppressWarnings("static-access")
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(convenienceBean.getSourceOfCertification()), convenienceCache.getMethod(convenienceBean.getSourceOfCertification()), 
					interfaceNumber,sb.toString(),convenienceCache.getUserid(convenienceBean.getSourceOfCertification()),convenienceCache.getUserpwd(convenienceBean.getSourceOfCertification()),convenienceCache.getKey(convenienceBean.getSourceOfCertification()));

			refBean.setCode(respStr.get("CODE").toString());  //返回状态码
			refBean.setMsg(respStr.get("MSG").toString());	  //返回消息描述
			
			logger.info("【民意云】设备损坏通报信息采集结果:"+respStr);
		} catch (Exception e) {
			logger.error("【民意云】设备损坏通报信息采集失败！convenienceBean="+convenienceBean.toString(),e);
			
//			refBean.setCode(MsgCode.exception);  //返回状态码  系统返回错误
//			refBean.setMsg("服务器繁忙！");	  //返回消息描述
			throw e;
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
	public BaseBean safeHiddenDanger(ConvenienceBean convenienceBean) throws Exception{
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
			.append("<ssly>").append(convenienceBean.getSourceOfCertification()).append("</ssly>")    //采集来源  A移动APP C微信Z支付宝E邮政
//			.append("<xjyhid>").append(convenienceBean.getUserId()).append("</xjyhid>")    //星级用户id  可不传
			.append("<sfzmhm>").append(convenienceBean.getIdentityCard()).append("</sfzmhm>")    //星际用户身份证号码
			.append("</request>");
		
		try {
			@SuppressWarnings("static-access")
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(convenienceBean.getSourceOfCertification()), convenienceCache.getMethod(convenienceBean.getSourceOfCertification()), 
					interfaceNumber,sb.toString(),convenienceCache.getUserid(convenienceBean.getSourceOfCertification()),convenienceCache.getUserpwd(convenienceBean.getSourceOfCertification()),convenienceCache.getKey(convenienceBean.getSourceOfCertification()));

			refBean.setCode(respStr.get("CODE").toString());  //返回状态码
			refBean.setMsg(respStr.get("MSG").toString());	  //返回消息描述
			
			logger.info("【民意云】安全隐患通报信息采集结果:"+respStr);
		} catch (Exception e) {
			logger.error("【民意云】安全隐患通报信息采集失败！convenienceBean="+convenienceBean.toString(),e);
			
//			refBean.setCode(MsgCode.exception);  //返回状态码  系统返回错误
//			refBean.setMsg("服务器繁忙！");	  //返回消息描述
			throw e;
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
	public BaseBean trafficCongestion(ConvenienceBean convenienceBean) throws Exception{
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
//			 .append("<yhly>C</yhly>")											//用户来源  微信
			 .append("<yhly>").append(convenienceBean.getSourceOfCertification()).append("</yhly>")    //用户来源  A移动APP C微信Z支付宝E邮政
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
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(convenienceBean.getSourceOfCertification()), convenienceCache.getMethod(convenienceBean.getSourceOfCertification()), 
					interfaceNumber,sbf.toString(),convenienceCache.getUserid(convenienceBean.getSourceOfCertification()),convenienceCache.getUserpwd(convenienceBean.getSourceOfCertification()),convenienceCache.getKey(convenienceBean.getSourceOfCertification()));

			refBean.setCode(respStr.getJSONObject("head").get("fhz").toString());  //返回状态码
			refBean.setMsg(respStr.getJSONObject("head").get("fhz-msg").toString());	  //返回消息描述
			
			logger.info("【民意云】交通拥堵通报信息采集结果:"+respStr);
		} catch (Exception e) {
			logger.error("【民意云】交通拥堵通报信息采集失败！convenienceBean="+convenienceBean.toString(),e);
			
//			refBean.setCode(MsgCode.exception);  //返回状态码  系统返回错误
//			refBean.setMsg("服务器繁忙！");	  //返回消息描述
			throw e;
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
	public BaseBean sequenceChaos(ConvenienceBean convenienceBean) throws Exception{
		logger.info("【民意云】秩序混乱通报信息采集webService...");
		
		String interfaceNumber = "jtztp";  //接口编号
		BaseBean refBean = new BaseBean();  //创建返回信息
			
		//拼装xml数据
		StringBuffer sbf = new StringBuffer();
			sbf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request><head>")
			 .append("<sfzmhm>").append(convenienceBean.getIdentityCard()).append("</sfzmhm>")  //身份证
			 .append("<sjhm>").append(convenienceBean.getMobilephone()).append("</sjhm>")		//手机号码
			 .append("<ip>").append(convenienceBean.getIp()).append("</ip>")		 //ip地址
//			 .append("<yhly>C</yhly>")											//用户来源  微信
			 .append("<yhly>").append(convenienceBean.getSourceOfCertification()).append("</yhly>")    //用户来源  A移动APP C微信Z支付宝E邮政
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
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(convenienceBean.getSourceOfCertification()), convenienceCache.getMethod(convenienceBean.getSourceOfCertification()), 
					interfaceNumber,sbf.toString(),convenienceCache.getUserid(convenienceBean.getSourceOfCertification()),convenienceCache.getUserpwd(convenienceBean.getSourceOfCertification()),convenienceCache.getKey(convenienceBean.getSourceOfCertification()));

			refBean.setCode(respStr.getJSONObject("head").get("fhz").toString());  //返回状态码
			refBean.setMsg(respStr.getJSONObject("head").get("fhz-msg").toString());	  //返回消息描述
			
			logger.info("【民意云】秩序混乱通报信息采集结果:"+respStr);
		} catch (Exception e) {
			logger.error("【民意云】秩序混乱通报信息采集失败！convenienceBean="+convenienceBean.toString(),e);
			
//			refBean.setCode(MsgCode.exception);  //返回状态码  系统返回错误
//			refBean.setMsg("服务器繁忙！");	  //返回消息描述
			throw e;
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
	public BaseBean oneKeyDodgen(ConvenienceBean convenienceBean) throws Exception{
		logger.info("【民意云】一键挪车信息采集webService...");
		
		String interfaceNumber = "HM1004";  //接口编号
		BaseBean refBean = new BaseBean();  //创建返回信息
			
		//拼装xml数据
		StringBuffer sbf = new StringBuffer();
			sbf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request>")
			.append("<hphm>").append(convenienceBean.getAbbreviation()+""+convenienceBean.getNumberPlate()).append("</hphm>")   //号牌号码
			.append("<hpzl>").append(convenienceBean.getCarType()).append("</hpzl>")  		//号牌种类
			.append("<qqly>").append(convenienceBean.getSourceOfCertification()).append("</qqly>") 							    //请求来源
			.append("<qqrsfzmhm>").append(convenienceBean.getIdentityCard()).append("</qqrsfzmhm>")  //请求人身份证明号码
			.append("<lcdz>").append(convenienceBean.getDoodgenAddress()).append("</lcdz>")		//挪车地址
			.append("</request>");
			
				
		try {
			@SuppressWarnings("static-access")
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(convenienceBean.getSourceOfCertification()), convenienceCache.getMethod(convenienceBean.getSourceOfCertification()), 
					interfaceNumber,sbf.toString(),convenienceCache.getUserid(convenienceBean.getSourceOfCertification()),convenienceCache.getUserpwd(convenienceBean.getSourceOfCertification()),convenienceCache.getKey(convenienceBean.getSourceOfCertification()));

			refBean.setCode(respStr.get("CODE").toString());  //返回状态码
			refBean.setMsg(respStr.get("MSG").toString());	  //返回消息描述
			
			logger.info("【民意云】一键挪车信息采集结果:"+respStr);
		} catch (Exception e) {
			logger.error("【民意云】一键挪车信息采集失败！convenienceBean="+convenienceBean.toString(),e);
//			
//			refBean.setCode(MsgCode.exception);  //返回状态码  系统返回错误
//			refBean.setMsg("服务器繁忙！");	  //返回消息描述
			throw e;
		}
		return refBean;
	}

	/**
	 * @Title: historyNotice
	 * @Description: TODO(历史通报)
	 * @return List 返回类型
	 * @throws
	 */
	@Override
	public List<FeedbackResultBean> getAllResourcesAbsoluteUrl() throws Exception {
		logger.info("【民意云】办理情况通报信息采集本地...");
		
		List<FeedbackResultBean> list;
		try {
			list = new ArrayList<>();
			
			list.add(new FeedbackResultBean("深圳交警民意云2017年第三十四期办理情况通报（2017年10月2日至2017年10月8日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第三十四期办理情况通报（2017年10月2日至2017年10月8日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第三十三期办理情况通报（2017年9月18日至2017年9月24日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第三十三期办理情况通报（2017年9月18日至2017年9月24日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第三十二期办理情况通报（2017年9月11日至2017年9月17日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第三十二期办理情况通报（2017年9月11日至2017年9月17日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第三十一期办理情况通报（2017年9月4日至2017年9月10日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第三十一期办理情况通报（2017年9月4日至2017年9月10日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第三十期办理情况通报 （2017年8月28日至2017年9月3日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第三十期办理情况通报 （2017年8月28日至2017年9月3日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第二十九期办理情况通报（2017年8月21日至2017年8月27日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第二十九期办理情况通报（2017年8月21日至2017年8月27日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第二十八期办理情况通报（2017年8月14日至2017年8月20日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第二十八期办理情况通报（2017年8月14日至2017年8月20日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第二十七期办理情况通报 （2017年8月7日至2017年8月13日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第二十七期办理情况通报 （2017年8月7日至2017年8月13日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第二十六期办理情况通报（2017年7月31日至2017年8月6日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第二十六期办理情况通报（2017年7月31日至2017年8月6日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第二十五期办理情况通报（2017年7月24日至2017年7月30日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第二十五期办理情况通报（2017年7月24日至2017年7月30日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第二十四期办理情况通报（2017年7月17日至2017年7月23日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第二十四期办理情况通报（2017年7月17日至2017年7月23日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第二十三期办理情况通报（2017年7月10日至2017年7月16日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第二十三期办理情况通报（2017年7月10日至2017年7月16日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第二十二期办理情况通报（2017年7月3日至2017年7月9日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第二十二期办理情况通报（2017年7月3日至2017年7月9日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第二十一期办理情况通报（2017年6月26日至2017年7月2日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第二十一期办理情况通报（2017年6月26日至2017年7月2日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第二十期办理情况通报（2017年6月19日至2017年6月25日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第二十期办理情况通报（2017年6月19日至2017年6月25日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第十九期办理情况通报（2017年6月12日至2017年6月18日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第十九期办理情况通报（2017年6月12日至2017年6月18日）.docx"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第十八期办理情况通报（2017年6月5日至2017年6月11日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第十八期办理情况通报（2017年6月5日至2017年6月11日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第十七期办理情况通报（2017年5月29日至2017年6月4日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第十七期办理情况通报（2017年5月29日至2017年6月4日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第十六期办理情况通报（2017年5月22日至2017年5月28日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第十六期办理情况通报（2017年5月22日至2017年5月28日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第十五期办理情况通报（2017年5月15日至2017年5月21日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第十五期办理情况通报（2017年5月15日至2017年5月21日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第十四期办理情况通报（2017年5月8日至2017年5月14日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第十四期办理情况通报（2017年5月8日至2017年5月14日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第十三期办理情况通报（2017年5月1日至2017年5月7日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第十三期办理情况通报（2017年5月1日至2017年5月7日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第十二期办理情况通报（2017年4月24日至2017年4月30日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第十二期办理情况通报（2017年4月24日至2017年4月30日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第十一期办理情况通报（2017年4月17日至2017年4月23日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第第十一期办理情况通报（2017年4月17日至2017年4月23日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第十期办理情况通报（2017年4月10日至2017年4月16日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第十期办理情况通报（2017年4月10日至2017年4月16日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第九期办理情况通报（2017年4月3日至2017年4月9日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第九期办理情况通报（2017年4月3日至2017年4月9日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第八期办理情况通报（2017年3月27日至2017年4月2日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第八期办理情况通报（2017年3月27日至2017年4月2日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第七期办理情况通报（2017年3月20日至2017年3月26日）","http://szjj.u-road.com/fileserver/file/深圳交警民意云2017年第七期办理情况通报（2017年3月20日至2017年3月26日）.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第六期办理情况通报（2017年3月13日至2017年3月19日）","http://szjj.u-road.com/szjjpro/assets/doc/20170313-20170319.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第五期办理情况通报（2017年3月6日至2017年3月12日）","http://szjj.u-road.com/szjjpro/assets/doc/20170306-20170312.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第四期办理情况通报（2017年2月20日至2017年2月26日）","http://szjj.u-road.com/szjjpro/assets/doc/20170220-20170226.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第三期办理情况通报（2017年2月14日至2017年2月19日）","http://szjj.u-road.com/szjjpro/assets/doc/20170214-20170219.docx"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第二期办理情况通报（2017年2月3日至2017年2月13日）","http://szjj.u-road.com/szjjpro/assets/doc/20170203-20170213.doc"));
			list.add(new FeedbackResultBean("深圳交警民意云2017年第一期办理情况通报（2017年1月13日至2017年2月5日）","http://szjj.u-road.com/szjjpro/assets/doc/20170113-20170205.docx"));
			
			logger.info("【民意云】办理情况通报信息采集结果:"+JSON.toJSONString(list));
		} catch (Exception e) {
			logger.error("【民意云】办理请款通报信息采集失败！",e);
			throw e;
		}
		
		return list;
	}

	/**
	 * 根据档案编号查询电动车档案信息
	 * @Description: TODO(根据档案编号查询电动车档案信息)
	 * @param fileNo 档案编号
	 * @throws Exception
	 */
	public BaseBean getEbikeInfoByFileNo(String fileNo) throws Exception {
		logger.info("【电动车】根据档案编号查询电动车档案信息采集电动车系统...");
		
		BaseBean baseBean = new BaseBean();  //创建返回信息
		
		try {
			String urlEbike = convenienceCache.getUrlEbike();//电动车内网API地址
			//String respStr = HttpClientUtil.get("http://green.stc.gov.cn:8088/ebike/appAction/getEbikeInfoByDabh?dabh=" + fileNo);
			//String respStr = HttpClientUtil.get("http://192.168.2.197:8088/ebike/appAction/getEbikeInfoByDabh?dabh=" + fileNo);
			String respStr = HttpClientUtil.get(urlEbike + "getEbikeInfoByDabh?dabh=" + fileNo);
            
			JSONObject jsonObj = JSON.parseObject(respStr);
			boolean isSuccess = jsonObj.getBooleanValue("isSuccess");
			if(isSuccess){
				Map<String, Object> map = new HashMap<>();
				EbikeInfoBean ebikeInfo = new EbikeInfoBean();
				JSONObject data = jsonObj.getJSONObject("data");
				
				addDataToEbikeInfo(map, ebikeInfo, data);
				
				baseBean.setCode(MsgCode.success);
				baseBean.setData(map);
			}else{
				baseBean.setCode(MsgCode.paramsError);
			}
			baseBean.setMsg(jsonObj.getString("message"));
			
			logger.info("【电动车】根据档案编号查询电动车档案信息采集结果:" + JSON.toJSONString(jsonObj));
		} catch (Exception e) {
			logger.error("【电动车】根据档案编号查询电动车档案信息采集失败！", e);
			throw e;
		}
		
		return baseBean;
	}
	
	/**
	 * 封装数据到电动车信息
	 * @param ebikeInfo
	 * @param data
	 */
	private void addDataToEbikeInfo(Map<String, Object> map, EbikeInfoBean ebikeInfo, JSONObject data) {
		ebikeInfo.setEbikeImgUrl(data.getString("vcShowEbikeImg"));	//电动车照片资源路径
		ebikeInfo.setPlateNo(data.getString("cphm"));      			//车辆号牌
		ebikeInfo.setVehicleStatus(data.getString("ztName"));		//车辆状态	已备案,已注销
		ebikeInfo.setFileNo(data.getString("dabh"));       			//档案编号
		ebikeInfo.setEngineNo(data.getString("djh"));				//电机号码
		ebikeInfo.setDrivingArea(data.getString("xsqyName"));  		//行驶区域
		ebikeInfo.setBrandType(data.getString("ppxh"));    			//品牌型号
		ebikeInfo.setFootDevice(data.getString("jtzz"));   			//脚踏装置	0-有,1-无
		ebikeInfo.setColor(data.getString("cysyName"));        		//车身颜色
		ebikeInfo.setAssocName(data.getString("hyxhzhName"));    	//协会名称
		map.put("ebikeInfo", ebikeInfo);
		
		int number = 1;
		List<UserInfoBean> userInfoList = new ArrayList<>();
		while(data.getString("jsrxm" + number) != null){
			UserInfoBean userInfo = new UserInfoBean();
			userInfo.setUserImgUrl(data.getString("vcShowUser" + number + "Img"));	//使用人照片资源路径
			userInfo.setDriverName(data.getString("jsrxm" + number));   			//驾驶人姓名
			userInfo.setGender(data.getString("xb" + number));       				//性别	0-男,1-女
			userInfo.setAge(getAgeByID(data.getString("sfzmhm" + number)));			//年龄  
			userInfo.setIdentityNo(data.getString("sfzmhm" + number)); 				//身份证号
			userInfo.setMobilephone(data.getString("lxdh" + number));  				//联系电话
			userInfo.setCompanyName(data.getString("ssdwName"));  					//所属单位名称
			
			userInfoList.add(userInfo);
			number++;
		}
		map.put("userInfoList", userInfoList);
	}
	
	/**
	 * 根据身份证号码计算年龄
	 */
	public String getAgeByID(String id){
		if(StringUtils.isNotBlank(id) && id.length() == 18){ //18位身份证号  440301199002101119
			int bornYear = Integer.parseInt(id.substring(6, 10));
			int currYear = DateUtil.getNowYear();
			return String.valueOf(currYear - bornYear);
		}
		return "";
	}

	@Override
	public BaseBean applyForPAGoodCarOwners(ApplyForPAGoodCarOwners applyForPAGoodCarOwners) throws Exception {
	logger.info("平安好车主评选采集webService...");
		
		String interfaceNumber = "pahcz01";  //接口编号
		BaseBean refBean = new BaseBean();  //创建返回信息
		
		//拼装xml数据
		StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><REQUEST>")
			.append("<CZXM>").append(applyForPAGoodCarOwners.getOwnerName()).append("</CZXM>")     
			.append("<JSZH>").append(applyForPAGoodCarOwners.getDriverLicense()).append("</JSZH>")  
			.append("<HPHM>").append(applyForPAGoodCarOwners.getLicenseNumber()).append("</HPHM>")    
			.append("<HPZL>").append(applyForPAGoodCarOwners.getNumberPlate()).append("</HPZL>")   
			.append("<SJHM>").append(applyForPAGoodCarOwners.getMobile()).append("</SJHM>")  
			.append("<AQXY>").append(applyForPAGoodCarOwners.getSecurityDeclaration()).append("</AQXY>")   
			.append("<RZZP>").append(applyForPAGoodCarOwners.getRZZP()).append("</RZZP>")   
			.append("<SQLY>").append(applyForPAGoodCarOwners.getSourceOfCertification()).append("</SQLY>")    
			.append("</REQUEST>");
		
		try {
			@SuppressWarnings("static-access")
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(convenienceCache.getUrl(applyForPAGoodCarOwners.getSourceOfCertification()), convenienceCache.getMethod(applyForPAGoodCarOwners.getSourceOfCertification()), 
					interfaceNumber,sb.toString(),convenienceCache.getUserid(applyForPAGoodCarOwners.getSourceOfCertification()),convenienceCache.getUserpwd(applyForPAGoodCarOwners.getSourceOfCertification()),convenienceCache.getKey(applyForPAGoodCarOwners.getSourceOfCertification()));

			refBean.setCode(respStr.get("CODE").toString());  //返回状态码
			refBean.setMsg(respStr.get("MSG").toString());	  //返回消息描述
			
			logger.info("【平安好车主评选信息采集结果:"+respStr);
		} catch (Exception e) {
			logger.error("平安好车主评选信息采集失败！applyForPAGoodCarOwners="+applyForPAGoodCarOwners.toString(),e);
			
//			refBean.setCode(MsgCode.exception);  //返回状态码  系统返回错误
//			refBean.setMsg("服务器繁忙！");	  //返回消息描述
			throw e;
		}
		return refBean;
	}
	
}