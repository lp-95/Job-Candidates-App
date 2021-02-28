package application.services;

import application.dto.CreateSkillRequest;
import application.exceptions.BadRequestException;
import application.models.Skill;
import application.repositories.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SkillServiceImplTest {
    @InjectMocks
    SkillServiceImpl skillService;
    @Mock
    SkillRepository skillRepository;

    @BeforeEach
    final void setUp() {
        MockitoAnnotations.initMocks(this );
    }

    @Test
    final void save_whenSkillWithGivenNameExist_throwsException() {
        CreateSkillRequest createSkill = new CreateSkillRequest( "name" );
        Skill skillTest = new Skill( UUID.randomUUID(), createSkill.getName(), null );
        when( this.skillRepository.save( skillTest ) )
                .thenThrow( BadRequestException.class );
        try {
            this.skillService.save( createSkill );
        } catch ( BadRequestException ex ) {
            ex.printStackTrace();
        }
    }

    @Test
    final void save_whenSkillWithGivenNameNotExist_success() {
        CreateSkillRequest createSkill = new CreateSkillRequest( "name" );
        Skill testSkill = new Skill( UUID.randomUUID(), createSkill.getName(), null );
        when( this.skillRepository.save( testSkill ) )
                .thenReturn( testSkill );
        assertNotNull( this.skillService.save( createSkill ) );
    }

    @Test
    final void getAllSkillsById_whenGivenIstIsEmpty_returnEmptyList() {
        List<UUID> skillIds = new ArrayList<>();
        List<Skill> skills = new ArrayList<>();
        when( this.skillRepository.findAllById( skillIds ) )
                .thenReturn( skills );
        assertEquals( skills, new ArrayList<>() );
    }
    @Test
    final void getAllSkillsById_whenGivenListIsNotEmpty_returnEmptyList() {
        List<UUID> skillIds = new ArrayList<>();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        skillIds.add( id1 );
        skillIds.add( id2 );
        when( this.skillRepository.existsById( id1 ) )
                .thenReturn( false );
        when( this.skillRepository.existsById( id2 ) )
                .thenReturn( false );
        assertEquals( this.skillService.getAllSkillsById( skillIds ), new ArrayList<>() );
    }

    @Test
    final void getAllSkillsById_whenGivenListIsNotEmpty_returnListOfElementsWithExistingIds() {
        List<UUID> skillIds = new ArrayList<>();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        skillIds.add( id1 );
        skillIds.add( id2 );
        when( this.skillRepository.existsById( id1 ) )
                .thenReturn( false );
        when( this.skillRepository.existsById( id2 ) )
                .thenReturn( true );
        Skill skill = this.skillRepository.getOne( id2 );
        List<Skill> skills = new ArrayList<>();
        skills.add( skill );
        List<Skill> returnedSkills = this.skillService.getAllSkillsById( skillIds );
        assertEquals( skills, returnedSkills );
    }

    @Test
    final void getAllSkillsById_whenGivenListIsNotEmpty_returnListOfElementsWithAllIds() {
        List<UUID> skillIds = new ArrayList<>();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        skillIds.add( id1 );
        skillIds.add( id2 );
        when( this.skillRepository.existsById( id1 ) )
                .thenReturn( true );
        when( this.skillRepository.existsById( id2 ) )
                .thenReturn( true );
        Skill skill1 = this.skillRepository.getOne( id1 );
        Skill skill2 = this.skillRepository.getOne( id2 );
        List<Skill> skills = new ArrayList<>();
        skills.add( skill1 );
        skills.add( skill2 );
        List<Skill> returnedSkills = this.skillService.getAllSkillsById( skillIds );
        assertEquals( skills, returnedSkills );
    }

    @Test
    final void getAllSkills_whenDataBaseIsEmpty_returnListOfElements() {
        int page = 1;
        int size  = 30;
        Pageable pageable = PageRequest.of( page, size );
        Page<Skill> skills = this.skillRepository.findAll( pageable );
        when( this.skillRepository.findAll( pageable ) )
                .thenReturn( skills );
    }

    @Test
    final void getAllSkills_whenDataBaseIsEmpty_returnEmptyList() {
        int page = 1;
        int size  = 30;
        Pageable pageable = PageRequest.of( page, size );
        when( this.skillRepository.findAll( pageable ) )
                .thenReturn( Page.empty() );
        assertEquals( this.skillService.getAllSkills( page, size ), new ArrayList<>() );
    }
}