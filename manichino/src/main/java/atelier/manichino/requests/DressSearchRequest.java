package atelier.manichino.requests;

import lombok.Data;

import java.util.List;

@Data
public class DressSearchRequest {
	private List<String> dressSizes;
	private List<String> dressTypes;
    private List<Integer> prices;
    private boolean sortByPrice;
    private String dressSize;
    private String dressType;
}
