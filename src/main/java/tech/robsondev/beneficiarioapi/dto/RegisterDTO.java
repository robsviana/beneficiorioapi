package tech.robsondev.beneficiarioapi.dto;

import jakarta.validation.constraints.NotNull;
import tech.robsondev.beneficiarioapi.enuns.UserRole;

public record RegisterDTO(@NotNull String email, @NotNull String password, @NotNull UserRole role) {
}
