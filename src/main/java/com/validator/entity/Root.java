package com.validator.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "root")
public class Root implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long rootId;

    @Column(name = "root")
    private String root;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equation_id")
    private Equation equation;

    @Override
    public String toString() {
        return "Id#%d root: %s, %s".formatted(rootId, root, equation);
    }
}
