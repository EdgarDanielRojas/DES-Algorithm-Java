
// Created by Edgar Daniel Rojas Vazquez
import java.util.Random;
import java.lang.StringBuilder;
public class DESObject{

    // Here we have the variables used in the class, most of them are hardcoded but the optimum solution would be to generate them dynamically
    private long DESKey; // Used to store our 64 bit key.
    private long subKeys[] = new long[16]; // Used to store our 16, 48 bit keys.
    // PC1 and PC2 are simply arrays that store the order in which bits should be changed
    // PC1 and PC2 are used for generating the subkeys
    private int pc1[] = {57,49,41,33,25,17,9,1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,60,52,44,36,63,55,47,39,31,23,15,7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4};
	private int pc2[] = {14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32};
    // IP an IP_1 are used for the encryption itself, serving the same purpose as PC1 and PC2 of ordering bits in a new fashion 
    private int ip[] = {58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,62,54,46,38,30,22,14,6,64,56,48,40,32,24,16,8,57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7};
    private int ip_1[] = {40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,38,6,46,14,54,22,62,30,37,5,45,13,53,21,61,29,36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27,34,2,42,10,50,18,58,26,33,1,41,9,49,17,57,25};
    // SN holds are S boxes used in the encryption process, it holds 8 boxes with 64 values in eache one
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
    // E is used to convert a 32 bit number into a 48 bit number while P is used as a permutation of the final result of the feistal function.
    private int e[]={32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1};
    private int p[]={16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25};

    public DESObject(){
        // First we generate our key
        this.DESKey = generateKey();
        // Once our key is generated we generate our subkeys
        generateSubkeys(this.DESKey);
    }

    // This is the function that actually encrypts the message
    public String encrypt(String message){
        String encrypted = "";
        long block;
        long left,right;
        int length = message.length();
        // First we pad the string if it is necessary with 0s 
        // so that we can divide it into 8 character chunks
        if(length%8!=0){
            int buffer = 8 - (length%8);
            for(int i = 0 ; i<buffer; i++){
                message += "0";
            }
        }
        length = message.length();
        int rounds = length/8;
        long block2=0;
        // We convert each 8 character block into the encrypted hex value
        for(int i = 0; i<rounds; i++){
            block2 = 0 ;
            // We get our 8 character block as a long value
            block = convertStringToLong(message.substring(i*8,(i*8)+8));
            // An initial permutation is applied to the block
            for(int j = 0 ; j<64 ; j++){
                if(getBit(block,(ip[63-j])-1)==1){
                    block2 = setBit(block2,j);
                }
            }
            block=block2;
            left = 0;
            right = 0;
            long temp = 0;
            // We divide our block into left and right, each variable holds 32 bits.
            for(int j = 0 ; j<32; j++){
                if(getBit(block,j)==1){
                    right = setBit(right,j);
                if(getBit(block,j+32) == 1)
                    left = setBit(left,j);
                }
            }
            // The block goes through 16 rounds of encryption.
            // Our left variable now holds the previous rounds right and the
            // right now holds the previous left xor with the feistal function
            // applied to the previous right 
            for(int j = 0 ; j<16; j++){
                temp = left;
                left = right;
                right = temp ^ feistalFunction(left,j);
            }
            long finalBlock=0;
            // We join both sides, switching the left to the right and the right to the left
            finalBlock = finalBlock | left;
            finalBlock = finalBlock | (right << 32);
            block2=0;
            // An inverse of the initial permutation is applied to the final block
            for(int j = 0 ; j<64 ; j++){
                if(getBit(finalBlock,ip_1[63-j] - 1)==1)
                    block2 = setBit(block2,j);
            }
            finalBlock = block2;
            // We convert the final result to hex and append it to our string to return
            encrypted += convertLongToString(finalBlock);
        }
        return encrypted;
    }

    // This function applys a fesital function to a 32 bit number with a specified subkey
    private long feistalFunction(long num,int keynum){
        long newNum = 0;
        // The num that comes in gets converted so that it matches our E selection bit table
        // This expands our 32 bit number to a 48 bit number
        for(int i = 0 ; i<48; i++){
            if(getBit(num,e[47-i]) == 1)
                newNum = setBit(newNum,i);
        }
        // Next apply an xor with our subkey at index keynum
        newNum = newNum ^ subKeys[keynum];
        long sNumber=0;
        long offset;
        // Now that we have our number it is necessary to convert back to
        // our 32 bit number which is done with our S boxes
        for(int i = 0 ; i<8 ; i++){
            // The index in the S box is calculated
            offset = 0;
            offset = (2*getBit(newNum,(47-(i*6))) + getBit(newNum,47-(i*6)-5))*16;
            offset = 8*getBit(newNum,47-(i*6)-1) + 4*getBit(newNum,47-(i*6)-2) + 2*getBit(newNum,47-(i*6)-3) + getBit(newNum,47-(i*6)-4);
            offset = sn[i][(int)offset];
            // We get the value in the s box and shift it 4 bits to the left and apply an or to our variable which will store it
            offset = offset << (i*4);
            sNumber = sNumber|offset;
        }
        long returnNumber=0;
        // This is equivalent to returnNumber = sNumber
        for(int i = 0 ; i<32; i++){
            if(getBit(sNumber,i)==1)
                returnNumber = setBit(returnNumber,i);
        }
        return returnNumber;
    }

