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

    @Shadow   public float aoLightValueScratchXYZNNN;
    /** Used as a scratch variable for ambient occlusion on the north/bottom/west corner. */
    @Shadow  public float aoLightValueScratchXYZNNP;
    /** Used as a scratch variable for ambient occlusion on the south/bottom/east corner. */
    @Shadow  public float aoLightValueScratchXYZPNN;
    /** Used as a scratch variable for ambient occlusion on the south/bottom/west corner. */
    @Shadow   public float aoLightValueScratchXYZPNP;
    /** Used as a scratch variable for ambient occlusion on the north/top/east corner. */
    @Shadow   public float aoLightValueScratchXYZNPN;
    /** Used as a scratch variable for ambient occlusion on the north/top/west corner. */
    @Shadow   public float aoLightValueScratchXYZNPP;
    /** Used as a scratch variable for ambient occlusion on the south/top/east corner. */
    @Shadow   public float aoLightValueScratchXYZPPN;
    /** Used as a scratch variable for ambient occlusion on the south/top/west corner. */
    @Shadow   public float aoLightValueScratchXYZPPP;
    /** Ambient occlusion brightness XYZNNN */
    @Shadow    public int aoBrightnessXYZNNN;
    /** Ambient occlusion brightness XYZNNP */
    @Shadow   public int aoBrightnessXYZNNP;
    /** Ambient occlusion brightness XYZPNN */
    @Shadow  public int aoBrightnessXYZPNN;
    /** Ambient occlusion brightness XYZPNP */
    @Shadow    public int aoBrightnessXYZPNP;
    /** Ambient occlusion brightness XYZNPN */
    @Shadow    public int aoBrightnessXYZNPN;
    /** Ambient occlusion brightness XYZNPP */
    @Shadow   public int aoBrightnessXYZNPP;
    /** Ambient occlusion brightness XYZPPN */
    @Shadow  public int aoBrightnessXYZPPN;
    /** Ambient occlusion brightness XYZPPP */
    @Shadow  public int aoBrightnessXYZPPP;

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
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        int l = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z);
        Tessellator tessellator = Tessellator.instance;

        int i1;
        float f7;

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

        if (this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y - 1, z, 0))
        {
            this.aoLightValueScratchXYZNNN = this.blockAccess.getBlock(x - 1, y, z - 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z - 1);

            this.aoLightValueScratchXYZNNP = this.blockAccess.getBlock(x - 1, y, z + 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z + 1);

            this.aoLightValueScratchXYZPNN = this.blockAccess.getBlock(x + 1, y, z - 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z - 1);

            this.aoLightValueScratchXYZPNP = this.blockAccess.getBlock(x + 1, y, z + 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z + 1);

            i1 = l;

            if (this.renderMinY <= 0.0D || !this.blockAccess.getBlock(x, y - 1, z).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
            }

            f7 = this.blockAccess.getBlock(x, y - 1, z).getAmbientOcclusionLightValue();
            f3 = (this.aoLightValueScratchXYZNNP + aoLightXNeg + aoLightZPos + f7) / 4.0F;
            f6 = (aoLightZPos + f7 + this.aoLightValueScratchXYZPNP + aoLightXPos) / 4.0F;
            f5 = (f7 + aoLightZNeg + aoLightXPos + this.aoLightValueScratchXYZPNN) / 4.0F;
            f4 = (aoLightXNeg + this.aoLightValueScratchXYZNNN + f7 + aoLightZNeg) / 4.0F;
            this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, brightnessXNeg, brightnessZPos, i1);
            this.brightnessTopRight = this.getAoBrightness(brightnessZPos, this.aoBrightnessXYZPNP, brightnessXPos, i1);
            this.brightnessBottomRight = this.getAoBrightness(brightnessZNeg, brightnessXPos, this.aoBrightnessXYZPNN, i1);
            this.brightnessBottomLeft = this.getAoBrightness(brightnessXNeg, this.aoBrightnessXYZNNN, brightnessZNeg, i1);

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
            this.renderFaceYNeg(block, (double)x, (double)y, (double)z, this.getBlockIcon(block, this.blockAccess, x, y, z, 0));
            flag = true;
        }

        if (this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y + 1, z, 1))
        {
            this.aoLightValueScratchXYZNPN = this.blockAccess.getBlock(x - 1, y, z - 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z - 1);

            this.aoLightValueScratchXYZPPN = this.blockAccess.getBlock(x + 1, y, z - 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z - 1);

            this.aoLightValueScratchXYZNPP = this.blockAccess.getBlock(x - 1, y, z + 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z + 1);

            this.aoLightValueScratchXYZPPP = this.blockAccess.getBlock(x + 1, y, z + 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z + 1);

            i1 = l;

            if (this.renderMaxY >= 1.0D || !this.blockAccess.getBlock(x, y + 1, z).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);
            }

            f7 = this.blockAccess.getBlock(x, y + 1, z).getAmbientOcclusionLightValue();
            f6 = (this.aoLightValueScratchXYZNPP + aoLightXNeg + aoLightZPos + f7) / 4.0F;
            f3 = (aoLightZPos + f7 + this.aoLightValueScratchXYZPPP + aoLightXPos) / 4.0F;
            f4 = (f7 + aoLightZNeg + aoLightXPos + this.aoLightValueScratchXYZPPN) / 4.0F;
            f5 = (aoLightXNeg + this.aoLightValueScratchXYZNPN + f7 + aoLightZNeg) / 4.0F;
            this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, brightnessXNeg, brightnessZPos, i1);
            this.brightnessTopLeft = this.getAoBrightness(brightnessZPos, this.aoBrightnessXYZPPP, brightnessXPos, i1);
            this.brightnessBottomLeft = this.getAoBrightness(brightnessZNeg, brightnessXPos, this.aoBrightnessXYZPPN, i1);
            this.brightnessBottomRight = this.getAoBrightness(brightnessXNeg, this.aoBrightnessXYZNPN, brightnessZNeg, i1);
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
            this.renderFaceYPos(block, (double)x, (double)y, (double)z, this.getBlockIcon(block, this.blockAccess, x, y, z, 1));
            flag = true;
        }

        float f8;
        float f9;
        float f10;
        float f11;
        int j1;
        int k1;
        int l1;
        int i2;
        IIcon iicon;

        if (this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y, z - 1, 2))
        {
            this.aoLightValueScratchXYZNNN = this.blockAccess.getBlock(x - 1, y - 1, z).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y - 1, z);

            this.aoLightValueScratchXYZNPN = this.blockAccess.getBlock(x - 1, y + 1, z).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y + 1, z);

            this.aoLightValueScratchXYZPNN = this.blockAccess.getBlock(x + 1, y - 1, z).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y - 1, z);

            this.aoLightValueScratchXYZPPN = this.blockAccess.getBlock(x + 1, y + 1, z).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y + 1, z);

            i1 = l;

            if (this.renderMinZ <= 0.0D || !this.blockAccess.getBlock(x, y, z - 1).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1);
            }

            f7 = this.blockAccess.getBlock(x, y, z - 1).getAmbientOcclusionLightValue();
            f8 = (aoLightXNeg + this.aoLightValueScratchXYZNPN + f7 + aoLightYPos) / 4.0F;
            f9 = (f7 + aoLightYPos + aoLightXPos + this.aoLightValueScratchXYZPPN) / 4.0F;
            f10 = (aoLightYNeg + f7 + this.aoLightValueScratchXYZPNN + aoLightXPos) / 4.0F;
            f11 = (this.aoLightValueScratchXYZNNN + aoLightXNeg + aoLightYNeg + f7) / 4.0F;
            f3 = (float)((double)f8 * this.renderMaxY * (1.0D - this.renderMinX) + (double)f9 * this.renderMaxY * this.renderMinX + (double)f10 * (1.0D - this.renderMaxY) * this.renderMinX + (double)f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
            f4 = (float)((double)f8 * this.renderMaxY * (1.0D - this.renderMaxX) + (double)f9 * this.renderMaxY * this.renderMaxX + (double)f10 * (1.0D - this.renderMaxY) * this.renderMaxX + (double)f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
            f5 = (float)((double)f8 * this.renderMinY * (1.0D - this.renderMaxX) + (double)f9 * this.renderMinY * this.renderMaxX + (double)f10 * (1.0D - this.renderMinY) * this.renderMaxX + (double)f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
            f6 = (float)((double)f8 * this.renderMinY * (1.0D - this.renderMinX) + (double)f9 * this.renderMinY * this.renderMinX + (double)f10 * (1.0D - this.renderMinY) * this.renderMinX + (double)f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
            j1 = this.getAoBrightness(brightnessXNeg, this.aoBrightnessXYZNPN, brightnessYPos, i1);
            k1 = this.getAoBrightness(brightnessYPos, brightnessXPos, this.aoBrightnessXYZPPN, i1);
            l1 = this.getAoBrightness(brightnessYNeg, this.aoBrightnessXYZPNN, brightnessXPos, i1);
            i2 = this.getAoBrightness(this.aoBrightnessXYZNNN, brightnessXNeg, brightnessYNeg, i1);
            this.brightnessTopLeft = this.mixAoBrightness(j1, k1, l1, i2, this.renderMaxY * (1.0D - this.renderMinX), this.renderMaxY * this.renderMinX, (1.0D - this.renderMaxY) * this.renderMinX, (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
            this.brightnessBottomLeft = this.mixAoBrightness(j1, k1, l1, i2, this.renderMaxY * (1.0D - this.renderMaxX), this.renderMaxY * this.renderMaxX, (1.0D - this.renderMaxY) * this.renderMaxX, (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
            this.brightnessBottomRight = this.mixAoBrightness(j1, k1, l1, i2, this.renderMinY * (1.0D - this.renderMaxX), this.renderMinY * this.renderMaxX, (1.0D - this.renderMinY) * this.renderMaxX, (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
            this.brightnessTopRight = this.mixAoBrightness(j1, k1, l1, i2, this.renderMinY * (1.0D - this.renderMinX), this.renderMinY * this.renderMinX, (1.0D - this.renderMinY) * this.renderMinX, (1.0D - this.renderMinY) * (1.0D - this.renderMinX));

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
            this.renderFaceZNeg(block, (double)x, (double)y, (double)z, iicon);

            flag = true;
        }

        if (this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x, y, z + 1, 3))
        {
            this.aoLightValueScratchXYZNNP = this.blockAccess.getBlock(x - 1, y - 1, z).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y - 1, z);

            this.aoLightValueScratchXYZNPP = this.blockAccess.getBlock(x - 1, y + 1, z).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y + 1, z);

            this.aoLightValueScratchXYZPNP = this.blockAccess.getBlock(x + 1, y - 1, z).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y - 1, z);

            this.aoLightValueScratchXYZPPP = this.blockAccess.getBlock(x + 1, y + 1, z).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y + 1, z);

            i1 = l;

            if (this.renderMaxZ >= 1.0D || !this.blockAccess.getBlock(x, y, z + 1).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1);
            }

            f7 = this.blockAccess.getBlock(x, y, z + 1).getAmbientOcclusionLightValue();
            f8 = (aoLightXNeg + this.aoLightValueScratchXYZNPP + f7 + aoLightYPos) / 4.0F;
            f9 = (f7 + aoLightYPos + aoLightXPos + this.aoLightValueScratchXYZPPP) / 4.0F;
            f10 = (aoLightYNeg + f7 + this.aoLightValueScratchXYZPNP + aoLightXPos) / 4.0F;
            f11 = (this.aoLightValueScratchXYZNNP + aoLightXNeg + aoLightYNeg + f7) / 4.0F;
            f3 = (float)((double)f8 * this.renderMaxY * (1.0D - this.renderMinX) + (double)f9 * this.renderMaxY * this.renderMinX + (double)f10 * (1.0D - this.renderMaxY) * this.renderMinX + (double)f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
            f4 = (float)((double)f8 * this.renderMinY * (1.0D - this.renderMinX) + (double)f9 * this.renderMinY * this.renderMinX + (double)f10 * (1.0D - this.renderMinY) * this.renderMinX + (double)f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
            f5 = (float)((double)f8 * this.renderMinY * (1.0D - this.renderMaxX) + (double)f9 * this.renderMinY * this.renderMaxX + (double)f10 * (1.0D - this.renderMinY) * this.renderMaxX + (double)f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
            f6 = (float)((double)f8 * this.renderMaxY * (1.0D - this.renderMaxX) + (double)f9 * this.renderMaxY * this.renderMaxX + (double)f10 * (1.0D - this.renderMaxY) * this.renderMaxX + (double)f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
            j1 = this.getAoBrightness(brightnessXNeg, this.aoBrightnessXYZNPP, brightnessYPos, i1);
            k1 = this.getAoBrightness(brightnessYPos, brightnessXPos, this.aoBrightnessXYZPPP, i1);
            l1 = this.getAoBrightness(brightnessYNeg, this.aoBrightnessXYZPNP, brightnessXPos, i1);
            i2 = this.getAoBrightness(this.aoBrightnessXYZNNP, brightnessXNeg, brightnessYNeg, i1);
            this.brightnessTopLeft = this.mixAoBrightness(j1, i2, l1, k1, this.renderMaxY * (1.0D - this.renderMinX), (1.0D - this.renderMaxY) * (1.0D - this.renderMinX), (1.0D - this.renderMaxY) * this.renderMinX, this.renderMaxY * this.renderMinX);
            this.brightnessBottomLeft = this.mixAoBrightness(j1, i2, l1, k1, this.renderMinY * (1.0D - this.renderMinX), (1.0D - this.renderMinY) * (1.0D - this.renderMinX), (1.0D - this.renderMinY) * this.renderMinX, this.renderMinY * this.renderMinX);
            this.brightnessBottomRight = this.mixAoBrightness(j1, i2, l1, k1, this.renderMinY * (1.0D - this.renderMaxX), (1.0D - this.renderMinY) * (1.0D - this.renderMaxX), (1.0D - this.renderMinY) * this.renderMaxX, this.renderMinY * this.renderMaxX);
            this.brightnessTopRight = this.mixAoBrightness(j1, i2, l1, k1, this.renderMaxY * (1.0D - this.renderMaxX), (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX), (1.0D - this.renderMaxY) * this.renderMaxX, this.renderMaxY * this.renderMaxX);

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
            this.renderFaceZPos(block, (double)x, (double)y, (double)z, iicon);

            flag = true;
        }

        if (this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x - 1, y, z, 4))
        {
            this.aoLightValueScratchXYZNNN = this.blockAccess.getBlock(x, y - 1, z - 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z - 1);

            this.aoLightValueScratchXYZNNP = this.blockAccess.getBlock(x, y - 1, z + 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z + 1);

            this.aoLightValueScratchXYZNPN = this.blockAccess.getBlock(x, y + 1, z - 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z - 1);

            this.aoLightValueScratchXYZNPP = this.blockAccess.getBlock(x, y + 1, z + 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z + 1);

            i1 = l;

            if (this.renderMinX <= 0.0D || !this.blockAccess.getBlock(x - 1, y, z).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z);
            }

            f7 = this.blockAccess.getBlock(x - 1, y, z).getAmbientOcclusionLightValue();
            f8 = (aoLightYNeg + this.aoLightValueScratchXYZNNP + f7 + aoLightZPos) / 4.0F;
            f9 = (f7 + aoLightZPos + aoLightYPos + this.aoLightValueScratchXYZNPP) / 4.0F;
            f10 = (aoLightZNeg + f7 + this.aoLightValueScratchXYZNPN + aoLightYPos) / 4.0F;
            f11 = (this.aoLightValueScratchXYZNNN + aoLightYNeg + aoLightZNeg + f7) / 4.0F;
            f3 = (float)((double)f9 * this.renderMaxY * this.renderMaxZ + (double)f10 * this.renderMaxY * (1.0D - this.renderMaxZ) + (double)f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ) + (double)f8 * (1.0D - this.renderMaxY) * this.renderMaxZ);
            f4 = (float)((double)f9 * this.renderMaxY * this.renderMinZ + (double)f10 * this.renderMaxY * (1.0D - this.renderMinZ) + (double)f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ) + (double)f8 * (1.0D - this.renderMaxY) * this.renderMinZ);
            f5 = (float)((double)f9 * this.renderMinY * this.renderMinZ + (double)f10 * this.renderMinY * (1.0D - this.renderMinZ) + (double)f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ) + (double)f8 * (1.0D - this.renderMinY) * this.renderMinZ);
            f6 = (float)((double)f9 * this.renderMinY * this.renderMaxZ + (double)f10 * this.renderMinY * (1.0D - this.renderMaxZ) + (double)f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ) + (double)f8 * (1.0D - this.renderMinY) * this.renderMaxZ);
            j1 = this.getAoBrightness(brightnessYNeg, this.aoBrightnessXYZNNP, brightnessZPos, i1);
            k1 = this.getAoBrightness(brightnessZPos, brightnessYPos, this.aoBrightnessXYZNPP, i1);
            l1 = this.getAoBrightness(brightnessZNeg, this.aoBrightnessXYZNPN, brightnessYPos, i1);
            i2 = this.getAoBrightness(this.aoBrightnessXYZNNN, brightnessYNeg, brightnessZNeg, i1);
            this.brightnessTopLeft = this.mixAoBrightness(k1, l1, i2, j1, this.renderMaxY * this.renderMaxZ, this.renderMaxY * (1.0D - this.renderMaxZ), (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ), (1.0D - this.renderMaxY) * this.renderMaxZ);
            this.brightnessBottomLeft = this.mixAoBrightness(k1, l1, i2, j1, this.renderMaxY * this.renderMinZ, this.renderMaxY * (1.0D - this.renderMinZ), (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ), (1.0D - this.renderMaxY) * this.renderMinZ);
            this.brightnessBottomRight = this.mixAoBrightness(k1, l1, i2, j1, this.renderMinY * this.renderMinZ, this.renderMinY * (1.0D - this.renderMinZ), (1.0D - this.renderMinY) * (1.0D - this.renderMinZ), (1.0D - this.renderMinY) * this.renderMinZ);
            this.brightnessTopRight = this.mixAoBrightness(k1, l1, i2, j1, this.renderMinY * this.renderMaxZ, this.renderMinY * (1.0D - this.renderMaxZ), (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ), (1.0D - this.renderMinY) * this.renderMaxZ);

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
            this.renderFaceXNeg(block, (double)x, (double)y, (double)z, iicon);

            flag = true;
        }

        if (this.renderAllFaces || block.shouldSideBeRendered(this.blockAccess, x + 1, y, z, 5))
        {
            this.aoLightValueScratchXYZPNN = this.blockAccess.getBlock(x, y - 1, z - 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z - 1);

            this.aoLightValueScratchXYZPNP = this.blockAccess.getBlock(x, y - 1, z + 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z + 1);

            this.aoLightValueScratchXYZPPN = this.blockAccess.getBlock(x, y + 1, z - 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z - 1);

            this.aoLightValueScratchXYZPPP = this.blockAccess.getBlock(x, y + 1, z + 1).getAmbientOcclusionLightValue();
            this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z + 1);

            i1 = l;

            if (this.renderMaxX >= 1.0D || !this.blockAccess.getBlock(x + 1, y, z).isOpaqueCube())
            {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z);
            }

            f7 = this.blockAccess.getBlock(x + 1, y, z).getAmbientOcclusionLightValue();
            f8 = (aoLightYNeg + this.aoLightValueScratchXYZPNP + f7 + aoLightZPos) / 4.0F;
            f9 = (this.aoLightValueScratchXYZPNN + aoLightYNeg + aoLightZNeg + f7) / 4.0F;
            f10 = (aoLightZNeg + f7 + this.aoLightValueScratchXYZPPN + aoLightYPos) / 4.0F;
            f11 = (f7 + aoLightZPos + aoLightYPos + this.aoLightValueScratchXYZPPP) / 4.0F;
            f3 = (float)((double)f8 * (1.0D - this.renderMinY) * this.renderMaxZ + (double)f9 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ) + (double)f10 * this.renderMinY * (1.0D - this.renderMaxZ) + (double)f11 * this.renderMinY * this.renderMaxZ);
            f4 = (float)((double)f8 * (1.0D - this.renderMinY) * this.renderMinZ + (double)f9 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ) + (double)f10 * this.renderMinY * (1.0D - this.renderMinZ) + (double)f11 * this.renderMinY * this.renderMinZ);
            f5 = (float)((double)f8 * (1.0D - this.renderMaxY) * this.renderMinZ + (double)f9 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ) + (double)f10 * this.renderMaxY * (1.0D - this.renderMinZ) + (double)f11 * this.renderMaxY * this.renderMinZ);
            f6 = (float)((double)f8 * (1.0D - this.renderMaxY) * this.renderMaxZ + (double)f9 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ) + (double)f10 * this.renderMaxY * (1.0D - this.renderMaxZ) + (double)f11 * this.renderMaxY * this.renderMaxZ);
            j1 = this.getAoBrightness(brightnessYNeg, this.aoBrightnessXYZPNP, brightnessZPos, i1);
            k1 = this.getAoBrightness(brightnessZPos, brightnessYPos, this.aoBrightnessXYZPPP, i1);
            l1 = this.getAoBrightness(brightnessZNeg, this.aoBrightnessXYZPPN, brightnessYPos, i1);
            i2 = this.getAoBrightness(this.aoBrightnessXYZPNN, brightnessYNeg, brightnessZNeg, i1);
            this.brightnessTopLeft = this.mixAoBrightness(j1, i2, l1, k1, (1.0D - this.renderMinY) * this.renderMaxZ, (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ), this.renderMinY * (1.0D - this.renderMaxZ), this.renderMinY * this.renderMaxZ);
            this.brightnessBottomLeft = this.mixAoBrightness(j1, i2, l1, k1, (1.0D - this.renderMinY) * this.renderMinZ, (1.0D - this.renderMinY) * (1.0D - this.renderMinZ), this.renderMinY * (1.0D - this.renderMinZ), this.renderMinY * this.renderMinZ);
            this.brightnessBottomRight = this.mixAoBrightness(j1, i2, l1, k1, (1.0D - this.renderMaxY) * this.renderMinZ, (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ), this.renderMaxY * (1.0D - this.renderMinZ), this.renderMaxY * this.renderMinZ);
            this.brightnessTopRight = this.mixAoBrightness(j1, i2, l1, k1, (1.0D - this.renderMaxY) * this.renderMaxZ, (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ), this.renderMaxY * (1.0D - this.renderMaxZ), this.renderMaxY * this.renderMaxZ);

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
            this.renderFaceXPos(block, (double)x, (double)y, (double)z, iicon);

            flag = true;
        }

        this.enableAO = false;
        return flag;
    }

}
