package ua.kpi.iasa.invoiceservice.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import ua.kpi.iasa.commons.dto.RepairmentApplicationDto;

@Service
public class CalculationService {

  private static final BigDecimal PARTIAL_MULTIPLIER = BigDecimal.valueOf(0.3);
  private static final BigDecimal MAJOR_MULTIPLIER = BigDecimal.valueOf(0.6);
  private static final BigDecimal COMPLETE_MULTIPLIER = BigDecimal.valueOf(0.9);

  private static final BigDecimal PER_SQUARE = new BigDecimal(40000L);

  public BigDecimal calculateReimbursement(RepairmentApplicationDto request) {
    BigDecimal premiseSquare = request.getPremiseSquare();
    BigDecimal amountForPremise = premiseSquare.multiply(PER_SQUARE);

    switch (request.getDamageLevelType()) {
      case PARTIALLY -> amountForPremise = amountForPremise.multiply(PARTIAL_MULTIPLIER);
      case MAJORLY -> amountForPremise = amountForPremise.multiply(MAJOR_MULTIPLIER);
      case COMPLETELY -> amountForPremise = amountForPremise.multiply(COMPLETE_MULTIPLIER);
    }
    if(request.isCulturalHeritage()) {
      amountForPremise = amountForPremise.multiply(BigDecimal.valueOf(2L));
    }
    return amountForPremise;
  }
}
