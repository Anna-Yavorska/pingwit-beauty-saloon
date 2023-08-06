package pingwit.beautysaloon.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pingwit.beautysaloon.controller.dto.ProcedureDTO;
import pingwit.beautysaloon.exception.ValidationException;
import pingwit.beautysaloon.service.MasterService;
import pingwit.beautysaloon.service.PriceService;
import pingwit.beautysaloon.service.ProcedureService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PriceServiceImpl implements PriceService {
    private static final BigDecimal MIDDLE_PROF_COEFFICIENT = new BigDecimal("1.2");
    private static final BigDecimal SENIOR_PROF_COEFFICIENT = new BigDecimal("1.3");
    private final List<String> violations = new ArrayList<>();
    @Value("${beautySaloon.baseRate}")
    private BigDecimal baseRate;

    private final MasterService masterService;
    private final ProcedureService procedureService;

    public PriceServiceImpl(MasterService masterService, ProcedureService procedureService) {
        this.masterService = masterService;
        this.procedureService = procedureService;
    }

    @Override
    public BigDecimal calculatePrice(Integer masterId, Integer procedureId) {
        Collection<ProcedureDTO> mastersProcedures = masterService.getMasterById(masterId).getProcedures();
        Set<Integer> procedures = mastersProcedures.stream()
                .map(ProcedureDTO::getId)
                .collect(Collectors.toSet());
        BigDecimal time = procedureService.getProcedureById(procedureId).getTime();
        BigDecimal price = null;
        String profLevel = masterService.getMasterById(masterId).getProfLevel();

        if (procedures.contains(procedureId)) {
            if (profLevel.equals("basic")) {
                price = baseRate.multiply(time);
            } else if (profLevel.equals("middle")) {
                price = baseRate.multiply(time).multiply(MIDDLE_PROF_COEFFICIENT);
            } else {
                price = baseRate.multiply(time).multiply(SENIOR_PROF_COEFFICIENT);
            }
        } else {
            violations.add(String.format("master '%s' does not do procedure '%s'", masterService.getMasterById(masterId).getName(), procedureService.getProcedureById(procedureId).getName()));
            throw new ValidationException("Calculate price is impossible", violations);
        }
        return price.setScale(2, RoundingMode.HALF_UP);
    }
}
