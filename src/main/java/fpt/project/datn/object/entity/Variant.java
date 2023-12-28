package fpt.project.datn.object.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Variant extends GeneralEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double importPrice;
    private double price;
    private int quantity;
    @OneToMany(mappedBy = "variant")
    private List<OrderDetail> orderDetails;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @OneToMany(mappedBy = "variant")
    private List<OptionValue> optionValues;
}
