package com.hardware.business.service.impl;

import com.hardware.business.service.FengDianLoginService;
import org.springframework.stereotype.Service;

@Service("fengDianLoginService")
public class FengDianLoginServiceImpl implements FengDianLoginService{

//	 @Autowired
//	 private RestTemplate restTemplate;
//	 @Override
//	 public HashMap login() {
//		// TODO Auto-generated method stub
//		HashMap<String,String> params=new HashMap<String,String>();
//		params.put("version", FengdianConf.VERSION);
//		//返回的结果集通过Hash的方式进行存储
//		String url=FengdianConf.HOST+"/user/login/"+FengdianConf.USERID+"/"+FengdianConf.PASS;
//		return restTemplate.getForObject(url.toString(),HashMap.class,params);
//	}

}
