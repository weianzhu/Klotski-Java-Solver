package huaRongDao;

import java.math.BigInteger;


/**
 * Initially I used a number longer that 64-bits to store the image of the Board.  java.math.BigInteger is immutable.
 * It allocates a new object for every operation.  That is a little inefficient for repeated operations.  
 * So I wrote this MutableBigInteger.  I later realized 63 bits are enough for the image and this class becomes 
 * deprecated.  But it could be useful if we need to expand the puzzle size.  
 * I should use long instead of int in this class. 
 * 
 * @author weian.zhu
 *
 */
@Deprecated
public class MutableBigInteger implements Cloneable {
	
	
	static MutableBigInteger ZERO = new MutableBigInteger(0);
	static final int NO_OF_INTS = 5; // max number of bits = 5 * 32 = 160 bits
	int[] mag = new int[NO_OF_INTS]; // internal representation of the big integer
	
	public MutableBigInteger() {
		
	}
	public MutableBigInteger(int x) {
		mag[NO_OF_INTS - 1] = x;
	}
	
	private MutableBigInteger(int[] mag) {
		this.mag = mag;
	}
	
	@Override
	protected 	Object clone() {
		MutableBigInteger newObj = new MutableBigInteger();
		for (int i = 0; i < NO_OF_INTS; i ++) 
			newObj.mag[i] = mag[i];
		return newObj;
	}
	
	
	protected MutableBigInteger copyFrom(MutableBigInteger obj) {
		for (int i = 0; i < NO_OF_INTS; i ++) 
			mag[i] = obj.mag[i];
		return this;
	}
	
	public MutableBigInteger shiftLeft(int n) {
        int nInts = n/32;
        int nBits = n%32;

        
        int i, j;
		for (i = 0, j = nInts; i < NO_OF_INTS - nInts - 1 && j < NO_OF_INTS -1; i++, j++) {
			mag[i] = mag[j] << nBits | (nBits == 0 ? 0 : mag[j+1] >>> (32 - nBits));
		}
		mag[i] = mag[j] << nBits;
		for (i = NO_OF_INTS - nInts; i < NO_OF_INTS; i ++) {
			mag[i] = 0;
		}
		return this;
	}
	
	public MutableBigInteger shiftRight(int n) {
        int nInts = n/32; 
        int nBits = n%32;

        int i, j;
		for (i = NO_OF_INTS - 1, j = NO_OF_INTS - 1 - nInts; i >  nInts   && j > 0; i--, j--) {
			mag[i] = mag[j] >>> nBits | (nBits == 0 ? 0 : mag[j-1] << (32 - nBits));
		}
		mag[i] = mag[j] >>> nBits;
		for (i = nInts - 1; i > 0; i --) {
			mag[i] = 0;
		}
		return this;
	}



	public MutableBigInteger xor(MutableBigInteger n) {
		for (int i = 0; i < NO_OF_INTS; i++) {
			mag[i] ^= n.mag[i];
		}
		
		return this;
	}

	public MutableBigInteger and(MutableBigInteger n) {
		for (int i = 0; i < NO_OF_INTS; i++) {
			mag[i] &= n.mag[i];
		}
		
		return this;
	}

	public MutableBigInteger or(MutableBigInteger n) {
		for (int i = 0; i < NO_OF_INTS; i++) {
			mag[i] |= n.mag[i];
		}
		return this;
	}


	public String toBinaryString() {
		StringBuilder str = null;
		for (int i = 0; i < NO_OF_INTS; i++) {
			if ((mag[i] != 0 || i == NO_OF_INTS - 1) && str == null ) {
				str = new StringBuilder(Integer.toBinaryString(mag[i]));
			}
			else if (str != null) {
				str.append(String.format("%1$32s",  Integer.toBinaryString(mag[i])));
			}
		}
		return str.toString();
	}
	
	@Override
    public boolean equals(Object x) {
        MutableBigInteger xInt = (MutableBigInteger) x;
        for (int i = 0; i < NO_OF_INTS; i++)
            if (this.mag[i] != xInt.mag[i])
                return false;

        return true;
    }
	
    final static long LONG_MASK = 0xffffffffL;
    int hashCode = 0;
    @Override
    public int hashCode() {
    	if (hashCode != 0) return hashCode;
        //int hashCode = 0;

        for (int i=0; i < mag.length; i++)
            hashCode = (int)(31*hashCode + (mag[i] & LONG_MASK));

        return hashCode ;
    }


}
