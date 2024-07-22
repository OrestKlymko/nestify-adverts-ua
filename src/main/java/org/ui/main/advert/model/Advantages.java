package org.ui.main.advert.model;


import jakarta.persistence.*;
import org.ui.main.advert.model.enums.Advantage;

import java.util.Objects;

@Entity
@Table(name = "ADVANTAGES")
public class Advantages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ADVANTAGE")
    @Enumerated(EnumType.STRING)
    private Advantage advantageName;
    @ManyToOne
    @JoinColumn(name = "PROPERTY_ID", referencedColumnName = "ID")
    private PropertyRealty propertyRealty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Advantage getAdvantageName() {
        return advantageName;
    }

    public void setAdvantageName(Advantage advantageName) {
        this.advantageName = advantageName;
    }

    public PropertyRealty getPropertyRealty() {
        return propertyRealty;
    }

    public void setPropertyRealty(PropertyRealty propertyRealty) {
        this.propertyRealty = propertyRealty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advantages that = (Advantages) o;
        return Objects.equals(id, that.id) && advantageName == that.advantageName && Objects.equals(propertyRealty, that.propertyRealty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, advantageName, propertyRealty);
    }
}
