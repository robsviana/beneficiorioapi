package tech.robsondev.beneficiarioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.robsondev.beneficiarioapi.entity.Documento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, UUID> {
    Optional<List<Documento>> findByBeneficiario_id(UUID uuid);
}
