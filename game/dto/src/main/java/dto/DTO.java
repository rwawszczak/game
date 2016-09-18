package dto;

import java.io.Serializable;

public class DTO implements Serializable {
    private static final long serialVersionUID = 3658264928019852286L;
    private int code = 0;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
