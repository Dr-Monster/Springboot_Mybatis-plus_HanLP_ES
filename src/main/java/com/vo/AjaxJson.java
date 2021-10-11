package com.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AjaxJson {
    String msg;
    Object data;
    boolean isSuccess;
}
