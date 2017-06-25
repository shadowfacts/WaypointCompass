package net.shadowfacts.waypointcompass

import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.shadowfacts.waypointcompass.item.ItemWaypointCompass

/**
 * @author shadowfacts
 */
@Mod(modid = MOD_ID, name = NAME, version = "@VERSION@", dependencies = "required-after:shadowmc;", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object WaypointCompass {

	@Mod.EventHandler
	fun preInit(event: FMLPreInitializationEvent) {
		Config.init(event.modConfigurationDirectory)
	}

	@Mod.EventBusSubscriber
	object RegistrationHandler {

		@JvmStatic
		@SubscribeEvent
		fun registerItems(event: RegistryEvent.Register<Item>) {
			event.registry.register(ItemWaypointCompass)
		}

	}

	@Mod.EventBusSubscriber
	object ClientRegistrationHandler {

		@JvmStatic
		@SubscribeEvent
		fun registerModels(event: ModelRegistryEvent) {
			ItemWaypointCompass.initItemModel()
		}

	}

}