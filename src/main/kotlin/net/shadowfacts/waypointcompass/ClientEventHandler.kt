package net.shadowfacts.waypointcompass

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer.TEXTURE_BEACON_BEAM
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer.renderBeamSegment
import net.minecraft.world.World
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.shadowfacts.waypointcompass.item.ItemWaypointCompass
import net.shadowfacts.waypointcompass.util.hasWaypoint
import net.shadowfacts.waypointcompass.util.waypoint
import net.shadowfacts.waypointcompass.util.waypointDimension

/**
 * @author shadowfacts
 */
object ClientEventHandler {

	@SubscribeEvent
	fun renderWorldLast(event: RenderWorldLastEvent) {
		if (Config.renderWaypointInWorld) {
			val player = Minecraft.getMinecraft().player
			val world = Minecraft.getMinecraft().world
			val compass = if (player.heldItemMainhand.item == ItemWaypointCompass) player.heldItemMainhand else if (player.heldItemOffhand.item == ItemWaypointCompass) player.heldItemOffhand else null
			if (compass != null && compass.hasWaypoint) {
				val dimension = compass.waypointDimension
				if (player.dimension == dimension) {
					val waypoint = compass.waypoint
					renderBeaconSegment(waypoint.x.toDouble(), 0.0, waypoint.z.toDouble(), event.partialTicks.toDouble(), 1.0, world)
				}
			}
		}
	}

	private fun renderBeaconSegment(x: Double, y: Double, z: Double, partialTicks: Double, textureScale: Double, world: World) {
		GlStateManager.alphaFunc(516, 0.1f)
		Minecraft.getMinecraft().textureManager.bindTexture(TEXTURE_BEACON_BEAM)

		if (textureScale > 0) {
			GlStateManager.disableFog()

			renderBeamSegment(x, y, z, partialTicks, textureScale, world.totalWorldTime.toDouble(), 0, 256, floatArrayOf(1f, 1f, 1f))

			GlStateManager.enableFog()
		}
	}

}