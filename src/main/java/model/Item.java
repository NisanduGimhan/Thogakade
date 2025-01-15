package model;

import lombok.*;

@NoArgsConstructor
@ToString
@Data@AllArgsConstructor
@Getter
@Setter

public class Item {
    private String code;
    private String description;
    private Double price;
    private Integer qty;
}
