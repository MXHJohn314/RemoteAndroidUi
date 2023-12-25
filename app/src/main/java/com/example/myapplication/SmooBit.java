package com.example.myapplication;

import java.util.Objects;

/**
 * A SmooBit is the unit measurement of a Smoo.
 */
public class SmooBit {
	/**
	 * the left of a SmooBit.
	 */
	public int left;
	/**
	 * the right of a SmooBit.
	 */
	public int right;
	
	/**
	 * @param left makes no sense
	 * @param right it's an example...
	 */
	public SmooBit(int left, int right) {
		this.left = left;
		this.right = right;
	}
	
	@Override public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(!( o instanceof SmooBit)) {
			return false;
		}
		SmooBit smooBit = (SmooBit)o;
		return left == smooBit.left && right == smooBit.right;
	}
	
	@Override public int hashCode() {
		return Objects.hash(left, right);
	}
}
