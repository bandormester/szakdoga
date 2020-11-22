package hu.szurdok.szakdogaservice.enitites;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "members")
public class Member implements Serializable {

    @Id
    @Basic
    @Column(name = "user_id")
    private int userId;

    @Id
    @Basic
    @Column(name = "group_id")
    private int groupId;
}
