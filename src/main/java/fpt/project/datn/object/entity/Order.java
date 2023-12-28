package fpt.project.datn.object.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "`order`")
@Data
public class Order extends GeneralEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double totalBill;
    private String description;
    private int status;
    @ManyToOne
    @JoinColumn(name = "profiles_id")
    private Profiles profiles;
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetail;
}
