package com.xebia.fs101.zohoreplica.api.response;

public class GenericResponse {

    private Object data;
    private String message;
    private String status;


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GenericResponse() {
    }

    public GenericResponse(Object data, String message, String status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    private GenericResponse(Builder builder) {
        data = builder.data;
        message = builder.message;
        status = builder.status;
    }


    public static final class Builder {
        private Object data;
        private String message;
        private String status;

        public Builder() {
        }

        public Builder withData(Object val) {
            data = val;
            return this;
        }

        public Builder withMessage(String val) {
            message = val;
            return this;
        }

        public Builder withStatus(String val) {
            status = val;
            return this;
        }

        public GenericResponse build() {
            return new GenericResponse(this);
        }
    }
}
