package core.controller.utils;

public class Response {

    private final String message;
    private final int status;
    private final Object object;

    public Response(String message, int status) {
        this.message = message;
        this.status = status;
        this.object = null;
    }

    public Response(String message, int status, Object object) {
        this.message = message;
        this.status = status;
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public Object getObject() {
        return object;
    }
}
