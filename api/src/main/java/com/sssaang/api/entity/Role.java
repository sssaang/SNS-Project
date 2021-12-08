package com.sssaang.api.entity;

import com.sssaang.api.entity.constants.RoleName;
import org.hibernate.annotations.NaturalId;
import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId @Column(length = 60)
    public RoleName name;

    public Role() { }

    public Role(RoleName name) {
        this.name = name;
    }
}

