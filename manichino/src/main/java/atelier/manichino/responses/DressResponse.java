package atelier.manichino.responses;

import lombok.Data;

//import java.util.List;

//import org.springframework.web.multipart.MultipartFile;

@Data
public class DressResponse {
    private Long id;
    private String dressName;
    private Integer price;
    private String dressSize;
    private String dressType;
    private String filename;
//    private List<ReviewResponse> reviews;
//    private MultipartFile file;
}
