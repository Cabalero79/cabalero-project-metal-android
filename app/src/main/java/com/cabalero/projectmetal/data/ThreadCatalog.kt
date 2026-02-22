package com.cabalero.projectmetal.data

enum class ThreadSystem(val displayName: String) {
  METRIC("MM"),
  INCH("Polegada"),
}

data class ThreadSpec(
  val code: String,
  val majorDiameterMm: Double,
  val pitchMm: Double,
  val system: ThreadSystem,
  val source: String,
) {
  val recommendedDrillMm: Double
    get() = majorDiameterMm - pitchMm

  val tpi: Double?
    get() = if (system == ThreadSystem.INCH) 25.4 / pitchMm else null
}

object ThreadCatalog {
  // ISO 261/724 coarse metric series (selected shop sizes).
  val metricIso: List<ThreadSpec> = listOf(
    ThreadSpec("M3 x 0.5", 3.0, 0.5, ThreadSystem.METRIC, "ISO 261 + ISO 724"),
    ThreadSpec("M4 x 0.7", 4.0, 0.7, ThreadSystem.METRIC, "ISO 261 + ISO 724"),
    ThreadSpec("M5 x 0.8", 5.0, 0.8, ThreadSystem.METRIC, "ISO 261 + ISO 724"),
    ThreadSpec("M6 x 1.0", 6.0, 1.0, ThreadSystem.METRIC, "ISO 261 + ISO 724"),
    ThreadSpec("M8 x 1.25", 8.0, 1.25, ThreadSystem.METRIC, "ISO 261 + ISO 724"),
    ThreadSpec("M10 x 1.5", 10.0, 1.5, ThreadSystem.METRIC, "ISO 261 + ISO 724"),
    ThreadSpec("M12 x 1.75", 12.0, 1.75, ThreadSystem.METRIC, "ISO 261 + ISO 724"),
    ThreadSpec("M14 x 2.0", 14.0, 2.0, ThreadSystem.METRIC, "ISO 261 + ISO 724"),
    ThreadSpec("M16 x 2.0", 16.0, 2.0, ThreadSystem.METRIC, "ISO 261 + ISO 724"),
    ThreadSpec("M18 x 2.5", 18.0, 2.5, ThreadSystem.METRIC, "ISO 261 + ISO 724"),
    ThreadSpec("M20 x 2.5", 20.0, 2.5, ThreadSystem.METRIC, "ISO 261 + ISO 724"),
    ThreadSpec("M24 x 3.0", 24.0, 3.0, ThreadSystem.METRIC, "ISO 261 + ISO 724"),
  )

  // ISO 228-1 BSPP nominal sizes (selected shop sizes).
  val inchIso: List<ThreadSpec> = listOf(
    ThreadSpec("1/8 in - 28 TPI", 9.728, 25.4 / 28.0, ThreadSystem.INCH, "ISO 228-1"),
    ThreadSpec("1/4 in - 19 TPI", 13.157, 25.4 / 19.0, ThreadSystem.INCH, "ISO 228-1"),
    ThreadSpec("3/8 in - 19 TPI", 16.662, 25.4 / 19.0, ThreadSystem.INCH, "ISO 228-1"),
    ThreadSpec("1/2 in - 14 TPI", 20.955, 25.4 / 14.0, ThreadSystem.INCH, "ISO 228-1"),
    ThreadSpec("3/4 in - 14 TPI", 26.441, 25.4 / 14.0, ThreadSystem.INCH, "ISO 228-1"),
    ThreadSpec("1 in - 11 TPI", 33.249, 25.4 / 11.0, ThreadSystem.INCH, "ISO 228-1"),
  )

  fun bySystem(system: ThreadSystem): List<ThreadSpec> =
    if (system == ThreadSystem.METRIC) metricIso else inchIso
}

