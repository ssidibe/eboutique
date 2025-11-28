package ml.finances.caisff.eboutique;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiErrorMessage {
    private Integer code;
    private String msg;
    public ApiErrorMessage(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiErrorMessage of(Integer code, String msg) {
        return new ApiErrorMessage(code, msg);
    }
}
