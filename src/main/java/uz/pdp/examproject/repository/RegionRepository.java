package uz.pdp.examproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.examproject.entity.Region;

public interface RegionRepository extends JpaRepository<Region,Integer> {
    boolean existsByName(String name);
}
