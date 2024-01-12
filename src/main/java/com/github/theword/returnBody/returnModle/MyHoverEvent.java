package com.github.theword.returnBody.returnModle;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class MyHoverEvent {

    private String action;
    @SerializedName("base_component_list")
    private List<MyBaseComponent> baseComponentList;

    @SerializedName("hover_item")
    private MyHoverItem item;

    @SerializedName("hover_entity")
    private MyHoverEntity entity;


}
