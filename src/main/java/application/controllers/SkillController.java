package application.controllers;

import application.dto.CreateSkillRequest;
import application.exceptions.BadRequestException;
import application.services.SkillServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping( "/skills" )
public class SkillController {
    private final SkillServiceImpl skillService;

    @ApiOperation( value = "Add new skill" )
    @RequestMapping( path = "/save",
                     method = RequestMethod.POST,
                     consumes = MediaType.APPLICATION_JSON_VALUE,
                     produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> save( @Valid @RequestBody CreateSkillRequest createSkillRequest) throws BadRequestException {
        return new ResponseEntity<>( this.skillService.save(createSkillRequest), HttpStatus.OK );
    }
}