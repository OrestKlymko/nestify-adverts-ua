package org.ui.main.model;


import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "ADDRESS")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private UUID id;
	@Column(name = "BUILD_MAP_TILER")
	private long buildIdMapTiler;
	@Column(name = "DISTRICT")
	private String district;
	@Column(name = "ADDRESS_NAME")
	private String addressName;
	@Column(name = "CITY")
	private String city;
	@Column(name = "LATITUDE")
	private double latitude;
	@Column(name = "LONGITUDE")
	private double longitude;
	@OneToMany(mappedBy = "address")
	private Set<Advert> adverts;
}
