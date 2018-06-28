package huaRongDao;

import java.util.HashMap;


public class PrintUtility {
	
	private static boolean VERBOSE_MODE = false;


	static public void printID(long id) {
		String temp = toString( id);
		System.out.println(temp);
		System.out.println("");

	}
	
	static public String toString(long id) {
		String temp = String.format("%1$63s", Long.toBinaryString(id));
		temp = temp.replace(' ', '0');
		char[] tempChar = new char[81];
		for (int i = 0; i < 9; i++) {
			temp.getChars(i * 7, (i+1)*7, tempChar, i*9);
			tempChar[i*9+7] = '\r';
			tempChar[i*9+8] = '\n';
		}
		return new String(tempChar);
	}
	


	 static void printBestSolution(Game game, GameSolver gameSolver) {
		String tempString = game.name + " Best Solution" ;
		System.out.println(tempString + "<br>");
		System.out.println("<table bgcolor=#333333>");
		Board iterator = game.initialBoard;
		Board finalStep = null;
		int stepNumberTillBestSolution = game.initialBoard.stepNumberToSolution;
		HashMap<Long, BlockPrototype> blockNameMapping = new HashMap<Long, BlockPrototype>();
		for (int i = 0; i < game.initialBoard.blocks.length; i++) {
			blockNameMapping.put(game.initialBoard.blocks[i].bitImage, game.initialBoard.blocks[i].prototype);
		}

		do {
			Board nextBoardBestSolution = null;
			Move moveBestSolution = null;
			tempString = "";
			if (iterator.stepNumberToSolution > 0) {
				nextBoardBestSolution = iterator.nextBoard;
				if (nextBoardBestSolution.stepNumberToSolution >= iterator.stepNumberToSolution) {
					nextBoardBestSolution = null;
					moveBestSolution = null;
				}
				else {
					//printBoardLayout(iterator);
					moveBestSolution = iterator.calculcateMove(nextBoardBestSolution);
					int stepNumber = game.initialBoard.stepNumberToSolution - iterator.stepNumberToSolution + 1;
					if (stepNumber % 5 == 1 ) tempString = "<tr>";
					tempString += "<td bgcolor=#ffffff>";
					if (VERBOSE_MODE) tempString += stepNumber + ": Board #" + iterator.idOfBoardsExplored 
							+ "->" + (blockNameMapping.get(moveBestSolution.oldBlock)).name + "(" /*+moveBestSolution.oldBlock.hashString()*/ + ") " + moveBestSolution.moveType;
					else tempString += stepNumber + ": " + (blockNameMapping.get(moveBestSolution.oldBlock.bitImage)).name  + " " + moveBestSolution.moveType;
					tempString += "</td>";
					if (stepNumber % 5 == 0 ) tempString += "</tr>";
				}
				//if (VERBOSE_MODE) tempString += " (" + PrintUtility.getBoardInfo(iterator) + ")";
			}
			System.out.println(tempString );
			assert stepNumberTillBestSolution == iterator.stepNumberToSolution;
			if (moveBestSolution != null) {
				blockNameMapping.put(moveBestSolution.newBlock.bitImage, blockNameMapping.get(moveBestSolution.oldBlock.bitImage));

			}
			stepNumberTillBestSolution = stepNumberTillBestSolution - 1;
			finalStep = iterator;
			iterator = nextBoardBestSolution;
		} while (iterator != null);
		if (game.initialBoard.stepNumberToSolution % 5 != 0) {
			for (int i= game.initialBoard.stepNumberToSolution % 5; i < 5; i++ ) {
				System.out.println("<td bgcolor=#dddddd>&nbsp;</td>");
			}
		}
		System.out.println("</table><br>");
		System.out.println("<table><tr><td>");
		printBoardLayout(game.initialBoard);
		System.out.println("</td><td width=50 align=center valign=center><font size=20>&#x2192;</font></td><td>");
		for (Block block : finalStep.blocks) {
			block.prototype = blockNameMapping.get(block.bitImage);
		}
		printBoardLayout(finalStep);
		System.out.println("</td></tr></table>");
		tempString = game.name + " " + gameSolver.getClass().getSimpleName() + " End"  ;
		System.out.println(tempString+"<br>");
			System.out.println("boardsExplored.size=" + gameSolver.boardsExplored.size() + "<br>");

	}
	 
		public static void printBoardLayout(Board board) {
			String tempString = "";
			System.out.println("<table bgcolor=#333333>");
			for (int i = 0; i <= Board.MAXYPOS; i++) {
				tempString = "<tr>";
				for (int j = 0; j <= Board.MAXXPOS; j++) {
					boolean isEmpty = true;
					for (int k = 0; k < board.blocks.length; k++) {
						if (j >= board.blocks[k].xPos() && j < board.blocks[k].xPos() + board.blocks[k].prototype.width
								&& i >= board.blocks[k].yPos() && i < board.blocks[k].yPos() + board.blocks[k].prototype.height) {
							isEmpty = false;
							if (i == board.blocks[k].yPos() && j == board.blocks[k].xPos()) {
								switch (board.blocks[k].prototype.blockType) {
									case SQUARE:
											tempString += "<td colspan=2 rowspan=2 width=70 height=70 align=center valign=cener bgcolor=#dddddd>" + board.blocks[k].prototype.name + "</td>";
											break;
									case HORIZONTAL:
											tempString += "<td colspan=2 width=70 height=35 align=center valign=cener bgcolor=#dddddd>" + board.blocks[k].prototype.name + "</td>";
											break;
									case VERTICAL:
										tempString += "<td rowspan=2 width=35 height=70 align=center valign=cener bgcolor=#dddddd>" + board.blocks[k].prototype.name.substring(0, 1) + "<br>" + board.blocks[k].prototype.name.substring(1)+ "</td>";
										break;
									case SINGLE:
										tempString += "<td width=35 height=35 align=center valign=cener bgcolor=#dddddd>" + board.blocks[k].prototype.name + "</td>";
										break;
								}
							}
							break; // break out of k
						}					
					}
					if (isEmpty) {
						tempString += "<td width=35 height=35 align=center valign=cener bgcolor=#ffffff>&nbsp;</td>";
					}
					
				}
				tempString += "</tr>";
				System.out.println(tempString);
			}
			System.out.println("</table><br>");

		}
		static public String toString(MutableBigInteger id) {
			String temp = String.format("%1$143s", id.toBinaryString());
			temp = temp.replace(' ', '0');
			char[] tempChar = new char[169];
			for (int i = 0; i < 13; i++) {
				temp.getChars(i * 11, (i+1)*11, tempChar, i*13);
				tempChar[i*13+11] = '\r';
				tempChar[i*13+12] = '\n';
			}
			return new String(tempChar);
		}
		
		static public void printID(MutableBigInteger id) {
			String temp = toString( id);
			System.out.println(temp);
			System.out.println("");

		}
		
}
