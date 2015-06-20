package com.hea3ven.buildingbricks.core;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.hea3ven.buildingbricks.ModBuildingBricks;
import com.hea3ven.buildingbricks.core.network.TrowelRotateBlockTypeMessage;

public class KeyInputEventHandler {

	@SubscribeEvent
	public void onKeyInput(MouseEvent e) {
		if (e.dwheel != 0 && Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) {
			ItemStack stack = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem();
			if (stack.getItem() == ModBuildingBricks.trowel) {
				TrowelRotateBlockTypeMessage.send(e.dwheel > 0);
				e.setCanceled(true);
			}
		}
	}
}