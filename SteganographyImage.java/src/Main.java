// Required Java libraries for image processing and user input/output
// Required for image manipulation and I/O operations
import java.awt.image.BufferedImage;  // Represents an image in memory for manipulation
import java.io.File;  // Provides file-related operations
import java.io.IOException;  // Handles I/O exceptions
import javax.imageio.ImageIO;  // Reads and writes images
import javax.swing.JFileChooser;  // Provides a file chooser dialog for user file selection
import javax.swing.JOptionPane;  // Enables user input via dialogs
import javax.swing.filechooser.FileNameExtensionFilter;  // Filters file types in file chooser dialogs


// Main class for Image Steganography Project
class SteganographyImage{

    public static void main(String[] args) {
        // Get the path of the image file from user
        String imgPath = getImagePath();

        // Get the secret message from user input
        String secretMessage = getSecretMessage();

        BufferedImage image = null;
        try {
            // Read the image from the given path
            image = ImageIO.read(new File(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Encode the secret message into the image
        encodeMessage(image, secretMessage);

        try {
            // Write the modified image with the encoded message to a new file
            ImageIO.write(image, "png", new File("output_image.png"));
            System.out.println("\n Message encoded successfully! \n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Decode the message hidden in the image
        String decodedMessage = decodeMessage(image);
        System.out.println("Decoded Message: " + decodedMessage);
    }

    // Method to get the path of the image file using a file chooser dialog
    private static String getImagePath() {
        // Create a file chooser dialog for image selection
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose an image file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg", "gif"));

        // Show the file chooser and get the user's selection
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    // Method to get the secret message from user input
    private static String getSecretMessage() {
        // Use JOptionPane for user input of the secret message
        return JOptionPane.showInputDialog(null, "Enter the secret message:");
    }

    // Method to encode the secret message into the image
    private static void encodeMessage(BufferedImage img, String message) {
        // Your existing encodeMessage method implementation goes here
        // This is where the message will be hidden in the image pixels
        // ...
    }

    // Method to decode the hidden message from the image
    private static String decodeMessage(BufferedImage img) {
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();

        StringBuilder decodedMessage = new StringBuilder();
        int pixelIndex = 0;
        char currentChar = 0;

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                // Get the RGB value of the pixel at (x, y)
                int pixel = img.getRGB(x, y);
                // Extract the blue component (least significant bits contain the message)
                int blue = pixel & 0xFF;

                // Extract the message bits from the least significant bit of blue
                currentChar |= (blue & 0x01) << (7 - (pixelIndex++ % 8));

                // If 8 bits are processed (1 character), append it to the decoded message
                if (pixelIndex % 8 == 0) {
                    decodedMessage.append(currentChar);
                    currentChar = 0;
                }
            }
        }

        // Return the decoded message
        return decodedMessage.toString();
    }
}
