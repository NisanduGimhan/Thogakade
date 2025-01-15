package model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString


public class Order {
    private String orderId;
    private String date;
    private String customerId;
}
