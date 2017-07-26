package cn.convenience.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.convenience.bean.ApplyGreenRet;
import cn.convenience.bean.GreenTravelBean;
import cn.sdk.bean.BaseBean;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:junit-test.xml" })
public class TestGreenTravelService {
	
	@Autowired
	@Qualifier("greentravelService")
	private IGreentravelService greentravelService;
	@Test
	public void testapplyDownDate(){
		GreenTravelBean greenTravelBean=new GreenTravelBean();
		greenTravelBean.setHphm("粤B701NR");   //车牌号码(带’粤’)
		greenTravelBean.setHpzl("02");    //号牌种类
		greenTravelBean.setSfzmhm("440301199002101119");   //身份证明号码
		greenTravelBean.setMonth("201704");    //月份
		try {
			BaseBean baseBean=greentravelService.applyDownDate(greenTravelBean);
			System.out.println(baseBean.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testdownDatedeclare(){
		GreenTravelBean greenTravelBean=new GreenTravelBean();
		greenTravelBean.setSname("杨明畅");
		greenTravelBean.setHphm("粤B701NR");   //车牌号码(带’粤’)
		greenTravelBean.setHpzl("02");
		greenTravelBean.setSfzmhm("440301199002101119");   //身份证明号码
		greenTravelBean.setMobile("18603017278");
		greenTravelBean.setSfbr("1");
		greenTravelBean.setLrly("WX");
		ApplyGreenRet appret=new ApplyGreenRet();
		appret.setCdate("20170724");
		appret.setType("1");
		List<ApplyGreenRet> list=new ArrayList<ApplyGreenRet>();
		list.add(appret);
		greenTravelBean.setApplyGreenRetList(list);
		try {
			BaseBean baseBean=greentravelService.downDatedeclare(greenTravelBean);
			System.out.println(baseBean.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testapplyrunningQuery(){
		GreenTravelBean greenTravelBean=new GreenTravelBean();
		greenTravelBean.setHphm("粤B701NR");   //车牌号码(带’粤’)
		greenTravelBean.setHpzl("02");    //号牌号码
		greenTravelBean.setSqrq("20170728");
		try {
			BaseBean baseBean=greentravelService.applyrunningQuery(greenTravelBean);
			System.out.println(baseBean.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
