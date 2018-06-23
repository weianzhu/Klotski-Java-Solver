package huaRongDao;

import java.util.ArrayList;
import java.util.Comparator;



public class Board {
	
	Block[] blocks;
	static final int MINPOS = 0;
	static final int MAXXPOS = 3;
	static final int MAXYPOS = 4;

	long image = 0L; 
	int idOfBoardsExplored;
	int stepNumberToInitialNode;
	int stepNumberToSolution = Integer.MAX_VALUE;
	
	static private long shiftLeftBorderMask = 0b1111100L | (0b1111100L << 7) | (0b1111100L << 14) | (0b1111100L << 21) | (0b1111100L << 28)
			 | (0b1111100L << 35) | (0b1111100L << 42) | (0b1111100L << 49) | (0b1111100L << 56);
	static private long shiftRightBorderMask = 0b0011111L | (0b0011111L << 7) | (0b0011111L << 14) | (0b0011111L << 21) | (0b0011111L << 28)
			 | (0b0011111L << 35) | (0b0011111L << 42) | (0b0011111L << 49) | (0b0011111L << 56);
	static private long shiftUpBorderMask = 0b0L | (0b0L << 7) | (0b1111111L << 14) | (0b1111111L << 21) | (0b1111111L << 28)
			 | (0b1111111L << 35) | (0b1111111L << 42) | (0b1111111L << 49) | (0b1111111L << 56);
	static private long shiftDownBorderMask = 0b1111111L | (0b1111111L << 7) | (0b1111111L << 14) | (0b1111111L << 21) | (0b1111111L << 28)
			 | (0b1111111L << 35) | (0b1111111L << 42) | (0b0L << 49) | (0b0L << 56);
	static private long shiftLeft1BitBorderMask = 0b1111110L | (0b1111110L << 7) | (0b1111110L << 14) | (0b1111110L << 21) | (0b1111110L << 28)
			 | (0b1111110L << 35) | (0b1111110L << 42) | (0b1111110L << 49) | (0b1111110L << 56);
	static private long shiftRight1BitBorderMask = 0b0111111L | (0b0111111L << 7) | (0b0111111L << 14) | (0b0111111L << 21) | (0b0111111L << 28)
			 | (0b0111111L << 35) | (0b0111111L << 42) | (0b0111111L << 49) | (0b0111111L << 56);
	
	ArrayList<Board> connectedBoards = new ArrayList<Board>();
	

	public Board(Block[] blocks) {
		this.blocks = blocks;

		for (Block block : blocks) {
			image |= block.image;
			/*PrintUtility.printID(image);	*/
		}
	}

	public Board(Block[] blocks, long boardConfigID) {
		this.blocks = blocks;

		image = boardConfigID;
	}
	
