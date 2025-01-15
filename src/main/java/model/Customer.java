package model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class Customer {

    private String id;
    private String name;
    private String address;
    private Double salary;
}
