package huaRongDao2;

import java.math.BigInteger;

public class BlockPrototype {
	public String name;
	public int width;
	public int height;
	public BlockType blockType;
	
	
	public BlockPrototype(String name, BlockType blockType){
		this.name = name;
		this.blockType = blockType;
		switch (blockType) {
			case SQUARE:
				width = 2;
				height = 2;
				break;
			case B_HORIZONTAL:
				width = 2;
				height = 1;
				break;
			case VERTICAL:
				width = 1;
				height = 2;
				break;
			case SINGLE:
				width = 1;
				height = 1;
				break;
		}
	}
}
