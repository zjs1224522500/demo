package tech.shunzi.domain;

import java.security.PublicKey;

import tech.shunzi.util.StringUtils;

/**
 * Version:v1.0 (description:  ) Date:2018/5/5 0005  Time:12:31
 */
public class TransactionOutput {

	private String id;

	//also known as the new owner of these coins.
	private PublicKey recipient;

	//the amount of coins they own
	private double value;

	//the id of the transaction this output was created in
	private String parentTransactionId;

	//Constructor
	public TransactionOutput(PublicKey recipient, double value, String parentTransactionId) {
		this.recipient = recipient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		this.id = StringUtils.encodeBySha256(
				StringUtils.getStringFromKey(recipient) + Double.toString(value)
						+ parentTransactionId);
	}

	//Check if coin belongs to you
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == recipient);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getParentTransactionId() {
		return parentTransactionId;
	}

	public void setParentTransactionId(String parentTransactionId) {
		this.parentTransactionId = parentTransactionId;
	}
}
