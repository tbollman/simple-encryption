import java.util.*;
import java.io.*;

public class crypt
{
    public static GenLL<String> file = new GenLL<String>();
    public static GenLL<String> copy = new GenLL<String>();
    public static String fileName;
    public static String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    public static int count = 0;
    public static boolean quit = false;
    public static Scanner keyboard = new Scanner(System.in);
    public static Random r = new Random();
    public static void main(String[] args)
    {

        System.out.println("Welcome to the Encryption/Decryption Machine");
        menu();
        while(!quit)
            newMenu();
        System.exit(0);
    }
    public static void menu()
    {
        System.out.println("What would you like to do?" +
                "\n1) Encrypt a file" +
                "\n2) Decrypt a file" +
                "\n9) Quit");
        int choice = keyboard.nextInt();
        menuSwitch(choice);
    }
    public static void newMenu()
    {
        System.out.println("What would you like to do?" +
                "\n1) Encrypt a file" +
                "\n2) Decrypt a file" +
                "\n3) Print Recent Copy: "+fileName +
                "\n9) Quit");
        int choice = keyboard.nextInt();
        menuSwitch(choice);
    }
    public static void menuSwitch(int choice)
    {
        file = new GenLL<String>();
        copy = new GenLL<String>();
        keyboard.nextLine();
        switch(choice)
        {
            case 1: System.out.println("Please enter the name of the file to be encrypted: ");
                fileName = "./";
                fileName += keyboard.nextLine()+".txt";
                encrypt(fileName);
                break;
            case 2: System.out.println("Please enter the name of the file to be decrypted: ");
                fileName = "./";
                fileName += keyboard.nextLine()+".txt";
                decrypt(fileName);
                break;
            case 3: readFromFile(fileName);
                print(file);
                break;
            case 9: quit = true;
                break;
            default: System.out.println("Invalid Choice. Try Again.");
                break;
        }
    }
    public static void encrypt(String aFileName)
    {
        readFromFile(aFileName);
        int offset = r.nextInt(24)+1;
        System.out.println(offset);
        file.reset();
        while(file.getCurrent() != null)
        {
            String current = file.getCurrent();
            String converted = "";
            for (int i = 0; i < current.length(); i++)
            {
                if(Character.isWhitespace(current.charAt(i)))
                {
                    converted += " ";
                }
                else
                    {
                    converted += convert(current.charAt(i), offset);
                    }
            }
            copy.insert(converted);
            file.goToNext();
        }
        removeType(fileName);
        fileName += ".encrypted.txt";
        writeToFile(fileName, copy, offset);
    }
    public static void removeType(String aFileName)
    {
        fileName = "./";
        for(int i = 0; i < aFileName.length(); i++)
        {
            if(aFileName.charAt(i) != '.' && aFileName.charAt(i) != '/')
            {
                fileName += aFileName.charAt(i);
            }
            else if(aFileName.charAt(i) == '.' && i > 1)
                return;
        }
    }
    public static char convert(char letter, int offset)
    {
        String curr = Character.toString(letter);
        int index = 0;
        for(int i = 0; i < letters.length; i++)
        {
            if(curr.equalsIgnoreCase(letters[i]))
            {
                index = i;
                break;
            }
        }
        char ret = letters[index+offset].charAt(0);
        return ret;
    }
    public static char revert(char letter, int offset)
    {
        String curr = Character.toString(letter);
        int index = 0;
        for(int i = letters.length-1; i > 0; i--)
        {
            if(curr.equalsIgnoreCase(letters[i]))
            {
                index = i;
                break;
            }
        }
        return letters[index-offset].charAt(0);
    }
    public static void print(GenLL<String> aCopy)
    {
        aCopy.reset();
        count = aCopy.count();
        if(aCopy.getCurrent().equals(null))
        {
            return;
        }
        for(int i = 0; i < count; i++)
        {
            System.out.println(aCopy.getCurrent());
            aCopy.goToNext();
        }
    }
    public static void decrypt(String aFileName)
    {
        readFromFile(aFileName);
        file.reset();
        String[] splitLine = file.getCurrent().split("\\s");
        int offset = Integer.parseInt(splitLine[1]);
        if(offset < 0 || offset > 25)
        {
            System.out.println("Invalid Offset!");
            return;
        }
        System.out.println(offset);
        file.goToNext();
        while(file.getCurrent() != null)
        {
            String current = file.getCurrent();
            String converted = "";
            for (int i = 0; i < current.length(); i++)
            {
                if(Character.isWhitespace(current.charAt(i)))
                {
                    converted += " ";
                }
                else
                {
                    converted += revert(current.charAt(i), offset);
                }
            }
            copy.insert(converted);
            file.goToNext();
        }
        removeType(fileName);
        fileName += ".decrypted.txt";
        writeToFile(fileName, copy, offset);
    }
    public static void readFromFile(String aFileName)
    {
        try
        {
            Scanner fileScanner = new Scanner(new File(aFileName));
            while(fileScanner.hasNextLine())
            {
                String fileLine = fileScanner.nextLine();
                file.insert(fileLine);
                count++;
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public static void writeToFile(String aFileName, GenLL<String> copy, int offset)
    {
        try
        {
            PrintWriter fileWriter = new PrintWriter(new FileOutputStream(aFileName));
            fileWriter.println("Offset: "+offset);
            for(int i = 0; i < copy.count(); i++)
            {
                fileWriter.println(copy.getCurrent());
                copy.deleteCurrent();
                i--;
            }
            fileWriter.close();
            readFromFile(aFileName);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public static void emptyList(GenLL<String> aList)
    {
        aList.reset();
        for(int i = 0; i < aList.count(); i++)
        {
            aList.deleteCurrent();
            i--;
        }
        System.out.println(aList.getCurrent());
    }
}
