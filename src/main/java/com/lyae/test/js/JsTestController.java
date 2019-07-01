package com.lyae.test.js;

import com.lyae.menu.Menu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/jstest")
@Controller
@Menu(name = "JsTest", desc = "JsTest")
public class JsTestController {

    @Menu(name = "JsonArray", desc = "JsonArray", order = 1)
    @GetMapping("/jsarray")
    public String jsArray() {

        return "js/jsArray";
    }

    @RequestMapping("/ajax")
    public String ajax(@ModelAttribute JsVO vo, HttpServletRequest req, HttpServletResponse res) {
        System.out.println("여기다");
        System.out.println(vo);
        return null;
    }
}
