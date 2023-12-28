package fpt.project.datn.object.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Product extends GeneralEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String brand;
    @OneToMany(mappedBy = "product")
    private List<Variant> variants;
    @OneToMany(mappedBy = "product")
    private List<Option> options;
}
