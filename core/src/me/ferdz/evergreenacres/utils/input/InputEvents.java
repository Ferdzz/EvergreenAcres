package me.ferdz.evergreenacres.utils.input;

public class InputEvents {
	public static class LeftClickEvent {
		private int x, y;
		public LeftClickEvent(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
	}
	
	public static class RightClickEvent {
		private int x, y;
		public RightClickEvent(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
	}
	
	public static class ScrollEvent {
		private int amount;
		public ScrollEvent(int amount) {
			this.amount = amount;
		}
		
		public int getAmount() {
			return this.amount;
		}
	}
	
	public static class KeyPressedEvent {
		private int key;
		public KeyPressedEvent(int key) {
			this.key = key;
		}
		
		public int getKey() {
			return this.key;
		}
	}
}
