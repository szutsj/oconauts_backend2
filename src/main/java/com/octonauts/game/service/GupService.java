package com.octonauts.game.service;

import com.octonauts.game.model.dto.CrewDTO;
import com.octonauts.game.model.dto.CrewMemberDTO;
import com.octonauts.game.model.dto.GupDTO;
import com.octonauts.game.model.dto.GupListDTO;
import com.octonauts.game.model.entity.CrewMember;
import com.octonauts.game.model.entity.Gup;
import com.octonauts.game.model.entity.Octopod;
import com.octonauts.game.model.entity.User;
import com.octonauts.game.model.enums.GupType;
import com.octonauts.game.repository.GupRepository;
import com.octonauts.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.graalvm.compiler.options.OptionType.User;

@Service
public class GupService {

    private GupRepository gupRepository;
    private UserRepository userRepository;

    @Autowired
    public GupService(GupRepository gupRepository, UserRepository userRepository) {
        this.gupRepository = gupRepository;
        this.userRepository = userRepository;
    }

    public List<Gup> initGups(Octopod octopod) {
        List<Gup> gups = new ArrayList<>();
        for (GupType gupType : GupType.values()) {
            Gup gup = new Gup(gupType, false, octopod);
            gupRepository.save(gup);
            gups.add(gup);
        }
        return gups;

    }

    public GupListDTO createGupList(Octopod octopod) {
        List<GupDTO> gupDTOList = new ArrayList<>();
        List<Gup> gups = gupRepository.findAllByOctopod(octopod);
        for (Gup gup : gups) {
            gupDTOList.add(createGupDTO(gup));
        }
        return new GupListDTO(gupDTOList);
    }

    public GupDTO createGupDTO(Gup gup) {
        GupDTO gupDTO = new GupDTO();
        gupDTO.setType(gup.getType());
        gupDTO.setActive(gup.isActive());
        return gupDTO;
    }

    public int pointsPaidForGups(Octopod octopod) {
        if (gupRepository.countPointForActivate(octopod) == null){
            return 0;
        }
        return gupRepository.countPointForActivate(octopod);
    }

    public GupDTO activate(Gup gup) {
        User user = gup.getOctopod().getUser();
        user.setPoints(user.getPoints() - gup.getPointsForActivate());
        userRepository.save(user);
        gup.setActive(true);
        gupRepository.save(gup);
        return createGupDTO(gup);
    }

    public Optional<Gup> findById(Long gupId){
        return gupRepository.findById(gupId);
    }

}
