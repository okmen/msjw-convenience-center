package cn.convenience.dao.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.convenience.bean.UserOpenidBean;
import cn.convenience.bean.UserRegInfo;
import cn.convenience.bean.WechatUserInfoBean;
import cn.convenience.dao.IConvenienceDao;
import cn.convenience.dao.mapper.ConvenienceMapper;
import cn.convenience.orm.DeviceORM;
import cn.convenience.orm.UsernameORM;
import cn.convenience.orm.WechatUserInfoORM;

@Repository
public class IConvenienceDaoImpl implements IConvenienceDao {

	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private ConvenienceMapper userMapper;
	
	
	@Override
	public int insertWechatUserInfo(WechatUserInfoBean wechatUserInfo) {
		userMapper.insertWechatUserInfo(wechatUserInfo);
		return wechatUserInfo.getId();
	}

	@Override
	public WechatUserInfoBean getWechatUserInfoById(int id) {
		WechatUserInfoORM orm = userMapper.getWechatUserInfoById(id);
	
		if (orm != null) {
			WechatUserInfoBean bean = new WechatUserInfoBean();
			BeanUtils.copyProperties(orm, bean);
			return bean;
		}
	
	return null;
	}
	
	public List<WechatUserInfoBean> getAllWechatUserInfoBeanList(){
		return userMapper.getAllWechatUserInfoBeanList();
	}

	@Override
	public long addNewUser(UserRegInfo userRegInfo) {
		return userMapper.addNewUser(userRegInfo);
	}

	@Override
	public long getMaxUsername() {
		return Long.valueOf(userMapper.getMaxUsername());
	}

    @Override
    public UsernameORM createUsername() {
        long addTime = System.currentTimeMillis()/1000;
        UsernameORM usernameORM = new UsernameORM();
        usernameORM.setAddTime(addTime);
        
        userMapper.createUsername(usernameORM);
        return usernameORM;
    }

    @Override
    public long addBindOpenid(UserOpenidBean userOpenidBean) {
        return userMapper.addBindOpenid(userOpenidBean);
    }

    @Override
    public long updateBindOpenidStatus(UserOpenidBean userOpenidBean) {
        return userMapper.updateBindOpenidStatus(userOpenidBean);
    }

    @Override
    public Long getUserIdByOpenid(String openid) {
        return userMapper.getUserIdByOpenid(openid);
    }

    @Override
    public String getOpenidByUserId(long userId) {
        return userMapper.getOpenidByUserId(userId);
    }

    @Override
    public DeviceORM getDevice(String deviceUuid,int osType) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("deviceUuid",deviceUuid );
        map.put("osType",osType);
        return userMapper.getDevice(map);
    }

    @Override
    public void addDevice(String deviceUuid,int osType,long userId,long addTime) {
        DeviceORM deviceORM = new DeviceORM();
        deviceORM.setDeviceUuid(deviceUuid);
        deviceORM.setOsType(osType);
        deviceORM.setUserId(userId);
        deviceORM.setAddTime(addTime);
        userMapper.addDevice(deviceORM);
    }

    @Override
    public boolean updateDevice(String deviceUuid,int osType,long userId) {
        DeviceORM deviceORM = new DeviceORM();
        deviceORM.setDeviceUuid(deviceUuid);
        deviceORM.setOsType(osType);
        deviceORM.setUserId(userId);
        return userMapper.updateDevice(deviceORM);
    }

}
