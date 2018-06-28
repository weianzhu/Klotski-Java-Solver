package huaRongDao2;


public class Game {

	Board initialBoard;
	String name;


	public static void main(String[] args) {
		long startTime = System.nanoTime();
		GameSolver gameSolver = new GameSolver();
		Game game = initGame横刀立马();
		//Game game = initGame四将连关();
		gameSolver.solve(game);
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		System.out.println("<html><body><font face=Courier>");
		PrintUtility.printFinalSolution(game, gameSolver);
		System.out.println("Run time " + ((float)duration)/1000000000.0 +" seconds <br></font></body></html>");



	}
	
	public Game(String name, Board initialBoard) {
		this.name = name;
		this.initialBoard = initialBoard;
		initialBoard.stepNumberToInitialNode = 0;			

	}
	
	private static Game initGame四将连关() {
		// 四将连关
		Block[] blocks = new Block[10];
		
		blocks[0] = new Block(new BlockPrototype("曹操", BlockType.SQUARE), 0, 0);
		blocks[1] = new Block(new BlockPrototype("关羽", BlockType.B_HORIZONTAL), 2, 0);
		blocks[2] = new Block(new BlockPrototype("黄忠", BlockType.B_HORIZONTAL), 2, 1);
		blocks[3] = new Block(new BlockPrototype("张飞", BlockType.VERTICAL), 0, 2);
		blocks[4] = new Block(new BlockPrototype("赵云", BlockType.VERTICAL), 1, 2);
		blocks[5] = new Block(new BlockPrototype("马超", BlockType.B_HORIZONTAL), 2, 2);
		blocks[6] = new Block(new BlockPrototype("卒1", BlockType.SINGLE), 0, 4);
		blocks[7] = new Block(new BlockPrototype("卒2", BlockType.SINGLE), 2, 3);
		blocks[8] = new Block(new BlockPrototype("卒3", BlockType.SINGLE), 3, 3);
		blocks[9] = new Block(new BlockPrototype("卒4", BlockType.SINGLE), 3, 4);
		Board boardConfig = new Board(blocks);
		Game game = new Game("四将连关", boardConfig);
		return game;

	}

	
	private static Game initGameTest() {
		Block[] blocks = new Block[10];
		
		blocks[0] = new Block(new BlockPrototype("曹操", BlockType.SQUARE), 1, 1);
		blocks[3] = new Block(new BlockPrototype("张飞", BlockType.VERTICAL), 0, 2);
		blocks[4] = new Block(new BlockPrototype("赵云", BlockType.VERTICAL), 3, 3);
		blocks[6] = new Block(new BlockPrototype("卒1", BlockType.SINGLE), 0, 0);
		blocks[7] = new Block(new BlockPrototype("卒2", BlockType.SINGLE), 0, 1);
		blocks[8] = new Block(new BlockPrototype("卒3", BlockType.SINGLE), 3, 0);
		blocks[9] = new Block(new BlockPrototype("卒4", BlockType.SINGLE), 3, 1);
		blocks[10] = new Block(new BlockPrototype("卒5", BlockType.SINGLE), 0, 4);
		blocks[11] = new Block(new BlockPrototype("卒6", BlockType.SINGLE), 1, 3);
		blocks[12] = new Block(new BlockPrototype("卒7", BlockType.SINGLE), 1, 4);
		blocks[13] = new Block(new BlockPrototype("卒8", BlockType.SINGLE), 2, 3);
		blocks[14] = new Block(new BlockPrototype("卒9", BlockType.SINGLE), 2, 4);

		Board boardConfig = new Board(blocks);
		Game game = new Game("Test", boardConfig);
		return game;

	}

	private static Game initGame横刀立马() {
		// 横刀立马
		Block[] blocks = new Block[10];
		
		blocks[3] = new Block(new BlockPrototype("张飞", BlockType.VERTICAL), 0, 0);
		blocks[0] = new Block(new BlockPrototype("曹操", BlockType.SQUARE), 1, 0);
		blocks[4] = new Block(new BlockPrototype("赵云", BlockType.VERTICAL), 3, 0);
		blocks[2] = new Block(new BlockPrototype("黄忠", BlockType.VERTICAL), 0, 2);
		blocks[1] = new Block(new BlockPrototype("关羽", BlockType.B_HORIZONTAL), 1, 2);
		blocks[5] = new Block(new BlockPrototype("马超", BlockType.VERTICAL), 3, 2);
		blocks[6] = new Block(new BlockPrototype("卒1", BlockType.SINGLE), 0, 4);
		blocks[7] = new Block(new BlockPrototype("卒2", BlockType.SINGLE), 1, 3);
		blocks[8] = new Block(new BlockPrototype("卒3", BlockType.SINGLE), 2, 3);
		blocks[9] = new Block(new BlockPrototype("卒4", BlockType.SINGLE), 3, 4);
		Board boardConfig = new Board(blocks);
		Game game = new Game("横刀立马", boardConfig);
		return game;

	}
	

	

}
