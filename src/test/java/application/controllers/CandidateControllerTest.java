package application.controllers;

import application.dto.CandidateResponse;
import application.dto.CreateCandidateRequest;
import application.models.Candidate;
import application.services.CandidateServiceImpl;
import application.services.SkillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CandidateControllerTest {
    @InjectMocks
    CandidateController candidateController;
    @Mock
    CandidateServiceImpl candidateService;
    @Mock
    SkillServiceImpl skillService;

    CreateCandidateRequest createCandidate;
    CandidateResponse candidateResponse1;
    CandidateResponse candidateResponse2;
    CandidateResponse newCandidate;
    Candidate returnedCandidate;

    List<CandidateResponse> candidateResponses;
    List<UUID> skillIds;
    List<UUID> toUpdateSkills;

    final String candidateName = "name";
    final UUID candidateId1 = UUID.randomUUID();
    final UUID candidateId2 = UUID.randomUUID();
    final UUID newCandidateId = UUID.randomUUID();
    final UUID skillId1 = UUID.randomUUID();
    final UUID skillId2 = UUID.randomUUID();
    final int page = 1;
    final int size = 30;


    @BeforeEach
    final void setUp() {
        MockitoAnnotations.initMocks( this );
        this.skillIds = new ArrayList<>();
        this.skillIds.add( this.skillId1 );
        this.skillIds.add( this.skillId2 );

        this.candidateResponse1 = new CandidateResponse();
        this.candidateResponse1.setId( this.candidateId1 );
        this.candidateResponse1.setName( "name1" );
        this.candidateResponse1.setEmail( "email" );
        this.candidateResponse1.setBirthDate( new Date() );
        this.candidateResponse1.setPhone( 1L );
        this.candidateResponse1.setSkills( this.skillIds );

        this.candidateResponse2 = new CandidateResponse();
        this.candidateResponse2.setId( this.candidateId2 );
        this.candidateResponse2.setName( "name1" );
        this.candidateResponse2.setEmail( "email" );
        this.candidateResponse2.setBirthDate( new Date() );
        this.candidateResponse2.setPhone( 1L );
        this.candidateResponse2.setSkills( this.skillIds );
        this.candidateResponses = new ArrayList<>();
        this.candidateResponses.add( this.candidateResponse1 );
        this.candidateResponses.add( this.candidateResponse2 );

        this.createCandidate = new CreateCandidateRequest();
        this.createCandidate.setName( "name3" );
        this.createCandidate.setEmail( "email3" );
        this.createCandidate.setBirthDate( new Date() );
        this.createCandidate.setPhone( 1L );
        this.createCandidate.setSkills( this.skillIds );

        this.newCandidate = new CandidateResponse();
        this.newCandidate.setId( this.newCandidateId );
        this.newCandidate.setName( this.createCandidate.getName() );
        this.newCandidate.setEmail( this.createCandidate.getEmail() );
        this.newCandidate.setBirthDate( this.createCandidate.getBirthDate() );
        this.newCandidate.setPhone( this.createCandidate.getPhone() );
        this.newCandidate.setSkills( this.createCandidate.getSkills() );

        this.returnedCandidate = new Candidate();
        this.returnedCandidate.setId( this.candidateResponse1.getId() );
        this.returnedCandidate.setName( this.candidateResponse1.getName() );
        this.returnedCandidate.setEmail( this.candidateResponse1.getEmail() );
        this.returnedCandidate.setBirthDate( this.candidateResponse1.getBirthDate() );
        this.returnedCandidate.setPhone( this.candidateResponse1.getPhone() );
        this.returnedCandidate.setSkills( this.skillService.getAllSkillsById( this.candidateResponse1.getSkills() ) );

        this.toUpdateSkills = new ArrayList<>();
        this.toUpdateSkills.add( this.skillId1 );
    }

    @Test
    final void getAllCandidates() {
        when( this.candidateService.getAllCandidates( this.page, this.size ) )
                .thenReturn( this.candidateResponses );
        ResponseEntity<?> response = this.candidateController.getAllCandidates( this.page, this.size );
        assertEquals( response.getStatusCode(), HttpStatus.OK );
    }

    @Test
    final void searchByName() {
        when( this.candidateService.getCandidatesByName( this.page, this.size, this.candidateName ) )
                .thenReturn( this.candidateResponses );
        ResponseEntity<?> response = this.candidateController.searchByName( this.page, this.size, this.candidateName );
        assertEquals( response.getStatusCode(), HttpStatus.OK );
    }

    @Test
    final void searchBySkill() {
        when( this.candidateService.getCandidatesBySkill( this.page, this.size, this.skillIds ) )
                .thenReturn( this.candidateResponses );
        ResponseEntity<?> response = this.candidateController.searchBySkill( this.page, this.size, this.skillIds );
        assertEquals( response.getStatusCode(), HttpStatus.OK );
    }

    @Test
    final void save() {
        when( this.candidateService.save( this.createCandidate ) )
                .thenReturn( this.candidateResponse1 );
        ResponseEntity<?> response = this.candidateController.save( this.createCandidate );
        assertEquals( response.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void update() {
        when( this.candidateService.getCandidateById( this.candidateId1 ) )
                .thenReturn( this.returnedCandidate );
        ResponseEntity<?> response = this.candidateController.update( this.candidateId1, this.toUpdateSkills );
        assertEquals( response.getStatusCode(), HttpStatus.OK );
    }

    @Test
    void delete() {
        when( this.candidateService.getCandidateById( this.candidateId1 ) )
                .thenReturn( this.returnedCandidate );
        ResponseEntity<?> response = this.candidateController.delete( this.candidateId1 );
        assertEquals( response.getStatusCode(), HttpStatus.OK );
    }
}