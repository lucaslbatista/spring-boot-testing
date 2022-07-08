package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE e.firstName = ?1 and e.lastName = ?2")
    Optional<Employee> findByJPQL(String firstName, String lastName);

    @Query("SELECT e FROM Employee e WHERE e.firstName = :firstName and e.lastName = :lastName")
    Optional<Employee> findByJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query(value = "SELECT * FROM employees e WHERE e.first_name = ?1 AND e.last_name = ?1", nativeQuery = true)
    Optional<Employee> findbyNativeSQL(String firstName, String lastName);

    @Query(value = "SELECT * FROM employees e WHERE e.first_name = :firstName AND e.last_name = :lastName", nativeQuery = true)
    Optional<Employee> findbyNativeSQLNamed(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
