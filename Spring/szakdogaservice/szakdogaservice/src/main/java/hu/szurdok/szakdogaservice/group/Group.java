package hu.szurdok.szakdogaservice.group;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "owner_id")
    private String ownerId;

    @Basic
    @Column(name = "join_code")
    private String joinCode;

    @Basic
    @Column(name = "picture")
    private String picture;
}
