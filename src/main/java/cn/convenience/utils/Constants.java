package cn.convenience.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sdk.bean.DownValue;

public class Constants {
	
	/**
	 * （测试公众号）民生警务-办理类业务消息推送模板id
	 */
	public final static String TEST_HANDLE_BUSINESS_TEMPLATE_ID = "PkGXcDpXPWOQRRcSoIkbrDVidGUDt-WmDxFVpOgZ5t0";
	
	/**
	 * （测试公众号）民生警务-预约类业务消息推送模板id
	 */
	public final static String TEST_BOOK_BUSINESS_TEMPLATE_ID = "Q3Zz5NpKlPKiw0LX6uaJ2xifSfOtdcJhaftEkqJB55U";
	
	
	/**
	 * 车辆状态 A正常
	 */
	public final static String VEHICLE_STATUS_A_CODE = "A";
	public final static String VEHICLE_STATUS_A_TEXT = "正常";
	
	/**
	 * 车辆状态 B转出
	 */
	public final static String VEHICLE_STATUS_B_CODE = "B";
	public final static String VEHICLE_STATUS_B_TEXT = "转出";
	
	/**
	 * 车辆状态 C被盗抢
	 */
	public final static String VEHICLE_STATUS_C_CODE = "C";
	public final static String VEHICLE_STATUS_C_TEXT = "被盗抢";
	
	/**
	 * 车辆状态 D停驶
	 */
	public final static String VEHICLE_STATUS_D_CODE = "D";
	public final static String VEHICLE_STATUS_D_TEXT = "停驶";
	
	/**
	 * 车辆状态 E注销
	 */
	public final static String VEHICLE_STATUS_E_CODE = "E";
	public final static String VEHICLE_STATUS_E_TEXT = "注销";
	
	/**
	 * 车辆状态 G违法未处理
	 */
	public final static String VEHICLE_STATUS_G_CODE = "G";
	public final static String VEHICLE_STATUS_G_TEXT = "违法未处理";
	
	/**
	 * 车辆状态 H海关监管
	 */
	public final static String VEHICLE_STATUS_H_CODE = "H";
	public final static String VEHICLE_STATUS_H_TEXT = "海关监管";
	
	/**
	 * 车辆状态 I事故未处理
	 */
	public final static String VEHICLE_STATUS_I_CODE = "I";
	public final static String VEHICLE_STATUS_I_TEXT = "事故未处理";
	
	/**
	 * 车辆状态 J嫌疑车
	 */
	public final static String VEHICLE_STATUS_J_CODE = "J";
	public final static String VEHICLE_STATUS_J_TEXT = "嫌疑车";
	
	/**
	 * 车辆状态 K查封
	 */
	public final static String VEHICLE_STATUS_K_CODE = "K";
	public final static String VEHICLE_STATUS_K_TEXT = "查封";
	
	/**
	 * 车辆状态 L暂扣
	 */
	public final static String VEHICLE_STATUS_L_CODE = "L";
	public final static String VEHICLE_STATUS_L_TEXT = "暂扣";
	
	/**
	 * 车辆状态 M强制注销
	 */
	public final static String VEHICLE_STATUS_M_CODE = "M";
	public final static String VEHICLE_STATUS_M_TEXT = "强制注销";
	
	/**
	 * 车辆状态 N事故逃逸
	 */
	public final static String VEHICLE_STATUS_N_CODE = "N";
	public final static String VEHICLE_STATUS_N_TEXT = "事故逃逸";
	
	/**
	 * 车辆状态 O锁定
	 */
	public final static String VEHICLE_STATUS_O_CODE = "O";
	public final static String VEHICLE_STATUS_O_TEXT = "锁定";
	
	/**
	 * 车辆状态 P到达报废标准公告牌证作废
	 */
	public final static String VEHICLE_STATUS_P_CODE = "P";
	public final static String VEHICLE_STATUS_P_TEXT = "到达报废标准公告牌证作废";
	
	/**
	 * 车辆状态 Q逾期未检验
	 */
	public final static String VEHICLE_STATUS_Q_CODE = "Q";
	public final static String VEHICLE_STATUS_Q_TEXT = "逾期未检验";
	
	public static List<DownValue<String>> VEHICLE_STATUS_LIST = new ArrayList<DownValue<String>>();
	
	static {
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_A_CODE, VEHICLE_STATUS_A_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_B_CODE, VEHICLE_STATUS_B_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_C_CODE, VEHICLE_STATUS_C_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_D_CODE, VEHICLE_STATUS_D_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_E_CODE, VEHICLE_STATUS_E_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_G_CODE, VEHICLE_STATUS_G_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_H_CODE, VEHICLE_STATUS_H_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_I_CODE, VEHICLE_STATUS_I_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_J_CODE, VEHICLE_STATUS_J_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_K_CODE, VEHICLE_STATUS_K_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_L_CODE, VEHICLE_STATUS_L_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_M_CODE, VEHICLE_STATUS_M_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_N_CODE, VEHICLE_STATUS_N_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_O_CODE, VEHICLE_STATUS_O_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_P_CODE, VEHICLE_STATUS_P_TEXT));
		VEHICLE_STATUS_LIST.add(new DownValue<String>(VEHICLE_STATUS_Q_CODE, VEHICLE_STATUS_Q_TEXT));
	}
	
	public static <T> Map<T, String> listToMap(List<DownValue<T>> list){
		Map<T, String> map = new HashMap<>();
		for (DownValue o : list) {
			T t = (T) o.getValue();
			String v = (String) o.getText();
			map.put(t, v);
		}
		return map;
	}
}
