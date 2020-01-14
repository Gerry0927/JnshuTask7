package com.gerry.jnshu.pojo;

import lombok.*;

import javax.annotation.sql.DataSourceDefinition;
import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsInfo implements Serializable {

    private String id;
    private String content;
    private String outId;
    private String phoneNum;
}
