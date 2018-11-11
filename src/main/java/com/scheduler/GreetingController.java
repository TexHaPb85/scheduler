package com.scheduler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


/**
 * @Controller is a program module which listen to user`s requests on path "/greeting",
 * and returns some data, (11.11.2018: it will returns some template to greeting and main files...)
 */
@Controller
public class GreetingController {

    /**
     *
     * @param name - data (parameter) which we transfer to our template
     * @param model - instance where we`ll lay data which we want to return
     * @return
     */
    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String,Object> model) {
        model.put("name", name);
        return "greeting";
    }

    /**
     * by this method we add text "Welcome to Spring sufferer!!!" by key "key1" to "main.mustache" file.
     *  "main.mustache" file is our root file like 'index.html'
     * @param model
     * @return
     */
    @GetMapping
    public  String main(Map<String, Object> model){
        model.put("key1", "Welcome to Spring sufferer!!!");
        return "main";
    }

}