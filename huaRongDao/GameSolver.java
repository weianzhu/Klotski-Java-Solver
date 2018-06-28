package huaRongDao;

import java.util.ArrayDeque;
import java.util.HashMap;

public class GameSolver {
	Game game;
	Board solutionNode;
	HashMap<Long, Board> boardsExplored = new HashMap<Long, Board>();
	ArrayDeque<Board> boardWorkQueue = new ArrayDeque<Board>();
	@Deprecated
	protected int nextIdOfBoardExplored = 0; //Used to set Board.idOfBoardExplored, which could be useful for troubleshooting.

	public void solve(Game game) {
		this.game = game;
		boardsExplored.put(game.initialBoard.bitImage, game.initialBoard);
		//game.initialBoard.idOfBoardExplored = nextIdOfBoardExplored ++;
		
		buildGraph(game.initialBoard);
		
		findOneShortestPath(); 
		
		return ; 
		
	}

	/**
	 * Build graph of the Boards and their connections.
	 * 
	 * @param currentBoard
	 */
	private void buildGraph(Board currentBoard) {
		MoveType[] moveTypes = MoveType.values(); 
		boolean[] hasValidMoves = new boolean[moveTypes.length];
		while (currentBoard != null) {			
						
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
        
	/**
	 * Get the one shortest path.
	 * 
	 */
	protected void findOneShortestPath() {
		Board iterator = solutionNode;
		//Board.StepToInitialNodeComparator comparator = new Board.StepToInitialNodeComparator();
		while (iterator != game.initialBoard) {
			
			//iterator.connectedBoards.sort(comparator);
			//Board previousStep = iterator.connectedBoards.get(0);
			Board previousStep = iterator.previousBoard;
			previousStep.nextBoard = iterator;
			previousStep.stepNumberToSolution = iterator.stepNumberToSolution + 1;
			iterator = previousStep;
		}

	}
	

	/**
	 * Starting from the currentBoard, get all the next Boards that the moveType can lead to.
	 * 
	 * @param currentBoard
	 * @param moveType
	 * @return
	 */
	private boolean getNextBoard(Board currentBoard, MoveType moveType) {
		
		boolean hasValidMoves = false;
		
		// The mask has all the Blocks that can do the moveTypes.  It utilizes bit operations to check all the Blocks simultaneously.
		long validMovesMask = currentBoard.getValidMovesMask(moveType);
		//PrintUtility.printID(boardMask);
		for (int i = 0; i < currentBoard.blocks.length; i++) {
			//NewPrintUtility.printID(board.and(blockPlacements[i].blockPlacementID));
			if ((validMovesMask & currentBoard.blocks[i].bitImage) == currentBoard.blocks[i].bitImage) {
				hasValidMoves = true;
				//System.out.println(currentBoard.stepNumberToInitialNode + "->" +currentBoard.blocks[i].prototype.name + " " + moveType);
				long newBlockImage = currentBoard.blocks[i].getMovedImage(moveType);
				long nextBoardImage = currentBoard.getNextBoardID(currentBoard.blocks[i].bitImage, newBlockImage);
				//PrintUtility.printID(nextBoardImage);
				if (!boardsExplored.containsKey(nextBoardImage)) {
					Block newBlock = new Block(currentBoard.blocks[i], newBlockImage);
					Block[] newBlocks = currentBoard.blocks.clone(); 
					newBlocks[i] = newBlock;
					Board nextBoard = new Board(newBlocks, nextBoardImage);
					//PrintUtility.printID(newBoard.image);
					//newBoard.idOfBoardExplored = game.nextIdOfBoardExplored ++;
					boardsExplored.put(nextBoardImage, nextBoard);
					nextBoard.stepNumberToInitialNode = currentBoard.stepNumberToInitialNode + 1;	
					
					//nextBoard.connectedBoards.add(currentBoard);
					//currentBoard.connectedBoards.add(nextBoard);
					nextBoard.previousBoard = currentBoard;
					// Check if it is a solution
					if (nextBoard.isSolved()) solutionNode = nextBoard;
					boardWorkQueue.offerLast(nextBoard);
					
				}
				/*else {
				 
				 For finding the shortest path, we do not necessarily need to build the full graph of the Board 
				 nodes and their connections.
				 
				 If the nextBoardImage has already been explored, there is no need to record the connection 
			     between it and the currentBoard.  The reason is that the newBoard must already have a 
				 connection to another Board that is a shortest path to the starting node.
				 
				 Because of this, connectedBoards should have no duplicates. Actually, only one in the connectedBoards 
				 is the Board that goes before the current Board.  In other word, we construct just a tree, rather
				 than a networked full graph. 
				 
				 Because of this, we can only get one shortest path at the end. There could be multiple 
				 slightest variations of the shortest paths.  We can generalize connectedBoards to build the full graph
				 if we are interested in finding all the possible solutions.  We can also narrow down the connectedBoards
				 to store only the prior Board.  This will slightly save some time in finding the shortest path.
				   

				} */
			}
		}
		
		return hasValidMoves;

	}

	
}
