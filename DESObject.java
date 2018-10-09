
import java.util.Random;

public class DESObject{

    private long DESKey;
    private long subKeys[] = new long[16];
    private int pc1[] = {57,49,41,33,25,17,9,1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,60,52,44,36,63,55,47,39,31,23,15,7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4}
	public DESObject(){
        this.DESKey = generateKey();

    }

    private long generateKey(){
        long keyGenerated = 0 ;
        Random random = new Random();
        for(int i = 0 ; i < 64 ; i++){
            if(random.nextBoolean())
                keyGenerated = setBit(keyGenerated,i);
        }
        //System.out.println(keyGenerated);
        return keyGenerated;
    }

    private long setBit(long num,int k){
        long newNum = num | (long)(Math.pow(2,k));
        return newNum;
    }

    private long getBit(long num, int k){
        return (num >> k) & 1;
    }

}
