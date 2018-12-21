package com.scheduler.controller;

import com.scheduler.domain.Message;
import com.scheduler.domain.User;
import com.scheduler.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


/**
 * @Controller is a program module which listen to user`s requests on path "/greeting",
 * and returns some data, (11.11.2018: it will returns some migration to greeting and main files...)
 */
@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    /**
     * анотація   @Value("${upload.path}") говорить спрінгу, що ми хочеть отримати деяку змінну
     * це стрінгова мова запитів, ця команда бере(видьоргує) якесь значення із "application.properties"
     */
    @Value("${upload.path}")
    private String uploadPath;
    /**
        * @param name - data (parameter) which we transfer to our migration
        * @param model - instance where we`ll lay data which we want to return
        * @return
       *@GetMapping("/greeting")
       public String greeting(
               @RequestParam(name="name", required=false, defaultValue="World") String name,
               Map<String,Object> model) {
               model.put("name", name);
           return "greeting";
       }*/
    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    /**
     *  by this method we add text "Welcome to Spring sufferer!!!" by key "key1" to "main.ftl" file.
     *  "main.ftl" file is our root file like 'index.html'
     * @param model
     * @return
     */
    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = messageRepo.findAll();

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Message message = new Message(text, tag, user);
        //файл має існувати і не бути пустим
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            /**
             * наступний код захищає нашу програму від коллізї(повторення) файлів UUID - universal upload ID
             */
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            /**
             *
             */
            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }

        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "main";
    }
}