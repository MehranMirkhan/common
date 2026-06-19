package io.github.mehranmirkhan.common.model;

import java.io.Serializable;
import java.time.Instant;
import org.mapstruct.factory.Mappers;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * BaseEntity is a base class for entities that provides common fields and functionality for
 * auditing purposes. It includes fields for tracking creation and modification timestamps, as well
 * as the users responsible for those actions. The class is designed to be extended by other entity
 * classes, allowing them to inherit these common properties and behaviors.
 *
 * <p>
 * Example Liquibase changelog for creating a table with BaseEntity fields:
 * 
 * <pre>{@code
 *  changes:
 *    - createTable:
 *        tableName: book
 *        columns:
 *          - column:
 *              name: id
 *              type: UUID
 *              constraints:
 *                primaryKey: true
 *                nullable: false
 *          - column:
 *              name: created_at
 *              type: TIMESTAMP
 *              constraints:
 *                nullable: false
 *                modifiable: false
 *          - column:
 *              name: created_by
 *              type: UUID
 *              constraints:
 *                modifiable: false
 *          - column:
 *              name: updated_at
 *              type: TIMESTAMP
 *          - column:
 *              name: updated_by
 *              type: UUID
 *          - column:
 *              name: opt_lock
 *              type: BIGINT
 *              defaultValue: 0
 *              constraints:
 *                nullable: false
 * }</pre>
 *
 * @param <USER> the type used to represent the auditing user (e.g., {@link java.util.UUID})
 */
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity<USER> {
  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  protected Instant createdAt;

  @CreatedBy
  @Column(name = "created_by", updatable = false)
  protected USER createdBy;

  @LastModifiedDate
  @Column(name = "updated_at")
  protected Instant updatedAt;

  @LastModifiedBy
  @Column(name = "updated_by")
  protected USER updatedBy;

  @Version
  @JsonIgnore
  @Column(name = "opt_lock", nullable = false)
  protected Long optLock;

  @PrePersist
  public void prePersist() {
    if (this.optLock == null) {
      this.optLock = 0L;
    }
  }

  @Data
  @SuperBuilder
  @NoArgsConstructor
  public static class Dto<USER> implements Serializable {
    private Instant createdAt;
    private USER createdBy;
    private Instant updatedAt;
    private USER updatedBy;
  }

  @org.mapstruct.Mapper
  public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    default <USER> Dto<USER> toDto(BaseEntity<USER> entity) {
      if (entity == null) {
        return null;
      }
      Dto<USER> dto = new Dto<>();
      dto.setCreatedAt(entity.getCreatedAt());
      dto.setCreatedBy(entity.getCreatedBy());
      dto.setUpdatedAt(entity.getUpdatedAt());
      dto.setUpdatedBy(entity.getUpdatedBy());
      return dto;
    }

    default <USER> BaseEntity<USER> toEntity(Dto<USER> dto) {
      if (dto == null) {
        return null;
      }
      BaseEntity<USER> entity = new BaseEntity<>();
      entity.setCreatedAt(dto.getCreatedAt());
      entity.setCreatedBy(dto.getCreatedBy());
      entity.setUpdatedAt(dto.getUpdatedAt());
      entity.setUpdatedBy(dto.getUpdatedBy());
      return entity;
    }
  }

}
