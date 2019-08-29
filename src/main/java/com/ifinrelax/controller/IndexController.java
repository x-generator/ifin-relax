package com.ifinrelax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Timur Berezhnoi
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }
}