package atelier.manichino.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import atelier.manichino.entity.Dress;

@Repository
public interface DressRepository extends JpaRepository<Dress, Long> {
	
//	List<Dress> findByNameContaining(String keyword);
	
    List<Dress> findAllByOrderByIdAsc();

    List<Dress> findByDressSizeIn(List<String> dressSizes);

    List<Dress> findByDressTypeIn(List<String> dressTypes);

    List<Dress> findByPriceBetween(Integer startingPrice, Integer endingPrice);

    List<Dress> findByDressSizeOrderByPriceDesc(String dressSize);

    List<Dress> findByDressTypeOrderByPriceDesc(String dressType);

    List<Dress> findByIdIn(List<Long> dressesIds);

}
