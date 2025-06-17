package com.example.AnVD_project.until.page;

import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class PageableUntil {
    public static   <T> List<T> paginate(List<T> fullList, int page, int size) {
        if (CollectionUtils.isEmpty(fullList)) {
            return Collections.emptyList();
        }

        int fromIndex = (page - 1) * size;
        if (fromIndex >= fullList.size()) {
            return Collections.emptyList();
        }

        int toIndex = Math.min(fromIndex + size, fullList.size());

        return fullList.subList(fromIndex, toIndex);
    }
}
