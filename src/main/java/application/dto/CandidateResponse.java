package application.dto;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CandidateResponse {
    private UUID id;
    private String name;
    private String email;
    private Date birthDate;
    private Long phone;
    private List<UUID> skills;
}
