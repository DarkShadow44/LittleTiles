package com.creativemd.littletiles.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import com.creativemd.littletiles.common.utils.small.LittleTileBox;
import com.creativemd.littletiles.common.utils.small.LittleTileVec;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ShiftHandler {

    @SideOnly(Side.CLIENT)
    public abstract void handleRendering(Minecraft mc, double x, double y, double z);

    public abstract double getDistance(LittleTileVec suggestedPos);

    protected abstract LittleTileBox getNewPos(World world, int x, int y, int z, LittleTileBox suggested);

    public void init(World world, int x, int y, int z) {

    }

    public LittleTileBox getNewPosition(World world, int x, int y, int z, LittleTileBox suggested) {
        LittleTileBox oldBox = suggested.copy();
        LittleTileBox newBox = getNewPos(world, x, y, z, suggested);
        if (newBox != null) return newBox;
        return oldBox;
    }

}
