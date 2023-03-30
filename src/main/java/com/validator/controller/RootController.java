package com.validator.controller;

import com.validator.entity.Equation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
@AllArgsConstructor
public class RootController {
    @GetMapping("/root")
    public final String findAll(final Model model) {
        Equation equation = new Equation();
        model.addAttribute("equation", equation);
        return "index";
    }
}
