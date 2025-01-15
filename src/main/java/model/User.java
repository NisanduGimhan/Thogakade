package model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data


public class User {
    private Integer id;
    private String userName;
    private String email;
    private String password;

}
