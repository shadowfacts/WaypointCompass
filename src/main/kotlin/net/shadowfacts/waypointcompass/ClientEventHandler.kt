package net.shadowfacts.waypointcompass

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer.TEXTURE_BEACON_BEAM
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer.renderBeamSegment
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.shadowfacts.waypointcompass.item.ItemWaypointCompass
import net.shadowfacts.waypointcompass.util.hasWaypoint
import net.shadowfacts.waypointcompass.util.waypoint
import net.shadowfacts.waypointcompass.util.waypointDimension

/**
 * @author shadowfacts
 */
@Mod.EventBusSubscriber(modid = MOD_ID)
object ClientEventHandler {

	@JvmStatic
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
					prepare(player, event.partialTicks)
					renderBeaconSegment(waypoint.x.toDouble(), 0.0, waypoint.z.toDouble(), event.partialTicks.toDouble(), world)
					clean()
				}
			}
		}
	}

	private fun prepare(player: EntityPlayer, partialTicks: Float) {
		val x = -(player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks)
		val y = -(player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks)
		val z = -(player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks)

		GlStateManager.pushAttrib()
		GlStateManager.pushMatrix()

		GlStateManager.translate(x, y, z)
	}

	private fun clean() {
		GlStateManager.popMatrix()
		GlStateManager.popAttrib()
	}

	private fun renderBeaconSegment(x: Double, y: Double, z: Double, partialTicks: Double, world: World) {
		GlStateManager.alphaFunc(516, 0.1f)
		Minecraft.getMinecraft().textureManager.bindTexture(TEXTURE_BEACON_BEAM)

		renderBeamSegment(x, y, z, partialTicks, 1.0, world.totalWorldTime.toDouble(), 0, 256, floatArrayOf(1f, 1f, 1f))
	}

}