package com.coolplay.user.user.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by majiancheng on 2019/12/13.
 */
public class EsBaseDto implements Serializable {

    private static final long serialVersionUID = 4181101934813478399L;

    private String id;

    private Integer type;

    private Integer typeId;

    private String name;

    private Integer userId;

    private List<String> labelNames;


}
