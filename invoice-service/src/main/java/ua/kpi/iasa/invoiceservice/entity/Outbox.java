package ua.kpi.iasa.invoiceservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "outbox")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Outbox {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @JdbcTypeCode(SqlTypes.UUID)
  private UUID id;

  @Column(name = "queue_name")
  private String queueName;

  @Column(name = "message")
  @JdbcTypeCode(SqlTypes.LONGNVARCHAR)
  private String message;

  @Column(name = "time")
  private LocalDateTime time;

}
