package com.example.myapplication;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;


/**
 * Example class to showcase BitPacker.
 */
public class Wumpus {
	private static final Random R = new Random();
	/**
	 * We currently treat this as a string, even though it comes from an enum.
	 * We should transmit fewer data by sending the enum ordinal, and using that
	 * to discern how to create the value we need.
	 */
	public WumpType wumpType;
	
	/**
	 * 	 * 7-bits needed to hold one of 95 printable chars.
	 * 	 * 22 chars max in an ID.
	 * 	 * 7*22=154 bits = 20 bytes vs 44 bytes.
	 */	
	public String id;
	
	@Override public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(!( o instanceof Wumpus)) {
			return false;
		}
		Wumpus wumpus = (Wumpus)o;
		return angle == wumpus.angle && wumpType == wumpus.wumpType &&
		 Objects.equals(id, wumpus.id) && Objects.equals(mid, wumpus.mid) &&
		 Arrays.equals(homeSquare, wumpus.homeSquare);
	}
	
	@Override public int hashCode() {
		int result = Objects.hash(wumpType, id, mid, angle);
		result = 31 * result + Arrays.hashCode(homeSquare);
		return result;
	}
	
	/**
	 * 4*7-bit packs for a point
	 * */
	public SmooBit mid;
	/**
	 * 16*7-bit packs for 4 points
	 * */
	public SmooBit[] homeSquare;
	/**
	 * 1-bit to represent a boolean
	 * */
	public int angle;
	
	/**
	 * @param homeSquare the bounds of a Wumpus.
	 * @param mid the center of a Wumpus' home.
	 * @param wumpType a Wumpus' birth wumpType.
	 * @param id a Wumpus' state-issued ID number as a string.
	 */
	public Wumpus(SmooBit[] homeSquare, SmooBit mid, WumpType wumpType, String id, short angle) {
		this.wumpType = wumpType;
		this.mid = mid;
		this.homeSquare = homeSquare;
		this.id = id;
		this.angle = angle;
	}
	
	/**
	 * Randomly generate a Wumpus.
	 */
	public Wumpus() {
		this.angle = R.nextInt(360);
		this.mid = new SmooBit(R.nextInt(20001) + 1, R.nextInt(20001) + 1);
		this.homeSquare = new SmooBit[] {
		 new SmooBit(R.nextInt(20001) + 1, R.nextInt(20001) + 1),
		 new SmooBit(R.nextInt(20001) + 1, R.nextInt(20001) + 1),
		 new SmooBit(R.nextInt(20001) + 1, R.nextInt(20001) + 1),
		 new SmooBit(R.nextInt(20001) + 1, R.nextInt(20001) + 1),
		 };
		this.wumpType = WumpType.values()[R.nextInt(WumpType.values().length)];
		this.id = R.ints(22).toString();
	}
}

