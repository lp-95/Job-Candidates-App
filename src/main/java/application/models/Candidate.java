package application.models;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Candidate {
    @Id
    @Type( type = "uuid-char" )
    private UUID id;
    @Column( nullable = false )
    private String name;
    @Column( nullable = false, unique = true )
    private String email;
    @Column( name = "birth_date", nullable = false )
    @Temporal( TemporalType.DATE )
    private Date birthDate;
    @Column( nullable = false )
    private Long phone;
    @ManyToMany( fetch = FetchType.LAZY )
    @JoinTable( name = "candidate_skill",
                joinColumns = @JoinColumn( name = "candidate" ),
                inverseJoinColumns = @JoinColumn( name = "skill" ) )
    private List<Skill> skills;
}
