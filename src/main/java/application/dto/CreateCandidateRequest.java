package application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CreateCandidateRequest {
    @Size( min = 3, max = 20, message =  "Size must be between 3 and 20" )
    private String name;
    @Email
    private String email;
    @JsonFormat( pattern = "dd-mm-yyyy" )
    @Past
    private Date birthDate;
    @Min( value = 10000000 )
    private Long phone;
    private List<UUID> skills;
}
