package application.services;

import application.exceptions.BadRequestException;
import application.exceptions.ErrorMessages;
import application.models.Skill;
import application.repositories.SkillRepository;
import application.dto.CreateSkillRequest;
import application.dto.SkillResponse;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;

    @Override
    public SkillResponse save( CreateSkillRequest createSkillRequest ) throws BadRequestException {
        Skill skill = new Skill( UUID.randomUUID(), createSkillRequest.getName(), null );
        try {
            this.skillRepository.save( skill );
        } catch ( DataIntegrityViolationException ex ) {
            throw new BadRequestException(
                    String.format( ErrorMessages.NAME_ALREADY_EXIST, createSkillRequest.getName() ) );
        }
        return new SkillResponse( skill.getId(), skill.getName() );
    }

    @Override
    public List<Skill> getAllSkillsById( List<UUID> skillIds ) {
        List<Skill> skills = new ArrayList<>();
        for ( UUID id : skillIds ) {
            if ( id == null || !this.skillRepository.existsById( id ) ) {
                continue;
            }
            skills.add( this.skillRepository.getOne( id ) );
        }
        return skills;
    }

    @Override
    public List<SkillResponse> getAllSkills( int page, int size ) {
        List<Skill> skills =  this.skillRepository.findAll( PageRequest.of( page, size ) ).getContent();
        List<SkillResponse> skillsResponse = new ArrayList<>();
        for ( Skill skill : skills ) {
            skillsResponse.add( new SkillResponse(
                    skill.getId(),
                    skill.getName() ) );
        }
        return skillsResponse;
    }
}