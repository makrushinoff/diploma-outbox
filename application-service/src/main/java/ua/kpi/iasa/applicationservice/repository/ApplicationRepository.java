package ua.kpi.iasa.applicationservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.iasa.applicationservice.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

  List<Application> findAllByUserIdOrderByCreationDateTimeAsc(Long userId);

}
