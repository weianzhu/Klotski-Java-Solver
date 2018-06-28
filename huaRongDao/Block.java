package huaRongDao;

public class Block {
	public BlockPrototype prototype;
	//public int xPos; //We could store the positions as instance variables but we don't really need them to solve the puzzle
	//public int yPos;
	long bitImage;
	
	public Block (BlockPrototype prototype, int xPos, int yPos) {
		this.prototype = prototype;
		bitImage = prototype.bitImage << (63 - Long.toBinaryString(prototype.bitImage).length() - (14 * yPos + 2 * xPos));
		/*PrintUtility.printID(image);*/
		
	}
	
	public Block (Block oldBlock, MoveType moveType) {
		this.prototype = oldBlock.prototype;
		bitImage = oldBlock.getMovedImage(moveType);

	}
	
	public Block (Block oldBlock, long newBlockImage) {
		this.prototype = oldBlock.prototype;
		bitImage = newBlockImage;

	}
	
	int xPos() {
		return (63 - Long.toBinaryString(bitImage).length())%14/2 ;
	}
	
	int yPos() {
		return (63 - Long.toBinaryString(bitImage).length())/14;
	}
	
	public long getMovedImage(MoveType moveType) {
		switch (moveType) {
			case LEFT:
				return bitImage << 2;
			case RIGHT:
				return bitImage >>> 2;
			case UP:
				return bitImage << 14;
			case DOWN:
				return bitImage >>> 14;
			case LEFT2:
				return bitImage << 4;
			case RIGHT2:
				return bitImage >>> 4;
			case UP2:
				return bitImage << 28;
			case DOWN2:
				return bitImage >>> 28;
			case LEFTUP:
			case UPLEFT:
				return bitImage << 16;
			case LEFTDOWN:
			case DOWNLEFT:
				return bitImage >>> 12;
			case RIGHTUP:
			case UPRIGHT:
				return bitImage << 12;
			case RIGHTDOWN:
			case DOWNRIGHT:
				return bitImage >>> 16;
			default:
				return 0L;
		}
	}
	
	
	
}
