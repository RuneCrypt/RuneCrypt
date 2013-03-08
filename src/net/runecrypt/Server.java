/*
    Copyright (C) 2013, RuneCrypt Development Team.

    RuneCrypt is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    RuneCrypt is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with RuneCrypt.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.runecrypt;

import net.runecrypt.codec.Codec;
import net.runecrypt.codec.CodecManifest;
import net.runecrypt.ondemand.UpdateService;
import net.runecrypt.util.ConsoleLogger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.openrs.cache.Cache;
import org.openrs.cache.Container;
import org.openrs.cache.FileStore;

import javax.swing.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * The main class used to start the actual server application. From here, we're
 * going to start the actual server application, this will require some things
 * before we can actually begin to accept connections, such as:
 * <p>
 * First off we will start with miscellaneous things that are required such as
 * the cache for the appropriate revision, or just loading stuff like MySQL. And
 * then, we're going to continue onto finally loading the networking.
 * </p>
 *
 * @author Thomas Le Godais <thomaslegodais@live.com>
 * @author James Barton <sirjames1996@hotmail.com>
 * @since 1.0 <4:48:11 PM - Mar 3, 2013>
 */
public class Server {

    public static final BigInteger MODULUS_KEY = new BigInteger("93640689943905294863765289827408621343761622604573228853685321771858455502571982144215122897901870174704904532494432887476204270705658036872335563522839443554400688853336049767174347131774078429389825418360856177758652274802442661649907032212044334175713179544786760681935009554912428382556040178918498953291");
    public static final BigInteger PRIVATE_KEY = new BigInteger("35276179776692216990272689328911781930148323854987387997141109742975928222271383602522653908880468632120624807402765667621343778925675439405848493641994332117332306893601213916700706755356821003854299821309833095832525178993298632475524816949423812953734885712188792894365245111417299421699918648649881074073");
    private ServerContext serverContext;
    public UpdateService updateService = new UpdateService();
    private Executor executor = Executors.newCachedThreadPool();
    public Cache cache;
    public ByteBuffer checksumTable;

    /**
     * Constructs a new {@code Server} instance.
     *
     * @param serverContext The context that is used to represent the server.
     */
    public Server(ServerContext serverContext) {
        System.setOut(new ConsoleLogger(System.out));
        this.serverContext = serverContext;
    }

    /**
     * The main method required to run this application (as stated by the JVM).
     * Here is where all of the loading is going to take place, and from there,
     * we will branch out to do other things.
     *
     * @param args The command line arguments of the application, I will explain
     *             what arguments we're going to use.
     *             <p>
     *             The first argument we're going to use is a string named "host"
     *             this is the {@link InetSocketAddress} host the server will be
     *             hosting from. Next, we're going to have an argument (an
     *             integer) called "port" this is the port the
     *             {@link InetSocketAddress} will be hosting from (pairs with the
     *             address). Finally (this one is optional), this will be used to
     *             decide if the application is running in "verbose (local)"
     *             mode, so we print out data and other things (I mostly use this
     *             for debugging).
     *             </p>
     * @throws ClassNotFoundException The class can not be found.
     * @throws IllegalAccessException We do not have access to the class.
     * @throws InstantiationException We can not initate the class instance.
     */
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        boolean isVerboseMode = false;
        for (String argument : args) {
            if (argument.contains("verbose") || argument.contains("local"))
                isVerboseMode = true;
        }
        String serverAddress = args[0];
        int serverPort = Integer.valueOf(args[1]);

        System.out.println("+=====================================================+");
        System.out.println("|             RuneCrypt Server Emulator.              |");
        System.out.println("|  Copyright (C) 2013, Thomas LeGodais & James Barton |");
        System.out.println("|  Special thanks to: Graham, Lazaro, Blake, and Sean |");
        System.out.println("+=====================================================+");
        System.out.println();

        ServerContext serverContext = new ServerContext(serverAddress, serverPort, isVerboseMode);
        Server server = new Server(serverContext);

        System.out.println("Bootstrapping server...");

        String asString = JOptionPane.showInputDialog("Please select a revision.");
        int revision = Integer.valueOf(asString);

        Codec codec = Codec.forRevision(server, revision);
        CodecManifest manifest = null;

        if (codec.getClass().getAnnotation(CodecManifest.class) != null)
            manifest = codec.getClass().getAnnotation(CodecManifest.class);

        if (manifest != null && manifest.requiredProtocol() != revision)
            throw new IllegalStateException("Protocol doesn't match input!");

        if (manifest.requiredProtocol() >= 700) {
            try {
                server.cache = new Cache(FileStore.open("data/cache/" + manifest.requiredProtocol()));
                Container container = new Container(Container.COMPRESSION_NONE, server.cache.createChecksumTable().encode(true, MODULUS_KEY, PRIVATE_KEY));
                server.checksumTable = container.encode();
                server.executor.execute(server.updateService);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (codec != null) {

            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.setFactory(new NioServerSocketChannelFactory());
            bootstrap.setPipelineFactory(Codec.pipelineFactoryForRevision(revision, codec, manifest));

            if (manifest.requiredProtocol() != 317) {
                switch (manifest.requiredProtocol()) {
                    case 751:
                        int[] networkingPorts = new int[]{43594, 53594, 40001, 50001};
                        for (int i = 0; i < networkingPorts.length; i++)
                            bootstrap.bind(new InetSocketAddress(networkingPorts[i]));
                        break;
                }
            } else {
                bootstrap.setOption("localAddress", new InetSocketAddress(serverContext.getServerAddress(), serverContext.getServerPort()));
                bootstrap.bindAsync();
            }
        }

        String[] authors = manifest.authors();
        String author = Arrays.toString(authors);

        codec.setIncommingPackets();
        codec.setOutgoingPackets();
        codec.setPacketLengths();

        System.out.println("Successfully bootstrapped [revision=" + manifest.requiredProtocol() + ", authors=" + author + "]");
    }

    /**
     * Gets the context of the server application.
     *
     * @return the context.
     */
    public ServerContext getServerContext() {
        return serverContext;
    }
}
