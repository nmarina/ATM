package com.nmarina.controller;

import com.google.gson.Gson;
import com.nmarina.dataobject.Atm;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AjaxController {

    @RequestMapping(method = RequestMethod.GET, value="/ajax/get-cash-from-atm")
    public ModelAndView getCashFromAtm(@RequestParam(value = "cashAmount", required = true) Integer cashAmount) {
        ModelAndView mav = new ModelAndView("ajax");
        Atm atm = Atm.getAtmInstance();
        int result = atm.getCashFromAtm(cashAmount);
        mav.addObject("ajax", result);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value="/ajax/add-cash-to-atm")
    public ModelAndView addCashToAtm(@RequestParam(value = "cashAmount", required = true) Integer cashAmount) {
        ModelAndView mav = new ModelAndView("ajax");
        Atm atm = Atm.getAtmInstance();
        boolean resultOperation = atm.addCashToAtm(cashAmount);
        mav.addObject("ajax", resultOperation);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value="/ajax/get-atm-balance")
    public ModelAndView getAtmBalance() {
        ModelAndView mav = new ModelAndView("ajax");
        Atm atm = Atm.getAtmInstance();
        mav.addObject("ajax", atm.getAtmBalance());
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value="/ajax/get-atm-notes-information")
    public ModelAndView getAtmNotesInformation() {
        ModelAndView mav = new ModelAndView("ajax");
        Atm atm = Atm.getAtmInstance();
        Gson gson = new Gson();
        String jsonAtmNotesInformation = gson.toJson(atm);
        mav.addObject("ajax", jsonAtmNotesInformation);
        return mav;
    }
}
