package model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    private List<OrderDetails> orderDetaiList;
}
