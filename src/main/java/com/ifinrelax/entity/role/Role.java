package com.ifinrelax.entity.role;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Timur Berezhnoi
 */
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role() {}

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + roleName + '\'' +
                '}';
    }
}