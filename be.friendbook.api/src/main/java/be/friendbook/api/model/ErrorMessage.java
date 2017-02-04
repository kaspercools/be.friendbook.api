package be.friendbook.api.model;

/**
 *
 * @author kaspercools
 */
public class ErrorMessage {
    private String field;
    private String msg;

    public ErrorMessage() {
    }

    public ErrorMessage(String field, String msg) {
        this.field = field;
        this.msg = msg;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
}
