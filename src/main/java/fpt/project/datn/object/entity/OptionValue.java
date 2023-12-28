package fpt.project.datn.object.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class OptionValue extends GeneralEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String value;
    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;
    @ManyToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;
}
