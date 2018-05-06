package tech.shunzi.domain;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import tech.shunzi.util.StringUtils;

/**
 * Version:v1.0 (description:  ) Date:2018/5/5 0005  Time:12:27
 */
public class Transaction {

	private String transactionId;

	private PublicKey sender;

	private PublicKey recipient;

	private double value;

	private byte[] signature;

	private List<TransactionInput> inputs;

	private List<TransactionOutput> outputs;

	private static int sequence = 0;

	public Transaction(PublicKey sender, PublicKey recipient, double value,
			List<TransactionInput> inputs) {
		this.sender = sender;
		this.recipient = recipient;
		this.value = value;
		this.inputs = inputs;
	}

	public String generateHash() {
		sequence++;
		return StringUtils.encodeBySha256(
				StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(recipient)
						+ Double.toString(value) + sequence);
	}

	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(recipient)
				+ Double.toString(value);
		this.signature = StringUtils.generateECDSASignature(privateKey, data);
	}

	public boolean verifySignature() {
		String data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(recipient)
				+ Double.toString(value);
		return StringUtils.verifyECDSASignature(sender, data, signature);
	}

	public boolean processTransaction() {

		if (!verifySignature()) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}

		//gather transaction inputs (Make sure they are unspent):
		for (TransactionInput input : getInputs()) {
			input.setUnspentTransactionOutput(
					BlockChain.unspentTransactionOutputs.get(input.getTransactionOutputId()));
		}

		//check if transaction is valid:
		if (getInputValue() < BlockChain.minimumTransaction) {
			System.out.println("#Transaction Inputs to small: " + getInputValue());
			return false;
		}

		//generate transaction outputs:
		double leftOver = getOutputValue() - value;//get value of inputs then the left over change:
		transactionId = generateHash();
		outputs.add(new TransactionOutput(this.recipient, value,
				transactionId));//send value to recipient
		outputs.add(new TransactionOutput(this.sender, leftOver,
				transactionId));//send the left over change back to sender

		//add outputs to Unspent list
		for (TransactionOutput output : getOutputs()) {
			BlockChain.unspentTransactionOutputs.put(output.getId(), output);
		}

		//remove transaction inputs from UTXO lists as spent:
		for(TransactionInput input : getInputs()) {
			if(null == input.getUnspentTransactionOutput()) {
				BlockChain.unspentTransactionOutputs.remove(input.getUnspentTransactionOutput().getId());
			}
		}
		return true;

	}

	public double getInputValue() {
		double total = 0;
		for (TransactionInput input : inputs) {
			if (null == input.getUnspentTransactionOutput()) {
				continue;
			}
			total += input.getUnspentTransactionOutput().getValue();
		}
		return total;
	}

	public double getOutputValue() {
		double total = 0;
		for (TransactionOutput output : outputs) {
			total += output.getValue();
		}
		return value;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public PublicKey getSender() {
		return sender;
	}

	public void setSender(PublicKey sender) {
		this.sender = sender;
	}

	public PublicKey getRecipient() {
		return recipient;
	}

	public void setRecipient(PublicKey recipient) {
		this.recipient = recipient;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public List<TransactionInput> getInputs() {
		if(null == inputs) {
			inputs = new ArrayList<>();
		}
		return inputs;
	}

	public void setInputs(List<TransactionInput> inputs) {
		this.inputs = inputs;
	}

	public List<TransactionOutput> getOutputs() {
		if(null == outputs)  {
			outputs = new ArrayList<>();
		}
		return outputs;
	}

	public void setOutputs(List<TransactionOutput> outputs) {
		this.outputs = outputs;
	}

	public static int getSequence() {
		return sequence;
	}

	public static void setSequence(int sequence) {
		Transaction.sequence = sequence;
	}
}
