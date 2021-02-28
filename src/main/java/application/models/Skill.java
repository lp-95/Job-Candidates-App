package application.models;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table( name = "skills" )
public class Skill {
    @Id
    @Type( type = "uuid-char" )
    private UUID id;
    @Column( unique = true )
    private String name;
    @ManyToMany( mappedBy = "skills" )
    private List<Candidate> candidates;
}
