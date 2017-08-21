package com.hardware.business.service.impl;

import com.hardware.business.dao.HouseStreetDao;
import com.hardware.business.model.HouseStreet;
import com.hardware.business.service.HouseStreetService;
import org.iframework.support.spring.hibernate.service.BaseServiceSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("houseStreetServiceImpl")
public class HouseStreetServiceImpl extends BaseServiceSupport<HouseStreet, String> implements HouseStreetService {

    @Resource(name = "houseStreetDao")
    private HouseStreetDao houseStreetDao;
}
