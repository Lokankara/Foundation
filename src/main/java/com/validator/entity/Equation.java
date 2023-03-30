package com.validator.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Double.compare;
import static java.lang.Double.parseDouble;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "equation")
public class Equation implements BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "expression",
            nullable = false)
    private String expression;

    @Column(name = "solution",
            nullable = false)
    private boolean solution;

    @OneToMany(
            mappedBy = "equation",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Root> roots = new HashSet<>();

    @Override
    public String toString() {
        return String.format("expression: %s, roots: %s", expression, getString());
    }

    public String getString() {
        return this.roots != null
                ? roots.stream()
                .map(Root::getRoot)
                .collect(Collectors.joining(", ")) : "";
    }

    public boolean addRoot(Root rootBuilder) {
        if (this.roots == null) {
            this.roots = new HashSet<>();
        }
        return rootBuilder.getRoot().equalsIgnoreCase("no roots")
                ? roots.add(rootBuilder) : roots.stream()
                .noneMatch(root -> compare(
                        parseDouble(root.getRoot()),
                        parseDouble(rootBuilder.getRoot())) == 0)
                && roots.add(rootBuilder);
    }
}
