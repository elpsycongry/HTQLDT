package com.example.quanlydaotao.dto;

public class PaginateRequest {
    private int page;
    private int size;

    public PaginateRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }
    public PaginateRequest() {
        this.page = 0;
        this.size = 5;
    }
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
