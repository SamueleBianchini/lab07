package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private final static int ACCEPTABLE_MESSAGE_LENGTH = 10;
    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private StrictBankAccount bankAccount;
    private double amount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
        this.amount = 0;
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(amount, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        this.bankAccount.deposit(mRossi.getUserID(), 100);
        this.bankAccount.chargeManagementFees(mRossi.getUserID());
        this.amount = 100-0.1-5;
        assertEquals(this.amount, this.bankAccount.getBalance());
        assertEquals(0, this.bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try{
            this.bankAccount.withdraw(mRossi.getUserID(), -10);
        } catch (final IllegalArgumentException e){
            assertEquals(amount, bankAccount.getBalance());
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
            assertTrue(e.getMessage().length() >= ACCEPTABLE_MESSAGE_LENGTH); // A message with a decent length
        }

    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try{
            this.bankAccount.withdraw(mRossi.getUserID(), 10000);
        } catch (final IllegalArgumentException e){
            assertEquals(amount, bankAccount.getBalance());
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
            assertTrue(e.getMessage().length() >= ACCEPTABLE_MESSAGE_LENGTH); // A message with a decent length
        }
    }
}
