package huaRongDao2;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;

public class Block {
	public BlockPrototype prototype;
	public int xPos; // from Board.MINPOS to Board.MAXPOS
	public int yPos;
	private String hashString;
	private int hashCode;
	private boolean hashCodeCalculated = false;
	static Comparator<Block> blockComparator = new Comparator<Block>() {

		@Override
		public int compare(Block a, Block b) {
		       String aSortCriteria = a.hashString();
		       String bSortCriteria = b.hashString();
	
		       return aSortCriteria.compareTo(bSortCriteria);
		    }
	};
	
	 Block (BlockPrototype blockPrototype, int xPos, int yPos) {
		this.prototype = blockPrototype;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	

	
	 Block(Block block, int deltaXPos, int deltaYPos) {
		this.prototype = block.prototype;
		this.xPos = block.xPos + deltaXPos;
		this.yPos = block.yPos + deltaYPos;
	}
	
	


	
	public String hashString() {
		if (hashString == null) {
			hashString = new StringBuilder(prototype.blockType.toString()).append(xPos).append(yPos).toString();
		}
		return hashString;
		
	}
	
	@Override
	public int hashCode() {
		if (!hashCodeCalculated) {
			hashCode = hashString().hashCode();
			hashCodeCalculated = true;
		}
		return hashCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (hashString().equals(((Block)obj).hashString())) {
			return true;
		}
		return false;
	}
	
}

