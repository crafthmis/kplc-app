package com.kplc.bulksms.app.controller;


import com.kplc.bulksms.app.service.*;
import com.kplc.bulksms.app.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BaseController {
    @Autowired
    protected Environment env;
    @Autowired
    public AreaService areaService;
    @Autowired
    public ConstituencyService constituencyService;
    @Autowired
    public CountyService countyService;
    @Autowired
    public RegionService regionService;
    @Autowired
    public ContactService contactService;

    @Autowired
    protected PasswordEncoder passwordEncoder;
}
