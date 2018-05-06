package tech.shunzi.domain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Version:v1.0 (description:  ) Date:2018/5/5 0005  Time:10:26
 */
public class Wallet {

	private PrivateKey privateKey;

	private PublicKey publicKey;

	private HashMap<String, TransactionOutput> unspentTransactionOutputs = new HashMap<>();

	public Wallet() {
		generateKeyPairs();
	}

	//returns balance and stores the UTXO's owned by this wallet in this.UTXOs
	public double getBalance() {
		double total = 0;
		for (Map.Entry<String, TransactionOutput> item : BlockChain.unspentTransactionOutputs
				.entrySet()) {
			TransactionOutput output = item.getValue();
			if (output.isMine(publicKey)) {
				unspentTransactionOutputs.put(output.getId(), output);
				total += output.getValue();
			}
		}

		return total;
	}

	//Generates and returns a new transaction from this wallet.
	public Transaction sendFunds(PublicKey _recipient, double value) {

		//gather balance and check funds.
		if (getBalance() < value) {
			System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
			return null;
		}

		//create array list of inputs
		ArrayList<TransactionInput> inputs = new ArrayList<>();

		double total = 0;
		for (Map.Entry<String, TransactionOutput> item : unspentTransactionOutputs.entrySet()) {
			TransactionOutput output = item.getValue();
			total += output.getValue();
			inputs.add(new TransactionInput(output.getId()));
			if(total > value) {
				break;
			}
		}

		Transaction newTransaction = new Transaction(publicKey, _recipient , value, inputs);
		newTransaction.generateSignature(privateKey);
		for (TransactionInput input : inputs) {
			unspentTransactionOutputs.remove(input.getTransactionOutputId());
		}

		return newTransaction;
	}

	public void generateKeyPairs() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
			KeyPair keyPair = keyGen.generateKeyPair();
			// Set the public and private keys from the keyPair
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
}
