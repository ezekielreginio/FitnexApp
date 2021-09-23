package com.pupccis.fitnex.api.globals;

import java.util.Map;

public interface Observer {

    Map<String, Object> toMap();
    Object map(Map<String, Object> data);
    String getKey();
    String getId();

}
