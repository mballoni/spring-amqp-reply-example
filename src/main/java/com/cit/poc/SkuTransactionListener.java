package com.cit.poc;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SkuTransactionListener {

    private static final Map<String, Integer> STOCK = new HashMap<>();
    private static final Pattern PATTERN = Pattern.compile("-?\\d+");

    public String handleMessage(String rawData) {
        Matcher matcher = PATTERN.matcher(rawData);

        matcher.find();
        String sku = matcher.group();

        matcher.find();
        Integer qtdy = Integer.parseInt(matcher.group());

        Integer value = STOCK.get(sku);
        if (value != null) {
            value += qtdy;
        }
        STOCK.put(sku, value);

        return "ok";
    }

}
