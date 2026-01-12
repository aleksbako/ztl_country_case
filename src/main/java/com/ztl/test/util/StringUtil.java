package com.ztl.test.util;

import com.ztl.test.domain.SortOrder;

import java.util.Comparator;

public class StringUtil {
    public static Comparator<String> stringComparator(SortOrder order) {
        return order == SortOrder.ASC
                ? Comparator.nullsLast(String::compareToIgnoreCase)
                : Comparator.nullsLast(String::compareToIgnoreCase).reversed();
    }

}
