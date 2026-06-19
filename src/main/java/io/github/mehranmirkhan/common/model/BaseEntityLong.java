package io.github.mehranmirkhan.common.model;

import org.hibernate.proxy.HibernateProxy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class BaseEntityLong<USER> extends BaseEntity<USER> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

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

    BaseEntityLong<?> that = (BaseEntityLong<?>) o;

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
    private Long id;
  }

  @org.mapstruct.Mapper(uses = {BaseEntity.Mapper.class})
  public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    default <USER> Dto<USER> toDto(BaseEntityLong<USER> entity) {
      if (entity == null) {
        return null;
      }
      Dto<USER> dto = new Dto<>();
      dto.setCreatedAt(entity.getCreatedAt());
      dto.setCreatedBy(entity.getCreatedBy());
      dto.setUpdatedAt(entity.getUpdatedAt());
      dto.setUpdatedBy(entity.getUpdatedBy());
      dto.setId(entity.getId());
      return dto;
    }

    default <USER> BaseEntityLong<USER> toEntity(Dto<USER> dto) {
      if (dto == null) {
        return null;
      }
      BaseEntityLong<USER> entity = new BaseEntityLong<>();
      entity.setCreatedAt(dto.getCreatedAt());
      entity.setCreatedBy(dto.getCreatedBy());
      entity.setUpdatedAt(dto.getUpdatedAt());
      entity.setUpdatedBy(dto.getUpdatedBy());
      entity.setId(dto.getId());
      return entity;
    }
  }
}
