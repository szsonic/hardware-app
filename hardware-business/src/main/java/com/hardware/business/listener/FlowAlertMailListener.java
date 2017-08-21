package com.hardware.business.listener;

import com.hardware.business.event.FlowAlertMailEvent;
import com.hardware.business.model.DataPlan;
import org.iframework.commons.utils.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * <br>
 *
 * @author chengzhifeng
 */
@Component
public class FlowAlertMailListener implements ApplicationListener<FlowAlertMailEvent> {

	@Autowired
	private MailSender mailSender;

	@Autowired
	private SimpleMailMessage simpleMailMessage;

	@Override
	public void onApplicationEvent(FlowAlertMailEvent flowAlertMailEvent) {
		Log.i("FlowAlertMailEvent is start.");

		String url = "http://iot.video-box.cn/telecom/f/telecom/package/doQuery4App";
		RestTemplate restTemplate = new RestTemplate();
		//调接口，获取流量套餐信息
		for (String accNo : flowAlertMailEvent.getAccNo()) {
			DataPlan dataPlan = restTemplate.getForObject(url + "?accNo=" + accNo, DataPlan.class);
			simpleMailMessage.setSubject("流量告警通知");
			simpleMailMessage.setText("尊敬的用户，您的流量已不足20M，请及时充值！");
			try {
				mailSender.send(simpleMailMessage);
			} catch (RestClientException e) {
				System.out.println(e.getStackTrace());
			}
		}
		Log.i("FlowAlertMailEvent is end.");

	}
}
