package net.runecrypt.codec.codec317.context;

import net.runecrypt.network.packet.PacketContext;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 2:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class RunEnergyContext implements PacketContext {

    public int runEnergyAmount;

    public RunEnergyContext(int runEnergyAmount) {
        this.runEnergyAmount = runEnergyAmount;
    }
}
