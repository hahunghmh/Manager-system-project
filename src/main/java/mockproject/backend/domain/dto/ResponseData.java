/*
 *@project : BackEnd
 *@Create by: TrienND
 *@Create time: 11/17/2023 - 11:27 AM
 */


package mockproject.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class ResponseData implements Serializable {
    private int statusCode;
    private String description;
    private Object data;

    public ResponseData(int statusCode, String description, Object data) {
        this.statusCode = statusCode;
        this.description = description;
        this.data = data;
    }
}
