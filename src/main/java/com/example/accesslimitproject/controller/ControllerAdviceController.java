package com.example.accesslimitproject.controller;

import com.example.accesslimitproject.domain.Author;
import com.example.accesslimitproject.domain.Book;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ControllerAdviceController {

    @GetMapping("test1")
    public String test(Model model){
        Map<String, Object> map = model.asMap();
        System.out.println(map);
        String s =null;
        return s.toUpperCase();
    }

    @GetMapping("test2")
    public String test2(@ModelAttribute("b") Book book,@ModelAttribute("a") Author author){
        System.out.println(book);
        System.out.println(author);
        return "true";
    }

}
