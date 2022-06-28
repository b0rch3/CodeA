package atelier.manichino.security.service.impl;

//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.PutObjectRequest;

import atelier.manichino.entity.Dress;
import atelier.manichino.exceptions.ApiRequestException;
import atelier.manichino.repository.DressRepository;
import atelier.manichino.security.service.DressService;
//import atelier.manichino.security.repository.ReviewRepository;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;

//import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DressServiceImpl implements DressService {

    private final DressRepository dressRepository;
//    private final ReviewRepository reviewRepository;
//    private final AmazonS3 amazonS3client;

//    @Value("${amazon.s3.bucket.name}")
//    private String bucketName;

    @Override
    public DataFetcher<Dress> getDressByQuery() {
        return dataFetchingEnvironment -> {
            Long dressId = Long.parseLong(dataFetchingEnvironment.getArgument("id"));
            return dressRepository.findById(dressId).get();
        };
    }

    @Override
    public DataFetcher<List<Dress>> getAllDressesByQuery() {
        return dataFetchingEnvironment -> dressRepository.findAllByOrderByIdAsc();
    }

    @Override
    public DataFetcher<List<Dress>> getAllDressesByIdsQuery() {
        return dataFetchingEnvironment -> {
            List<String> objects = dataFetchingEnvironment.getArgument("ids");
            List<Long> dressesId = objects.stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            return dressRepository.findByIdIn(dressesId);
        };
    }

    @Override
    public Dress findDressById(Long dressId) {
        return dressRepository.findById(dressId)
                .orElseThrow(() -> new ApiRequestException("Dress not found.", HttpStatus.NOT_FOUND)); // TODO add test
    }

    @Override
    public List<Dress> findAllDresses() {
        return dressRepository.findAllByOrderByIdAsc();
    }

    @Override
    public List<Dress> findDressesByIds(List<Long> dressesId) {
        return dressRepository.findByIdIn(dressesId);
    }

    @Override
    public List<Dress> filter(List<String> dressSizes, List<String> dressTypes, List<Integer> prices, boolean sortByPrice) {
        List<Dress> dressList = new ArrayList<>();

        if (!dressSizes.isEmpty() || !dressTypes.isEmpty() || !prices.isEmpty()) {
            if (!dressSizes.isEmpty()) {
                if (!dressList.isEmpty()) {
                    List<Dress> dressSizesList = new ArrayList<>();
                    for (String dressSize : dressSizes) {
                    	dressSizesList.addAll(dressList.stream()
                                .filter(dress -> dress.getDressSize().equals(dressSize))
                                .collect(Collectors.toList()));
                    }
                    dressList = dressSizesList;
                } else {
                	dressList.addAll(dressRepository.findByDressSizeIn(dressSizes));
                }
            }
            if (!dressTypes.isEmpty()) {
                if (!dressList.isEmpty()) {
                    List<Dress> dressTypesList = new ArrayList<>();
                    for (String dressType : dressTypes) {
                    	dressTypesList.addAll(dressList.stream()
                                .filter(dress -> dress.getDressType().equals(dressType))
                                .collect(Collectors.toList()));
                    }
                    dressList = dressTypesList;
                } else {
                	dressList.addAll(dressRepository.findByDressTypeIn(dressTypes));
                }
            }
            if (!prices.isEmpty()) {
            	dressList = dressRepository.findByPriceBetween(prices.get(0), prices.get(1));
            }
        } else {
        	dressList = dressRepository.findAllByOrderByIdAsc();
        }
        if (sortByPrice) {
        	dressList.sort(Comparator.comparing(Dress::getPrice));
        } else {
        	dressList.sort((dress1, dress2) -> dress2.getPrice().compareTo(dress1.getPrice()));
        }
        return dressList;
    }

    @Override
    public List<Dress> findByDressSizeOrderByPriceDesc(String dressSize) {
        return dressRepository.findByDressSizeOrderByPriceDesc(dressSize);
    }

    @Override
    public List<Dress> findByDressTypeOrderByPriceDesc(String dressType) {
        return dressRepository.findByDressTypeOrderByPriceDesc(dressType);
    }

//    @Override
//    public Dress saveDress(Dress dress, MultipartFile multipartFile) {
//        if (multipartFile == null) {
//        	dress.setFilename.(dressRepository.findAll().get();
//        } else {
//            File file = new File(multipartFile.getOriginalFilename());
//            try (FileOutputStream fos = new FileOutputStream(file)) {
//                fos.write(multipartFile.getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String fileName = UUID.randomUUID().toString() + "." + multipartFile.getOriginalFilename();
//            dressRepository.findByNameContaining(fileName);
//            file.delete();
//        }
//        return dressRepository.save(dress);
//    }
    
    @Override
	public List<Dress> addDress(Dress dress) {
			dressRepository.save(dress);
			return dressRepository.findAllByOrderByIdAsc();

	}
    
    @Override
    @Transactional
	public List<Dress> deleteDress(Long dressId) {
			Dress dress = dressRepository.findById(dressId)
					.orElseThrow(() -> new ApiRequestException("Dress not found.", HttpStatus.NOT_FOUND)); // TODO add test
			dressRepository.delete(dress);
			return dressRepository.findAllByOrderByIdAsc();
	}
    
}
