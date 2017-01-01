package net.shadowfacts.waypointcompass

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import java.io.File

/**
 * @author shadowfacts
 */
object Config {

	var config: Config = ConfigFactory.load("assets/waypointcompass/reference.conf")
		private set

	val renderWaypointInWorld: Boolean
		get() = config.getBoolean("waypointcompass.renderWaypointInWorld")

	fun init(configDir: File) {
		val f = File(configDir, "shadowfacts/WaypointCompass.conf")
		if (!f.parentFile.exists()) {
			f.parentFile.mkdirs()
		}
		if (!f.exists()) {
			f.createNewFile()
		}

		config = ConfigFactory.parseFile(f).withFallback(config)

		val toRender = config.root().withOnlyKey("waypointcompass")
		val s = toRender.render(ConfigRenderOptions.defaults().setOriginComments(false).setJson(false))
		f.writeText(s)
	}

}