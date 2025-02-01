package com.creativemd.littletiles.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.renderer.RenderBlocks;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RenderBlocks.class)
public abstract class MixinRenderBlocks {

    @Shadow
    public int brightnessTopLeft;
    @Shadow
    public int brightnessBottomLeft;
    @Shadow
    public int brightnessBottomRight;
    @Shadow
    public int brightnessTopRight;

    @Shadow
    public double renderMinX;
    @Shadow
    public double renderMaxX;
    @Shadow
    public double renderMinZ;
    @Shadow
    public double renderMaxZ;

    @Shadow
    public boolean enableAO;

    /** Red color value for the top left corner */
    @Shadow    public float colorRedTopLeft;
    /** Red color value for the bottom left corner */
    @Shadow    public float colorRedBottomLeft;
    /** Red color value for the bottom right corner */
    @Shadow   public float colorRedBottomRight;
    /** Red color value for the top right corner */
    @Shadow   public float colorRedTopRight;
    /** Green color value for the top left corner */
    @Shadow   public float colorGreenTopLeft;
    /** Green color value for the bottom left corner */
    @Shadow public float colorGreenBottomLeft;
    /** Green color value for the bottom right corner */
    @Shadow public float colorGreenBottomRight;
    /** Green color value for the top right corner */
    @Shadow public float colorGreenTopRight;
    /** Blue color value for the top left corner */
    @Shadow public float colorBlueTopLeft;
    /** Blue color value for the bottom left corner */
    @Shadow public float colorBlueBottomLeft;
    /** Blue color value for the bottom right corner */
    @Shadow  public float colorBlueBottomRight;
    /** Blue color value for the top right corner */
    @Shadow  public float colorBlueTopRight;


    @Shadow   public IBlockAccess blockAccess;
    /** If set to >=0, all block faces will be rendered using this texture index */
    @Shadow  public IIcon overrideBlockTexture;

