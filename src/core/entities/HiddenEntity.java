package core.entities;

public abstract class HiddenEntity<T extends Entity> extends Entity {

	protected T hiddenEntity;
	
	public HiddenEntity(int id) {
		super(id);
	}
	
	public HiddenEntity(String s, int id) {
		super(s, id);
	}
	
	public boolean isHidingEntity() {
		return hiddenEntity != null;
	}
	
	public boolean hideEntity(T entity) {
		if (hiddenEntity != null) {
			return false;
		}
		hiddenEntity = entity;
		return true;
	}
	
	public T unhideEntity() {
		T temp = hiddenEntity;
		hiddenEntity = null;
		return temp;
	}
	
	public T getHiddenEntity() {
		return hiddenEntity;
	}

}
