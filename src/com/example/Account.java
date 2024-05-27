package com.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	private Lock lock = new ReentrantLock();
	double balance;
	int id;

	public Account(int id, double balance) {
		this.balance = balance;
		this.id = id;
	}

	void debit(double amount) {
		balance -= amount;
	}

	void credit(double amount) {
		balance += amount;
	}

	public Lock getLock() {
		return lock;
	}

	public void setLock(Lock lock) {
		this.lock = new ReentrantLock();
	}
	
	
}
