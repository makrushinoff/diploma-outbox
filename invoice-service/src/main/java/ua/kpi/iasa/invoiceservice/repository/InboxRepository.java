package ua.kpi.iasa.invoiceservice.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.iasa.invoiceservice.entity.Inbox;

@Repository
public interface InboxRepository extends JpaRepository<Inbox, UUID> {

}
