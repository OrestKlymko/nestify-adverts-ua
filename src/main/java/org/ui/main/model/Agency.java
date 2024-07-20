package org.ui.main.model;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "AGENCY")
public class Agency {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private UUID id;

	@Column(name = "AGENCY_NAME")
	private String agencyName;

	@Column(name = "AGENCY_CATALOG")
	private String agencyCatalog;

	@OneToMany(mappedBy = "agency")
	private Set<Seller> sellers;

}
