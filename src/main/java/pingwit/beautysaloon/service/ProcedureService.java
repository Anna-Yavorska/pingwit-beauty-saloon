package pingwit.beautysaloon.service;

import pingwit.beautysaloon.controller.dto.ProcedureDTO;

import java.util.Collection;

public interface ProcedureService {

    ProcedureDTO getProcedureById(Integer id);

    Collection<ProcedureDTO> getAllProcedures();

    Integer createProcedure(ProcedureDTO procedureToCreate);

    ProcedureDTO updateProcedure(Integer id, ProcedureDTO procedureToUpdate);

    void deleteProcedureById(Integer id);
}
