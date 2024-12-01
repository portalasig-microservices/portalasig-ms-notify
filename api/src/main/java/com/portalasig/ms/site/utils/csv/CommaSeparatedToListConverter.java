package com.portalasig.ms.site.utils.csv;

import com.opencsv.bean.AbstractBeanField;

import java.util.List;

public class CommaSeparatedToListConverter extends AbstractBeanField<List<String>, String> {
    @Override
    protected List<String> convert(String value) {
        if (value == null || value.isEmpty()) {
            return List.of();
        }
        return List.of(value.split("\\s*,\\s*"));
    }
}
