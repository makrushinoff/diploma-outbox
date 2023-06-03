package ua.kpi.iasa.applicationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ua.kpi.iasa.commons.constant.DamageLevelType;
import ua.kpi.iasa.commons.constant.RealEstateType;
import ua.kpi.iasa.commons.dto.Address;

@Entity
@Table(name = "application")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Application {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "real_estate_type")
  @Enumerated(EnumType.STRING)
  private RealEstateType realEstateType;

  @Column(name = "premise_square")
  private BigDecimal premiseSquare;

  @Column(name = "address" ,columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private Address address;

  @Column(name = "damage_level")
  @Enumerated(EnumType.STRING)
  private DamageLevelType damageLevelType;

  @Column(name = "is_cultural_heritage")
  private boolean isCulturalHeritage;

  @Column(name = "damage_date_time")
  private LocalDateTime damageDateTime;

  @Column(name = "comments")
  private String comments;

  @Column(name = "creation_datetime")
  @CreationTimestamp
  private LocalDateTime creationDateTime;

}
