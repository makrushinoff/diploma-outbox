package ua.kpi.iasa.invoiceservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "invoice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "application_id")
  private Long applicationId;

  @Column(name = "creation_datetime")
  @CreationTimestamp
  private LocalDateTime creationDateTime;

  @Column(name = "reimbursement_amount")
  private BigDecimal reimbursementAmount;

  @Column(name = "user_id")
  private Long userId;
}
