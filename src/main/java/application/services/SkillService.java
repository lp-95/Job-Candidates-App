package application.services;

import application.models.Skill;
import application.dto.CreateSkillRequest;
import application.dto.SkillResponse;

import java.util.List;
import java.util.UUID;

public interface SkillService {
    List<Skill> getAllSkillsById(List<UUID> skillIds);
    List<SkillResponse> getAllSkills(int page, int size);
    SkillResponse save(CreateSkillRequest dto) throws Exception;
}