	static private long [] validMovesMask = new long[MoveType.values().length];

	
	/**
	 * Get the mask that can be used to determine which Blocks are eligible for the MoveType.
	 * 
	 * Use LEFT for example:
	 * 
	 * image << 2 : Move the board image by 1 spot to the left. 
	 * 				To logically shift the image left by one spot, we shift the long value of image by 2 bits to the left.
	 * 				The left-most 2 bits should logically be shifted out of the board boundary.
	 *				But they actually get overflown into the previous row.
	 * 				We want to blank out those two bits to achieve the desired logical result. 
	 *				That is what the shiftLeftBoarderMask is for.
	 * ^ image : XOR with original board image to get valid moves.  
	 *           If a Block moves into a blank spot, XOR 1 with 0 = 1.  This is a valid move.
	 *           If a Block overlaps with another one after moving, XOR 1 with 1 = 0.  This is an invalid move.
	 *           There is one caveat: the horizontal block gets its right bit gets lost when it gets XORed with the left bit.
	 *           This should not be counted as invalidating the move.  We use | (image >>> 1) to add the right bit back.
	 * & shiftLeftBorderMask:  blank out the out-of-bound bits
	 * >>> 2 : move board back 
	 * image >>> 1 : the horizontal connecting spot
	 * | (image >>> 1) : add back the rights dots of the horizontal blocks.
	 * 
	 * Store the result invalidMovesMask[2] so that later we can quickly check it before we validate the LEFT2, LEFTUP, LEFTDOWN moves.
	 * The possible validity of those moves depends on LEFT.
	 * 
	 * @param moveType
	 * @return
	 */
	public long getValidMovesMask(MoveType moveType) {
		switch (moveType) {
		case UP:
			 // return nextBoardMask[0].copyFrom(image).shiftLeft(22).xor(image).and(Board.borderMask).shiftRight(22)/*.and(Board.borderMask)*/.or(connectorMask.copyFrom(image).shiftRight(11))/*.and(image)*/;
			validMovesMask[0] = ((((image << 14) ^ image) & shiftUpBorderMask) >>> 14) | (image >>> 7);
			return validMovesMask[0];
		case DOWN:
			validMovesMask[1] = ((((image >>> 14) ^ image) & shiftDownBorderMask) << 14) | (image << 7);
			return validMovesMask[1];
		case LEFT:
			validMovesMask[2] = ((((image << 2) ^ image) & shiftLeftBorderMask) >>> 2) | ((image >>> 1) & shiftRight1BitBorderMask );
			return validMovesMask[2];
		case RIGHT:
			validMovesMask[3] = ((((image >>> 2) ^ image) & shiftRightBorderMask) << 2) | ((image << 1) & shiftLeft1BitBorderMask);
			return validMovesMask[3];
			
		case UP2:
			validMovesMask[4] = ((((((image << 14) & shiftUpBorderMask) << 14) ^ image) & shiftUpBorderMask) >>> 28) | (image >>> 7);
			return validMovesMask[4]& validMovesMask[0];
		case DOWN2:
			validMovesMask[5] = ((((((image >>> 14) & shiftDownBorderMask) >>> 14) ^ image) & shiftDownBorderMask) << 28) | (image << 7);
			return validMovesMask[5] & validMovesMask[1];
		case LEFT2:
			validMovesMask[6] = ((((((image << 2) & shiftLeftBorderMask) << 2) ^ image) & shiftLeftBorderMask) >>> 4) | (image >>> 1);
			return validMovesMask[6] & validMovesMask[2];
		case RIGHT2:
			validMovesMask[7] = ((((((image >>> 2) & shiftRightBorderMask) >>> 2) ^ image) & shiftRightBorderMask) << 4) | (image << 1);
		
			return validMovesMask[7] & validMovesMask[3];
		case UPLEFT:
			validMovesMask[8] = ((((((image << 14) & shiftUpBorderMask) << 2) ^ image) & shiftLeftBorderMask) >>> 16);
			return validMovesMask[8] & validMovesMask[0];
		case DOWNLEFT:
			validMovesMask[9] = ((((((image >>> 14) & shiftDownBorderMask) << 2) ^ image) & shiftLeftBorderMask) << 12) ;
			return validMovesMask[9] & validMovesMask[1];
		case LEFTUP:
			validMovesMask[10] = ((((((image << 2) & shiftLeftBorderMask) << 14) ^ image) & shiftUpBorderMask) >>> 16) ;
			return validMovesMask[10] & validMovesMask[2];
		case RIGHTUP:
			validMovesMask[11] = ((((((image >>> 2) & shiftRightBorderMask) << 14) ^ image) & shiftUpBorderMask) >>> 12);
			return validMovesMask[11] & validMovesMask[3];
		case UPRIGHT:
			validMovesMask[12] = ((((((image << 14) & shiftUpBorderMask) >>> 2) ^ image) & shiftRightBorderMask) >>> 12) ;
			return validMovesMask[12] & validMovesMask[0];
		case DOWNRIGHT:
			validMovesMask[13] = ((((((image >>> 14) & shiftDownBorderMask) >>> 2) ^ image) & shiftRightBorderMask) << 16) ;
			return validMovesMask[13] & validMovesMask[1];
		case LEFTDOWN:
			validMovesMask[14] = ((((((image << 2) & shiftLeftBorderMask) >>> 14) ^ image) & shiftDownBorderMask) << 12) ;
			return validMovesMask[14] & validMovesMask[2];
		case RIGHTDOWN:
			validMovesMask[15] = ((((((image >>> 2) & shiftRightBorderMask) >>> 14) ^ image) & shiftDownBorderMask) << 16);
			return validMovesMask[15] & validMovesMask[3];
		 default:
			return 0;
		}
	}
	
