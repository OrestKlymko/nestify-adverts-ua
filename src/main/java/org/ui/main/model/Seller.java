package org.ui.main.model;


import jakarta.persistence.*;
import org.ui.main.model.enums.TypeOwner;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "SELLER")
public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private UUID id;
	@Column(name = "NAME_OWNER")
	private String nameOwner;
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE_OWNER")
	private TypeOwner typeOwner;
	@Column(name = "NUMBER_PHONE")
	private String numberPhone;

	@OneToMany(mappedBy = "seller")
	private Set<Advert> adverts;
	@ManyToOne
	@JoinColumn(name = "AGENCY_ID", referencedColumnName = "ID")
	private Agency agency;
}
