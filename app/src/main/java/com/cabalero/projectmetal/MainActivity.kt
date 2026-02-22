package com.cabalero.projectmetal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cabalero.projectmetal.data.ThreadCatalog
import com.cabalero.projectmetal.data.ThreadSpec
import com.cabalero.projectmetal.data.ThreadSystem
import com.cabalero.projectmetal.ui.theme.AccentSteel
import com.cabalero.projectmetal.ui.theme.CabaleroTheme
import com.cabalero.projectmetal.ui.theme.TextSecondary
import java.util.Locale

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CabaleroTheme {
        AppScreen()
      }
    }
  }
}

@Composable
private fun AppScreen() {
  var selectedSystem by remember { mutableStateOf(ThreadSystem.METRIC) }
  val options = remember(selectedSystem) { ThreadCatalog.bySystem(selectedSystem) }
  var selectedThread by remember(selectedSystem) { mutableStateOf(options.first()) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(
        brush = Brush.verticalGradient(
          listOf(Color(0xFF111318), Color(0xFF0E1622)),
        ),
      )
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
  ) {
    Header()

    SystemSelector(
      selectedSystem = selectedSystem,
      onSystemChange = {
        selectedSystem = it
        selectedThread = ThreadCatalog.bySystem(it).first()
      },
    )

    ThreadButtons(
      options = options,
      selected = selectedThread,
      onSelect = { selectedThread = it },
    )

    ResultCard(selectedThread)
  }
}

@Composable
private fun Header() {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    Row(
      modifier = Modifier
        .clip(CircleShape)
        .background(Color(0xFF1F2D3F))
        .border(1.dp, AccentSteel, CircleShape)
        .padding(horizontal = 14.dp, vertical = 10.dp),
    ) {
      Text(
        text = "CPM",
        style = MaterialTheme.typography.titleMedium,
        color = AccentSteel,
        fontWeight = FontWeight.Bold,
      )
    }

    Column {
      Text(
        text = "Cabalero's Project Metal",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
      )
      Text(
        text = "Consulta rápida de furos para roscas ISO",
        style = MaterialTheme.typography.bodyMedium,
        color = TextSecondary,
      )
    }
  }
}

@Composable
private fun SystemSelector(
  selectedSystem: ThreadSystem,
  onSystemChange: (ThreadSystem) -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    ThreadSystem.entries.forEach { system ->
      FilterChip(
        selected = selectedSystem == system,
        onClick = { onSystemChange(system) },
        label = { Text(system.displayName) },
        colors = FilterChipDefaults.filterChipColors(
          selectedContainerColor = AccentSteel.copy(alpha = 0.2f),
          selectedLabelColor = MaterialTheme.colorScheme.onBackground,
        ),
      )
    }
  }
}

@Composable
private fun ThreadButtons(
  options: List<ThreadSpec>,
  selected: ThreadSpec,
  onSelect: (ThreadSpec) -> Unit,
) {
  Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
    Text(
      text = "Escolha a rosca",
      style = MaterialTheme.typography.labelLarge,
      color = TextSecondary,
    )

    options.chunked(2).forEach { line ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        line.forEach { option ->
          OutlinedButton(
            onClick = { onSelect(option) },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
          ) {
            Text(
              text = option.code,
              textAlign = TextAlign.Center,
              color = if (option == selected) AccentSteel else MaterialTheme.colorScheme.onSurface,
            )
          }
        }

        if (line.size == 1) {
          Spacer(modifier = Modifier.weight(1f))
        }
      }
    }
  }
}

@Composable
private fun ResultCard(spec: ThreadSpec) {
  Card(
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    shape = RoundedCornerShape(16.dp),
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Text(
        text = "Diâmetro de furo recomendado",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
      )

      Text(
        text = "${spec.code} -> ${formatMm(spec.recommendedDrillMm)} mm",
        style = MaterialTheme.typography.headlineSmall,
        color = AccentSteel,
        fontWeight = FontWeight.Bold,
      )

      Text(
        text = "Diâmetro maior: ${formatMm(spec.majorDiameterMm)} mm",
        style = MaterialTheme.typography.bodyMedium,
      )

      val pitchLabel = if (spec.system == ThreadSystem.METRIC) {
        "Passo: ${formatMm(spec.pitchMm)} mm"
      } else {
        "Passo: ${formatMm(spec.pitchMm)} mm (${formatTpi(spec.tpi)} TPI)"
      }
      Text(
        text = pitchLabel,
        style = MaterialTheme.typography.bodyMedium,
      )

      Text(
        text = "Base: ${spec.source}",
        style = MaterialTheme.typography.bodySmall,
        color = TextSecondary,
      )
    }
  }

  Spacer(modifier = Modifier.height(8.dp))

  Text(
    text = "Regra usada no app: furo recomendado ˜ diâmetro maior - passo.",
    style = MaterialTheme.typography.bodySmall,
    color = TextSecondary,
  )
}

private fun formatMm(value: Double): String = String.format(Locale.US, "%.2f", value)

private fun formatTpi(value: Double?): String =
  if (value == null) "-" else String.format(Locale.US, "%.1f", value)

