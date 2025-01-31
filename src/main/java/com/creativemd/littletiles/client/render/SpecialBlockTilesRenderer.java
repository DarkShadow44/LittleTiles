package com.creativemd.littletiles.client.render;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.block.BlockRenderHelper;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.littletiles.client.LittleTilesClient;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.creativemd.littletiles.common.utils.LittleTile;
import com.creativemd.littletiles.common.utils.small.LittleTileVec;
import com.creativemd.littletiles.utils.TileList;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SpecialBlockTilesRenderer extends TileEntitySpecialRenderer
        implements ISimpleBlockRenderingHandler, IItemRenderer {

    /** Used for renderInventoryBlock */
    public ItemStack currentRenderedStack = null;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        /*
         * try{ LittleTile tile = ItemBlockTiles.getLittleTile(Minecraft.getMinecraft().theWorld, currentRenderedStack);
         * if(tile != null) renderLittleTileInventory(tile, renderer, false); }catch(Exception e){ }
         */
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityLittleTiles) {
            TileEntityLittleTiles little = (TileEntityLittleTiles) tileEntity;
            TileList<LittleTile> tiles = little.getTiles();
            for (LittleTile tile : tiles) {
                ArrayList<CubeObject> cubes = tile.getRenderingCubes();
                BlockRenderHelper.renderCubes(world, cubes, x, y, z, block, renderer, null);
            }
        }

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return LittleTilesClient.modelID;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        currentRenderedStack = item;
        return item.getItem() instanceof ITilesRenderer && item.stackTagCompound != null;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (item.getItem() instanceof ITilesRenderer) {
            Minecraft mc = Minecraft.getMinecraft();
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            // GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4d(1, 1, 1, 1);

            if (type == ItemRenderType.INVENTORY) {
                if (((ITilesRenderer) item.getItem()).hasBackground(item))
                    RenderItem.getInstance().renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, item, 0, 0);

                GL11.glTranslatef(7.5F, 7.5F, 10);
                GL11.glScalef(10F, 10F, 10F);
                GL11.glScalef(1.0F, 1.0F, -1F);
                GL11.glRotatef(210F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
            } else {
                GL11.glTranslatef(0.5F, 0.5F, 0);
            }
            mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);

            ArrayList<CubeObject> cubes = ((ITilesRenderer) item.getItem()).getRenderingCubes(item);

            Vec3 size = CubeObject.getSizeOfCubes(cubes);
            double largestSide = Math.max(size.xCoord, Math.max(size.yCoord, size.zCoord));
            if (largestSide > 1) {
                double scaler = 1 / largestSide;
                GL11.glScaled(scaler, scaler, scaler);
                GL11.glTranslated(0.5 - largestSide / 2D, 0.5 - largestSide / 2D, 0.5 - largestSide / 2D);
            }
            BlockRenderHelper.renderInventoryCubes(
                    (RenderBlocks) data[0],
                    cubes,
                    Block.getBlockFromItem(item.getItem()),
                    item.getItemDamage());
            // ArrayList<LittleTile> tiles = ItemRecipe.loadTiles(Minecraft.getMinecraft().theWorld, item);
            /*
             * for (int i = 0; i < tiles.size(); i++) { //renderLittleTileInventory(tiles.get(i), (RenderBlocks)
             * data[0], true); GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F); }
             */
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTime) {
        if (tileEntity instanceof TileEntityLittleTiles) {
            TileEntityLittleTiles te = (TileEntityLittleTiles) tileEntity;
            for (int i = 0; i < te.customRenderingTiles.size(); i++) {
                LittleTileVec cornerVec = te.customRenderingTiles.get(i).cornerVec;
                te.customRenderingTiles.get(i).renderTick(
                        x + cornerVec.getPosX(),
                        y + cornerVec.getPosY(),
                        z + cornerVec.getPosZ(),
                        partialTime);
            }
        }
    }

}
