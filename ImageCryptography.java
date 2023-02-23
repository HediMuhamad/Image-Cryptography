
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCryptography {
    static File file = null;

    static enum operationTypes {
        ENC,
        DEC
    }

    static void printUsage() {
        System.out.println("Usage: java ImageCryptography <operation> <key> <file>");
        System.out.println("Operation: enc/dec");
        System.out.println("Key: 32bit integer");
        System.out.println("File: Path to file");
    }

    static boolean isEncrypted(File file) {
        try {
            BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(file);
            if (image == null)
                return true;
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    static void operation(int key, File file, boolean doEncrypt) {
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[fis.available()];
            fis.read(data);

            int i = 0;
            for (byte b : data) {
                data[i] = (byte) (b ^ key);
                i++;
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if(args.length < 3){
            printUsage();
            System.exit(0);
        }

        operationTypes operation = operationTypes.valueOf(args[0].toUpperCase());
        String keyString = args[1];
        String filePath = args[2];

        int key = 0;
        try {
            key = Integer.parseInt(keyString);
        } catch (Exception e) {
            System.out.println("Invalid key, should be an integer value.");
            System.exit(0);
        }
        

        try {
            file = new File(filePath);

            if(!file.exists()){
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("File not found.");
            System.exit(0);
        }

        if (operation == operationTypes.ENC) {
            if (isEncrypted(file)) {
                System.out.println("File is already encrypted.");
                System.exit(0);
            }
            operation(key, file, true);
            System.out.println("File encrypted successfully.");
        } else if (operation == operationTypes.DEC) {
            if (!isEncrypted(file)) {
                System.out.println("File is not encrypted.");
                System.exit(0);
            }
            System.out.println("File decrypted successfully.");
        } else {
            System.out.println("Invalid operation.");
            System.exit(0);
        }
    }
}