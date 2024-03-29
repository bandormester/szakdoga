package hu.szurdok.szakdogaservice.enitites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "todo_groups")
public class TodoGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "owner_id")
    private Integer ownerId;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "join_code")
    private String joinCode;

    @Basic
    @Column(name = "has_picture")
    private Boolean hasPicture;
}
