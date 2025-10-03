package Dao;

import java.util.Optional;

import Modal.Operation;

public interface OperationRepository {
    public void saveOperation(Operation operation);
    // Optional<Operation> findOperationByClientId(int clientId);
    // Optional<Operation> findOperationById(int operationId);
    // Optional<Operation> getOperationsByCardId(int cardId);
}
