package org.ui.main.services.review.model;

import jakarta.persistence.*;
import org.ui.main.services.advert.model.Advert;

import java.util.List;

@Entity
@Table(name = "REVIEWS")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @ManyToMany
    @JoinTable(
            name = "REVIEWS_ADVERTS",
            joinColumns = @JoinColumn(name = "REVIEW_ID"),
            inverseJoinColumns = @JoinColumn(name = "ADVERT_ID"))
    private List<Advert> adverts;
}
