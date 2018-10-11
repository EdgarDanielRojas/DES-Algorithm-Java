
import java.util.Random;
import java.lang.StringBuilder;
public class DESObject{

    private long DESKey;
    private long subKeys[] = new long[16];
    private int pc1[] = {57,49,41,33,25,17,9,1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,60,52,44,36,63,55,47,39,31,23,15,7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4};
	private int pc2[] = {14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32};
    private int ip[] = {58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,62,54,46,38,30,22,14,6,64,56,48,40,32,24,16,8,57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7};
    private int ip_1[] = {40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,38,6,46,14,54,22,62,30,37,5,45,13,53,21,61,29,36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27,34,2,42,10,50,18,58,26,33,1,41,9,49,17,57,25};
    private int sn[][]={
     {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7,0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8,4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0,15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13},
     {15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10,3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5,0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15,13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9},
     {10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8,13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1,13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7,1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12},
     {7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15,13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9,10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4,3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14},
     {2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9,14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6,4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14,11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3},
     {12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11,10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8,9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6,4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13},
     {4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1,13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6,1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2,6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12},
     {13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7,1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2,7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8,2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}
    };
    private int e[]={32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1};
    private int p[]={16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25};

    public DESObject(){
        this.DESKey = generateKey();
        generateSubkeys(this.DESKey);
        for(int i = 0;i<64;i++){
            setBit(0,i);
        }
    }

    public String encrypt(String message){
        String encrypted = "";
        long block;
        long left,right;
        int length = message.length();
        if(length%8!=0){
            int buffer = 8 - (length%8);
            for(int i = 0 ; i<buffer; i++){
                message += "0";
            }
        }
        length = message.length();
        int rounds = length/8;
        long block2=0;
        for(int i = 0; i<rounds; i++){
            block2 = 0 ;
            block = convertStringToLong(message.substring(i*8,(i*8)+8));
            for(int j = 0 ; j<64 ; j++){
                if(getBit(block,(ip[63-j])-1)==1){
                    block2 = setBit(block2,j);
                }
            }
            block=block2;
            left = 0;
            right = 0;
            long temp = 0;
            for(int j = 0 ; j<32; j++){
                if(getBit(block,j)==1){
                    right = setBit(right,j);
                if(getBit(block,j+32) == 1)
                    left = setBit(left,j);
                }
            }
            for(int j = 0 ; j<16; j++){
                temp = left;
                left = right;
                right = temp ^ feistalFunction(left,j);
            }
            long finalBlock=0;
            finalBlock = finalBlock | left;
            finalBlock = finalBlock | (right << 32);
            block2=0;
            for(int j = 0 ; j<64 ; j++){
                if(getBit(finalBlock,ip_1[63-j] - 1)==1)
                    block2 = setBit(block2,j);
            }
            finalBlock = block2;
            encrypted += convertLongToString(finalBlock);
        }
        return encrypted;
    }

    private long feistalFunction(long num,int keynum){
        long newNum = 0;
        for(int i = 0 ; i<48; i++){
            if(getBit(num,e[47-i]) == 1)
                newNum = setBit(newNum,i);
        }
        newNum = newNum ^ subKeys[keynum];
        long sNumber=0;
        long offset;
        for(int i = 0 ; i<8 ; i++){
            offset = 0;
            offset = (2*getBit(newNum,(47-(i*6))) + getBit(newNum,47-(i*6)-5))*16;
            offset = 8*getBit(newNum,47-(i*6)-1) + 4*getBit(newNum,47-(i*6)-2) + 2*getBit(newNum,47-(i*6)-3) + getBit(newNum,47-(i*6)-4);
            offset = sn[i][(int)offset];
            offset = offset << (i*4);
            sNumber = sNumber|offset;
        }
        long returnNumber=0;
        for(int i = 0 ; i<32; i++){
            if(getBit(sNumber,i)==1)
                returnNumber = setBit(returnNumber,i);
        }
        return returnNumber;
    }

    private String convertLongToString(Long numb){
        int bytes = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i<16; i++){
            bytes = 0 ;
            for(int j = 0 ; j<4;j++){
                if(getBit(numb,63 - (i*4) - (3-j)) == 0)
                    bytes = bytes | (int)(Math.pow(2,j));
            }
            sb.append(String.format("%X", bytes));
        }
        return sb.toString();
    }

    private long convertStringToLong(String s){
        long stringNum=0;
        byte[] bytes = s.getBytes();
        for(int i = 7 ; i>=0; i--){
            for(int j = 0; j<8;j++){
                if(getBit(bytes[i],j)==1)
                    stringNum = setBit(stringNum,((7-i)*8)+j);
            }
        }
        return stringNum;
    }

    public String decrypt(String message){
        String decrypted = "";
        return decrypted;
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
        return keyGenerated;
    }

    private long setBit(long num,int k){
        long newNum = num | ((long)1<<k);
        return newNum;
    }

    private long getBit(long num, int k){
        return (num >> k) & 1;
    }

}
