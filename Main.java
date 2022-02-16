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

    public static void createFile(String x) {
        File myFile = new File(x);
        try {
            if (myFile.createNewFile())
                System.out.println("Файл был создан   " + x);
            stringBuilderAndWrite("Файл был создан   " + x);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFile(String x) {
        File deleteFile = new File(x);
        if (deleteFile.delete())
            System.out.println("Файл был удален   " + x);
        stringBuilderAndWrite("Файл был удален   " + x);
    }

    public static void stringBuilderAndWrite(String x) {
        StringBuilder sb = new StringBuilder();
        Date date = new Date();
        sb.append(x + date);
        String ss = sb.toString();
        try (FileWriter writer = new FileWriter("r://GAMES/temp/temp.txt", true)) {
            writer.write(ss);
            writer.append('\n');
            writer.append('!');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void saveGame(String z, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(z);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String k, String z) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(k));
             FileInputStream fis = new FileInputStream(z)) {
            ZipEntry entry = new ZipEntry(z);
            zout.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openZip(String k) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(k))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openProgress(String z) {
        GameProgress gameProgress = null;
        try (FileInputStream fos = new FileInputStream(z);
             ObjectInputStream ois = new ObjectInputStream(fos)) {
            gameProgress = (GameProgress) ois.readObject();
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
