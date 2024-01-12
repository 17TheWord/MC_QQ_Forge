package com.github.theword.returnBody.returnModle;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MyTextComponent extends MyBaseComponent {

    @SerializedName("click_event")
    private MyClickEvent clickEvent;

    @SerializedName("hover_event")
    private MyHoverEvent hoverEvent;

}
