package huaRongDao2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;

public class Move {
	public Board oldBoard;
	public Block oldBlock;
	public Block newBlock;
	public MoveType moveType;
	public int deltaXPos;
	public int deltaYPos;

	

    
    public Move (Board boardConfig, Block block, MoveType moveType) {
		this.oldBlock = block;
		this.moveType = moveType;
		switch (moveType) {
			case UP:
				deltaXPos = 0;
				deltaYPos = -1;
				break;
			case DOWN:
				deltaXPos = 0;
				deltaYPos = 1;
				break;
			case LEFT:
				deltaXPos = -1;
				deltaYPos = 0;
				break;
			case RIGHT: 
				deltaXPos = 1;
				deltaYPos = 0;
				break;
			case UP2:
				deltaXPos = 0;
				deltaYPos = -2;
				break;
			case DOWN2:
				deltaXPos = 0;
				deltaYPos = 2;
				break;
			case LEFT2:
				deltaXPos = -2;
				deltaYPos = 0;
				break;
			case RIGHT2: 
				deltaXPos = 2;
				deltaYPos = 0;
				break;
			case UPLEFT:
				deltaXPos = -1;
				deltaYPos = -1;
				break;
			case UPRIGHT:
				deltaXPos = 1;
				deltaYPos = -1;
				break;
			case DOWNLEFT:
				deltaXPos = -1;
				deltaYPos = 1;
				break;
			case DOWNRIGHT:
				deltaXPos = 1;
				deltaYPos = 1;
				break;
			case LEFTUP:
				deltaXPos = -1;
				deltaYPos = -1;
				break;
			case LEFTDOWN:
				deltaXPos = -1;
				deltaYPos = 1;
				break;
			case RIGHTUP:
				deltaXPos = 1;
				deltaYPos = -1;
				break;
			case RIGHTDOWN:
				deltaXPos = 1;
				deltaYPos = 1;
				break;
			default:
				assert false;
		}
    }

	
	public Move(Board oldBoard, Block oldBlock, int deltaXPos, int deltaYPos) {
		this.oldBoard = oldBoard;
		this.oldBlock = oldBlock;
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
			this.moveType = MoveType.UPLEFT;
			newBlock = GameSolver.calcNewBlock(oldBoard, oldBlock, moveType);
			if (newBlock == null) {
				moveType = MoveType.LEFTUP;
			}

		}
		else if (deltaXPos == 1 && deltaYPos == -1) {
			this.moveType = MoveType.UPRIGHT;
			newBlock = GameSolver.calcNewBlock(oldBoard, oldBlock, moveType);
			if (newBlock == null) {
				moveType = MoveType.RIGHTUP;
			}
			
		}
		else if (deltaXPos == -1 && deltaYPos == 1) {
			this.moveType = MoveType.DOWNLEFT;
			newBlock = GameSolver.calcNewBlock(oldBoard, oldBlock, moveType);
			if (newBlock == null) {
				moveType = MoveType.LEFTDOWN;
			}
		}
		else if (deltaXPos == 1 && deltaYPos == 1) {
			this.moveType = MoveType.DOWNRIGHT;
			newBlock = GameSolver.calcNewBlock(oldBoard, oldBlock, moveType);
			if (newBlock == null) {
				moveType = MoveType.RIGHTDOWN;
			}
		}
		else {
			assert false;
		}
		
		newBlock = GameSolver.calcNewBlock(oldBoard, oldBlock, moveType);
	}
	

}
