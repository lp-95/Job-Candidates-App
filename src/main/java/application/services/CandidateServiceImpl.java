package application.services;

import application.exceptions.ErrorMessages;
import application.exceptions.NotFoundException;
import application.repositories.CandidateRepository;
import application.dto.CandidateResponse;
import application.dto.CreateCandidateRequest;
import application.exceptions.BadRequestException;
import application.models.Candidate;
import application.models.Skill;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final SkillServiceImpl skillService;

    public List<CandidateResponse> getAllCandidates( int page, int size ) {
        List<Candidate> candidates = this.candidateRepository
                .findAll( PageRequest.of( page, size) ).getContent();
        return this.getCandidatesResponse( candidates );
    }

    @Override
    public Candidate getCandidateById( UUID id ) throws NotFoundException {
        return this.candidateRepository
                .findById( id ).orElseThrow( () -> new NotFoundException ( String.format( ErrorMessages.ID_NOT_FOUND, id ) ) );
    }

    @Override
    public List<CandidateResponse> getCandidatesByName( int page, int size, String name ) {
        List<Candidate> candidates =  this.candidateRepository
                .findByName( name, PageRequest.of( page, size) ).getContent();
        return this.getCandidatesResponse( candidates );
    }

    @Override
    public List<CandidateResponse> getCandidatesBySkill( int page, int size, List<UUID> skills ) {
        List<Candidate> candidates =  this.candidateRepository
                .findBySkills( skills, PageRequest.of( page, size ) ).getContent();
        return this.getCandidatesResponse( candidates );
    }

    @Override
    public CandidateResponse save( CreateCandidateRequest createCandidate ) throws BadRequestException {
        Candidate candidate = new Candidate( UUID.randomUUID(), createCandidate.getName(),
                createCandidate.getEmail(), createCandidate.getBirthDate(), createCandidate.getPhone(),
                this.skillService.getAllSkillsById( createCandidate.getSkills() ) );
        try {
            this.candidateRepository.save( candidate );
        } catch ( DataIntegrityViolationException ex ) {
            throw new BadRequestException(
                    String.format( ErrorMessages.EMAIL_ALREADY_EXIST, createCandidate.getEmail() ) );
        }
        return this.getCandidateResponse( candidate );
    }

    @Override
    public CandidateResponse update( UUID id, List<UUID> skillIds ) throws NotFoundException {
        Candidate candidate = this.getCandidateById( id );
        candidate.setSkills( this.skillService.getAllSkillsById( skillIds ) );
        this.candidateRepository.save( candidate );
        return this.getCandidateResponse( candidate );
    }

    @Override
    public void delete( UUID id ) throws NotFoundException {
        this.candidateRepository.delete( this.getCandidateById( id ) );
    }

    private List<UUID> getSkillIds( List<Skill> skills ) {
        List<UUID> skillIds = new ArrayList<>();
        for ( Skill skill : skills ) {
            skillIds.add( skill.getId() );
        }
        return skillIds;
    }

    private List<CandidateResponse> getCandidatesResponse( List<Candidate> candidates ) {
        List<CandidateResponse> candidatesResponse = new ArrayList<>();
        for ( Candidate candidate : candidates ) {
            candidatesResponse.add( this.getCandidateResponse( candidate ) );
        }
        return candidatesResponse;
    }

    private CandidateResponse getCandidateResponse( Candidate candidate ) {
        return new CandidateResponse( candidate.getId(), candidate.getName(), candidate.getEmail(),
                candidate.getBirthDate(), candidate.getPhone(),
                this.getSkillIds( candidate.getSkills() ) );
    }
}