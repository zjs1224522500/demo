package tech.shunzi.domain;

/**
 * Version:v1.0 (description:  ) Date:2018/5/5 0005  Time:12:31
 */
public class TransactionInput {

	//Reference to TransactionOutputs -> transactionId
	private String transactionOutputId;

	//Contains the Unspent transaction output
	private TransactionOutput unspentTransactionOutput;

	public TransactionInput(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}

	public String getTransactionOutputId() {
		return transactionOutputId;
	}

	public void setTransactionOutputId(String transactionOutputId) {
		this.transactionOutputId = transactionOutputId;
	}

	public TransactionOutput getUnspentTransactionOutput() {
		return unspentTransactionOutput;
	}

	public void setUnspentTransactionOutput(TransactionOutput unspentTransactionOutput) {
		this.unspentTransactionOutput = unspentTransactionOutput;
	}
}
