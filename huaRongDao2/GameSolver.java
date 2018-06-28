package huaRongDao2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;


public class GameSolver {
	Game game;
	protected HashMap<String, Board> boardConfigsExplored = new HashMap<String, Board>();
	protected int nextIdOfBoardExplored = 0;

	ArrayDeque<Board> boardConfigWorkQueue = new ArrayDeque<Board>();
	Board solutionNode;

	public void solve(Game game) {
		game.initialBoard.idOfBoardExplored = nextIdOfBoardExplored ++;
		boardConfigsExplored.put(game.initialBoard.hashString(), game.initialBoard);

		this.game = game;
		buildGraph(game.initialBoard);
		findOneShortestPath(); 
		return ; 
		
	}

	private void buildGraph(Board currentBoard) {
		while (currentBoard != null) {			
			for (int i = 0; i < currentBoard.blocks.length; i++) {
				MoveType[] moveTypes = validMoveTypes.get(currentBoard.blocks[i].prototype.blockType);
				
				for (MoveType moveType: moveTypes) {
					Block newBlock = calcNewBlock(currentBoard, currentBoard.blocks[i], moveType);
					if (newBlock == null) continue; // Invalid move
					Board nextBoard = getNextBoard(currentBoard, currentBoard.blocks[i], newBlock);
					PrintUtility.printTestMove(currentBoard, i, currentBoard.blocks[i], moveType, nextBoard);
					if (nextBoard.stepNumberToSolution < Integer.MAX_VALUE) {
						solutionNode = nextBoard; //The first solution we reach is the fastest solution.  
						return;
					}
					
					
					// Only explore the nodes that have not been explored before.  
					if (nextBoard.isNewBoard) {
					
						//PrintUtility.printTestMove(currentBoard, i, moveInTest, nextBoardInTest);
						boardConfigWorkQueue.offerLast(nextBoard);
					}
				}
			}
			
			currentBoard = boardConfigWorkQueue.pollFirst();
		
		}
	}
        
	
	protected void findOneShortestPath() {
		Board iterator = solutionNode;
		while (iterator != game.initialBoard) {
			
			ArrayList<Board> connected = new ArrayList<Board>(iterator.connectedBoards);
			connected.sort(iterator.new StepToInitialNodeComparator());
			Board previousStep = connected.get(0);
			previousStep.stepNumberToSolution = iterator.stepNumberToSolution + 1;
			iterator = previousStep;
		}

	}
	
	static final private MoveType[] validMoveTypesSQUARE =  {MoveType.UP, MoveType.DOWN, MoveType.LEFT, MoveType.RIGHT};
	static final private MoveType[] validMoveTypesHORIZONTAL =  {MoveType.UP, MoveType.DOWN, MoveType.LEFT, MoveType.RIGHT, MoveType.LEFT2, MoveType.RIGHT2};
	static final private MoveType[] validMoveTypesVERTICAL =  {MoveType.UP, MoveType.DOWN, MoveType.LEFT, MoveType.RIGHT, MoveType.UP2, MoveType.DOWN2};
	static final private MoveType[] validMoveTypesSINGLE =  MoveType.values();
	static EnumMap<BlockType, MoveType[]> validMoveTypes = new EnumMap<BlockType, MoveType[]>(BlockType.class);
	
        
    static {
        
        validMoveTypes.put(BlockType.SQUARE, validMoveTypesSQUARE);
        validMoveTypes.put(BlockType.B_HORIZONTAL, validMoveTypesHORIZONTAL);
        validMoveTypes.put(BlockType.VERTICAL, validMoveTypesVERTICAL);
        validMoveTypes.put(BlockType.SINGLE, validMoveTypesSINGLE);

        
    }
    
	
	Board getNextBoard(Board currentBoard, Block oldBlock, Block newBlock) {
		
		Board newBoard = new Board(currentBoard, oldBlock, newBlock);
		String boardConfigHashString = newBoard.hashString();
		if (boardConfigsExplored.containsKey(boardConfigHashString) ) {
			// use the existing instance in game.boardConfigsExplored
			newBoard = boardConfigsExplored.get(boardConfigHashString);
			newBoard.isNewBoard = false;
		}
		else {
			newBoard.idOfBoardExplored = nextIdOfBoardExplored ++;
			boardConfigsExplored.put(boardConfigHashString, newBoard);
			newBoard.stepNumberToInitialNode = currentBoard.stepNumberToInitialNode + 1;	
			
			// Check if a solution
			newBoard.checkWhetherSolved();
	
		}
		newBoard.connectedBoards.add(currentBoard);
		currentBoard.connectedBoards.add(newBoard);
		return newBoard;
	}

