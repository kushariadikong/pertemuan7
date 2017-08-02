package com.example.optimus.pertemuan7.api.model;

import java.util.List;

/**
 * Created by sunny on 8/2/17.
 */

public class Repository {

    private Integer totalCount;
    private List<Repo> items;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<Repo> getItems() {
        return items;
    }

    public void setItems(List<Repo> items) {
        this.items = items;
    }
}
