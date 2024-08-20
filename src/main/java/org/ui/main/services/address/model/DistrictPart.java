package org.ui.main.services.address.model;


import jakarta.persistence.*;

@Entity
@Table(name = "DISTRICT_PART")
public class DistrictPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DISTRICT_NAME")
    private String districtName;

    @ManyToOne
    @JoinColumn(name = "DISTRICT_ID",referencedColumnName = "ID")
    private District district;
}
