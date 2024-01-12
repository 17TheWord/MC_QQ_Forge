package com.github.theword.returnBody.returnModle;

import lombok.Data;

@Data
public class MyBaseComponent {
    private String text;
    private String color;
    private String font;
    private boolean bold;
    private boolean italic;
    private boolean underlined;
    private boolean strikethrough;
    private boolean obfuscated;
    private String insertion;
}
