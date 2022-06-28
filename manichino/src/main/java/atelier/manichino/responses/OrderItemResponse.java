package atelier.manichino.responses;

import lombok.Data;

@Data
public class OrderItemResponse {
    private Long id;
    private Long amount;
    private Long quantity;
    private DressResponse dress;
}
