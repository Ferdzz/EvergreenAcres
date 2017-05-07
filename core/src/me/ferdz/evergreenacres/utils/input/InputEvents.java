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
}
