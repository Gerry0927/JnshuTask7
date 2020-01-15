package com.gerry.jnshu.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Setter
@Getter
public class EmailInfo implements Serializable {

    private String id;
    private String toAddress;
    private String subject;
    private String content;

}
