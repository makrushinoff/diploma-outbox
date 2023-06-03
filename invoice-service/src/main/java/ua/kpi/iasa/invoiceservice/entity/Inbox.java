package ua.kpi.iasa.invoiceservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "inbox")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inbox {

  @Id
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
