package application.services;

import application.dto.CandidateResponse;
import application.dto.CreateCandidateRequest;
import application.exceptions.BadRequestException;
import application.exceptions.NotFoundException;
import application.models.Candidate;
import application.repositories.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CandidateServiceImplTest {
    @InjectMocks
    CandidateServiceImpl candidateService;
    @Mock
    CandidateRepository candidateRepository;
    @Mock
    SkillServiceImpl skillService;

    CreateCandidateRequest createCandidate;
    CandidateResponse candidateResponse1;
    CandidateResponse candidateResponse2;
    CandidateResponse newCandidate;
    Candidate testCandidate;

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
        MockitoAnnotations.initMocks(this );
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

        this.testCandidate = new Candidate();
        this.testCandidate.setId( this.candidateResponse1.getId() );
        this.testCandidate.setName( this.candidateResponse1.getName() );
        this.testCandidate.setEmail( this.candidateResponse1.getEmail() );
        this.testCandidate.setBirthDate( this.candidateResponse1.getBirthDate() );
        this.testCandidate.setPhone( this.candidateResponse1.getPhone() );
        this.testCandidate.setSkills( this.skillService.getAllSkillsById( this.candidateResponse1.getSkills() ) );

        this.toUpdateSkills = new ArrayList<>();
        this.toUpdateSkills.add( this.skillId1 );
    }

    @Test
    final void getAllCandidates_whenDatabaseIsEmpty_returnEmptyList() {
        Pageable pageable = PageRequest.of( this.page, this.size );
        Page<Candidate> candidatePage = this.candidateRepository.findAll( pageable );
        when( this.candidateRepository.findAll( pageable ) )
                .thenReturn( candidatePage );
    }

    @Test
    final void getAllCandidates_whenDatabaseNotEmpty_returnListOfCandidates() {
        Pageable pageable = PageRequest.of( this.page, this.size );
        when( this.candidateRepository.findAll( pageable ) )
                .thenReturn( Page.empty() );
        assertEquals( this.candidateService.getAllCandidates( this.page, this.size ), new ArrayList<>() );
    }

    @Test
    final void getCandidateById_whenCandidateRepositoryDoesNotHaveEntityWithGivenId_throwsException() {
        Optional<Candidate> candidateMyb = Optional.of( this.testCandidate );
        when( this.candidateRepository.findById( candidateMyb.get().getId() ) )
                .thenThrow( NotFoundException.class );
        assertThrows( NotFoundException.class, () -> {
            this.candidateService.getCandidateById( candidateMyb.get().getId() );
        } );
    }

    @Test
    final void getCandidateById_whenCandidateRepositoryHaveEntityWithGivenId_success() {
        Optional<Candidate> candidateMyb = Optional.of( this.testCandidate );
        when( this.candidateRepository.findById( candidateMyb.get().getId() ) ).thenReturn( candidateMyb );
        assertEquals( candidateMyb.get(), this.testCandidate );
    }

    @Test
    final void getCandidatesByName_whenCandidatesNamesNotLikeName_returnEmptyList() {
        Pageable pageable = PageRequest.of( this.page, this.size );
        when( this.candidateRepository.findByName( this.candidateName, pageable ) )
                .thenReturn( Page.empty() );
        assertEquals( this.candidateService.getCandidatesByName(
                this.page, this.size, this.candidateName ), new ArrayList<>() );
    }

    @Test
    final void getCandidatesByName_WhenCandidatesNamesLikeName_returnListOfCandidates() {
        Pageable pageable = PageRequest.of( this.page, this.size );
        Page<Candidate> candidatePage = this.candidateRepository.findByName( this.candidateName, pageable );
        when( this.candidateRepository.findByName( this.candidateName, pageable ) )
                .thenReturn( candidatePage );
    }

    @Test
    final void getCandidatesBySkill_whenCandidateSkillsNotNamedLikeSkill_returnEmptyList() {
        Pageable pageable = PageRequest.of( this.page, this.size );
        when( this.candidateRepository.findBySkills( this.skillIds, pageable ) )
                .thenReturn( Page.empty() );
        assertEquals( this.candidateService.getCandidatesBySkill(
                this.page, this.size, this.skillIds ), new ArrayList<>() );
    }

    @Test
    final void getCandidatesBySkill_WhenCandidatesSkillsNamedLikeSkill_returnListOfCandidates() {
        List<UUID> skills = new ArrayList<>();
        Pageable pageable = PageRequest.of( this.page, this.size );
        Page<Candidate> candidatePage = this.candidateRepository.findBySkills( skills, pageable );
        when( this.candidateRepository.findBySkills( skills, pageable ) )
                .thenReturn( candidatePage );
    }

    @Test
    final void save_whenCandidateWithGivenEmailExist_throwsException() {
        when( this.candidateRepository.save( this.testCandidate ) )
                .thenThrow( BadRequestException.class );
        try {
            this.candidateService.save( this.createCandidate );
        } catch ( BadRequestException ex ) {
            ex.printStackTrace();
        }
    }

    @Test
    final void save_whenCandidateWithGivenEmailNotExist_success() {
        when( this.candidateRepository.save( this.testCandidate ) )
                .thenReturn( this.testCandidate );
        assertNotNull( this.candidateService.save( this.createCandidate ) );
    }

    @Test
    final void update_whenIdNotExist_throwsException() {
        Optional<Candidate> candidateMyb = Optional.of( this.testCandidate );
        when( this.candidateRepository.findById( candidateMyb.get().getId() ) )
                .thenThrow( NotFoundException.class );
        assertThrows( NotFoundException.class, () -> {
            this.candidateService.update( candidateMyb.get().getId(), this.skillIds );
        } );
    }

    @Test
    final void update_whenIdExist_success() {
        Optional<Candidate> candidateMyb = Optional.of( this.testCandidate );
        when( this.candidateRepository.findById( candidateMyb.get().getId() ) )
                .thenReturn( candidateMyb );
        assertNotNull( candidateMyb );
        CandidateResponse candidateResponse = this.candidateService.update( candidateMyb.get().getId(), this.skillIds );
        assertNotNull( candidateResponse );
    }

    @Test
    final void delete_whenIdNotExist_throwsException() {
        Optional<Candidate> candidateMyb = Optional.of( this.testCandidate );
        when( this.candidateRepository.findById( candidateMyb.get().getId() ) )
                .thenThrow( NotFoundException.class );
        assertThrows( NotFoundException.class, () -> {
            this.candidateService.delete( candidateMyb.get().getId() );
        } );
    }

    @Test
    final void delete_whenIdExist_success() {
        Optional<Candidate> candidateMyb = Optional.of( this.testCandidate );
        when( this.candidateRepository.findById( candidateMyb.get().getId() ) )
                .thenReturn( candidateMyb );
        assertEquals( candidateMyb.get(), this.testCandidate );
        this.candidateService.delete( candidateMyb.get().getId() );
    }
}