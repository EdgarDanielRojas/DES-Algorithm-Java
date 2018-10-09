
import java.util.Random;

public class DESObject{

    private long DESKey;
    private long subKeys[] = new long[16];
    private int pc1[] = {57,49,41,33,25,17,9,1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,60,52,44,36,63,55,47,39,31,23,15,7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4};
	private int pc2[] = {14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32};

    public DESObject(){
        this.DESKey = generateKey();
        generateSubkeys(this.DESKey);
    }

    private void generateSubkeys(long key){
        long firstKey = 0;
        for(int i = 0 ; i<56 ; i++){
            if(getBit(key,pc1[i]-1) == 1)
                firstKey = setBit(firstKey,i);
        }
        long c[] = new long[17];
        long d[] = new long[17];
        for(int j = 0; j<28; j++){
            if(getBit(firstKey,j+28) == 1)
                c[0] = setBit(c[0],j);
            if(getBit(firstKey,j) == 1)
                d[0] = setBit(d[0],j);
        }
        c[1]=leftShift(c[0],1);
        d[1]=leftShift(d[0],1);
        c[2]=leftShift(c[1],1);
        d[2]=leftShift(d[1],1);
        c[3]=leftShift(c[2],2);
        d[3]=leftShift(d[2],2);
        c[4]=leftShift(c[3],2);
        d[4]=leftShift(d[3],2);
        c[5]=leftShift(c[4],2);
        d[5]=leftShift(d[4],2);
        c[6]=leftShift(c[5],2);
        d[6]=leftShift(d[5],2);
        c[7]=leftShift(c[6],2);
        d[7]=leftShift(d[6],2);
        c[8]=leftShift(c[7],2);
        d[8]=leftShift(d[7],2);
        c[9]=leftShift(c[8],1);
        d[9]=leftShift(d[8],1);
        c[10]=leftShift(c[9],2);
        d[10]=leftShift(d[9],2);
        c[11]=leftShift(c[10],2);
        d[11]=leftShift(d[10],2);
        c[12]=leftShift(c[11],2);
        d[12]=leftShift(d[11],2);
        c[13]=leftShift(c[12],2);
        d[13]=leftShift(d[12],2);
        c[14]=leftShift(c[13],2);
        d[14]=leftShift(d[13],2);
        c[15]=leftShift(c[14],2);
        d[15]=leftShift(d[14],2);
        c[16]=leftShift(c[15],1);
        d[16]=leftShift(d[15],1);

        long cdn = 0;

        for(int i=1;i<17;i++){
            cdn = 0 ;
            for(int j = 0; j<28 ;j++){
                if(getBit(d[i],j)==1)
                    cdn = setBit(cdn,j);
            }
            for(int j = 0; j<28 ;j++){
                if(getBit(c[i],j)==1)
                    cdn = setBit(cdn,j+28);
            }
            for(int j=0; j<48; j++){
                if(getBit(cdn,pc2[j]-1)==1)
                subKeys[i-1] = setBit(subKeys[i-1],j);
            }
            printBits(subKeys[i-1],48);
        }
    }

    private long leftShift(long num, int shift){
        long temp = 0;
        for(int i = 0 ; i<shift;i++){
            temp = getBit(num,27);
            num = num<<1;
            if(temp == 1)
                num = setBit(num,0);
        }
        return num;
    }

    public String encrypt(){
        return "";
    }
    private void printBits(long num, int length){
        String binaryRepresentation = "";
        for(int i = length-1; i>=0;i--){
            binaryRepresentation += getBit(num,i);
        }
        System.out.println(binaryRepresentation);
    }

    private long generateKey(){
        long keyGenerated = 0 ;
        Random random = new Random();
        for(int i = 0 ; i < 64 ; i++){
            if(random.nextBoolean())
                keyGenerated = setBit(keyGenerated,i);
        }
        printBits(keyGenerated,64);
        System.out.println(keyGenerated);
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
