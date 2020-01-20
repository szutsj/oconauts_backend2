package com.octonauts.game.controller;

import com.octonauts.game.model.dto.*;
import com.octonauts.game.model.entity.medicineFactory.Medicine;
import com.octonauts.game.model.entity.Octopod;
import com.octonauts.game.model.entity.User;
import com.octonauts.game.model.entity.medicineFactory.MedicineFactory;
import com.octonauts.game.model.enums.MedicineType;
import com.octonauts.game.repository.OctopodRepository;
import com.octonauts.game.service.MedicineService;
import com.octonauts.game.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicineController {

   private MedicineService medicineService;
    private UserService userService;
    private OctopodRepository octopodRepository;

    @Autowired
    public MedicineController(MedicineService medicineService, UserService userService, OctopodRepository octopodRepository) {
        this.medicineService = medicineService;
        this.userService = userService;
        this.octopodRepository = octopodRepository;
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = MedicineStockDTO.class)})
    @GetMapping("/octopod/medicines")
    public ResponseEntity<Object> medicineList() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByName(username).get();
        Octopod octopod = octopodRepository.findByUser(user).get();
        MedicineStockDTO medicineStockDTO = medicineService.createMedicineList(octopod);
        return ResponseEntity.status(200).body(medicineStockDTO);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = MedicineStockDTO.class),
            @ApiResponse (code = 409, message = "Invalid medicine type!", response = ErrorMessage.class),
            @ApiResponse(code = 408, message = "Not enough points!", response = ErrorMessage.class)})
    @PutMapping("/octopod/medicines")
    public ResponseEntity<Object> buyMedicine(@RequestBody BuyMedicineRequestDTO buyMedicineRequestDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByName(username).get();
        Octopod octopod = octopodRepository.findByUser(user).get();
        user = userService.recalculatePoints(user);
        if (buyMedicineRequestDTO == null || buyMedicineRequestDTO.getType().isEmpty() || medicineService.invalidMedicineType(buyMedicineRequestDTO.getType())){
            return ResponseEntity.status(409).body(new ErrorMessage("Invalid medicine type!"));
        }
        MedicineType medicineType = MedicineFactory.getMedicineType(buyMedicineRequestDTO.getType());
        Medicine medicine = MedicineFactory.getMedicine(medicineType, octopod);
        if (user.getPoints() < medicine.getPrice()){
            return ResponseEntity.status(408).body(new ErrorMessage("Not enough points!"));
        }
        return ResponseEntity.status(200).body(medicineService.buyMedicine(medicine));
    }

}
