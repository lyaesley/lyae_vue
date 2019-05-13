package com.lyae.test.js;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data @Getter @Setter
public class JsVO {
    private String title;
    private String conts;

    private List<JsVO> JsVOList;
}
