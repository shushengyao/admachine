package com.xmlan.machine.module.circuit.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 路灯电路管理类
 * @program: admachine
 * @description: controller
 * @author: YSS
 * @create: 2018-09-10 16:30
 **/
@Controller
@RequestMapping("${adminPath}/circuit")
public class CircuitController {
    @RequestMapping(value = "/index")
    public String index(){
        return "circuit/index";
    }
}
