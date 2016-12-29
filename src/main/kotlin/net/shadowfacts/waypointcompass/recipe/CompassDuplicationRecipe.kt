package net.shadowfacts.waypointcompass.recipe

import net.minecraft.init.Items
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.world.World
import net.minecraftforge.oredict.OreDictionary
import net.shadowfacts.waypointcompass.item.ItemWaypointCompass

/**
 * @author shadowfacts
 */
object CompassDuplicationRecipe: IRecipe {

	private val INGOT_GOLD = OreDictionary.getOreID("ingotGold")

	private fun isIngotGold(stack: ItemStack?): Boolean {
		return stack != null && OreDictionary.getOreIDs(stack).contains(INGOT_GOLD)
	}

	override fun matches(inv: InventoryCrafting, world: World): Boolean {
//				row 1
		return inv.getStackInRowAndColumn(0, 0)?.item == Items.COMPASS &&
				isIngotGold(inv.getStackInRowAndColumn(0, 1)) &&
				inv.getStackInRowAndColumn(0, 2)?.item == Items.COMPASS &&
//				row 2
				isIngotGold(inv.getStackInRowAndColumn(1, 0)) &&
				inv.getStackInRowAndColumn(1, 1)?.item == ItemWaypointCompass &&
				isIngotGold(inv.getStackInRowAndColumn(1, 2)) &&
//				row 3
				inv.getStackInRowAndColumn(2, 0)?.item == Items.COMPASS &&
				isIngotGold(inv.getStackInRowAndColumn(0, 1)) &&
				inv.getStackInRowAndColumn(2, 2)?.item == Items.COMPASS
	}

	override fun getCraftingResult(inv: InventoryCrafting): ItemStack {
		return inv.getStackInRowAndColumn(1, 1)!!.copy()
	}

	override fun getRecipeSize(): Int {
		return 9
	}

	override fun getRecipeOutput(): ItemStack {
		return ItemStack(ItemWaypointCompass)
	}

	override fun getRemainingItems(inv: InventoryCrafting): Array<ItemStack?> {
		val array = kotlin.arrayOfNulls<ItemStack>(0)
		array[4] = inv.getStackInRowAndColumn(1, 1)!!.copy()
		return array
	}

}