package huaRongDao;

public class Move {
	public Board oldBoard;
	public Block oldBlock;
	public Block newBlock;
	public MoveType moveType;
	public int deltaXPos;
	public int deltaYPos;


	
	public Move(Board oldBoard, Block oldBlock, int deltaXPos, int deltaYPos, Block newBlock) {
		this.oldBoard = oldBoard;
		this.oldBlock = oldBlock;
		this.newBlock = newBlock;
		this.deltaXPos = deltaXPos;
		this.deltaYPos = deltaYPos;
		if (deltaXPos == 0 && deltaYPos == -1) {
			this.moveType = MoveType.UP;
		}
		else if (deltaXPos == 0 && deltaYPos == 1) {
			this.moveType = MoveType.DOWN;
		}
		else if (deltaXPos == -1 && deltaYPos == 0) {
			this.moveType = MoveType.LEFT;
		}
		else if (deltaXPos == 1 && deltaYPos == 0) {
			this.moveType = MoveType.RIGHT;
		}
		else if (deltaXPos == 0 && deltaYPos == -2) {
			this.moveType = MoveType.UP2;
		}
		else if (deltaXPos == 0 && deltaYPos == 2) {
			this.moveType = MoveType.DOWN2;
		}
		else if (deltaXPos == -2 && deltaYPos == 0) {
			this.moveType = MoveType.LEFT2;
		}
		else if (deltaXPos == 2 && deltaYPos == 0) {
			this.moveType = MoveType.RIGHT2;
		}
		else if (deltaXPos == -1 && deltaYPos == -1) {
			if ((oldBoard.getValidMovesMask(MoveType.LEFTUP) & oldBlock.image) == oldBlock.image) {
					this.moveType = MoveType.LEFTUP;
			}
			else {
				this.moveType = MoveType.UPLEFT;
			}
		}
		else if (deltaXPos == 1 && deltaYPos == -1) {
			if ((oldBoard.getValidMovesMask(MoveType.RIGHTUP) & oldBlock.image) == oldBlock.image) {
				this.moveType = MoveType.RIGHTUP;
			}
			else {
				this.moveType = MoveType.UPRIGHT;
			}
			
		}
		else if (deltaXPos == -1 && deltaYPos == 1) {
			if ((oldBoard.getValidMovesMask(MoveType.LEFTDOWN) & oldBlock.image) == oldBlock.image) {
				this.moveType = MoveType.LEFTDOWN;
			}
			else {
				this.moveType = MoveType.DOWNLEFT;
			}
		}
		else if (deltaXPos == 1 && deltaYPos == 1) {
			if ((oldBoard.getValidMovesMask(MoveType.LEFTDOWN) & oldBlock.image) == oldBlock.image) {
				this.moveType = MoveType.RIGHTDOWN;
			}
			else {
				this.moveType = MoveType.DOWNRIGHT;
			}
		}
		else {
			assert false;
		}
		
	}
	

}
