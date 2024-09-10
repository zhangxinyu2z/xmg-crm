package com._520it.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WeatherController {
    @RequestMapping("/weather")
	public String index(){
		return "weather";
	}
}
