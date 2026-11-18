package net.rapsac.doubleclickspersecond;

import java.util.LinkedList;

public class InputHandler {
	private static final LinkedList<Long> clicks = new LinkedList<>();
	public static boolean WasDouble = false;
	public static boolean IsDouble = false;

	public static void Clicking() {
		long expiry = System.currentTimeMillis() + 1000;
		SetDoubleClick(expiry);
		clicks.add(expiry);
	}

	public static void SetDoubleClick(long expiry) {
		if (IsDouble) {
			WasDouble = true;
			IsDouble = false;
			return;
		}
		if (WasDouble) {
			WasDouble = false;
		}
		if (!clicks.isEmpty() && expiry - clicks.getLast() < 55) {
			IsDouble = true;
		}
	}

	public static int Cps() {
		while (!clicks.isEmpty() && clicks.peekFirst() < System.currentTimeMillis()) {
			clicks.remove();
		}
		return clicks.size();
	}

	public static boolean WasLastDouble() {
		return WasDouble || IsDouble;
	}
}
