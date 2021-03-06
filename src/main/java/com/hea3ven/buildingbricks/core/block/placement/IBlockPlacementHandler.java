package com.hea3ven.buildingbricks.core.block.placement;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.hea3ven.buildingbricks.core.materials.Material;
import com.hea3ven.tools.commonutils.util.PlaceParams;

public interface IBlockPlacementHandler {
	boolean isHandled(ItemStack stack);

	EnumActionResult place(World world, ItemStack stack, EntityLivingBase placer, Material mat, IBlockState state,
			PlaceParams params);

	boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer placer,
			ItemStack stack);
}
