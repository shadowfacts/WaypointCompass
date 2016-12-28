package net.shadowfacts.waypointcompass.util

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos
import net.minecraftforge.common.util.Constants

/**
 * @author shadowfacts
 */
var ItemStack.waypoint: BlockPos
	get() = BlockPos.fromLong(tagCompound!!.getLong("waypoint"))
	set(value) {
		if (!hasTagCompound()) tagCompound = NBTTagCompound()
		tagCompound!!.setLong("waypoint", value.toLong())
	}
var ItemStack.waypointDimension: Int
	get() = tagCompound!!.getInteger("waypointDim")
	set(value) {
		if (!hasTagCompound()) tagCompound = NBTTagCompound()
		tagCompound!!.setInteger("waypointDim", value)
	}

val ItemStack.hasWaypoint: Boolean
	get() = hasTagCompound() && tagCompound!!.hasKey("waypoint", Constants.NBT.TAG_LONG)