import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Install {
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) {
        //Создание папок
        File src = new File("D://Games", "src");
        makeDir(src);
        File res = new File("D://Games", "res");
        makeDir(res);
        File savegames = new File("D://Games", "savegames");
        makeDir(savegames);
        File temp = new File("D://Games", "temp");
        makeDir(temp);
        File main = new File(src, "main");
        makeDir(main);
        File test = new File(src, "test");
        makeDir(test);
        File drawables = new File(res, "drawables");
        makeDir(drawables);
        File vectors = new File(res, "vectors");
        makeDir(vectors);
        File icons = new File(res, "icons");
        makeDir(icons);
        //Создание файлов
        File mainJava = new File(main, "Main.java");
        createFile(mainJava);
        File utils = new File(main, "Utils.java");
        createFile(utils);
        File tempTxt = new File(temp, "temp.txt");
        createFile(tempTxt);
        //Запись
        try (FileOutputStream fos = new FileOutputStream(tempTxt)){
            byte[] bytes = sb.toString().getBytes();
            fos.write(bytes, 0 , bytes.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(sb.toString());

        GameProgress player1 = new GameProgress(100, 2, 23, 2300);
        GameProgress player2 = new GameProgress(100, 5, 50, 23000);
        GameProgress player3 = new GameProgress(90, 3, 2, 230);

        saveGame("D://Games//savegames//save1.dat", player1);
        saveGame("D://Games//savegames//save2.dat", player2);
        saveGame("D://Games//savegames//save3.dat", player3);

        zipFiles("D://Games//savegames//zip1.zip", "D://Games//savegames//save1.dat");
        zipFiles("D://Games//savegames//zip2.zip", "D://Games//savegames//save2.dat");
        zipFiles("D://Games//savegames//zip3.zip", "D://Games//savegames//save3.dat");

        delete("D://Games//savegames//save1.dat");
        delete("D://Games//savegames//save2.dat");
        delete("D://Games//savegames//save3.dat");
    }
    public static void delete(String path){
        File file = new File(path);
        file.delete();
    }

    public static void saveGame (String path, GameProgress player) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(player);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles (String path, String path2) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path));
             FileInputStream fis = new FileInputStream(path2)) {
            ZipEntry entry = new ZipEntry("packed_notes.txt");
            zout.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();}
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void makeDir (File dir){
        if (dir.mkdir())
            sb.append("Папка ").append(dir.getName()).append(" создана\n");
        else
            sb.append("Папка ").append(dir.getName()).append(" не создана\n");
    }

    public static void createFile (File file){
        try {
            if (file.createNewFile())
                sb.append("Файл ").append(file.getName()).append(" создана\n");
            else
                sb.append("Файл ").append(file.getName()).append(" не создана\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
