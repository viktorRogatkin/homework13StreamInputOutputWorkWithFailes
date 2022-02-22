package homework13StreamInputOutputWorkWithFailes;

import java.io.File;
import java.io.*;
import java.util.Date;
import java.util.zip.*;

public class Main {
    public static void createCatalog(String x) {
        File dir = new File(x);
        if (dir.mkdir())
            System.out.println("Каталог создан    " + x);
        stringBuilderAndWrite("Каталог создан    " + x);
    }

    public static void createFile(String y) {
        File myFile = new File(y);
        try {
            if (myFile.createNewFile())
                System.out.println("Файл был создан   " + y);
            stringBuilderAndWrite("Файл был создан   " + y);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFile(String z) {
        File deleteFile = new File(z);
        if (deleteFile.delete())
            System.out.println("Файл был удален   " + z);
        stringBuilderAndWrite("Файл был удален   " + z);
    }

    public static void stringBuilderAndWrite(String a) {
        StringBuilder stringBuilder = new StringBuilder();
        Date date = new Date();
        stringBuilder.append(a + date);
        String stringBuilderToString = stringBuilder.toString();//Чтобы получить строку, которая хранится в stringBuilder, мы можем использовать стандартный метод toString()
        try (FileWriter writer = new FileWriter("r://GAMES/temp/temp.txt", true)) {
            writer.write(stringBuilderToString);
            writer.append('\n');
            writer.append('!');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void saveGame(String b, GameProgress gameProgress) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(b);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String c, String d) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(c));
             FileInputStream fileInputStream = new FileInputStream(d)) {
            ZipEntry entry = new ZipEntry(d);
            zipOutputStream.putNextEntry(entry);
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            zipOutputStream.write(buffer);
            zipOutputStream.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openZip(String e) {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(e))) {
            ZipEntry entry;
            String name;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fileOutputStream = new FileOutputStream(name);
                for (int c = zipInputStream.read(); c != -1; c = zipInputStream.read()) {
                    fileOutputStream.write(c);
                }
                fileOutputStream.flush();
                zipInputStream.closeEntry();
                fileOutputStream.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openProgress(String f) {
        GameProgress gameProgress = null;
        try (FileInputStream fileInputStream = new FileInputStream(f);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            gameProgress = (GameProgress) objectInputStream.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(gameProgress);
    }

    public static void main(String[] args) {
        createCatalog("r://GAMES/temp");
        createFile("r://GAMES/temp/temp.txt");
        createCatalog("r://GAMES/src");
        createCatalog("r://GAMES/res");
        createCatalog("r://GAMES/savegames");
        createCatalog("r://GAMES/src/main");
        createCatalog("r://GAMES/src/test");
        createFile("r://GAMES/src/main/Main.java");
        createFile("r://GAMES/src/main/Utils.java");
        createCatalog("r://GAMES/res/drawables");
        createCatalog("r://GAMES/res/vectors");
        createCatalog("r://GAMES/res/icons");
        GameProgress gameProgress1 = new GameProgress(5, 56, 24, 23.6);
        GameProgress gameProgress2 = new GameProgress(12, 30, 12, 43.8);
        GameProgress gameProgress3 = new GameProgress(3, 44, 38, 18.2);
        saveGame("r://GAMES/savegames/save3.dat", gameProgress1);
        saveGame("r://GAMES/savegames/save3.dat", gameProgress2);
        saveGame("r://GAMES/savegames/save3.dat", gameProgress3);
        zipFiles("r://GAMES/savegames/zip.zip", "r://GAMES/savegames/save3.dat");
        deleteFile("r://GAMES/savegames/save3.dat");
        openZip("r://GAMES/savegames/zip.zip");
        openProgress("r://GAMES/savegames/save3.dat");
    }
}
