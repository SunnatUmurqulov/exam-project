package uz.pdp.examproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.examproject.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    boolean existsByName(String name);
}
