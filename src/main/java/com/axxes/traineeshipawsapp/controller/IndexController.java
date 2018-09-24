package com.axxes.traineeshipawsapp.controller;

import com.axxes.traineeshipawsapp.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {

    @Autowired
    private ScoreService scoreService;

    @RequestMapping
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/score")
    public String getScore(@RequestParam final String name, @RequestParam final String group, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("group", group);
        model.addAttribute("score", scoreService.getScore(name, group));

        return "score";
    }

    @RequestMapping(value = "/score/adjust", method = RequestMethod.POST)
    public RedirectView adjustScore(@RequestParam final String name, @RequestParam final String group, @RequestParam final int score,
                                    RedirectAttributes redirectAttributes) {
        scoreService.adjustScore(name, group, score);

        redirectAttributes.addAttribute("name", name);
        redirectAttributes.addAttribute("group", group);

        return new RedirectView("/score");
    }

    @RequestMapping(value = "/score/add", method = RequestMethod.POST)
    public RedirectView addScore(@RequestParam final String name, @RequestParam final String group, @RequestParam final int score,
                                 RedirectAttributes redirectAttributes) {
        scoreService.addScore(name, group, score);

        redirectAttributes.addAttribute("name", name);
        redirectAttributes.addAttribute("group", group);

        return new RedirectView("/score");
    }
}
