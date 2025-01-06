package by.tms.bookpoint.controller;

import by.tms.bookpoint.dto.ErrorResponse;
import by.tms.bookpoint.dto.ErrorResponseMap;
import by.tms.bookpoint.entity.Staff;
import by.tms.bookpoint.repository.StaffRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "Staff Resource") //for swagger doc
@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;

    //pagination response
//    @GetMapping("/all")
//    public ResponseEntity<Model> all(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
//        var all = staffRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
//        model.addAttribute("staff", all); // model for view
//        return ResponseEntity.ok(model);
//    }

    @Operation(summary = "Find all Staff", description = "Find all Staff")
    @GetMapping("/all")
    public ResponseEntity<List<Staff>> all() {
        var all = staffRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @ApiResponse(responseCode = "200", description = "request is successfully") // maybe annotation with @ApiResponse don't need
    @ApiResponse(responseCode = "400", description = "Staff Id not found")
    @Operation(summary = "Finds Staff by Id", description = "Finds Staff by Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> findStaffById(@PathVariable("id") Long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        if (staff.isPresent()){
            return ResponseEntity.ok(staff);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Staff not found"), HttpStatus.BAD_REQUEST);
    }

    //simple
//    @GetMapping("/{id}")
//    public ResponseEntity<Optional<Staff>> findById(@PathVariable("id") Long id) {
//        Optional<Staff> staff = staffRepository.findById(id);
//        return ResponseEntity.ok(staff);
//    }

    @Operation(summary = "Crate Staff", description = "Crate Staff, send to request Staff object")
    @PostMapping("/create")
    public ResponseEntity<?> createStaff(@Valid @RequestBody Staff staff, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(errorsResponse(bindingResult), HttpStatus.BAD_REQUEST);
//            ErrorResponseMap errorResponseMap = new ErrorResponseMap();
//            List<String> errors = new ArrayList<>();
//            for (FieldError fieldError : bindingResult.getFieldErrors()) {
//                errors.add(fieldError.getDefaultMessage());
//                errorResponseMap.getErrors().put(fieldError.getField(), errors);
//            }
//            return new ResponseEntity<>(errorResponseMap, HttpStatus.BAD_REQUEST);
        }

        staffRepository.save(staff);
        return ResponseEntity.ok(staff);
    }

    @ApiResponse(responseCode = "200", description = "request is successfully")
    @ApiResponse(responseCode = "400", description = "Request JSON have fail validation or Staff Id not found")
    @Operation(summary = "Update Staff by Id", description = "Update Staff by Id, check validate Staff object and exists Id")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStaffById(@PathVariable("id") Long id, @Valid @RequestBody Staff newStaff, BindingResult bindingResult) {
        Optional<Staff> staffFromDB = staffRepository.findById(id);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(errorsResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        if (staffFromDB.isPresent()){
            Staff tempStaff = staffFromDB.get();
            tempStaff.setStaff_id(newStaff.getStaff_id());
            tempStaff.setName(newStaff.getName());
            tempStaff.setSurname(newStaff.getSurname());
            tempStaff.setPassword(newStaff.getPassword());
            staffRepository.save(tempStaff);
            return ResponseEntity.ok(tempStaff);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Staff not found"), HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Delete Staff by Id", description = "Delete Staff by Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaffById(@PathVariable("id") Long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        if (staff.isPresent()){
            staffRepository.deleteById(id);
            return ResponseEntity.ok(staff);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Staff not found"), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponseMap errorsResponse (BindingResult bindingResult){
        ErrorResponseMap errorResponseMap = new ErrorResponseMap();
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.add(fieldError.getDefaultMessage());
            errorResponseMap.getErrors().put(fieldError.getField(), errors);
        }
        return errorResponseMap;
    }

}