    @Shadow    public double renderMinY;
    /** The maximum Y value for rendering (default 1.0). */
    @Shadow    public double renderMaxY;
    @Shadow
    public abstract void renderFaceYNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_);
    @Shadow
    public abstract void renderFaceYPos(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_);
    @Shadow
    public abstract void renderFaceXNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_);
    @Shadow
    public abstract void renderFaceXPos(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_);
    @Shadow
    public abstract void renderFaceZPos(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_);
    @Shadow
    public abstract void renderFaceZNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_);

    @Shadow
    public abstract IIcon getBlockIcon(Block p_147745_1_);
    @Shadow
    public abstract IIcon getBlockIcon(Block p_147793_1_, IBlockAccess p_147793_2_, int p_147793_3_, int p_147793_4_, int p_147793_5_, int p_147793_6_);

    @Shadow
    public abstract int mixAoBrightness(int p_147727_1_, int p_147727_2_, int p_147727_3_, int p_147727_4_, double p_147727_5_, double p_147727_7_, double p_147727_9_, double p_147727_11_);

    @Shadow
    public boolean renderAllFaces;

    @Shadow public abstract boolean hasOverrideBlockTexture();

    @Shadow
    public abstract int getAoBrightness(int p_147778_1_, int p_147778_2_, int p_147778_3_, int p_147778_4_);

    @Overwrite
    public boolean renderStandardBlockWithAmbientOcclusionPartial(Block block, int x, int y, int z, float colorR, float colorG, float colorB)
    {
        this.enableAO = true;
        boolean flag = false;
        int l = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z);
        Tessellator tessellator = Tessellator.instance;

        int i1;

        int brightnessXNeg = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z);
        int brightnessYNeg = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
        int brightnessZNeg = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1);
        int brightnessZPos = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1);
        int brightnessXPos = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z);
        int brightnessYPos = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);

        float aoLightXNeg = this.blockAccess.getBlock(x - 1, y, z).getAmbientOcclusionLightValue();
        float aoLightYNeg = this.blockAccess.getBlock(x, y - 1, z).getAmbientOcclusionLightValue();
        float aoLightZNeg = this.blockAccess.getBlock(x, y, z - 1).getAmbientOcclusionLightValue();
        float aoLightZPos = this.blockAccess.getBlock(x, y, z + 1).getAmbientOcclusionLightValue();
        float aoLightXPos = this.blockAccess.getBlock(x + 1, y, z).getAmbientOcclusionLightValue();
        float aoLightYPos = this.blockAccess.getBlock(x, y + 1, z).getAmbientOcclusionLightValue();

        if (renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y - 1, z, 0))
        {
            float aoLightValueScratchXYZNNN = this.blockAccess.getBlock(x - 1, y, z - 1).getAmbientOcclusionLightValue();
           int  aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z - 1);

            float aoLightValueScratchXYZNNP = this.blockAccess.getBlock(x - 1, y, z + 1).getAmbientOcclusionLightValue();
            int aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z + 1);

            float  aoLightValueScratchXYZPNN = this.blockAccess.getBlock(x + 1, y, z - 1).getAmbientOcclusionLightValue();
            int  aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z - 1);

            float  aoLightValueScratchXYZPNP = this.blockAccess.getBlock(x + 1, y, z + 1).getAmbientOcclusionLightValue();
            int  aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z + 1);

            i1 = l;

            if (renderMinY <= 0.0D || !this.blockAccess.getBlock(x, y - 1, z).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
            }

            float f7 = this.blockAccess.getBlock(x, y - 1, z).getAmbientOcclusionLightValue();
            float f3 = (aoLightValueScratchXYZNNP + aoLightXNeg + aoLightZPos + f7) / 4.0F;
            float f6 = (aoLightZPos + f7 + aoLightValueScratchXYZPNP + aoLightXPos) / 4.0F;
            float f5 = (f7 + aoLightZNeg + aoLightXPos + aoLightValueScratchXYZPNN) / 4.0F;
            float f4 = (aoLightXNeg + aoLightValueScratchXYZNNN + f7 + aoLightZNeg) / 4.0F;
            this.brightnessTopLeft = this.getAoBrightness(aoBrightnessXYZNNP, brightnessXNeg, brightnessZPos, i1);
            this.brightnessTopRight = this.getAoBrightness(brightnessZPos, aoBrightnessXYZPNP, brightnessXPos, i1);
            this.brightnessBottomRight = this.getAoBrightness(brightnessZNeg, brightnessXPos, aoBrightnessXYZPNN, i1);
            this.brightnessBottomLeft = this.getAoBrightness(brightnessXNeg, aoBrightnessXYZNNN, brightnessZNeg, i1);

            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.5F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.5F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.5F;

            this.colorRedTopLeft *= f3;
            this.colorGreenTopLeft *= f3;
            this.colorBlueTopLeft *= f3;
            this.colorRedBottomLeft *= f4;
            this.colorGreenBottomLeft *= f4;
            this.colorBlueBottomLeft *= f4;
            this.colorRedBottomRight *= f5;
            this.colorGreenBottomRight *= f5;
            this.colorBlueBottomRight *= f5;
            this.colorRedTopRight *= f6;
            this.colorGreenTopRight *= f6;
            this.colorBlueTopRight *= f6;
            renderFaceYNeg(block, (double)x, (double)y, (double)z, this.getBlockIcon(block, this.blockAccess, x, y, z, 0));
            flag = true;
        }

        if (renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y + 1, z, 1))
        {
          float   aoLightValueScratchXYZNPN = this.blockAccess.getBlock(x - 1, y, z - 1).getAmbientOcclusionLightValue();
            int  aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z - 1);

            float  aoLightValueScratchXYZPPN = this.blockAccess.getBlock(x + 1, y, z - 1).getAmbientOcclusionLightValue();
            int  aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z - 1);

            float  aoLightValueScratchXYZNPP = this.blockAccess.getBlock(x - 1, y, z + 1).getAmbientOcclusionLightValue();
            int  aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z + 1);

            float  aoLightValueScratchXYZPPP = this.blockAccess.getBlock(x + 1, y, z + 1).getAmbientOcclusionLightValue();
            int  aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z + 1);

            i1 = l;

            if (renderMaxY >= 1.0D || !this.blockAccess.getBlock(x, y + 1, z).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);
            }

            float f7 = this.blockAccess.getBlock(x, y + 1, z).getAmbientOcclusionLightValue();
            float f6 = (aoLightValueScratchXYZNPP + aoLightXNeg + aoLightZPos + f7) / 4.0F;
            float f3 = (aoLightZPos + f7 + aoLightValueScratchXYZPPP + aoLightXPos) / 4.0F;
            float f4 = (f7 + aoLightZNeg + aoLightXPos + aoLightValueScratchXYZPPN) / 4.0F;
            float f5 = (aoLightXNeg + aoLightValueScratchXYZNPN + f7 + aoLightZNeg) / 4.0F;
            this.brightnessTopRight = this.getAoBrightness(aoBrightnessXYZNPP, brightnessXNeg, brightnessZPos, i1);
            this.brightnessTopLeft = this.getAoBrightness(brightnessZPos, aoBrightnessXYZPPP, brightnessXPos, i1);
            this.brightnessBottomLeft = this.getAoBrightness(brightnessZNeg, brightnessXPos, aoBrightnessXYZPPN, i1);
            this.brightnessBottomRight = this.getAoBrightness(brightnessXNeg, aoBrightnessXYZNPN, brightnessZNeg, i1);
            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = colorR;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = colorG;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = colorB;
            this.colorRedTopLeft *= f3;
            this.colorGreenTopLeft *= f3;
            this.colorBlueTopLeft *= f3;
            this.colorRedBottomLeft *= f4;
            this.colorGreenBottomLeft *= f4;
            this.colorBlueBottomLeft *= f4;
            this.colorRedBottomRight *= f5;
            this.colorGreenBottomRight *= f5;
            this.colorBlueBottomRight *= f5;
            this.colorRedTopRight *= f6;
            this.colorGreenTopRight *= f6;
            this.colorBlueTopRight *= f6;
            renderFaceYPos(block, (double)x, (double)y, (double)z, this.getBlockIcon(block, this.blockAccess, x, y, z, 1));
            flag = true;
        }

        IIcon iicon;

        if (renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y, z - 1, 2))
        {
            float   aoLightValueScratchXYZNNN = this.blockAccess.getBlock(x - 1, y - 1, z).getAmbientOcclusionLightValue();
            int   aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y - 1, z);

            float  aoLightValueScratchXYZNPN = this.blockAccess.getBlock(x - 1, y + 1, z).getAmbientOcclusionLightValue();
            int  aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y + 1, z);

            float  aoLightValueScratchXYZPNN = this.blockAccess.getBlock(x + 1, y - 1, z).getAmbientOcclusionLightValue();
            int  aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y - 1, z);

            float  aoLightValueScratchXYZPPN = this.blockAccess.getBlock(x + 1, y + 1, z).getAmbientOcclusionLightValue();
         int   aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y + 1, z);

            i1 = l;

            if (renderMinZ <= 0.0D || !this.blockAccess.getBlock(x, y, z - 1).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1);
            }

            float f7 = this.blockAccess.getBlock(x, y, z - 1).getAmbientOcclusionLightValue();
            float f8 = (aoLightXNeg + aoLightValueScratchXYZNPN + f7 + aoLightYPos) / 4.0F;
            float f9 = (f7 + aoLightYPos + aoLightXPos + aoLightValueScratchXYZPPN) / 4.0F;
            float f10 = (aoLightYNeg + f7 + aoLightValueScratchXYZPNN + aoLightXPos) / 4.0F;
            float f11 = (aoLightValueScratchXYZNNN + aoLightXNeg + aoLightYNeg + f7) / 4.0F;
            float f3 = (float)((double)f8 * renderMaxY * (1.0D - renderMinX) + (double)f9 * renderMaxY * renderMinX + (double)f10 * (1.0D - renderMaxY) * renderMinX + (double)f11 * (1.0D - renderMaxY) * (1.0D - renderMinX));
            float f4 = (float)((double)f8 * renderMaxY * (1.0D - renderMaxX) + (double)f9 * renderMaxY * renderMaxX + (double)f10 * (1.0D - renderMaxY) * renderMaxX + (double)f11 * (1.0D - renderMaxY) * (1.0D - renderMaxX));
            float f5 = (float)((double)f8 * renderMinY * (1.0D - renderMaxX) + (double)f9 * renderMinY * renderMaxX + (double)f10 * (1.0D - renderMinY) * renderMaxX + (double)f11 * (1.0D - renderMinY) * (1.0D - renderMaxX));
            float f6 = (float)((double)f8 * renderMinY * (1.0D - renderMinX) + (double)f9 * renderMinY * renderMinX + (double)f10 * (1.0D - renderMinY) * renderMinX + (double)f11 * (1.0D - renderMinY) * (1.0D - renderMinX));
            int j1 = this.getAoBrightness(brightnessXNeg, aoBrightnessXYZNPN, brightnessYPos, i1);
            int k1 = this.getAoBrightness(brightnessYPos, brightnessXPos, aoBrightnessXYZPPN, i1);
            int l1 = this.getAoBrightness(brightnessYNeg, aoBrightnessXYZPNN, brightnessXPos, i1);
            int i2 = this.getAoBrightness(aoBrightnessXYZNNN, brightnessXNeg, brightnessYNeg, i1);
            this.brightnessTopLeft = this.mixAoBrightness(j1, k1, l1, i2, renderMaxY * (1.0D - renderMinX), renderMaxY * renderMinX, (1.0D - renderMaxY) * renderMinX, (1.0D - renderMaxY) * (1.0D - renderMinX));
            this.brightnessBottomLeft = this.mixAoBrightness(j1, k1, l1, i2, renderMaxY * (1.0D - renderMaxX), renderMaxY * renderMaxX, (1.0D - renderMaxY) * renderMaxX, (1.0D - renderMaxY) * (1.0D - renderMaxX));
            this.brightnessBottomRight = this.mixAoBrightness(j1, k1, l1, i2, renderMinY * (1.0D - renderMaxX), renderMinY * renderMaxX, (1.0D - renderMinY) * renderMaxX, (1.0D - renderMinY) * (1.0D - renderMaxX));
            this.brightnessTopRight = this.mixAoBrightness(j1, k1, l1, i2, renderMinY * (1.0D - renderMinX), renderMinY * renderMinX, (1.0D - renderMinY) * renderMinX, (1.0D - renderMinY) * (1.0D - renderMinX));

            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;

            this.colorRedTopLeft *= f3;
            this.colorGreenTopLeft *= f3;
            this.colorBlueTopLeft *= f3;
            this.colorRedBottomLeft *= f4;
            this.colorGreenBottomLeft *= f4;
            this.colorBlueBottomLeft *= f4;
            this.colorRedBottomRight *= f5;
            this.colorGreenBottomRight *= f5;
            this.colorBlueBottomRight *= f5;
            this.colorRedTopRight *= f6;
            this.colorGreenTopRight *= f6;
            this.colorBlueTopRight *= f6;
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 2);
            renderFaceZNeg(block, (double)x, (double)y, (double)z, iicon);

            flag = true;
        }

        if (renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y, z + 1, 3))
        {
            float   aoLightValueScratchXYZNNP = this.blockAccess.getBlock(x - 1, y - 1, z).getAmbientOcclusionLightValue();
            int  aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y - 1, z);

            float   aoLightValueScratchXYZNPP = this.blockAccess.getBlock(x - 1, y + 1, z).getAmbientOcclusionLightValue();
            int  aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y + 1, z);

            float   aoLightValueScratchXYZPNP = this.blockAccess.getBlock(x + 1, y - 1, z).getAmbientOcclusionLightValue();
            int aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y - 1, z);

          float  aoLightValueScratchXYZPPP = this.blockAccess.getBlock(x + 1, y + 1, z).getAmbientOcclusionLightValue();
            int aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y + 1, z);

            i1 = l;

            if (renderMaxZ >= 1.0D || !this.blockAccess.getBlock(x, y, z + 1).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1);
            }

            float f7 = this.blockAccess.getBlock(x, y, z + 1).getAmbientOcclusionLightValue();
            float f8 = (aoLightXNeg + aoLightValueScratchXYZNPP + f7 + aoLightYPos) / 4.0F;
            float f9 = (f7 + aoLightYPos + aoLightXPos + aoLightValueScratchXYZPPP) / 4.0F;
            float f10 = (aoLightYNeg + f7 + aoLightValueScratchXYZPNP + aoLightXPos) / 4.0F;
            float f11 = (aoLightValueScratchXYZNNP + aoLightXNeg + aoLightYNeg + f7) / 4.0F;
            float f3 = (float)((double)f8 * renderMaxY * (1.0D - renderMinX) + (double)f9 * renderMaxY * renderMinX + (double)f10 * (1.0D - renderMaxY) * renderMinX + (double)f11 * (1.0D - renderMaxY) * (1.0D - renderMinX));
            float f4 = (float)((double)f8 * renderMinY * (1.0D - renderMinX) + (double)f9 * renderMinY * renderMinX + (double)f10 * (1.0D - renderMinY) * renderMinX + (double)f11 * (1.0D - renderMinY) * (1.0D - renderMinX));
            float f5 = (float)((double)f8 * renderMinY * (1.0D - renderMaxX) + (double)f9 * renderMinY * renderMaxX + (double)f10 * (1.0D - renderMinY) * renderMaxX + (double)f11 * (1.0D - renderMinY) * (1.0D - renderMaxX));
            float f6 = (float)((double)f8 * renderMaxY * (1.0D - renderMaxX) + (double)f9 * renderMaxY * renderMaxX + (double)f10 * (1.0D - renderMaxY) * renderMaxX + (double)f11 * (1.0D - renderMaxY) * (1.0D - renderMaxX));
            int j1 = this.getAoBrightness(brightnessXNeg, aoBrightnessXYZNPP, brightnessYPos, i1);
            int k1 = this.getAoBrightness(brightnessYPos, brightnessXPos, aoBrightnessXYZPPP, i1);
            int l1 = this.getAoBrightness(brightnessYNeg, aoBrightnessXYZPNP, brightnessXPos, i1);
            int i2 = this.getAoBrightness(aoBrightnessXYZNNP, brightnessXNeg, brightnessYNeg, i1);
            this.brightnessTopLeft = this.mixAoBrightness(j1, i2, l1, k1, renderMaxY * (1.0D - renderMinX), (1.0D - renderMaxY) * (1.0D - renderMinX), (1.0D - renderMaxY) * renderMinX, renderMaxY * renderMinX);
            this.brightnessBottomLeft = this.mixAoBrightness(j1, i2, l1, k1, renderMinY * (1.0D - renderMinX), (1.0D - renderMinY) * (1.0D - renderMinX), (1.0D - renderMinY) * renderMinX, renderMinY * renderMinX);
            this.brightnessBottomRight = this.mixAoBrightness(j1, i2, l1, k1, renderMinY * (1.0D - renderMaxX), (1.0D - renderMinY) * (1.0D - renderMaxX), (1.0D - renderMinY) * renderMaxX, renderMinY * renderMaxX);
            this.brightnessTopRight = this.mixAoBrightness(j1, i2, l1, k1, renderMaxY * (1.0D - renderMaxX), (1.0D - renderMaxY) * (1.0D - renderMaxX), (1.0D - renderMaxY) * renderMaxX, renderMaxY * renderMaxX);

            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;

            this.colorRedTopLeft *= f3;
            this.colorGreenTopLeft *= f3;
            this.colorBlueTopLeft *= f3;
            this.colorRedBottomLeft *= f4;
            this.colorGreenBottomLeft *= f4;
            this.colorBlueBottomLeft *= f4;
            this.colorRedBottomRight *= f5;
            this.colorGreenBottomRight *= f5;
            this.colorBlueBottomRight *= f5;
            this.colorRedTopRight *= f6;
            this.colorGreenTopRight *= f6;
            this.colorBlueTopRight *= f6;
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 3);
            renderFaceZPos(block, (double)x, (double)y, (double)z, iicon);

            flag = true;
        }

        if (renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x - 1, y, z, 4))
        {
            float   aoLightValueScratchXYZNNN = this.blockAccess.getBlock(x, y - 1, z - 1).getAmbientOcclusionLightValue();
            int  aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z - 1);

            float   aoLightValueScratchXYZNNP = this.blockAccess.getBlock(x, y - 1, z + 1).getAmbientOcclusionLightValue();
            int  aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z + 1);

            float  aoLightValueScratchXYZNPN = this.blockAccess.getBlock(x, y + 1, z - 1).getAmbientOcclusionLightValue();
           int aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z - 1);

            float  aoLightValueScratchXYZNPP = this.blockAccess.getBlock(x, y + 1, z + 1).getAmbientOcclusionLightValue();
            int aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z + 1);

            i1 = l;

            if (renderMinX <= 0.0D || !this.blockAccess.getBlock(x - 1, y, z).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z);
            }

            float f7 = this.blockAccess.getBlock(x - 1, y, z).getAmbientOcclusionLightValue();
            float f8 = (aoLightYNeg + aoLightValueScratchXYZNNP + f7 + aoLightZPos) / 4.0F;
            float f9 = (f7 + aoLightZPos + aoLightYPos + aoLightValueScratchXYZNPP) / 4.0F;
            float f10 = (aoLightZNeg + f7 + aoLightValueScratchXYZNPN + aoLightYPos) / 4.0F;
            float f11 = (aoLightValueScratchXYZNNN + aoLightYNeg + aoLightZNeg + f7) / 4.0F;
            float f3 = (float)((double)f9 * renderMaxY * renderMaxZ + (double)f10 * renderMaxY * (1.0D - renderMaxZ) + (double)f11 * (1.0D - renderMaxY) * (1.0D - renderMaxZ) + (double)f8 * (1.0D - renderMaxY) * renderMaxZ);
            float f4 = (float)((double)f9 * renderMaxY * renderMinZ + (double)f10 * renderMaxY * (1.0D - renderMinZ) + (double)f11 * (1.0D - renderMaxY) * (1.0D - renderMinZ) + (double)f8 * (1.0D - renderMaxY) * renderMinZ);
            float f5 = (float)((double)f9 * renderMinY * renderMinZ + (double)f10 * renderMinY * (1.0D - renderMinZ) + (double)f11 * (1.0D - renderMinY) * (1.0D - renderMinZ) + (double)f8 * (1.0D - renderMinY) * renderMinZ);
            float f6 = (float)((double)f9 * renderMinY * renderMaxZ + (double)f10 * renderMinY * (1.0D - renderMaxZ) + (double)f11 * (1.0D - renderMinY) * (1.0D - renderMaxZ) + (double)f8 * (1.0D - renderMinY) * renderMaxZ);
            int j1 = this.getAoBrightness(brightnessYNeg, aoBrightnessXYZNNP, brightnessZPos, i1);
            int k1 = this.getAoBrightness(brightnessZPos, brightnessYPos, aoBrightnessXYZNPP, i1);
            int l1 = this.getAoBrightness(brightnessZNeg, aoBrightnessXYZNPN, brightnessYPos, i1);
            int i2 = this.getAoBrightness(aoBrightnessXYZNNN, brightnessYNeg, brightnessZNeg, i1);
            this.brightnessTopLeft = this.mixAoBrightness(k1, l1, i2, j1, renderMaxY * renderMaxZ, renderMaxY * (1.0D - renderMaxZ), (1.0D - renderMaxY) * (1.0D - renderMaxZ), (1.0D - renderMaxY) * renderMaxZ);
            this.brightnessBottomLeft = this.mixAoBrightness(k1, l1, i2, j1, renderMaxY * renderMinZ, renderMaxY * (1.0D - renderMinZ), (1.0D - renderMaxY) * (1.0D - renderMinZ), (1.0D - renderMaxY) * renderMinZ);
            this.brightnessBottomRight = this.mixAoBrightness(k1, l1, i2, j1, renderMinY * renderMinZ, renderMinY * (1.0D - renderMinZ), (1.0D - renderMinY) * (1.0D - renderMinZ), (1.0D - renderMinY) * renderMinZ);
            this.brightnessTopRight = this.mixAoBrightness(k1, l1, i2, j1, renderMinY * renderMaxZ, renderMinY * (1.0D - renderMaxZ), (1.0D - renderMinY) * (1.0D - renderMaxZ), (1.0D - renderMinY) * renderMaxZ);

            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;

            this.colorRedTopLeft *= f3;
            this.colorGreenTopLeft *= f3;
            this.colorBlueTopLeft *= f3;
            this.colorRedBottomLeft *= f4;
            this.colorGreenBottomLeft *= f4;
            this.colorBlueBottomLeft *= f4;
            this.colorRedBottomRight *= f5;
            this.colorGreenBottomRight *= f5;
            this.colorBlueBottomRight *= f5;
            this.colorRedTopRight *= f6;
            this.colorGreenTopRight *= f6;
            this.colorBlueTopRight *= f6;
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 4);
            renderFaceXNeg(block, (double)x, (double)y, (double)z, iicon);

            flag = true;
        }

        if (renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x + 1, y, z, 5))
        {
            float aoLightYNegZNeg = this.blockAccess.getBlock(x, y - 1, z - 1).getAmbientOcclusionLightValue();
            int brightnessYNegZNeg = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z - 1);

            float aoLightYNegZPos = this.blockAccess.getBlock(x, y - 1, z + 1).getAmbientOcclusionLightValue();
            int brightnessYNegZPos = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z + 1);

            float aoLightYPosZNeg = this.blockAccess.getBlock(x, y + 1, z - 1).getAmbientOcclusionLightValue();
            int brightnessYPosZNeg = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z - 1);

            float aoLightYPosZPos = this.blockAccess.getBlock(x, y + 1, z + 1).getAmbientOcclusionLightValue();
            int brightnessYPosZPos = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z + 1);

            i1 = l;

            if (renderMaxX >= 1.0D || !this.blockAccess.getBlock(x + 1, y, z).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z);
            }

            float cornerYNegZPos =  (aoLightXPos + aoLightYNeg + aoLightYNegZPos  + aoLightZPos) / 4.0F;
            float cornerYNegZNeg =  (aoLightXPos + aoLightYNegZNeg + aoLightYNeg + aoLightZNeg) / 4.0F;
            float cornerYPosZNeg = (aoLightXPos + aoLightZNeg + aoLightYPosZNeg + aoLightYPos) / 4.0F;
            float cornerYPosZPos = (aoLightXPos + aoLightZPos + aoLightYPos + aoLightYPosZPos) / 4.0F;
            float f3 = (float)((double)cornerYNegZPos * (1.0D - renderMinY) * renderMaxZ + (double)cornerYNegZNeg * (1.0D - renderMinY) * (1.0D - renderMaxZ) + (double)cornerYPosZNeg * renderMinY * (1.0D - renderMaxZ) + (double)cornerYPosZPos * renderMinY * renderMaxZ);
            float f4 = (float)((double)cornerYNegZPos * (1.0D - renderMinY) * renderMinZ + (double)cornerYNegZNeg * (1.0D - renderMinY) * (1.0D - renderMinZ) + (double)cornerYPosZNeg * renderMinY * (1.0D - renderMinZ) + (double)cornerYPosZPos * renderMinY * renderMinZ);
            float f5 = (float)((double)cornerYNegZPos * (1.0D - renderMaxY) * renderMinZ + (double)cornerYNegZNeg * (1.0D - renderMaxY) * (1.0D - renderMinZ) + (double)cornerYPosZNeg * renderMaxY * (1.0D - renderMinZ) + (double)cornerYPosZPos * renderMaxY * renderMinZ);
            float f6 = (float)((double)cornerYNegZPos * (1.0D - renderMaxY) * renderMaxZ + (double)cornerYNegZNeg * (1.0D - renderMaxY) * (1.0D - renderMaxZ) + (double)cornerYPosZNeg * renderMaxY * (1.0D - renderMaxZ) + (double)cornerYPosZPos * renderMaxY * renderMaxZ);
            int j1 = this.getAoBrightness(brightnessYNeg, brightnessYNegZPos, brightnessZPos, i1);
            int k1 = this.getAoBrightness(brightnessZPos, brightnessYPos, brightnessYPosZPos, i1);
            int l1 = this.getAoBrightness(brightnessZNeg, brightnessYPosZNeg, brightnessYPos, i1);
            int i2 = this.getAoBrightness(brightnessYNegZNeg, brightnessYNeg, brightnessZNeg, i1);
            this.brightnessTopLeft = this.mixAoBrightness(j1, i2, l1, k1, (1.0D - renderMinY) * renderMaxZ, (1.0D - renderMinY) * (1.0D - renderMaxZ), renderMinY * (1.0D - renderMaxZ), renderMinY * renderMaxZ);
            this.brightnessBottomLeft = this.mixAoBrightness(j1, i2, l1, k1, (1.0D - renderMinY) * renderMinZ, (1.0D - renderMinY) * (1.0D - renderMinZ), renderMinY * (1.0D - renderMinZ), renderMinY * renderMinZ);
            this.brightnessBottomRight = this.mixAoBrightness(j1, i2, l1, k1, (1.0D - renderMaxY) * renderMinZ, (1.0D - renderMaxY) * (1.0D - renderMinZ), renderMaxY * (1.0D - renderMinZ), renderMaxY * renderMinZ);
            this.brightnessTopRight = this.mixAoBrightness(j1, i2, l1, k1, (1.0D - renderMaxY) * renderMaxZ, (1.0D - renderMaxY) * (1.0D - renderMaxZ), renderMaxY * (1.0D - renderMaxZ), renderMaxY * renderMaxZ);

            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;

            this.colorRedTopLeft *= f3;
            this.colorGreenTopLeft *= f3;
            this.colorBlueTopLeft *= f3;
            this.colorRedBottomLeft *= f4;
            this.colorGreenBottomLeft *= f4;
            this.colorBlueBottomLeft *= f4;
            this.colorRedBottomRight *= f5;
            this.colorGreenBottomRight *= f5;
            this.colorBlueBottomRight *= f5;
            this.colorRedTopRight *= f6;
            this.colorGreenTopRight *= f6;
            this.colorBlueTopRight *= f6;
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 5);
            renderFaceXPos(block, (double)x, (double)y, (double)z, iicon);

            flag = true;
        }

        this.enableAO = false;
        return flag;
    }

}
