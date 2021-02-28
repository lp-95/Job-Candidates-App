package application.controllers;

import application.dto.CreateCandidateRequest;
import application.exceptions.BadRequestException;
import application.exceptions.NotFoundException;
import application.services.CandidateServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping( "/candidates" )
public class CandidateController {
    private final CandidateServiceImpl candidateService;

    @ApiOperation( value = "Get job candidates" )
    @RequestMapping( path = "/get-all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> getAllCandidates( @RequestParam( value = "page", defaultValue = "0" ) int page,
                                               @RequestParam( value = "limit", defaultValue = "25" ) int limit ) {
        return ResponseEntity.ok( this.candidateService.getAllCandidates( page, limit ) );
    }

    @ApiOperation( value = "Search candidates by name" )
    @RequestMapping( path = "/search/{name}",
                     method = RequestMethod.GET,
                     produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> searchByName( @RequestParam( value = "page", defaultValue = "0" ) int page,
                                           @RequestParam( value = "limit", defaultValue = "25" ) int limit,
                                           @PathVariable String name ) {
        return ResponseEntity.ok( this.candidateService.getCandidatesByName( page, limit, name ) );
    }

    @ApiOperation( value = "Search candidates by list of skills" )
    @RequestMapping( path = "/search",
                     method = RequestMethod.GET,
                     produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> searchBySkill( @RequestParam( value = "page", defaultValue = "0" ) int page,
                                            @RequestParam( value = "limit", defaultValue = "25" ) int limit,
                                            @RequestBody List<UUID> skills ) {
        return ResponseEntity.ok( this.candidateService.getCandidatesBySkill( page, limit,  skills ) );
    }

    @ApiOperation( value = "Add new candidate" )
    @RequestMapping( path = "/save",
                     method = RequestMethod.POST,
                     consumes = MediaType.APPLICATION_JSON_VALUE,
                     produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> save( @Valid @RequestBody CreateCandidateRequest createCandidateRequest ) throws BadRequestException {
        return ResponseEntity.ok( this.candidateService.save( createCandidateRequest ) );
    }

    @ApiOperation( value = "Update candidate skills" )
    @RequestMapping( path = "/update/{id}",
                     method = RequestMethod.PUT,
                     consumes = MediaType.APPLICATION_JSON_VALUE,
                     produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> update( @PathVariable UUID id, @RequestBody List<UUID> skills ) throws NotFoundException {
        return ResponseEntity.ok( this.candidateService.update( id, skills ) );
    }

    @ApiOperation( value = "Delete candidate" )
    @RequestMapping( path = "/delete/{id}",
                     method = RequestMethod.DELETE )
    public ResponseEntity<?> delete( @PathVariable UUID id ) throws NotFoundException {
        this.candidateService.delete( id );
        return ResponseEntity.ok().build();
    }
}
