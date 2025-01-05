package by.tms.bookpoint.repository;

import by.tms.bookpoint.entity.Staff;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    //query dsl
    Optional<Staff> findByName(String name);

    List<Staff> findAllByName(String name, Sort sort);

}
