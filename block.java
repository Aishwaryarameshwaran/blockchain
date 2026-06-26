import java.security.MessageDigest;
import java.util.ArrayList;

class Block {

    public String hash;
    public String previousHash;
    public String data;
    public long timeStamp;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        this.hash = calculateHash();
    }

    public String calculateHash() {

        String input = previousHash + Long.toString(timeStamp) + data;

        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1)
                    hexString.append('0');

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

public class BlockchainImplementation {

    public static ArrayList<Block> blockchain = new ArrayList<>();

    public static void main(String[] args) {

        blockchain.add(new Block("Genesis Block", "0"));

        blockchain.add(new Block(
                "Student: Rahul | Course: Java",
                blockchain.get(blockchain.size() - 1).hash));

        blockchain.add(new Block(
                "Student: Priya | Course: Python",
                blockchain.get(blockchain.size() - 1).hash));

        blockchain.add(new Block(
                "Student: Arun | Course: AI",
                blockchain.get(blockchain.size() - 1).hash));

        System.out.println("===== BLOCKCHAIN =====");

        for (int i = 0; i < blockchain.size(); i++) {

            Block block = blockchain.get(i);

            System.out.println("\nBlock " + i);
            System.out.println("Data         : " + block.data);
            System.out.println("Previous Hash: " + block.previousHash);
            System.out.println("Hash         : " + block.hash);
        }

        if (isChainValid()) {
            System.out.println("\nBlockchain is Valid.");
        } else {
            System.out.println("\nBlockchain is Invalid.");
        }
    }

    public static boolean isChainValid() {

        Block currentBlock;
        Block previousBlock;

        for (int i = 1; i < blockchain.size(); i++) {

            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                return false;
            }

            if (!currentBlock.previousHash.equals(previousBlock.hash)) {
                return false;
            }
        }

        return true;
    }
}