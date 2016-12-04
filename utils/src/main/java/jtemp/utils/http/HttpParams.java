package jtemp.utils.http;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZMS
 */
public final class HttpParams {

    private Map<String, List<Object>> params = new HashMap<>();


    public HttpParams addParam(String key, Object value) {

        List<Object> old = params.get(key);
        if (old != null) {
            addParamList(old, value);
        } else {
            old = new ArrayList<>();
            addParamList(old, value);
            params.put(key, old);
        }

        return this;
    }

    private void addParamList(List<Object> list, Object value) {
        if (value == null) {
            return;
        }
        if (value instanceof Collection) {
            Collection col = (Collection) value;
            list.addAll(col);
        } else if (value.getClass().isArray()) {
            list.addAll(Arrays.asList(value));
        } else {
            list.add(value);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            boolean first = true;
            for (Map.Entry<String, List<Object>> e : params.entrySet()) {
                if (e.getValue() != null && e.getValue().size() > 0) {
                    for (Object o : e.getValue()) {
                        if (o != null) {
                            if (!first) {
                                sb.append("&");
                            }
                            first = false;
                            sb.append(e.getKey());
                            sb.append("=");
                            sb.append(URLEncoder.encode(o.toString(), "UTF-8"));
                        }
                    }
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sb.toString();
    }
}
