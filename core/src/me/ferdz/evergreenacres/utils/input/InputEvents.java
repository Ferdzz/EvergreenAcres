package me.ferdz.evergreenacres.utils.input;

import lombok.Getter;

public class InputEvents {
	public static class LeftClickEvent {
		@Getter private int x, y;
		public LeftClickEvent(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static class RightClickEvent {
		@Getter private int x, y;
		public RightClickEvent(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public static class ScrollEvent {
		@Getter private int amount;
		public ScrollEvent(int amount) {
			this.amount = amount;
		}
	}
	
	public static class KeyPressedEvent {
		@Getter private int key;
		public KeyPressedEvent(int key) {
			this.key = key;
		}
	}
}
