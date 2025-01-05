package by.tms.bookpoint.controller;

import by.tms.bookpoint.dto.ErrorResponse;
import by.tms.bookpoint.entity.Staff;
import by.tms.bookpoint.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/all")
    public ResponseEntity<List<Staff>> all() {
        var all = staffRepository.findAll();
        return ResponseEntity.ok(all);
    }

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

    @PostMapping("/create")
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        staffRepository.save(staff);
        return ResponseEntity.ok(staff);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStaffById(@PathVariable("id") Long id, @RequestBody Staff newStaff) {
        Optional<Staff> staffFromDB = staffRepository.findById(id);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStaffById(@PathVariable("id") Long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        if (staff.isPresent()){
            staffRepository.deleteById(id);
            return ResponseEntity.ok(staff);
        }
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Staff not found"), HttpStatus.BAD_REQUEST);
    }



}
