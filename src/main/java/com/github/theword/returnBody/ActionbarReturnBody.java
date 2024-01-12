package com.github.theword.returnBody;

import com.github.theword.returnBody.returnModle.MyBaseComponent;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ActionbarReturnBody {
    @SerializedName("message_list")
    private List<MyBaseComponent> messageList;
}
