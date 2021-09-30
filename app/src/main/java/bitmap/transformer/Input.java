package bitmap.transformer;
import java.util.Scanner;
public class Input {


    private static String fileToManipulate;
    private static String newFileName;
    private static int transformerChoice;



    private static Scanner scan = new Scanner(System.in);
    static void userInput() {
        System.out.println("Enter a Bitmap file with .bmp extention");
        fileToManipulate = scan.nextLine();

        System.out.println("Enter a name for your new file ");
        newFileName = scan.nextLine();
        do {
            System.out.println(
                    "---------------------------------\n" +

                    "1 : gray scale\n" +
                    "2 : Flip Horizontal\n" +
                    "3 : add water mark\n");
            System.out.println("Enter a transformation type (enter a number only): ");
            try {
                transformerChoice = Integer.parseInt(scan.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("You did not enter a number, please try again\n");
            }
        } while (true);
        System.out.println();
        manipulateBitmap();
    }

    private static void manipulateBitmap() {


            if (transformerChoice == 1 || transformerChoice == 2 || transformerChoice == 3 ) {
                setBitmapClass();
            }
            else System.out.println("\nNot a correct option\n");


    }

    private static void setBitmapClass() {
        String imageFilePath = "./app/src/main/resources/" + fileToManipulate;
        String newFilePath = "./app/src/main/resources/";
        String newFile = newFileName + ".bmp";

        Bitmap newImage = new Bitmap(imageFilePath, newFilePath, newFile);
        newImage.readFile();

        if (transformerChoice == 1) {
            newImage.BlackAndWhite();
        }  else if(transformerChoice == 2){
            newImage.imageFlipVertical();
        }
        else{
            System.out.println("Enter the text you want to add");
            Scanner sc = new Scanner(System.in);
           String watermark = scan.nextLine();
            newImage.addTextWatermark(watermark);
        }

        newImage.saveFile();

    }
}
