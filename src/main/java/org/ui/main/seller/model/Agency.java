package org.ui.main.seller.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "AGENCY")
public class Agency {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "AGENCY_NAME")
	private String agencyName;

	@Column(name = "AGENCY_CATALOG")
	private String agencyCatalog;

	@OneToMany(mappedBy = "agency")
	@JsonManagedReference
	private Set<Seller> sellers;

	public Long getId() {
		return id;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAgencyCatalog() {
		return agencyCatalog;
	}

	public void setAgencyCatalog(String agencyCatalog) {
		this.agencyCatalog = agencyCatalog;
	}

	public Set<Seller> getSellers() {
		return sellers;
	}

	public void setSellers(Set<Seller> sellers) {
		this.sellers = sellers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Agency agency = (Agency) o;
		return Objects.equals(id, agency.id) && Objects.equals(agencyName, agency.agencyName) && Objects.equals(agencyCatalog, agency.agencyCatalog) && Objects.equals(sellers, agency.sellers);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, agencyName, agencyCatalog, sellers);
	}
}
