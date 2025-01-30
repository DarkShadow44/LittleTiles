package com.creativemd.littletiles;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.creativemd.creativecore.api.nei.NEIRecipeInfoHandler;
import com.creativemd.creativecore.common.entity.EntitySit;
import com.creativemd.creativecore.common.event.TickHandler;
import com.creativemd.creativecore.common.gui.GuiHandler;
import com.creativemd.creativecore.common.packet.*;
import com.creativemd.creativecore.common.utils.stack.StackInfo;
import com.creativemd.littletiles.common.blocks.BlockLTColored;
import com.creativemd.littletiles.common.blocks.BlockTile;
import com.creativemd.littletiles.common.blocks.ItemBlockColored;
import com.creativemd.littletiles.common.events.LittleEvent;
import com.creativemd.littletiles.common.items.ItemBlockTiles;
import com.creativemd.littletiles.common.items.ItemColorTube;
import com.creativemd.littletiles.common.items.ItemHammer;
import com.creativemd.littletiles.common.items.ItemLittleChisel;
import com.creativemd.littletiles.common.items.ItemLittleSaw;
import com.creativemd.littletiles.common.items.ItemLittleWrench;
import com.creativemd.littletiles.common.items.ItemMultiTiles;
import com.creativemd.littletiles.common.items.ItemRecipe;
import com.creativemd.littletiles.common.items.ItemRubberMallet;
import com.creativemd.littletiles.common.items.ItemTileContainer;
import com.creativemd.littletiles.common.packet.LittleBlockPacket;
import com.creativemd.littletiles.common.packet.LittleFlipPacket;
import com.creativemd.littletiles.common.packet.LittlePlacePacket;
import com.creativemd.littletiles.common.packet.LittleRotatePacket;
import com.creativemd.littletiles.common.sorting.LittleTileSortingList;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.creativemd.littletiles.common.utils.LittleTile;
import com.creativemd.littletiles.common.utils.LittleTileBlock;
import com.creativemd.littletiles.common.utils.LittleTileBlockColored;
import com.creativemd.littletiles.common.utils.LittleTileTileEntity;
import com.creativemd.littletiles.server.LittleTilesServer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = LittleTiles.modid, version = LittleTiles.version, name = "LittleTiles")
public class LittleTiles {

    @Instance(LittleTiles.modid)
    public static LittleTiles instance = new LittleTiles();

    @SidedProxy(
            clientSide = "com.creativemd.littletiles.client.LittleTilesClient",
            serverSide = "com.creativemd.littletiles.server.LittleTilesServer")
    public static LittleTilesServer proxy;

    public static final String modid = "littletiles";
    public static final String version = "GRADLETOKEN_VERSION";

    public static int maxNewTiles = 512;

    public static BlockTile blockTile = new BlockTile(Material.rock);
    public static Block coloredBlock = new BlockLTColored().setBlockName("LTBlocks");

    public static Item hammer = new ItemHammer().setUnlocalizedName("LTHammer");
    public static Item recipe = new ItemRecipe().setUnlocalizedName("LTRecipe");
    public static Item multiTiles = new ItemMultiTiles().setUnlocalizedName("LTMultiTiles");
    public static Item saw = new ItemLittleSaw().setUnlocalizedName("LTSaw");
    public static Item container = new ItemTileContainer().setUnlocalizedName("LTContainer");
    public static Item wrench = new ItemLittleWrench().setUnlocalizedName("LTWrench");
    public static Item chisel = new ItemLittleChisel().setUnlocalizedName("LTChisel");
    public static Item colorTube = new ItemColorTube().setUnlocalizedName("LTColorTube");
    public static Item rubberMallet = new ItemRubberMallet().setUnlocalizedName("LTRubberMallet");

    public static boolean isAngelicaLoaded;

    public static final Logger logger = LogManager.getLogger(modid);

    public static SimpleNetworkWrapper network;
    public static TickHandler tickHandler = new TickHandler();

