package net.shadowfacts.waypointcompass.item

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.resources.I18n
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItemFrame
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.IItemPropertyGetter
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.*
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.relauncher.SideOnly
import net.shadowfacts.shadowmc.item.ItemModelProvider
import net.shadowfacts.waypointcompass.MOD_ID
import net.shadowfacts.waypointcompass.util.hasWaypoint
import net.shadowfacts.waypointcompass.util.waypoint
import net.shadowfacts.waypointcompass.util.waypointDimension

/**
 * @author shadowfacts
 */
object ItemWaypointCompass: Item(), ItemModelProvider {

	init {
		setRegistryName("compass")
		unlocalizedName = registryName.toString()
		creativeTab = CreativeTabs.TOOLS

		this.addPropertyOverride(ResourceLocation("angle"), object: IItemPropertyGetter {
			private var rotation = 0.0
			private var rota = 0.0
			private var lastUpdateTick = 0L

			@SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
			override fun apply(stack: ItemStack, world: World?, entityIn: EntityLivingBase?): Float {

				if (entityIn == null && !stack.isOnItemFrame) {
					return 0.0f
				} else {
					val flag = entityIn != null
					val entity = (if (flag) entityIn else stack.itemFrame) as Entity
					val world = world ?: entity.world!!

					var d0: Double

					if (stack.hasWaypoint && stack.waypointDimension == world.provider.dimension) {
						var d1 = if (flag) entity.rotationYaw.toDouble() else getFrameRotation(entity as EntityItemFrame)
						d1 = MathHelper.positiveModulo(d1 / 360.0, 1.0)
						val d2 = getAngle(entity, stack) / (Math.PI * 2.0)
						d0 = 0.5 - (d1 - 0.25 - d2)
					} else {
						d0 = Math.random()
					}

					if (flag) {
						d0 = wobble(world, d0)
					}

					return MathHelper.positiveModulo(d0.toFloat(), 1f)
				}
			}

			private fun wobble(world: World, d: Double): Double {
				if (world.totalWorldTime != this.lastUpdateTick) {
					this.lastUpdateTick = world.totalWorldTime
					var d0 = d - this.rotation
					d0 = MathHelper.positiveModulo(d0 + 0.5, 1.0) - 0.5
					this.rota += d0 * 0.1
					this.rota *= 0.8
					this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0)
				}

				return this.rotation
			}

			private fun getFrameRotation(entity: EntityItemFrame): Double {
				return MathHelper.clampAngle(180 + entity.facingDirection!!.horizontalIndex * 90).toDouble()
			}

			private fun getAngle(entity: Entity, stack: ItemStack): Double {
				val waypoint = stack.waypoint
				return Math.atan2(waypoint.z - entity.posZ, waypoint.x - entity.posX)
			}
		})
	}

	override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
		val stack = player.getHeldItem(hand)
		if (player.isSneaking) {
			stack.waypoint = player.position
			stack.waypointDimension = player.dimension
			return ActionResult(EnumActionResult.SUCCESS, stack)
		}
		return ActionResult(EnumActionResult.PASS, stack)
	}

	override fun addInformation(stack: ItemStack, player: EntityPlayer, tooltip: MutableList<String>, advanced: Boolean) {
		if (stack.hasWaypoint) {
			val waypoint = stack.waypoint
			tooltip.add(I18n.format("$unlocalizedName.waypoint", waypoint.x, waypoint.z, stack.waypointDimension))
		}
	}

	override fun initItemModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, ModelResourceLocation(ResourceLocation(MOD_ID, "compass"), "inventory"))
	}

}