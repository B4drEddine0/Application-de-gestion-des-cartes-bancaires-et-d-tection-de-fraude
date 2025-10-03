package Dao;

import java.util.Optional;

import Modal.Operation;

public interface FraudRepository {
    public Optional<Operation> getLastOperationById(int cardId);
}
