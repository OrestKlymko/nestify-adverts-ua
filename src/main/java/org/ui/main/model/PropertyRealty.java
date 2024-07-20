package org.ui.main.model;

import jakarta.persistence.*;
import org.ui.main.model.enums.Advantage;
import org.ui.main.model.enums.AllowedStatus;
import org.ui.main.model.enums.ApartmentFeature;

import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "PROPERTY_BUILDING")
public class PropertyRealty {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private UUID propertyId;
	@Column(name = "SQUARE")
	private float square;
	@Column(name = "FLOOR")
	private int floor;
	@Column(name = "ROOM")
	private int room;
	@Column(name = "REALTY_PRICE")
	private long realtyPrice;
	@Column(name = "ENERGY_PRICE")
	private long energyPrice;
	@Column(name = "TOTAL_PRICE")
	private long totalPrice;
	@Column(name = "FEATURES")
	@ElementCollection(targetClass = ApartmentFeature.class)
	@Enumerated(EnumType.STRING)
	@JoinTable(name = "FEATURES", joinColumns = @JoinColumn(name = "PROPERTY_ID"))
	private List<ApartmentFeature> feature;
	@Enumerated(EnumType.STRING)
	@Column(name = "WITH_PETS")
	private AllowedStatus withPets;
	@Enumerated(EnumType.STRING)
	@Column(name = "WITH_KIDS")
	private AllowedStatus withKids;
	@Column(name = "ADVANTAGES")
	@ElementCollection(targetClass = Advantage.class)
	@Enumerated(EnumType.STRING)
	@JoinTable(name = "ADVANTAGES", joinColumns = @JoinColumn(name = "PROPERTY_ID"))
	private List<Advantage> advantageList;
	@OneToOne(mappedBy = "propertyRealty")
	private Advert advert;
}
