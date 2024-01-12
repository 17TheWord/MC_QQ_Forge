package com.github.theword.returnBody;

import com.google.gson.JsonElement;
import lombok.Data;

@Data
public class BaseReturnBody {

    private String api;

    private JsonElement data;

}