    // This function converts a long into a string
    // the returned string is a hex representation
    // of the long as converting to string gives us odd characters
    private String convertLongToString(Long numb){
        int bytes = 0;
        // Create a StringBuilder to store our new string
        StringBuilder sb = new StringBuilder();
        // The long is broken into 16 pieces which will be converted into a 
        // hexadecimal number and added to the string
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

    // This function converts a string into a long
    // The way the algorithm works makes this function only accept 8 character
    // strings correctly. If more than 8 characters are passed the algorithm 
    // will convert only the rightmost 8 characters.
    private long convertStringToLong(String s){
        long stringNum=0;
        // We get the bytes that make up the string
        // in ascii each byte represents a letter
        byte[] bytes = s.getBytes();
        // We convert each byte so that it gets added to 
        // the long number that is going to be returned
        for(int i = 7 ; i>=0; i--){
            for(int j = 0; j<8;j++){
                if(getBit(bytes[i],j)==1)
                    stringNum = setBit(stringNum,((7-i)*8)+j);
            }
        }
        return stringNum;
    }

    // Work in progess to implement decryption
    public String decrypt(String message){
        String decrypted = "";
        return decrypted;
    }

    // This function generates our subkeys that are necesary for the algorithm
    private void generateSubkeys(long key){
        // A 56 bit key is generated using our PC1 array. 
        long firstKey = 0;
        for(int i = 0 ; i<56 ; i++){
            // If bits from key at the position PC1[i]-1 is on, turn on bit at position i
            if(getBit(key,pc1[i]-1) == 1)
                firstKey = setBit(firstKey,i);
        }
        // We must generate 16 C and D
        // C = 28 left bits and D = 28 right bits
        long c[] = new long[17];
        long d[] = new long[17];
        // We generate our C0 and D0 dividing our 56 bit key in two
        for(int j = 0; j<28; j++){
            if(getBit(firstKey,j+28) == 1)
                c[0] = setBit(c[0],j);
            if(getBit(firstKey,j) == 1)
                d[0] = setBit(d[0],j);
        }
        // To generate the rest of C and D we shift the previous C and D by a set amount 
        // that can change
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

        // We must now unite each of the C and D to get CD and apply our PC2 array to get the 
        // actual subkeys.
        // We repeat this 16 times with each index of C and D to get our 16 subkeys
        long cdn = 0;
        for(int i=1;i<17;i++){
            cdn = 0 ;
            // We combine our left and right parts (C and D) to create a 58 bit number
            for(int j = 0; j<28 ;j++){
                if(getBit(d[i],j)==1)
                    cdn = setBit(cdn,j);
            }
            for(int j = 0; j<28 ;j++){
                if(getBit(c[i],j)==1)
                    cdn = setBit(cdn,j+28);
            }
            // We then pass that number through our PC2 array to create our subkey.
            for(int j=0; j<48; j++){
                if(getBit(cdn,pc2[j]-1)==1)
                subKeys[i-1] = setBit(subKeys[i-1],j);
            }
        }
    }

    // This function shifts a bits to the left and cycles the leftmost bit to the beginning.
    // You can specify how many left shifts to apply with shift
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

    // This function is for debugging purposes to print a long into a binary representation
    private void printBits(long num, int length){
        String binaryRepresentation = "";
        // The number is looked for bit by bit and stops once it's length is met
        for(int i = length-1; i>=0;i--){
            binaryRepresentation += getBit(num,i);
        }
        System.out.println(binaryRepresentation);
    }

    // This function generates the key used for the algorithm
    private long generateKey(){
        long keyGenerated = 0 ;
        // We generate random booleans to set bits on and off from 0 to 63
        Random random = new Random();
        for(int i = 0 ; i < 64 ; i++){
            // If boolean is true we turn on the ith bit of the key
            if(random.nextBoolean())
                keyGenerated = setBit(keyGenerated,i);
        }
        return keyGenerated;
    }

    // This function helps set a bit at k position from the number num. Numbering begins from 0 and is from right to left
    private long setBit(long num,int k){
        long newNum = num | ((long)1<<k);
        return newNum;
    }

    // This function helps get a bit at k position from the number num. Numbering begins from 0 and is from right to left
    private long getBit(long num, int k){
        return (num >> k) & 1;
    }

}
