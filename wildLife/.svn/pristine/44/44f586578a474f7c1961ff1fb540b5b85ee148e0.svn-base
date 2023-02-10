package com.globits.wl.dto.functiondto;

public class KeyDto<T,D> {
	private T x;
    private D y;

    public T getX() {
		return x;
	}

	public void setX(T x) {
		this.x = x;
	}

	public D getY() {
		return y;
	}

	public void setY(D y) {
		this.y = y;
	}

	public KeyDto(T x, D y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyDto)) return false;
        KeyDto key = (KeyDto) o;
        return x == key.x && y == key.y;
    }
    
    @Override
    public int hashCode() {
        int result = x.hashCode();
        result += y.hashCode();
        return result;
    }
}
