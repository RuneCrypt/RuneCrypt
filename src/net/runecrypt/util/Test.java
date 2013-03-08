package net.runecrypt.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Test {

    public static void main(String... args) {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("./configs.txt"));
            StringBuilder stringBuilder = new StringBuilder("public static final int[][] LOBBY_CONFIG = new int[][] { ");

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("player.misc.sendConfig(")) {
                    String[] lineSplit = line.split(", ");

                    String idString = lineSplit[0].replace("player.misc.sendConfig(", "").replaceAll("\t\t", "");
                    String valueString = lineSplit[1].replace(");", "");

                    int configId = Integer.valueOf(idString);
                    int configValue = Integer.valueOf(valueString);

                    stringBuilder.append("new int[] {" + configId + ", " + configValue + "}, ");

                }
            }

            stringBuilder.append(" };");
            System.out.println(stringBuilder.toString());
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