    @EventHandler
    public void Init(FMLInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel("CreativeMDPacket");
        network.registerMessage(PacketReciever.class, CreativeMessageHandler.class, 0, Side.CLIENT);
        network.registerMessage(PacketReciever.class, CreativeMessageHandler.class, 0, Side.SERVER);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        EntityRegistry.registerModEntity(EntitySit.class, "Sit", 0, this, 250, 250, true);

        // Init Packets
        CreativeCorePacket.registerPacket(GuiUpdatePacket.class, "guiupdatepacket");
        CreativeCorePacket.registerPacket(GuiControlPacket.class, "guicontrolpacket");
        CreativeCorePacket.registerPacket(ContainerControlUpdatePacket.class, "containercontrolpacket");
        CreativeCorePacket.registerPacket(TEContainerPacket.class, "TEContainer");
        CreativeCorePacket.registerPacket(GuiLayerPacket.class, "guilayerpacket");
        CreativeCorePacket.registerPacket(OpenGuiPacket.class, "opengui");
        CreativeCorePacket.registerPacket(BlockUpdatePacket.class, "blockupdatepacket");

        FMLCommonHandler.instance().bus().register(tickHandler);

        StackInfo.registerDefaultLoaders();

        if (Loader.isModLoaded("NotEnoughItems") && FMLCommonHandler.instance().getEffectiveSide().isClient())
            NEIRecipeInfoHandler.load();

        ForgeModContainer.fullBoundingBoxLadders = true;

        GameRegistry.registerItem(hammer, "hammer");
        GameRegistry.registerItem(recipe, "recipe");
        GameRegistry.registerItem(saw, "saw");
        GameRegistry.registerItem(container, "container");
        GameRegistry.registerItem(wrench, "wrench");
        GameRegistry.registerItem(chisel, "chisel");
        GameRegistry.registerItem(colorTube, "colorTube");
        GameRegistry.registerItem(rubberMallet, "rubberMallet");

        // GameRegistry.registerBlock(coloredBlock, "LTColoredBlock");
        GameRegistry.registerBlock(coloredBlock, ItemBlockColored.class, "LTColoredBlock");
        GameRegistry.registerBlock(blockTile, ItemBlockTiles.class, "BlockLittleTiles");

        GameRegistry.registerItem(multiTiles, "multiTiles");

        GameRegistry.registerTileEntity(TileEntityLittleTiles.class, "LittleTilesTileEntity");

        proxy.loadSide();

        LittleTile.registerLittleTile(LittleTileBlock.class, "BlockTileBlock");
        // LittleTile.registerLittleTile(LittleTileStructureBlock.class, "BlockTileStructure");
        LittleTile.registerLittleTile(LittleTileTileEntity.class, "BlockTileEntity");
        LittleTile.registerLittleTile(LittleTileBlockColored.class, "BlockTileColored");

        CreativeCorePacket.registerPacket(LittlePlacePacket.class, "LittlePlace");
        CreativeCorePacket.registerPacket(LittleBlockPacket.class, "LittleBlock");
        CreativeCorePacket.registerPacket(LittleRotatePacket.class, "LittleRotate");
        CreativeCorePacket.registerPacket(LittleFlipPacket.class, "LittleFlip");
        FMLCommonHandler.instance().bus().register(new LittleEvent());
        MinecraftForge.EVENT_BUS.register(new LittleEvent());

        LittleStructure.initStructures();

        // Recipes
        GameRegistry.addRecipe(
                new ItemStack(hammer),
                new Object[] { "XXX", "ALA", "ALA", 'X', Items.iron_ingot, 'L', new ItemStack(Items.dye, 1, 4) });

        GameRegistry.addRecipe(
                new ItemStack(container),
                new Object[] { "XXX", "XHX", "XXX", 'X', Items.iron_ingot, 'H', hammer });

        GameRegistry.addRecipe(
                new ItemStack(saw),
                new Object[] { "AXA", "AXA", "ALA", 'X', Items.iron_ingot, 'L', new ItemStack(Items.dye, 1, 4) });

        GameRegistry.addRecipe(
                new ItemStack(wrench),
                new Object[] { "AXA", "ALA", "ALA", 'X', Items.iron_ingot, 'L', new ItemStack(Items.dye, 1, 4) });

        GameRegistry.addRecipe(
                new ItemStack(rubberMallet),
                new Object[] { "XXX", "XLX", "ALA", 'X', Blocks.wool, 'L', new ItemStack(Items.dye, 1, 4) });

        GameRegistry.addRecipe(
                new ItemStack(colorTube),
                new Object[] { "XXX", "XLX", "XXX", 'X', Items.dye, 'L', Items.iron_ingot });

        isAngelicaLoaded = Loader.isModLoaded("angelica");
    }

    @EventHandler
    public void LoadComplete(FMLLoadCompleteEvent event) {
        LittleTileSortingList.initVanillaBlocks();
    }
}
