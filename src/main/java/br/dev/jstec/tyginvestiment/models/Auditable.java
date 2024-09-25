package br.dev.jstec.tyginvestiment.models;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable<U> {

    @CreatedBy
    private U createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private U lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
