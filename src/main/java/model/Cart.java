package model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class Cart {
    private String itemcode;
    private String desc;
    private Integer qty;
    private  Double unitPrice;
    private Double total;


}
