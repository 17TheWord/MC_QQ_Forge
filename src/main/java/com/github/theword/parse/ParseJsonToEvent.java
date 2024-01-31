package com.github.theword.parse;

import com.github.theword.returnBody.returnModle.MyBaseComponent;
import com.github.theword.returnBody.returnModle.MyTextComponent;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ParseJsonToEvent {

    public static MutableComponent parseMessages(List<? extends MyBaseComponent> myBaseComponentList) {
        MutableComponent mutableComponent = parsePerMessageToMultiText(myBaseComponentList.get(0));
        for (int i = 1; i < myBaseComponentList.size(); i++) {
            MyBaseComponent myBaseComponent = myBaseComponentList.get(i);
            MutableComponent tempMutableComponent = parsePerMessageToMultiText(myBaseComponent);
            mutableComponent.append(tempMutableComponent);
        }
        return mutableComponent;
    }

    public static MutableComponent parsePerMessageToMultiText(MyBaseComponent myBaseComponent) {
        LiteralContents literalContents = new LiteralContents(myBaseComponent.getText());

        ResourceLocation font = null;
        if (myBaseComponent.getFont() != null) {
            font = new ResourceLocation(myBaseComponent.getFont());
        }

        Style style = Style.EMPTY.
                withColor(TextColor.parseColor(myBaseComponent.getColor()))
                .withBold(myBaseComponent.isBold())
                .withItalic(myBaseComponent.isItalic())
                .withUnderlined(myBaseComponent.isUnderlined())
                .withStrikethrough(myBaseComponent.isStrikethrough())
                .withObfuscated(myBaseComponent.isObfuscated())
                .withInsertion(myBaseComponent.getInsertion())
                .withFont(font);

        if (myBaseComponent instanceof MyTextComponent myTextComponent) {
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
                            MutableComponent textComponent = parseMessages(myTextComponent.getHoverEvent().getBaseComponentList());
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
        MutableComponent mutableComponent = MutableComponent.create(literalContents);
        mutableComponent.setStyle(style);

        return mutableComponent;
    }
}
