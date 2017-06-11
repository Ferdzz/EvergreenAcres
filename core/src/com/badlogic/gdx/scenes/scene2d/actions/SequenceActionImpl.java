package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Pool;

public class SequenceActionImpl extends ParallelAction {
	private int index;

	public SequenceActionImpl () {
	}

	public SequenceActionImpl (Action action1) {
		addAction(action1);
	}

	public SequenceActionImpl (Action action1, Action action2) {
		addAction(action1);
		addAction(action2);
	}

	public SequenceActionImpl (Action action1, Action action2, Action action3) {
		addAction(action1);
		addAction(action2);
		addAction(action3);
	}

	public SequenceActionImpl (Action action1, Action action2, Action action3, Action action4) {
		addAction(action1);
		addAction(action2);
		addAction(action3);
		addAction(action4);
	}

	public SequenceActionImpl (Action action1, Action action2, Action action3, Action action4, Action action5) {
		addAction(action1);
		addAction(action2);
		addAction(action3);
		addAction(action4);
		addAction(action5);
	}

	@Override
	public boolean act(float delta) {
		if (index >= actions.size) return true;
		Pool<?> pool = getPool();
		setPool(null); // Ensure this action can't be returned to the pool while executings.
		try {
			if (actions.get(index).act(delta)) {
				index++;
				if (index >= actions.size) return true;
			}
			return false;
		} finally {
			setPool(pool);
		}
	}
	
	public Action getCurrentAction() {
		return actions.get(index);
	}
	
	@Override
	public void reset() {
		super.reset();
		this.index = 0;
		for (Action action : actions) {
			action.reset();
		}
	}
}
