package huaRongDao;

public class BlockPrototype {
		public String name;
		public BlockType blockType;
		long bitImage ;
		public int width;
		public int height;
		
		
		public BlockPrototype(String name, BlockType blockType){
			this.name = name;
			this.blockType = blockType;
			switch (blockType) {
				case SQUARE:
					//image = (new MyBigInteger(0b111)).or((new MyBigInteger(0b111)).shiftLeft(11)).or(new MyBigInteger(0b111).shiftLeft(22));
					bitImage = 0b111L | (0b111L << 7) | (0b111L << 14);
					width = 2;
					height = 2;
					break;
				case HORIZONTAL:
					//image = new MyBigInteger(0b111);
					bitImage = 0b111L;
					width = 2;
					height = 1;
					break;
				case VERTICAL:
					//image = new MyBigInteger(1).or(new MyBigInteger(0b1).shiftLeft(11)).or(new MyBigInteger(0b1).shiftLeft(22));
					bitImage = 0b1L | (0b1L << 7) | (0b1L << 14);
					width = 1;
					height = 2;
					break;
				case SINGLE:
					//image = new MyBigInteger(0b1);
					bitImage = 0b1L;
					width = 1;
					height = 1;
					break;
			}
		}
}
