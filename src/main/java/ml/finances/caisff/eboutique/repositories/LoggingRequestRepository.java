package ml.finances.caisff.eboutique.repositories;

import ml.finances.caisff.eboutique.entities.LoggingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggingRequestRepository extends JpaRepository<LoggingRequest, Long> {
}