	/**
 	 * remove old Block and add new one.  xor can do both subtraction and addition.
	 * 
	 * @param oldBlockImage
	 * @param newBlockImage
	 * @return
	 */
	public long getNextBoardID(long oldBlockImage, long newBlockImage) {
		//return ((MyBigInteger)image.clone()).xor(oldBlock.image).xor(newBlockImage);
		return image ^ oldBlockImage ^ newBlockImage;
	}
	
	
	static private long solvedMask = new Block(new BlockPrototype("曹操", BlockType.SQUARE), 1, 3).image;
	//static private MyBigInteger tempImageCopy = new MyBigInteger();
	boolean checkWhetherSolved() {
		// Check whether already solved
		if (stepNumberToSolution != Integer.MAX_VALUE) {
			return true;
		}
		
		if ((image & solvedMask) == solvedMask) {
			stepNumberToSolution = 0;
			return true;
		}
		
		return false;
		
	}

	/**
	 * 
	 * @author weian.zhu
	 *
	 * Comparator of the stepToSolution
	 */
	static public class StepToSolutionComparator implements Comparator<Board> {

		@Override
		public int compare(Board arg0, Board arg1) {
		
			return arg0.stepNumberToSolution == arg1.stepNumberToSolution ? 0 
				   : arg0.stepNumberToSolution < arg1.stepNumberToSolution ? -1
				   : 1;
		}
		
	}
	
	static public class StepToInitialNodeComparator implements Comparator<Board> {

		@Override
		public int compare(Board arg0, Board arg1) {
		
			return arg0.stepNumberToInitialNode == arg1.stepNumberToInitialNode ? 0 
				   : arg0.stepNumberToInitialNode < arg1.stepNumberToInitialNode ? -1
				   : 1;
		}
		
	}

	 Move calculcateMove(Board nextNode) {
		Move move;
		boolean foundMatch;
		Block oldBlock = null, newBlock = null;
		for (int i = 0; i < blocks.length; i++) {
			foundMatch = false;
			for (int j = 0; j < nextNode.blocks.length; j++) {
				if (blocks[i].prototype.blockType == nextNode.blocks[j].prototype.blockType
						&& blocks[i].xPos() == nextNode.blocks[j].xPos()
						&& blocks[i].yPos() == nextNode.blocks[j].yPos()) {
					// found match
					foundMatch = true;
					break;
				}
			}
			if (!foundMatch) {
				oldBlock = blocks[i];
			}
		}
		
		for (int j = 0; j < nextNode.blocks.length; j++) {
			foundMatch = false;
			for (int i = 0; i < blocks.length; i++) {
				if (blocks[i].prototype.blockType == nextNode.blocks[j].prototype.blockType
						&& blocks[i].xPos() == nextNode.blocks[j].xPos()
						&& blocks[i].yPos() == nextNode.blocks[j].yPos()) {
					// found match
					foundMatch = true;
					break;
				}
			}
			if (!foundMatch) {
				newBlock = nextNode.blocks[j];
			}
		}
				
		if (oldBlock != null && newBlock != null) {
			int deltaXPos = newBlock.xPos() - oldBlock.xPos();
			int deltaYPos = newBlock.yPos() - oldBlock.yPos();
			move = new Move(this, oldBlock, deltaXPos, deltaYPos, newBlock);
			return move;
		}
		
		assert false;
		return null;
		
	}
	 
	 // Not used actually
	 @Override
	 public int hashCode() {
		 
		 return Long.hashCode(image);
	 }

}
