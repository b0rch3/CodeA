package atelier.manichino.mapper;

import atelier.manichino.entity.Dress;
import atelier.manichino.exceptions.InputFieldException;
import atelier.manichino.requests.DressRequest;
import atelier.manichino.responses.DressResponse;
import atelier.manichino.security.service.DressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DressMapper {

    private final CommonMapper commonMapper;
    private final DressService dressService;

    public DressResponse findDressById(Long dressId) {
        return commonMapper.convertToResponse(dressService.findDressById(dressId), DressResponse.class);
    }

    public List<DressResponse> findDressesByIds(List<Long> dressesId) {
        return commonMapper.convertToResponseList(dressService.findDressesByIds(dressesId), DressResponse.class);
    }

    public List<DressResponse> findAllDresses() {
        return commonMapper.convertToResponseList(dressService.findAllDresses(), DressResponse.class);
    }

    public List<DressResponse> filter(List<String> dressSize, List<String> dressType, List<Integer> prices, boolean sortByPrice) {
        List<Dress> dressList = dressService.filter(dressSize, dressType, prices, sortByPrice);
        return commonMapper.convertToResponseList(dressList, DressResponse.class);
    }

    public List<DressResponse> findByDressSizeOrderByPriceDesc(String dressSize) {
        return commonMapper.convertToResponseList(dressService.findByDressSizeOrderByPriceDesc(dressSize), DressResponse.class);
    }

    public List<DressResponse> findByDressTypeOrderByPriceDesc(String dressType) {
        return commonMapper.convertToResponseList(dressService.findByDressTypeOrderByPriceDesc(dressType), DressResponse.class);
    }

    public DressResponse saveDress(DressRequest dressRequest , BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Dress dress = commonMapper.convertToEntity(dressRequest, Dress.class);
        return commonMapper.convertToResponse(dressService.addDress(dress), DressResponse.class);
    }
    
    public List<DressResponse> deleteOrder(Long dressId) {
        return commonMapper.convertToResponseList(dressService.deleteDress(dressId), DressResponse.class);
    }

}
