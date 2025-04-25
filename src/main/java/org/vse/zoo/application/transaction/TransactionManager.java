package org.vse.zoo.application.transaction;

public interface TransactionManager {
    Transaction createReadTx();

    Transaction createWriteTx();
}
