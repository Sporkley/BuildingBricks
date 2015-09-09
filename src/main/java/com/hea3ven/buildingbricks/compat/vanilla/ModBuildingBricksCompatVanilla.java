package com.hea3ven.buildingbricks.compat.vanilla;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.hea3ven.buildingbricks.compat.vanilla.blocks.BlockGrassSlab;
import com.hea3ven.buildingbricks.core.config.Config;
import com.hea3ven.buildingbricks.core.items.ItemColoredWrapper;
import com.hea3ven.buildingbricks.core.lib.BlockDescription;
import com.hea3ven.buildingbricks.core.materials.Material;
import com.hea3ven.buildingbricks.core.materials.MaterialBlockType;
import com.hea3ven.buildingbricks.core.materials.MaterialRegistry;
import com.hea3ven.buildingbricks.core.materials.StructureMaterial;

@Mod(modid = ModBuildingBricksCompatVanilla.MODID, name = "Building Bricks Vanilla Compatibilty",
		version = ModBuildingBricksCompatVanilla.VERSION)
public class ModBuildingBricksCompatVanilla {
	public static final String MODID = "buildingbrickscompatvanilla";
	public static final String VERSION = "1.0.0";

	public static Block grassSlab;

	static {
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Material grassMat = new Material("grass");
		grassMat.setTexture("minecraft:blocks/grass_top", "minecraft:blocks/dirt",
				"minecraft:blocks/grass_side");
		grassMat.setTexture("overlay", "minecraft:blocks/grass_side_overlay");
		grassMat.setStructureMaterial(StructureMaterial.GRASS);

		grassSlab = new BlockGrassSlab(grassMat).setUnlocalizedName("grass_slab");
		GameRegistry.registerBlock(grassSlab, ItemColoredWrapper.class, "grass_slab");

		grassMat.addBlock(new BlockDescription(MaterialBlockType.FULL, Blocks.grass));
		grassMat.addBlock(MaterialBlockType.STAIRS);
		grassMat.addBlock(new BlockDescription(MaterialBlockType.SLAB, grassSlab));
		grassMat.addBlock(MaterialBlockType.VERTICAL_SLAB);
		grassMat.addBlock(MaterialBlockType.STEP);
		grassMat.addBlock(MaterialBlockType.CORNER);
		MaterialRegistry.registerMaterial(grassMat);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (Config.generateGrassSlabs)
			MinecraftForge.EVENT_BUS.register(new GrassSlabWorldGen());

		replaceStoneSlabRecipe();

		if (event.getSide() == Side.CLIENT) {
			ItemModelMesher modelMesher = Minecraft
					.getMinecraft()
					.getRenderItem()
					.getItemModelMesher();
			modelMesher.register(Item.getItemFromBlock(grassSlab), 0, new ModelResourceLocation(
					"buildingbrickscompatvanilla:grass_slab", "inventory"));
		}
	}

	private void replaceStoneSlabRecipe() {
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++) {
			IRecipe recipe = recipes.get(i);
			ItemStack result = recipe.getRecipeOutput();
			if (result != null && result.getItem() instanceof ItemBlock
					&& ((ItemBlock) result.getItem()).getBlock() == Blocks.stone_slab
					&& result.getMetadata() == BlockStoneSlab.EnumType.STONE.getMetadata()) {
				recipes.remove(i);
			}
		}

		ItemStack stoneSlab = new ItemStack(Block.getBlockFromName("buildingbricks:stone_slab"));
		ItemStack stoneSlabSlab = new ItemStack(Blocks.stone_slab, 2,
				BlockStoneSlab.EnumType.STONE.getMetadata());
		GameRegistry.addShapedRecipe(stoneSlabSlab, "x", "x", 'x', stoneSlab);
	}

}
