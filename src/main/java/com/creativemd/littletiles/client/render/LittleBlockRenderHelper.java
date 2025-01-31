package com.creativemd.littletiles.client.render;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.creativemd.littletiles.common.utils.LittleTile;
import com.creativemd.littletiles.utils.TileList;

public class LittleBlockRenderHelper {

    public static void renderBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer, TileEntityLittleTiles te) {
        TileList<LittleTile> tiles = te.getTiles();
        for (LittleTile tile : tiles) {
            ArrayList<CubeObject> cubes = tile.getRenderingCubes();
            LittleTilesBlockRenderHelper.renderCubes(world, cubes, x, y, z, block, renderer, null);
        }
    }

}
