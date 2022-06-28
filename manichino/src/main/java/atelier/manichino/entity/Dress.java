package atelier.manichino.entity;

//import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
//import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "dress")
@Data
public class Dress {
	
	public Dress() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dress_id_seq")
    @SequenceGenerator(name = "dress_id_seq", sequenceName = "dress_id_seq", initialValue = 109, allocationSize = 1)
	private Long id;
	
	private String dressName;
	
	private Integer price;
	
    private String dressSize;
	
	private String dressType;
	
	private String filename;
	
	public Dress (String dressName, Integer price, String dressSize, String dressType, String filename) {
		this.dressName = dressName;
		this.price = price;
		this.dressSize = dressSize;
		this.dressType = dressType;
		this.filename = filename;
	}
	
}
