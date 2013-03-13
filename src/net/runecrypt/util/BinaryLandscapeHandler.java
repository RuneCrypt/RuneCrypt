/*
 * This file is part of Ieldor.
 *
 * Ieldor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Ieldor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ieldor.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.runecrypt.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles anything related to the landscape keys in binary format.
 *
 * @author Thomas Le Godais <thomaslegodais@live.com>
 */
public class BinaryLandscapeHandler {

    /**
     * The {@link Map} that is used to store the landscape keys.
     */
    private static Map<Integer, int[]> landscapeKeys = new HashMap<Integer, int[]>();

    /**
     * Was too lazy to implement this in, so I decided to make this runnable.
     * This is just going to pack XTEAs.
     *
     * @param args The command line arguments.
     * @throws IOException an IO based error has occured.
     */
    public static void main(String... args) throws IOException {
        final DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("./data/landscapeKeys.bin"));
        for (int i = 0; i < 16384; i++) {
            File mapFile = new File("./data/landscapeKeys/" + i + ".txt");
            if (mapFile.exists()) {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader("./data/landscapeKeys/" + i + ".txt"));
                for (int ii = 0; ii < 4; ii++)
                    outputStream.writeInt(Integer.parseInt(bufferedReader.readLine()));
                bufferedReader.close();
            } else
                for (int ii = 0; ii < 4; ii++)
                    outputStream.writeInt(0);
        }
        outputStream.flush();
        outputStream.close();
        System.out.println("Finished packing landscape keys!");
    }

    /**
     * Loads the binary format of landscape keys.
     *
     * @throws IOException An I/O error has occured.
     */
    @SuppressWarnings("resource")
    public static void loadLandscapes() throws IOException {
        File mapFile = new File("./data/mapxtea.dat");
        DataInputStream in = new DataInputStream(new FileInputStream(mapFile));
        for (int index = 0; index < mapFile.length() / (4 + (4 * 4)); index++) {
            int regionId = in.readInt();
            int[] landscapeHash = new int[4];
            for (int landscape = 0; landscape < 4; landscape++) {
                landscapeHash[landscape] = in.readInt();
            }
            landscapeKeys.put(regionId, landscapeHash);
        }
        System.out.println("Successfully loaded " + landscapeKeys.size() + " landscape keys.");
    }

    /**
     * Gets the landscape key based off the region.
     *
     * @param region The region id.
     * @return The landscape hash.
     */
    public static int[] get(int region) {
        return landscapeKeys.get(region);
    }
}
