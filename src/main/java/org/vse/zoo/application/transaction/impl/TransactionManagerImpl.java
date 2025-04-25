package org.vse.zoo.application.transaction.impl;

import org.vse.zoo.application.transaction.Transaction;
import org.vse.zoo.application.transaction.TransactionManager;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TransactionManagerImpl implements TransactionManager {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ThreadLocal<Transaction> readTxs = ThreadLocal.withInitial(ReadTx::new);
    private final ThreadLocal<Transaction> writeTxs = ThreadLocal.withInitial(WriteTx::new);

    @Override
    public Transaction createReadTx() {
        return readTxs.get();
    }

    @Override
    public Transaction createWriteTx() {
        return writeTxs.get();
    }

    private class WriteTx implements Transaction {
        private final ReentrantReadWriteLock.WriteLock writeLock;
        private boolean isLocked;

        WriteTx() {
            writeLock = lock.writeLock();
        }

        @Override
        public void begin() {
            if (!isLocked) {
                writeLock.lock();
                isLocked = true;
            }
        }

        @Override
        public void end() {
            if (isLocked) {
                writeLock.unlock();
                isLocked = false;
            }
        }
    }

    private class ReadTx implements Transaction {
        private final ReentrantReadWriteLock.ReadLock readLock;
        private boolean isLocked;

        ReadTx() {
            readLock = lock.readLock();
        }

        @Override
        public void begin() {
            if (!isLocked) {
                readLock.lock();
                isLocked = true;
            }
        }

        @Override
        public void end() {
            if (isLocked) {
                readLock.unlock();
                isLocked = false;
            }
        }
    }
}
