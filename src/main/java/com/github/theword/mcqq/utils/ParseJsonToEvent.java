package com.github.theword.mcqq.utils;

import com.github.theword.mcqq.returnBody.returnModle.MyBaseComponent;
import com.github.theword.mcqq.returnBody.returnModle.MyHoverEntity;
import com.github.theword.mcqq.returnBody.returnModle.MyHoverItem;
import com.github.theword.mcqq.returnBody.returnModle.MyTextComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ParseJsonToEvent {
    public StringTextComponent parseMessages(List<? extends MyBaseComponent> myBaseComponentList) {
        StringTextComponent mutableComponent = new StringTextComponent("");
        for (MyBaseComponent myBaseComponent : myBaseComponentList) {
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
        // TODO net.minecraft.util.text: no class found, fix!
//        Style style = Style.EMPTY.
//                withColor(Color.parseColor(myBaseComponent.getColor()))
//                .withBold(myBaseComponent.isBold())
//                .withItalic(myBaseComponent.isItalic())
//                .withUnderlined(myBaseComponent.isUnderlined())
//                .setStrikethrough(myBaseComponent.isStrikethrough())
//                .setObfuscated(myBaseComponent.isObfuscated())
//                .withInsertion(myBaseComponent.getInsertion())
//                .withFont(font);

        if (myBaseComponent instanceof MyTextComponent) {
            MyTextComponent myTextComponent = (MyTextComponent) myBaseComponent;
            if (myTextComponent.getClickEvent() != null) {
                ClickEvent.Action tempAction = ClickEvent.Action.getByName(myTextComponent.getClickEvent().getAction());
                ClickEvent clickEvent = new ClickEvent(tempAction, myTextComponent.getClickEvent().getValue());
//                style = style.withClickEvent(clickEvent);
            }

            if (myTextComponent.getHoverEvent() != null) {
                HoverEvent hoverEvent = null;
                switch (myTextComponent.getHoverEvent().getAction()) {
                    case "show_text":
                        if (myTextComponent.getHoverEvent().getBaseComponentList() != null && !myTextComponent.getHoverEvent().getBaseComponentList().isEmpty()) {
                            StringTextComponent textComponent = parseMessages(myTextComponent.getHoverEvent().getBaseComponentList());
                            hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, textComponent);
                        }
                        break;
                    case "show_item":
                        MyHoverItem myHoverItem = myTextComponent.getHoverEvent().getItem();
                        Item item = Item.byId(myHoverItem.getId());
                        ItemStack itemStack = new ItemStack(item, myHoverItem.getCount());
                        HoverEvent.ItemHover itemHover = new HoverEvent.ItemHover(itemStack);
                        hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_ITEM, itemHover);
                        break;
                    case "show_entity":
                        MyHoverEntity myHoverEntity = myTextComponent.getHoverEvent().getEntity();
                        Optional<EntityType<?>> entityType = EntityType.byString(myHoverEntity.getType());
                        if (entityType.isPresent()) {
                            HoverEvent.EntityHover entityTooltipInfo = new HoverEvent.EntityHover(entityType.get(), UUID.randomUUID(), parseMessages(myHoverEntity.getName()));
                            hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_ENTITY, entityTooltipInfo);
                        }
                        break;
                    default:
                        break;
                }
//                style = style.withHoverEvent(hoverEvent);
            }
        }
//        stringTextComponent.setStyle(style);

        return stringTextComponent;
    }
}
