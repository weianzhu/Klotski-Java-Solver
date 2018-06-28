package huaRongDao;

public class Game {
 


	public static void main(String[] args) {
		
		long startTime = System.nanoTime();
		GameSolver gameSolver = new GameSolver();
		Game game = initGame横刀立马();
		//Game game = initGame四将连关();
		//PrintUtility.printID(game.initialBoard.bitImage);
		gameSolver.solve(game);
		long endTime = System.nanoTime();
		long duration = endTime - startTime;

		System.out.println("<html>");
		System.out.println("<header><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		System.out.println("</header><body><font face=Courier>");
		PrintUtility.printBestSolution(game, gameSolver);
		System.out.println("Run time " + ((float)duration)/1000000000.0 +" seconds <br></font></body></html>");
		
		/*for (long i = 0; i < 10000000000L; i ++) {
			int x = 0;
			x = x + 1;
		}*/
		

	}
	
	Board initialBoard;
	String name;
	public Game(String name, Board initialBoard) {
		this.name = name;
		this.initialBoard = initialBoard;
		initialBoard.stepNumberToInitialNode = 0;			

	}
	
	private static Game initGame四将连关() {
		// 四将连关
		Block[] blockPlacements = new Block[10];
		
		blockPlacements[0] = new Block(new BlockPrototype("曹操", BlockType.SQUARE), 0, 0);
		blockPlacements[1] = new Block(new BlockPrototype("关羽", BlockType.HORIZONTAL), 2, 0);
		blockPlacements[2] = new Block(new BlockPrototype("黄忠", BlockType.HORIZONTAL), 2, 1);
		blockPlacements[3] = new Block(new BlockPrototype("张飞", BlockType.VERTICAL), 0, 2);
		blockPlacements[4] = new Block(new BlockPrototype("赵云", BlockType.VERTICAL), 1, 2);
		blockPlacements[5] = new Block(new BlockPrototype("马超", BlockType.HORIZONTAL), 2, 2);
		blockPlacements[6] = new Block(new BlockPrototype("卒1", BlockType.SINGLE), 0, 4);
		blockPlacements[7] = new Block(new BlockPrototype("卒2", BlockType.SINGLE), 2, 3);
		blockPlacements[8] = new Block(new BlockPrototype("卒3", BlockType.SINGLE), 3, 3);
		blockPlacements[9] = new Block(new BlockPrototype("卒4", BlockType.SINGLE), 3, 4);
		Board boardConfig = new Board(blockPlacements);
		Game game = new Game("四将连关", boardConfig);
		return game;

	}

	
	private static Game initGameTest() {
		Block[] blockPlacements = new Block[10];
		
		blockPlacements[0] = new Block(new BlockPrototype("曹操", BlockType.SQUARE), 1, 1);
		blockPlacements[3] = new Block(new BlockPrototype("张飞", BlockType.VERTICAL), 0, 2);
		blockPlacements[4] = new Block(new BlockPrototype("赵云", BlockType.VERTICAL), 3, 3);
		blockPlacements[6] = new Block(new BlockPrototype("卒1", BlockType.SINGLE), 0, 0);
		blockPlacements[7] = new Block(new BlockPrototype("卒2", BlockType.SINGLE), 0, 1);
		blockPlacements[8] = new Block(new BlockPrototype("卒3", BlockType.SINGLE), 3, 0);
		blockPlacements[9] = new Block(new BlockPrototype("卒4", BlockType.SINGLE), 3, 1);
		blockPlacements[10] = new Block(new BlockPrototype("卒5", BlockType.SINGLE), 0, 4);
		blockPlacements[11] = new Block(new BlockPrototype("卒6", BlockType.SINGLE), 1, 3);
		blockPlacements[12] = new Block(new BlockPrototype("卒7", BlockType.SINGLE), 1, 4);
		blockPlacements[13] = new Block(new BlockPrototype("卒8", BlockType.SINGLE), 2, 3);
		blockPlacements[14] = new Block(new BlockPrototype("卒9", BlockType.SINGLE), 2, 4);

		Board boardConfig = new Board(blockPlacements);
		Game game = new Game("Test", boardConfig);
		return game;

	}

	private static Game initGame横刀立马() {
		// 横刀立马
		Block[] blockPlacements = new Block[10];
		
		blockPlacements[0] = new Block(new BlockPrototype("曹操", BlockType.SQUARE), 1, 0);
		blockPlacements[1] = new Block(new BlockPrototype("关羽", BlockType.HORIZONTAL), 1, 2);
		blockPlacements[2] = new Block(new BlockPrototype("黄忠", BlockType.VERTICAL), 0, 2);
		blockPlacements[3] = new Block(new BlockPrototype("张飞", BlockType.VERTICAL), 0, 0);
		blockPlacements[4] = new Block(new BlockPrototype("赵云", BlockType.VERTICAL), 3, 0);
		blockPlacements[5] = new Block(new BlockPrototype("马超", BlockType.VERTICAL), 3, 2);
		blockPlacements[6] = new Block(new BlockPrototype("卒1", BlockType.SINGLE), 0, 4);
		blockPlacements[7] = new Block(new BlockPrototype("卒2", BlockType.SINGLE), 1, 3);
		blockPlacements[8] = new Block(new BlockPrototype("卒3", BlockType.SINGLE), 2, 3);
		blockPlacements[9] = new Block(new BlockPrototype("卒4", BlockType.SINGLE), 3, 4);
		Board boardConfig = new Board(blockPlacements);
		Game game = new Game("横刀立马", boardConfig);
		return game;

	}
	


	



}
