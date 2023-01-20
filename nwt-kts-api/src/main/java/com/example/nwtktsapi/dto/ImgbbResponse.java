package com.example.nwtktsapi.dto;

public class ImgbbResponse {
    private Data data;
    private boolean success;
    private int status;

    public Data getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    public class Data {
        private String url;
        public String getUrl() {
            return url;
        }
    }
}
