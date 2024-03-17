package com.github.theword.utils;

import com.github.theword.returnBody.returnModle.MyBaseComponent;
import com.github.theword.returnBody.returnModle.MyTextComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.util.List;

public class ParseJsonToEvent {
    public StringTextComponent parseMessages(List<? extends MyBaseComponent> myBaseComponentList) {
        StringTextComponent mutableComponent = parsePerMessageToMultiText(myBaseComponentList.get(0));
        for (int i = 1; i < myBaseComponentList.size(); i++) {
            MyBaseComponent myBaseComponent = myBaseComponentList.get(i);
            StringTextComponent tempMutableComponent = parsePerMessageToMultiText(myBaseComponent);
            mutableComponent.append(tempMutableComponent);
        }
        return mutableComponent;
    }

    public StringTextComponent parsePerMessageToMultiText(MyBaseComponent myBaseComponent) {
        StringTextComponent stringTextComponent = new StringTextComponent(myBaseComponent.getText());

        ResourceLocation font = null;
        if (myBaseComponent.getFont() != null) {
            font = new ResourceLocation(myBaseComponent.getFont());
        }

        Style style = Style.EMPTY.
                withColor(Color.parseColor(myBaseComponent.getColor()))
                .withBold(myBaseComponent.isBold())
                .withItalic(myBaseComponent.isItalic())
                .withUnderlined(myBaseComponent.isUnderlined())
                .setStrikethrough(myBaseComponent.isStrikethrough())
                .setObfuscated(myBaseComponent.isObfuscated())
                .withInsertion(myBaseComponent.getInsertion())
                .withFont(font);

        if (myBaseComponent instanceof MyTextComponent) {
            MyTextComponent myTextComponent = (MyTextComponent) myBaseComponent;
            if (myTextComponent.getClickEvent() != null) {
                ClickEvent.Action tempAction = ClickEvent.Action.getByName(myTextComponent.getClickEvent().getAction());
                ClickEvent clickEvent = new ClickEvent(tempAction, myTextComponent.getClickEvent().getValue());
                style = style.withClickEvent(clickEvent);
            }
            // TODO 悬浮事件待完善
            if (myTextComponent.getHoverEvent() != null) {
                HoverEvent hoverEvent = null;
                switch (myTextComponent.getHoverEvent().getAction()) {
                    case "show_text":
                        if (myTextComponent.getHoverEvent().getBaseComponentList() != null && myTextComponent.getHoverEvent().getBaseComponentList().size() > 0) {
                            StringTextComponent textComponent = parseMessages(myTextComponent.getHoverEvent().getBaseComponentList());
                            hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, textComponent);
                        }
                        break;
                    case "show_item":
//                            hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_ITEM, new Item());
                        break;
                    case "show_entity":
//                            hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new Entity());
                        break;
                    default:
                        break;
                }
                style = style.withHoverEvent(hoverEvent);
            }
        }
        stringTextComponent.setStyle(style);

        return stringTextComponent;
    }
}
