/*
* 3D File Manager - Project Looking Glass 
* Copyright Sun Microsystems, 2005
* 
* Project Course in Human-Computer Interaction
* Carnegie Mellon University
* 
* Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
*/


package edu.cmu.sun.folds;



/**
 * This represents a range of indexes.
 * The range can have a size of 0, which means that it is empty.
 * The range also return the type of overlap and distance that exists between index ranges. 
 * 
 * Note, the IndexRange object is mutable.
 * @author Braden Kowitz
 */
public class IndexRange {

	/**
	 * Position of the start of the range.
	 */
	protected int position;
	
	/**
	 * The size of the range, in number of items.
	 */
	protected int size;
	
	/**
	 * Create a new IndexRange
	 * @param position position of the start of the range
	 * @param size size of the range in number of items
	 */
	public IndexRange(int position, int size)
	{
		this.position = position;
		this.size = size;
		
		if (this.position < 0) this.position = 0;
		if (this.size < 0) this.size = 0;
	}
	
	/**
	 * Merges this range with the other range
	 * this function takes the lowest start, and the highest end,
	 * and sets this index range to encompas both
	 * @param ir index range to merge with.
	 */
	public void merge(IndexRange ir)
	{
		// get the lowest start.
		int start = getStart();
		if (ir.getStart() < start) start = ir.getStart();
		
		// get the greatest end
		int end = getEnd();
		if (ir.getEnd() > end) end = ir.getEnd();
		
		// set the position and size:
		position = start;
		size = end - start + 1;
	}
	
	public IndexRange(IndexRange range) {
		this(range.position, range.size);
	}

	/**
	 * @return the number of elements in this range
	 */
	public int size()
	{
		return size;
	}
	
	/**
	 * position is the place where this range starts.
	 * for the list: 0,2,3,4,5,6 <br>
	 * a position of 1 and a length of 2 includes: <br>
	 * the elements: 2,3
	 */
	public int position()
	{
		return position;
	}
	
	/**
	 * @return the first index in the range, or -1 if the size = 0
	 */
	public int getStart()
	{
		if (size == 0) return -1;
		return position;
	}
	
	/**
	 * @return the last index in the range, or -1 if the size = 0
	 */
	public int getEnd()
	{
		if (size == 0) return -1;
		return position + size - 1;
	}
	
	/**
	 * Determines the overlap between two ranges.
	 * @param r1 One range to use.
	 * @param r2 Another range to use.
	 * @return the overlap that exists between the two ranges.
	 */
	public static Overlap getOverlap(IndexRange r1, IndexRange r2)
	{
		// overlap does not work on empty ranges.
		if (r1.size == 0 || r2.size == 0)
		{
			System.err.println("Error: Can't find overlap for empty ranges");
			return null;
		}
		
		// detect exact matches
		if (r1.position == r2.position && r1.size == r2.size)
		{
				return new Overlap(Overlap.Type.EXACT_MATCH, 0);
		}
		
		// detect total overlap when r1 encompases r2
		if (r1.size > r2.size && r1.getStart() <= r2.getStart() && r1.getEnd() >= r2.getEnd())
		{
			int distance = (r2.getStart() - r1.getStart()) + (r1.getEnd() - r2.getEnd());
			return new Overlap(Overlap.Type.TOTAL_OVERLAP, distance);
		}
		
		// detect total overlap when r2 encompases r1
		if (r2.size > r1.size && r2.getStart() <= r1.getStart() && r2.getEnd() >= r1.getEnd())
		{
			int distance = (r1.getStart() - r2.getStart()) + (r2.getEnd() - r1.getEnd());
			return new Overlap(Overlap.Type.TOTAL_OVERLAP, distance);
		}
		
		// detect partial overlap with r1 before r2
		if (r1.getStart() < r2.getStart() && r2.getStart() <= r1.getEnd() && r1.getEnd() < r2.getEnd())
		{
			int distance = (r2.getStart() - r1.getStart()) + (r2.getEnd() - r1.getEnd());
			return new Overlap(Overlap.Type.PARTIAL_OVERLAP, distance);
		}
		// detect partial overlap with r2 before r1
		if (r2.getStart() < r1.getStart() && r1.getStart() <= r2.getEnd() && r2.getEnd() < r1.getEnd())
		{
			int distance = (r1.getStart() - r2.getStart()) + (r1.getEnd() - r2.getEnd());
			return new Overlap(Overlap.Type.PARTIAL_OVERLAP, distance);
		}
		
		// the ranges must be disjoint:
		if (r1.getEnd() < r2.getStart())
		{
			int distance = r2.getStart() - r1.getEnd() - 1;
			return new Overlap(Overlap.Type.DISJOINT, distance);
		}
		if (r2.getEnd() < r1.getStart())
		{
			int distance = r1.getStart() - r2.getEnd() - 1;
			return new Overlap(Overlap.Type.DISJOINT, distance);
		}
		
		// All cases should have been caught!
		return null;
	}
	
	/**
	 * Get the overlap between this range and another.
	 * @param r a range to compare with this range.
	 * @return The overlap that exists between this range and the one provided.
	 */
	public Overlap getOverlap(IndexRange r)
	{
		return getOverlap(this, r);
	}
	
	
	/**
	 *  An overlap describes the type and distance of overlap between two ranges.
	 *  
	 *  There are four basic types of matches between range A and B
	 *  <ul>
	 *  <li>EXACT_MATCH, the set of items in A matches the set of items in B
	 *  <li>TOTAL_OVERLAP, eithre set of items in A encompases the set of items in B,
	 *  or, the set of items in B encompas the set of items in A.
	 *  <li>PARTIAL_OVERLAP, sets A and B share some items, but neither encompases each otehr.
	 *  <li>DISJOINT, The two sets do not share any items. 
	 *  </ul>
	 *  
	 *  <br><br>
	 *  Distance is also calculated for each of the overlaps:
	 *  <ul>
	 *  <li>EXACT_MATCH, distance is always zero.
	 *  <li>TOTAL_OVERLAP, distance is the number of items that are not encompassed by both sets.
	 *  <li>PARTIAL_OVERLAP, distance is the number of items that are not encompassed by both sets. 
	 *  <li>DISJOINT, The number of items that exist between the two ranges.
	 *  </ul>*  
	 *  
	 */ 
	static public class Overlap
	{
		public enum Type {EXACT_MATCH, TOTAL_OVERLAP, PARTIAL_OVERLAP, DISJOINT};

		protected Type type;
		protected int distance;
		
		public Overlap(Type type, int distance)
		{
			this.type = type;
			this.distance = distance;
		}
		
		public Type getType()
		{
			return type;
		}
		
		public int getDistance()
		{
			return distance;
		}
	}

	/**
	 * @param p index to check
	 * @return True if P is within the range, False otherwise.
	 */
	public boolean encompassesIndex(int p) {
		if (p < 0) return false;
		if (size == 0) return false;
		return p >= getStart() && p <= getEnd();
	}
	
	/**
	 * For debugging only.
	 */
	public String toString()
	{
		return "<p=" + position + " s=" + size + ">";
	}

	/**
	 * Set the position for this range
	 * @param position new position.
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * set the size for this range.
	 * @param size new size.
	 */
	public void setSize(int size) {
		this.size = size;
	}
}
