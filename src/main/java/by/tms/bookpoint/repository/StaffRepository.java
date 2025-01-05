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

    //native SQL not sintaxsis validate
//    @Query(value = "SELECT * FROM POST WHERE TITLE = :title", nativeQuery = true)
//    Optional<Post> findByTitleCustom(String title);

    //JPQL
//    @Query(value = "SELECT p FROM Post p WHERE p.title = :title")
//    Optional<Post> findByTitleCustom(String title);
}
