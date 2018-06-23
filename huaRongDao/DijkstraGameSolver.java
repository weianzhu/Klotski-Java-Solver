package huaRongDao;

import java.util.ArrayDeque;
import java.util.HashMap;


public class DijkstraGameSolver extends GameSolver {

	//HashSet<Board> solutionNodes = new HashSet<Board>();
	Board solutionNode;
	protected HashMap<Long, Board> boardsExplored = new HashMap<Long, Board>();
	ArrayDeque<Board> boardWorkQueue = new ArrayDeque<Board>();
	@Deprecated
	protected int nextIdOfBoardExplored = 0; //Used to set Board.idOfBoardExplored, which could be useful for troubleshooting.

	@Override
	public void solve(Game game) {
		this.game = game;
		//game.initialBoard.idOfBoardExplored = nextIdOfBoardExplored ++;
		boardsExplored.put(game.initialBoard.image, game.initialBoard);
		
		buildGraph(game.initialBoard);
		findOneShortestPath(); 
		return ; 
		
	}

	private void buildGraph(Board currentBoard) {
		MoveType[] moveTypes = MoveType.values(); //{MoveType.UP2, MoveType.DOWN2, MoveType.LEFT2, MoveType.RIGHT2, MoveType.LEFTUP, MoveType.RIGHTUP, MoveType.LEFTDOWN, MoveType.RIGHTDOWN, MoveType.UP, MoveType.DOWN, MoveType.LEFT, MoveType.RIGHT};
		//MoveType[] moveTypes = { MoveType.UP, MoveType.DOWN, MoveType.LEFT, MoveType.RIGHT, MoveType.UP2, MoveType.DOWN2, MoveType.LEFT2, MoveType.RIGHT2};
		boolean[] hasValidMoves = new boolean[moveTypes.length];
		while (currentBoard != null) {			
			
			/*if (currentBoard.stepNumberToSolution < Integer.MAX_VALUE) {
				solutionNode = currentBoard; //The first solution we reach is the quickest solution.  
				return;
			}*/
			
			
			for (int i = 0; i < moveTypes.length; i ++) {
				// A shortcut for checking invalid moves.  
				// For example, if there is no valid moves for UP, then there could be no valid moves for UP2, UPLEFT, UPRIGHT 
				if (i > 3 && !hasValidMoves[i%4]) continue;
				MoveType moveType = moveTypes[i];
				hasValidMoves[i] = getNextBoard(currentBoard, moveType);
				if (solutionNode != null) return;
			}
			currentBoard = boardWorkQueue.pollFirst();
		}
		
		
		
	}
        
	
	protected void findOneShortestPath() {
		Board iterator = solutionNode;
		Board.StepToInitialNodeComparator comparator = new Board.StepToInitialNodeComparator();
		while (iterator != game.initialBoard) {
			
			iterator.connectedBoards.sort(comparator);
			Board previousStep = iterator.connectedBoards.get(0);
			previousStep.stepNumberToSolution = iterator.stepNumberToSolution + 1;
			iterator = previousStep;
		}

	}
	

	private boolean getNextBoard(Board currentBoard, MoveType moveType) {
		
		boolean hasValidMoves = false;
		long boardMask = currentBoard.getValidMovesMask(moveType);
		//PrintUtility.printID(boardMask);
		for (int i = 0; i < currentBoard.blocks.length; i++) {
			//NewPrintUtility.printID(board.and(blockPlacements[i].blockPlacementID));
			if ((boardMask & currentBoard.blocks[i].image) == currentBoard.blocks[i].image) {
					hasValidMoves = true;
				//System.out.println(currentBoard.stepNumberToInitialNode + "->" +currentBoard.blockPlacements[i].block.name + " " + moveType);
				long newBlockImage = currentBoard.blocks[i].getMovedImage(moveType);
				long nextBoardImage = currentBoard.getNextBoardID(currentBoard.blocks[i].image, newBlockImage);
				//PrintUtility.printID(nextBoardImage);
				if (!boardsExplored.containsKey(nextBoardImage)) {
					Block newBlock = new Block(currentBoard.blocks[i], newBlockImage);
					Block[] newBlocks = new Block[currentBoard.blocks.length];
					for (int j = 0; j < newBlocks.length; j++) {
							newBlocks[j] = currentBoard.blocks[j];
					}
					newBlocks[i] = newBlock;
					Board newBoard = new Board(newBlocks, nextBoardImage);
					//PrintUtility.printID(newBoard.image);
					//newBoard.idOfBoardExplored = game.nextIdOfBoardExplored ++;
					boardsExplored.put(nextBoardImage, newBoard);
					newBoard.stepNumberToInitialNode = currentBoard.stepNumberToInitialNode + 1;	
					
					newBoard.connectedBoards.add(currentBoard);
					currentBoard.connectedBoards.add(newBoard);
					// Check if it is a solution
					if (newBoard.checkWhetherSolved()) solutionNode = newBoard;
					boardWorkQueue.offerLast(newBoard);
					

				}
				//else {
				// if the nextBoardImage has already been explored, there is no need to record the connection 
			    // between it and the currentBoard.
				// The newBoard must already have a connection to another Board that leads to a 
				// shorter (no farther) path to the starting node.
				// Because of this, connectedBoards should have no duplicates.

				//}
			}
		}
		
		return hasValidMoves;

	}

	

}
