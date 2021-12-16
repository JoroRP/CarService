package project.carservice.repository;

import project.carservice.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository


public interface RoleRepository extends JpaRepository<RoleEntity,String> {

    Optional<RoleEntity> findByRole(String role);
}
