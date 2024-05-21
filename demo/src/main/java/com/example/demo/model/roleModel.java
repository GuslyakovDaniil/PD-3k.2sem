package com.example.demo.model;
import com.example.demo.Enums.Role_enum;
import jakarta.persistence.*;

@Entity
@Table(name = "user_role")
public class roleModel extends BaseEntity {

    public void setRole(Role_enum name) {
        this.name = name;
    }
    public roleModel(Role_enum name){this.name = name;}

    private Role_enum name;


    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    public Role_enum getName(){
        return name;
    }

    public roleModel() {}

}
