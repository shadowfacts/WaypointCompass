package net.shadowfacts.waypointcompass

import net.minecraft.init.Items
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.oredict.ShapedOreRecipe
import net.shadowfacts.waypointcompass.item.ItemWaypointCompass
import net.shadowfacts.waypointcompass.recipe.CompassDuplicationRecipe

/**
 * @author shadowfacts
 */
@Mod(modid = MOD_ID, name = NAME, version = "@VERSION@", dependencies = "required-after:shadowmc;", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object WaypointCompass {

	@Mod.EventHandler
	fun preInit(event: FMLPreInitializationEvent) {
		Config.init(event.modConfigurationDirectory)

		GameRegistry.register(ItemWaypointCompass)
		GameRegistry.addRecipe(ShapedOreRecipe(ItemWaypointCompass, "cgc", "g g", "cgc", 'g', "ingotGold", 'c', Items.COMPASS))
		GameRegistry.addRecipe(CompassDuplicationRecipe)
	}

	@Mod.EventHandler
	@SideOnly(Side.CLIENT)
	fun preInitClient(event: FMLPreInitializationEvent) {
		ItemWaypointCompass.initItemModel()
		MinecraftForge.EVENT_BUS.register(ClientEventHandler)
	}

}