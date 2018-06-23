package huaRongDao;

public class Block {
	public BlockPrototype prototype;
	//public int xPos; //We could store the positions as instance variables but we don't really need them to solve the puzzle
	//public int yPos;
	long image;
	
	public Block (BlockPrototype prototype, int xPos, int yPos) {
		this.prototype = prototype;
		image = prototype.image << (63 - Long.toBinaryString(prototype.image).length() - (14 * yPos + 2 * xPos));
		/*PrintUtility.printID(image);*/
		
	}
	
	public Block (Block oldBlock, MoveType moveType) {
		this.prototype = oldBlock.prototype;
		image = oldBlock.getMovedImage(moveType);

	}
	
	public Block (Block oldBlock, long newBlockImage) {
		this.prototype = oldBlock.prototype;
		image = newBlockImage;

	}
	
	int xPos() {
		return (63 - Long.toBinaryString(image).length())%14/2 ;
	}
	
	int yPos() {
		return (63 - Long.toBinaryString(image).length())/14;
	}
	
	public long getMovedImage(MoveType moveType) {
		switch (moveType) {
			case LEFT:
				return image << 2;
			case RIGHT:
				return image >>> 2;
			case UP:
				return image << 14;
			case DOWN:
				return image >>> 14;
			case LEFT2:
				return image << 4;
			case RIGHT2:
				return image >>> 4;
			case UP2:
				return image << 28;
			case DOWN2:
				return image >>> 28;
			case LEFTUP:
			case UPLEFT:
				return image << 16;
			case LEFTDOWN:
			case DOWNLEFT:
				return image >>> 12;
			case RIGHTUP:
			case UPRIGHT:
				return image << 12;
			case RIGHTDOWN:
			case DOWNRIGHT:
				return image >>> 16;
			default:
				return 0L;
		}
	}
	
	
	
}
