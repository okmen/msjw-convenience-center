package cn.convenience.service.impl;
import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.convenience.bean.MsjwInfo;
import cn.convenience.bean.MsjwInfo.AuthenticationBasicInformation;
import cn.convenience.bean.MsjwInfo.DriverLicenceInfo;
import cn.convenience.bean.MsjwInfo.VehicleInfo;
import cn.convenience.cached.impl.IConvenienceCachedImpl;
import cn.convenience.service.IMsjwService;
import cn.convenience.utils.HttpRequest;
import cn.sdk.bean.BaseBean;
import cn.sdk.util.Constants;
import cn.sdk.util.HttpClientUtil;
import cn.sdk.util.MsgCode;
import cn.sdk.webservice.WebServiceClient;

@Service("msjwService")
public class IMsjwServiceImpl implements IMsjwService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IConvenienceCachedImpl convenienceCache;
	
	/**
	 * 民生警务个人信息
	 * @param identityCard 身份证
	 * @param sourceOfCertification 认证来源
	 * @return
	 * @throws Exception
	 */
	public BaseBean getMSJWinfo(String identityCard, String sourceOfCertification) throws Exception {
		logger.info("【民生警务】getMSJWinfo请求参数，identityCard = " + identityCard + ",sourceOfCertification = " + sourceOfCertification);
		BaseBean baseBean = new BaseBean();
		String jkId = "MSJW01";
		StringBuffer sb = new StringBuffer();
		try {
			 String url = convenienceCache.getUrl(sourceOfCertification); //webservice请求url
			 String method = convenienceCache.getMethod(sourceOfCertification); //webservice请求方法名称
			 String userId = convenienceCache.getUserid(sourceOfCertification); //webservice登录账号
			 String userPwd = convenienceCache.getUserpwd(sourceOfCertification); //webservice登录密码
			 String key = convenienceCache.getKey(sourceOfCertification); //秘钥
			 
			 sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><REQUEST>")
			 .append("<SFZMHM>").append(identityCard).append("</SFZMHM>")
			 .append("<RZLY>").append(sourceOfCertification).append("</RZLY>")
			 .append("</REQUEST>");
			 
			 @SuppressWarnings("static-access")
			 JSONObject json = WebServiceClient.getInstance().requestWebService(url, method, jkId, sb.toString(), userId, userPwd, key);
			 
			 String CODE = json.getString("CODE");
			 String MSG = json.getString("MSG");
			 if(MsgCode.success.equals(CODE)){
				 MsjwInfo info = new MsjwInfo();
				 AuthenticationBasicInformation baseInfo = new MsjwInfo.AuthenticationBasicInformation();
				 
				 //个人信息
				 JSONObject BODY = json.getJSONObject("BODY");
				 baseInfo.setTrueName(BODY.getString("LOGIN_TRUENAME"));//真实姓名
				 baseInfo.setIdentityCard(BODY.getString("SFZMHM"));//身份证明号码
				 baseInfo.setUserRole(BODY.getString("YHJS"));//用户角色
				 baseInfo.setZt(BODY.getString("ZT"));//状态  1-机动车状态
				 baseInfo.setMobilephone(BODY.getString("YDDH"));//移动电话
				 baseInfo.setPlaceOfDomicile(BODY.getString("SFSH"));//是否深户
				 baseInfo.setAddress(BODY.getString("TXDZ"));//通讯地址
				 baseInfo.setCertTime(BODY.getString("SHSJ"));//认证时间
				 
				 //用户是否已绑定驾驶证0-未绑定，1-已绑定
				 String BDDRV = BODY.getString("BDDRV");
				 baseInfo.setBindDriverLicence(BDDRV);
				 //已绑定驾驶证
				 if("1".equals(BDDRV)){
					 //驾驶证信息
					 Object driverObj = BODY.get("ROWDRV");
					 ArrayList<MsjwInfo.DriverLicenceInfo> driverLicenceInfoList = new ArrayList<>();
					 if(driverObj instanceof JSONArray){
						 JSONArray arr = (JSONArray) driverObj;
						 for(int i = 0; i < arr.size(); i++){
							 JSONObject ROWDRV = arr.getJSONObject(i);
							 DriverLicenceInfo driverLicenceInfo = new MsjwInfo.DriverLicenceInfo();
							 driverLicenceInfo.setDriverLicenceNo(ROWDRV.getString("JSZHM"));//驾驶证号码
							 driverLicenceInfo.setFileNumber(ROWDRV.getString("DABH"));//驾驶证档案编号
							 driverLicenceInfoList.add(driverLicenceInfo);
						 }
					 }else if(driverObj instanceof JSONObject){
						 JSONObject ROWDRV = (JSONObject) driverObj;
						 DriverLicenceInfo driverLicenceInfo = new MsjwInfo.DriverLicenceInfo();
						 driverLicenceInfo.setDriverLicenceNo(ROWDRV.getString("JSZHM"));//驾驶证号码
						 driverLicenceInfo.setFileNumber(ROWDRV.getString("DABH"));//驾驶证档案编号
						 driverLicenceInfoList.add(driverLicenceInfo);
					 }
					 info.setDriverLicenceInfoList(driverLicenceInfoList);
				 }
				 
				 //用户是否已绑定车辆 0-未绑定，1-已绑定
				 String BDVEH = BODY.getString("BDVEH");
				 baseInfo.setBindVehicle(BDVEH);
				 //已绑定车辆
				 if("1".equals(BDVEH)){
					 Object vehicleObj = BODY.get("ROWVEH");
					 ArrayList<MsjwInfo.VehicleInfo> cars = new ArrayList<>();
					 Map<String, String> vehicleStatusMap = Constants.listToMap(cn.convenience.utils.Constants.VEHICLE_STATUS_LIST);
					 if(vehicleObj instanceof JSONArray){
						 JSONArray arr = (JSONArray) vehicleObj;
						 for(int i = 0; i < arr.size(); i++){
							 JSONObject ROWVEH = arr.getJSONObject(i);
							 VehicleInfo vehicleInfo = new MsjwInfo.VehicleInfo();
							 vehicleInfo.setMyNumberPlate(ROWVEH.getString("HPHM"));//号牌号码
							 vehicleInfo.setPlateType(ROWVEH.getString("HPZL"));//号牌种类
							 vehicleInfo.setInspectDate(ROWVEH.getString("SYRQ"));//审验日期
							 vehicleInfo.setBehindTheFrame4Digits(ROWVEH.getString("CJH4"));//车架后4位
							 vehicleInfo.setName(ROWVEH.getString("CZXM"));//车主姓名
							 vehicleInfo.setIdentityCard(ROWVEH.getString("CZSFZMHM"));//车主身份证明号码
							 vehicleInfo.setIsMySelf("本人".equals(ROWVEH.getString("SFBR")) ? "0" : "1");//是否本人 0-本人 1-非本人
							 vehicleInfo.setBindDepartment(ROWVEH.getString("BIND_DEPARTMENT"));//A app C微信 Z支付宝 E邮政W外网星火
							 vehicleInfo.setVehicleStatus(vehicleStatusMap.get(ROWVEH.getString("ZT")));//状态提醒 A正常B转出C被盗抢D停驶E注销G违法未处理H海关监管I事故未处理J嫌疑车K查封L暂扣M强制注销N事故逃逸O锁定P到达报废标准公告牌证作废Q逾期未检验
							 vehicleInfo.setForceScrapDate(ROWVEH.getString("QZBFQZ"));//强制报废期止提醒
							 cars.add(vehicleInfo);
						 }
					 }else if(vehicleObj instanceof JSONObject){
						 JSONObject ROWVEH = (JSONObject) vehicleObj;
						 VehicleInfo vehicleInfo = new MsjwInfo.VehicleInfo();
						 vehicleInfo.setMyNumberPlate(ROWVEH.getString("HPHM"));//号牌号码
						 vehicleInfo.setPlateType(ROWVEH.getString("HPZL"));//号牌种类
						 vehicleInfo.setInspectDate(ROWVEH.getString("SYRQ"));//审验日期
						 vehicleInfo.setBehindTheFrame4Digits(ROWVEH.getString("CJH4"));//车架后4位
						 vehicleInfo.setName(ROWVEH.getString("CZXM"));//车主姓名
						 vehicleInfo.setIdentityCard(ROWVEH.getString("CZSFZMHM"));//车主身份证明号码
						 vehicleInfo.setIsMySelf("本人".equals(ROWVEH.getString("SFBR")) ? "0" : "1");//是否本人 0-本人 1-非本人
						 vehicleInfo.setBindDepartment(ROWVEH.getString("BIND_DEPARTMENT"));//A app C微信 Z支付宝 E邮政W外网星火
						 vehicleInfo.setVehicleStatus(vehicleStatusMap.get(ROWVEH.getString("ZT")));//状态提醒 A正常B转出C被盗抢D停驶E注销G违法未处理H海关监管I事故未处理J嫌疑车K查封L暂扣M强制注销N事故逃逸O锁定P到达报废标准公告牌证作废Q逾期未检验
						 vehicleInfo.setForceScrapDate(ROWVEH.getString("QZBFQZ"));//强制报废期止提醒
						 cars.add(vehicleInfo);
					 }
					 info.setCars(cars);
				 }
				 info.setAuthenticationBasicInformation(baseInfo);
				 baseBean.setData(info);
			 }
			 
			 baseBean.setCode(CODE);
			 baseBean.setMsg(MSG);
			 
		} catch (Exception e) {
			logger.error("【民生警务】getMSJWinfo接口异常，identityCard="+ identityCard + ",sourceOfCertification=" + sourceOfCertification, e);
			throw e;
		}
		return baseBean;
	}

	/**
	 * 根据openid获取民生警务平台用户信息
	 * @param openId 民生警务平台公众号openId
	 * @return
	 */
	public JSONObject getUserInfoFromMsjw(String openId) {
		JSONObject json = new JSONObject();
		
		String token = convenienceCache.getMsjwToken();//民生警务平台提供的token
		String checkuserUrl = convenienceCache.getCheckuserUrl();
		String url = checkuserUrl + openId + "?token=" + token;
		try {
			String respStr = HttpClientUtil.get(url);
			logger.info("【民生警务】民生警务-用户验证接口返回结果：" + respStr);
			if(respStr != null){
				json = JSONObject.parseObject(respStr);
			}else{
				json.put("code", MsgCode.businessError);
				json.put("message", "用户验证接口异常");
				logger.info("【民生警务】民生警务-用户验证接口返回结果异常，url="+url);
			}
		} catch (Exception e) {
			logger.error("【民生警务】getUserInfoFromMsjw接口异常，url="+url, e);
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 发送模板消息到民生警务平台
	 * @param params 模板消息内容json格式字符串
	 * @return
	 * @throws Exception
	 */
	public JSONObject sendTemplateMsg2Msjw(String params) throws Exception {
		logger.info("【民生警务】民生警务-发送微信消息，内容参数：params=" + params);
		JSONObject json = new JSONObject();
		
		String url = convenienceCache.getGovnetUri() + "/bmswx/weixin/component/sendTemplate";
		try {
			String respStr = HttpRequest.sendPost(url, params, null, "application/json");
			logger.info("【民生警务】民生警务-发送微信消息返回结果：" + respStr);
			
			json = JSONObject.parseObject(respStr);
			
		} catch (Exception e) {
			logger.error("【民生警务】sendTemplate2Msjw接口异常，url="+url+",params="+params, e);
			e.printStackTrace();
		}
		return json;
	}

	
	
	
	/**
	 * 查询消息推送结果
	 * @param msgId 微信消息id
	 * @return
	 * @throws Exception
	 */
	public JSONObject queryTemplateMsgSendResult(String msgId) throws Exception {
		logger.info("【民生警务】民生警务-查询消息推送结果请求参数：msgId="+msgId);
		JSONObject json = new JSONObject();
		
		String token = convenienceCache.getMsjwToken();//民生警务平台提供的token
		String url = convenienceCache.getGovnetUri() + "/govnetProvider/services/tmpMsg/getStatus/" + msgId + "?token=" + token;
		try {
			String respStr = HttpClientUtil.get(url);
			logger.info("【民生警务】民生警务-查询消息推送返回结果：" + respStr);
			
			json = JSONObject.parseObject(respStr);
			String code = json.getString("code");
			if("200".equals(code)){
				String status = json.getJSONArray("datas").getString(0);
				if(status.contains("1")){
					json.put("statusDesc", "发送中");
				}else if(status.contains("2")){
					json.put("statusDesc", "已发送");
				}else if(status.contains("3")){
					json.put("statusDesc", "发送失败");
				}else if(status.contains("4")){
					json.put("statusDesc", "用户拒绝接收");
				}
			}
			
		} catch (Exception e) {
			logger.error("【民生警务】queryTemplateMsgSendResult接口异常，url="+url+",msgId="+msgId, e);
			e.printStackTrace();
		}
		return json;
	}
}