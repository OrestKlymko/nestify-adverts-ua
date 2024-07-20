package org.ui.main.model;


import jakarta.persistence.*;
import org.ui.main.model.enums.Status;
import org.ui.main.model.enums.TypeRealty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ADVERTS")
public class Advert {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private UUID id;
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE_REALTY")
	private TypeRealty typeRealty;
	@Column(name = "PUBLISHED_AT")
	private LocalDateTime publishedAt;
	@Column(name = "EDITED_AT")
	private LocalDateTime editedAt;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "FINAL_URL")
	private String finalUrl;
	@CollectionTable(name = "IMAGES", joinColumns = @JoinColumn(name = "ADVERTS_ID", referencedColumnName = "ID"))
	@ElementCollection(targetClass = String.class)
	private List<String> images;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PROPERTY_ID", referencedColumnName = "ID")
	private PropertyRealty propertyRealty;
	@ManyToOne
	@JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
	private Address address;
	@ManyToOne
	@JoinColumn(name = "SELLER_ID", referencedColumnName = "ID")
	private Seller seller;
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private Status status;
}
