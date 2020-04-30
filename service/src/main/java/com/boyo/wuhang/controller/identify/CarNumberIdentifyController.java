package com.boyo.wuhang.controller.identify;

import com.boyo.wuhang.service.identify.CarNumberIdentifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/identify/")
public class CarNumberIdentifyController {

    @Autowired
    private CarNumberIdentifyService identifyService;
}
