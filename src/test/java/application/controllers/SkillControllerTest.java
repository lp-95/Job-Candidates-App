package application.controllers;

import application.dto.CreateSkillRequest;
import application.dto.SkillResponse;
import application.models.Skill;
import application.services.SkillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


class SkillControllerTest {
    @InjectMocks
    SkillController skillController;
    @Mock
    SkillServiceImpl skillService;

    CreateSkillRequest createSkill;
    Skill testSkill;
    SkillResponse skillResponse;

    final UUID skillId1 = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks( this );
        this.createSkill = new CreateSkillRequest();
        this.createSkill.setName( "name" );

        this.testSkill = new Skill();
        this.testSkill.setId( this.skillId1 );
        this.testSkill.setName( this.createSkill.getName() );

        this.skillResponse = new SkillResponse();
        this.skillResponse.setId( this.testSkill.getId() );
        this.skillResponse.setName( this.testSkill.getName() );
    }

    @Test
    void save() {
        when( this.skillService.save( this.createSkill ) )
                .thenReturn( this.skillResponse );
        ResponseEntity<?> response = this.skillController.save( this.createSkill );
        assertEquals( response.getStatusCode(), HttpStatus.OK );
    }
}