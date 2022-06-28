package atelier.manichino.requests;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DressRequest {

    private Long id;

    @NotBlank(message = "Fill in the input field")
    @Length(max = 255)
    private String dressName;
    
    @NotNull(message = "Fill in the input field")
    private Integer dressPrice;
    
    @NotBlank(message = "Fill in the input field")
    @Length(max = 255)
    private String dressSize;
    
    @NotBlank(message = "Fill in the input field")
    @Length(max = 255)
    private String dressType;
    
    private String filename;
}
