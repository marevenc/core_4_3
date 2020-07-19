import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        final String pathZip = "C://Games//savegames//saves.zip";
        final String pathUnzip = "C://Games//savegames";
        ArrayList<String> savesPath = openZip(pathZip, pathUnzip);
        for(String path : savesPath){
            System.out.println(openProgress(path));
        }
    }

    public static ArrayList<String> openZip(String pathZip, String pathUnzip) {
        ArrayList<String> savesPath = new ArrayList<>();
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathZip))) {
            ZipEntry entry;
            String name;

            while ((entry = zin.getNextEntry()) != null) {
                name = pathUnzip + File.separator + entry.getName();
                savesPath.add(name);
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return savesPath;
    }

    public static GameProgress openProgress(String pathGame){
        GameProgress gameProgress = null;

        try (FileInputStream fis = new FileInputStream(pathGame);
           ObjectInputStream ois = new ObjectInputStream(fis)){
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return gameProgress;
    }
}
