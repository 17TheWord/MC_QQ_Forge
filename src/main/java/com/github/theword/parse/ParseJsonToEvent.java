package com.github.theword.parse;

import com.github.theword.returnBody.returnModle.MyBaseComponent;

import java.util.List;

public class ParseJsonToEvent {

    public static String parseMessageToText(List<? extends MyBaseComponent> myBaseComponentList) {
        StringBuilder msgLogText = new StringBuilder();

        for (MyBaseComponent myBaseComponent : myBaseComponentList) {
            msgLogText.append(myBaseComponent.getText());
        }

        return msgLogText.toString();
    }
}
