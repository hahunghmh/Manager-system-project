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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseDataType<T> {
    private int statusCode;
    private String description;
    private T data;
}
