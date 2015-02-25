package com.nmarina.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AtmController {

    @RequestMapping(method = RequestMethod.GET, value="/")
    public ModelAndView indexPage() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
}
