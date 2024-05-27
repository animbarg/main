package com.example;

import java.util.Scanner;

public class MultiThreadingExample {

	public static void transfer(Account from, Account to, int amount) {

		// This is Nested Lock which may Lead to Deadlock if working with 2 threads if
		// we call method below 1ST way
		synchronized (from) {
			synchronized (to) {
				from.debit(amount);
				to.credit(amount);

			}
		}

		// Solution 1: Rearrange above code like below
		synchronized (from) {
			from.debit(amount);
		}
		synchronized (to) {
			to.credit(amount);
		}

		// Solution 1:END

		// Solution 2: Start
		while (true) {
			if (from.getLock().tryLock()) {
				try {
					if (to.getLock().tryLock()) {
						try {
							from.debit(amount);
							to.credit(amount);
							break;
						} finally {
							to.getLock().unlock();
						}
					}
				} finally {
					from.getLock().unlock();
				}
				Scanner number = new Scanner(System.in);
				int n = number.nextInt(1000);
				int TIME = 1000 + n; // 1 second + random delay to prevent livelock
				try {
					Thread.sleep(TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// Solution 2: End
		
	}

	public static void main(String[] args) {

		final Account from = new Account(1, 1000);
		final Account to = new Account(2, 300);

		// 1st way start
		Thread a = new Thread() {
			public void run() {
				transfer(from, to, 200);
			}
		};
		Thread b = new Thread() {
			public void run() {
				transfer(to, from, 300);
			}
		};
		// 1st way end
		a.start();
		b.start();
	}

}
