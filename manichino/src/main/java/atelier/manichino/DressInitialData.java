package atelier.manichino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import atelier.manichino.entity.Dress;
import atelier.manichino.repository.DressRepository;

@Component
public class DressInitialData implements CommandLineRunner {

	private final DressRepository dressRepository;
	private final Faker faker = new Faker(Locale.getDefault());

	public DressInitialData(DressRepository dressRepository) {
		this.dressRepository = dressRepository;
	}
	
//	private String pic = "https://firebasestorage.googleapis.com/v0/b/manichino-1.appspot.com/o/slikata.jpg?alt=media&token=27980a55-3dd0-4f15-97ab-4c09266b7184";
	private String pic = "https://i.ibb.co/x6wGDkM/slikata.jpg";
	
	@Override
	public void run(String... args) {
		if (dressRepository.count() < 10)

			for (int i = 0; i < 30; i++) {
				ArrayList<String> dressSize = new ArrayList<>(Arrays.asList("XS", "S", "M", "L", "XL", "XXL"));
				Random randSize = new Random();
				String size = dressSize.get(randSize.nextInt(dressSize.size()));
				ArrayList<String> dressType = new ArrayList<>(Arrays.asList("Casual", "Formal", "Prom", "Wedding"));
				Random randType = new Random();
				String type = dressType.get(randType.nextInt(dressType.size()));
				dressRepository.save(new Dress(faker.name().lastName(), faker.number().numberBetween(10, 100), size, type, pic));
			}
	}

}