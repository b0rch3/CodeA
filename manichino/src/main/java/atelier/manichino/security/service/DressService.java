package atelier.manichino.security.service;

import atelier.manichino.entity.Dress;
import graphql.schema.DataFetcher;
//import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DressService {

    DataFetcher<Dress> getDressByQuery();

    DataFetcher<List<Dress>> getAllDressesByQuery();

    DataFetcher<List<Dress>> getAllDressesByIdsQuery();

    Dress findDressById(Long dressId);

    List<Dress> findAllDresses();

    List<Dress> findDressesByIds(List<Long> dressesId);

    List<Dress> filter(List<String> dressSizes, List<String> dressTypes, List<Integer> prices, boolean sortByPrice);

    List<Dress> findByDressSizeOrderByPriceDesc(String dressSize);

    List<Dress> findByDressTypeOrderByPriceDesc(String dressType);

    List<Dress> addDress(Dress dress);
    
    List<Dress> deleteDress(Long dressId);





}
