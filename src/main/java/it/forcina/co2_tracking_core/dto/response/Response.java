package it.forcina.co2_tracking_core.dto.response;

public class Response<T> {
    private String message;
    private T info;

    private Response(String message) {
        this.message = message;
    }

    private Response(String message, T info) {
        this.message = message;
        this.info = info;
    }

    public String getMessage() {
        return message;
    }

    public T getInfo() {
        return info;
    }

    public static class Builder<T> {
        private String message;
        private T info;

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> info(T info) {
            this.info = info;
            return this;
        }

        public Response<T> build() {
            if (info != null) {
                return new Response<>(message, info);
            } else {
                return new Response<>(message);
            }
        }
    }
}
