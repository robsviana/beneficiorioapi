package tech.robsondev.beneficiarioapi.dto;

import jakarta.validation.constraints.NotNull;
import tech.robsondev.beneficiarioapi.enums.UserRole;

public record RegisterDTO(@NotNull String email, @NotNull String password, @NotNull UserRole role) {
}
