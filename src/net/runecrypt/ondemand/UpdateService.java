package net.runecrypt.ondemand;

import java.util.ArrayDeque;
import java.util.Queue;

import net.runecrypt.codec.session.impl.UpdateSession;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public final class UpdateService implements Runnable {

	private final Queue<UpdateSession> pendingSessions = new ArrayDeque<>(); 

	public void addPendingSession(UpdateSession session) {
		synchronized (pendingSessions) {
			pendingSessions.add(session);
			pendingSessions.notifyAll();
		}
	}

	@Override
	public void run() {
		for (;;) {
			UpdateSession session;

			synchronized (pendingSessions) {
				while ((session = pendingSessions.poll()) == null) {
					try {
						pendingSessions.wait();
					} catch (InterruptedException e) {
						/* ignore */
					}
				}
			}

			session.processFileQueue();
		}
	}

}
