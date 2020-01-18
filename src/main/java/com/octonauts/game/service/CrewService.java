package com.octonauts.game.service;

import com.octonauts.game.model.dto.CrewDTO;
import com.octonauts.game.model.dto.CrewMemberDTO;
import com.octonauts.game.model.entity.Animal;
import com.octonauts.game.model.entity.CrewMember;
import com.octonauts.game.model.entity.Octopod;
import com.octonauts.game.model.entity.User;
import com.octonauts.game.model.enums.CrewMembers;
import com.octonauts.game.repository.CrewRepository;
import com.octonauts.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CrewService {

    private CrewRepository crewRepository;
    private UserRepository userRepository;

    @Autowired
    public CrewService(CrewRepository crewRepository, UserRepository userRepository) {
        this.crewRepository = crewRepository;
        this.userRepository = userRepository;
    }

    public List<CrewMember> initCrew(Octopod octopod){
        List<CrewMember> crew = new ArrayList<>();
        for (CrewMembers crewMember : CrewMembers.values()) {
            CrewMember newCrewMember;
            if (crewMember.equals(CrewMembers.CAPTAIN) || crewMember.equals(CrewMembers.PESO)){
                newCrewMember = new CrewMember(crewMember, true, octopod);
            } else {
                newCrewMember = new CrewMember(crewMember, false, octopod);
            }
            crewRepository.save(newCrewMember);
            crew.add(newCrewMember);
        }
        return crew;
    }

    public CrewDTO createCrewList(Octopod octopod) {
        List<CrewMemberDTO> crewMemberDTOList = new ArrayList<>();
        List<CrewMember>  crew = crewRepository.findAllByOctopod(octopod);
        for(CrewMember crewMember : crew){
            crewMemberDTOList.add(createCrewMemberDTO(crewMember));
        }
        return new CrewDTO(crewMemberDTOList);
    }

    public CrewMemberDTO createCrewMemberDTO(CrewMember crewMember){
        CrewMemberDTO crewMemberDTO = new CrewMemberDTO();
        crewMemberDTO.setName(crewMember.getName());
        crewMemberDTO.setActive(crewMember.isActive());
        return crewMemberDTO;
    }

    public int pointsPaidForCrew(Octopod octopod) {
        if (crewRepository.countPointForActivate(octopod) == null){
            return 0;
        }
        return crewRepository.countPointForActivate(octopod);
    }

    public Optional<CrewMember> findById(Long id){
        return crewRepository.findById(id);
    }

    public CrewDTO activate(CrewMember crewMember) {
        User user = crewMember.getOctopod().getUser();
        user.setPoints(user.getPoints() - crewMember.getPointsForActivate());
        userRepository.save(user);
        crewMember.setActive(true);
        crewRepository.save(crewMember);
        return createCrewList(crewMember.getOctopod());
    }

}
