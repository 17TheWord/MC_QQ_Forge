package com.github.theword.returnBody;

import com.github.theword.returnBody.returnModle.SendTitle;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SendTitleReturnBody {

    @SerializedName("send_title")
    private SendTitle sendTitle;

}
