package io.github.mehranmirkhan.common.model;

import java.util.UUID;
import org.hibernate.proxy.HibernateProxy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntityUuid<USER> extends BaseEntity<USER> {
  @Id
  @GeneratedValue(generator = "UUID")
  protected UUID id;

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }

    Class<?> oEffectiveClass = o instanceof HibernateProxy
        ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
        : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
        : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }

    BaseEntityUuid<?> that = (BaseEntityUuid<?>) o;

    return id != null ? id.equals(that.id) : that.id == null;
  }

  @Override
  public final int hashCode() {
    Class<?> effectiveClass = this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
        : this.getClass();
    return effectiveClass.hashCode() * 31 + (id != null ? id.hashCode() : 0);
  }

  @Data
  @SuperBuilder
  @NoArgsConstructor
  @EqualsAndHashCode(callSuper = true)
  public static class Dto<USER> extends BaseEntity.Dto<USER> {
    private UUID id;
  }

  @org.mapstruct.Mapper
  public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    default <USER> Dto<USER> toDto(BaseEntityUuid<USER> entity) {
      if (entity == null) {
        return null;
      }
      Dto<USER> dto = new Dto<>();
      dto.setId(entity.getId());
      dto.setCreatedAt(entity.getCreatedAt());
      dto.setCreatedBy(entity.getCreatedBy());
      dto.setUpdatedAt(entity.getUpdatedAt());
      dto.setUpdatedBy(entity.getUpdatedBy());
      return dto;
    }

    default <USER> BaseEntityUuid<USER> toEntity(Dto<USER> dto) {
      if (dto == null) {
        return null;
      }
      BaseEntityUuid<USER> entity = new BaseEntityUuid<>();
      entity.setId(dto.getId());
      entity.setCreatedAt(dto.getCreatedAt());
      entity.setCreatedBy(dto.getCreatedBy());
      entity.setUpdatedAt(dto.getUpdatedAt());
      entity.setUpdatedBy(dto.getUpdatedBy());
      return entity;
    }
  }
}
