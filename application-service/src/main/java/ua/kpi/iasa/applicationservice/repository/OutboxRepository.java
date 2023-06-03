package ua.kpi.iasa.applicationservice.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.iasa.applicationservice.entity.Outbox;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, UUID> {

}
