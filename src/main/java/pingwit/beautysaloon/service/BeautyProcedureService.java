package pingwit.beautysaloon.service;

import pingwit.beautysaloon.controller.dto.BeautyProcedureDTO;

import java.util.Collection;

public interface BeautyProcedureService {
    BeautyProcedureDTO getProcedureById(Integer id);

    Collection<BeautyProcedureDTO> getAllProcedures();

    Integer createProcedure(BeautyProcedureDTO procedureToCreate);

    BeautyProcedureDTO updateProcedure(Integer id, BeautyProcedureDTO procedureToUpdate);

    void deleteProcedureById(Integer id);
}