	static final private int[][] moveStepsUP = {{0, -1}};
	static final private int[][] moveStepsDOWN = {{0, 1}};
	static final private int[][] moveStepsLEFT = {{-1, 0}};
	static final private int[][] moveStepsRIGHT = {{1, 0}};
	static final private int[][] moveStepsUP2 = {{0, -1}, {0, -2}};
	static final private int[][] moveStepsDOWN2 = {{0, 1}, {0, 2}};
	static final private int[][] moveStepsLEFT2 = {{-1, 0}, {-2, 0}};
	static final private int[][] moveStepsRIGHT2 = {{1, 0}, {2, 0}};
	static final private int[][] moveStepsUPLEFT = {{0, -1}, {-1, -1}};
	static final private int[][] moveStepsUPRIGHT = {{0, -1}, {1, -1}};
	static final private int[][] moveStepsDOWNLEFT = {{0, 1}, {-1, 1}};
	static final private int[][] moveStepsDOWNRIGHT = {{0, 1}, {1, 1}};
	static final private int[][] moveStepsLEFTUP = {{-1, 0}, {-1, -1}};
	static final private int[][] moveStepsLEFTDOWN = {{-1, 0}, {-1, 1}};
	static final private int[][] moveStepsRIGHTUP = {{1, 0}, {1, -1}};
	static final private int[][] moveStepsRIGHTDOWN = {{1, 0}, {1, 1}};
	
	static Block calcNewBlock(Board oldBoard, Block oldBlock, MoveType moveType) {
		int[][] moveSteps = null;

		switch (moveType) {
			case UP:
				moveSteps = moveStepsUP;
				break;
			case DOWN:
				moveSteps = moveStepsDOWN;
				break;
			case LEFT:
				moveSteps = moveStepsLEFT;
				break;
			case RIGHT: 
				moveSteps = moveStepsRIGHT;
				break;
			case UP2:
				moveSteps = moveStepsUP2;
				break;
			case DOWN2:
				moveSteps = moveStepsDOWN2;
				break;
			case LEFT2:
				moveSteps = moveStepsLEFT2;
				break;
			case RIGHT2: 
				moveSteps = moveStepsRIGHT2;
				break;
			case UPLEFT:
				moveSteps = moveStepsUPLEFT;
				break;
			case UPRIGHT:
				moveSteps = moveStepsUPRIGHT;
				break;
			case DOWNLEFT:
				moveSteps = moveStepsDOWNLEFT;
				break;
			case DOWNRIGHT:
				moveSteps = moveStepsDOWNRIGHT;
				break;
			case LEFTUP:
				moveSteps = moveStepsLEFTUP;
				break;
			case LEFTDOWN:
				moveSteps = moveStepsLEFTDOWN;
				break;
			case RIGHTUP:
				moveSteps = moveStepsRIGHTUP;
				break;
			case RIGHTDOWN:
				moveSteps = moveStepsRIGHTDOWN;
				break;
			default:
				assert false;
		}
		for (int i = 0; i < moveSteps.length; i++) {
			if (!oldBoard.isValidMove(oldBlock, moveSteps[i][0], moveSteps[i][1])) {
				return null;
			}
		}
		return new Block(oldBlock, moveSteps[moveSteps.length-1][0], moveSteps[moveSteps.length-1][1]);
	}

	
}
