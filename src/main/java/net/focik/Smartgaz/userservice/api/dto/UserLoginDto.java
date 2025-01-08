package net.focik.Smartgaz.userservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    private Long id;
    private String firstName;
    private String username;
    private int idEmployee;
}
