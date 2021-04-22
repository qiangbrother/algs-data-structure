package com.jack.javabase.thread.reentrantlock;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: 模拟blockingQueue队列
 * @Author: ZhangQiang
 * @CreateDate: 2020/10/20 11:01
 */
public class BlockingQueueDemo<E> {

	int size;

	LinkedList<E> list = new LinkedList<>();

	ReentrantLock lock = new ReentrantLock();
	/**
	 * 当队列满的时候
	 */
	Condition notFull = lock.newCondition();
	/**
	 * 队列为空时
	 */
	Condition notEmpty = lock.newCondition();

	public BlockingQueueDemo(int size) {
		this.size = size;
	}

	/**
	 * 添加数据到队列中 生产者
	 *
	 * @param e
	 */
	public void enqueue(E e) {
		lock.lock();
		try {
			if (list.size() == size) {
				notFull.await();
			}
			list.add(e);
			System.out.println("入队：" + e);
			notEmpty.signal();
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 从队列中消费数据 消费者
	 *
	 * @return
	 */
	public E dqueue() throws InterruptedException {
		E e;
		lock.lock();
		try {
			if (list.size() == 0) {
				notEmpty.await();
			}
			e = list.removeFirst();
			System.out.println("出队：" + e);
			notFull.signal();
			return e;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		BlockingQueueDemo<Integer> queue = new BlockingQueueDemo<>(3);
		for (int i=0;i<10;i++){
			int data =i;
			new Thread(()->{
				queue.enqueue(data);
			}).start();
		}

		for (int i=0;i<10;i++){
			new Thread(()->{
				try {
					queue.dqueue();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
	}


}