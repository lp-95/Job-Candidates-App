package application.services;

import application.dto.CreateCandidateRequest;
import application.exceptions.BadRequestException;
import application.exceptions.NotFoundException;
import application.models.Candidate;
import application.dto.CandidateResponse;

import java.util.List;
import java.util.UUID;

public interface CandidateService {
    Candidate getCandidateById(UUID id) throws NotFoundException;
    List<CandidateResponse> getCandidatesByName(int page, int size, String name);
    List<CandidateResponse> getCandidatesBySkill(int page, int size, List<UUID> skills);
    CandidateResponse save(CreateCandidateRequest createCandidateRequest) throws BadRequestException;
    CandidateResponse update(UUID id, List<UUID> skillIds) throws NotFoundException, BadRequestException;
    void delete(UUID id) throws NotFoundException;
}
