package com.example.ui.screens.daily

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.theme.*
import com.example.viewmodel.DailyTool
import com.example.viewmodel.IslamicViewModel
import kotlin.math.*

@Composable
fun DailyPracticeScreen(
    viewModel: IslamicViewModel,
    onOpenCitySelector: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dailyTool by viewModel.dailyTool.collectAsState()
    val prayerTimes by viewModel.prayerTimes.collectAsState()
    val selectedCity by viewModel.selectedCity.collectAsState()
    val calculationMethod by viewModel.calculationMethod.collectAsState()
    val qiblaBearing by viewModel.qiblaBearing.collectAsState()
    val hijriDate by viewModel.hijriDateString.collectAsState()
    val gregorianDate by viewModel.gregorianDateString.collectAsState()
    val dhikrCount by viewModel.dhikrCount.collectAsState()
    val dhikrTarget by viewModel.dhikrTarget.collectAsState()
    val currentPhrase by viewModel.currentDhikrPhrase.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("daily_practice_screen")
    ) {
        // Tool Tabs
        TabRow(
            selectedTabIndex = dailyTool.ordinal,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            DailyTool.values().forEach { tool ->
                Tab(
                    selected = dailyTool == tool,
                    onClick = { viewModel.setDailyTool(tool) },
                    text = {
                        Text(
                            text = tool.title,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (dailyTool == tool) FontWeight.Bold else FontWeight.Normal
                            ),
                            maxLines = 1
                        )
                    }
                )
            }
        }

        when (dailyTool) {
            DailyTool.PRAYERS -> {
                PrayersSubView(
                    prayerTimes = prayerTimes,
                    city = selectedCity,
                    method = calculationMethod,
                    onSelectCity = onOpenCitySelector,
                    onSelectMethod = { method -> viewModel.selectCalculationMethod(method) }
                )
            }
            DailyTool.QIBLA -> {
                QiblaCompassSubView(
                    qiblaBearing = qiblaBearing,
                    city = selectedCity
                )
            }
            DailyTool.TASBIH -> {
                TasbihCounterSubView(
                    count = dhikrCount,
                    target = dhikrTarget,
                    phrase = currentPhrase,
                    onTap = { viewModel.onTasbihTap() },
                    onReset = { viewModel.resetTasbih() },
                    onSelectTarget = { target -> viewModel.setDhikrTarget(target) },
                    onSelectPhrase = { phrase -> viewModel.setDhikrPhrase(phrase) }
                )
            }
            DailyTool.CALENDAR -> {
                HijriCalendarSubView(hijriDate = hijriDate, gregorianDate = gregorianDate)
            }
        }
    }
}

@Composable
fun PrayersSubView(
    prayerTimes: List<PrayerTime>,
    city: CityLocation,
    method: CalculationMethod,
    onSelectCity: () -> Unit,
    onSelectMethod: (CalculationMethod) -> Unit
) {
    var showMethodDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Localização Atual:",
                            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
                        )
                        Text(
                            text = "${city.name}, ${city.country}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    OutlinedButton(
                        onClick = onSelectCity,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Alterar")
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Horários de Hoje",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                TextButton(onClick = { showMethodDialog = true }) {
                    Text(
                        text = "Método de Cálculo",
                        fontSize = 12.sp,
                        color = IslamicGoldDark,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        items(prayerTimes) { pt ->
            val isNext = pt.isNext
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("prayer_row_${pt.name.lowercase()}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isNext) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                ),
                border = if (isNext) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = if (isNext) IslamicGoldPrimary else MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (pt.name == "Shuruq") Icons.Default.WbSunny else Icons.Default.AccessTime,
                                    contentDescription = null,
                                    tint = if (isNext) Color.White else MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = pt.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isNext) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                )
                                if (isNext) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = IslamicGoldPrimary
                                    ) {
                                        Text(
                                            text = "A Seguir",
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                            Text(
                                text = pt.arabicName,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = if (isNext) IslamicGoldLight else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }

                    Text(
                        text = pt.timeFormatted,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isNext) Color.White else MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    }

    if (showMethodDialog) {
        AlertDialog(
            onDismissRequest = { showMethodDialog = false },
            title = { Text("Método de Cálculo") },
            text = {
                Column {
                    CalculationMethod.values().forEach { m ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelectMethod(m)
                                    showMethodDialog = false
                                }
                                .padding(vertical = 8.dp)
                        ) {
                            RadioButton(
                                selected = method == m,
                                onClick = {
                                    onSelectMethod(m)
                                    showMethodDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = m.displayName, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showMethodDialog = false }) {
                    Text("Fechar")
                }
            }
        )
    }
}

@Composable
fun QiblaCompassSubView(
    qiblaBearing: Float,
    city: CityLocation
) {
    // Interactive heading simulation for testing orientation
    var simulatedHeading by remember { mutableFloatStateOf(0f) }
    val animatedHeading by animateFloatAsState(targetValue = simulatedHeading, animationSpec = tween(300))

    // Difference between phone orientation and Kaaba bearing
    val diff = abs((qiblaBearing - animatedHeading + 360) % 360)
    val isAligned = diff <= 4f || diff >= 356f

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("qibla_compass_view"),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isAligned) IslamicGreenPrimary else MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, if (isAligned) IslamicGoldPrimary else MaterialTheme.colorScheme.outline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isAligned) "🕋 Alinhado com a Sagrada Kaaba!" else "Direção da Qibla",
                        color = if (isAligned) IslamicGoldLight else MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Ângulo: ${qiblaBearing.roundToInt()}° a partir de ${city.name} (~5.320 km de Makkah)",
                        color = if (isAligned) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        // Compass Canvas
        item {
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(260.dp)) {
                    val center = Offset(size.width / 2, size.height / 2)
                    val radius = size.width / 2

                    // Outer dial circle
                    drawCircle(
                        color = Color(0xFFD5DFD9),
                        radius = radius,
                        style = Stroke(width = 4.dp.toPx())
                    )

                    // Degree tick marks
                    for (i in 0 until 360 step 15) {
                        val angleRad = Math.toRadians((i - 90).toDouble())
                        val isMajor = i % 90 == 0
                        val strokeLen = if (isMajor) 16.dp.toPx() else 8.dp.toPx()
                        val startX = center.x + (radius - strokeLen) * cos(angleRad).toFloat()
                        val startY = center.y + (radius - strokeLen) * sin(angleRad).toFloat()
                        val endX = center.x + radius * cos(angleRad).toFloat()
                        val endY = center.y + radius * sin(angleRad).toFloat()

                        drawLine(
                            color = if (isMajor) Color(0xFF0E4D34) else Color(0xFF8FA397),
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = if (isMajor) 3.dp.toPx() else 1.5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    // Rotate compass needle toward Qibla
                    rotate(degrees = qiblaBearing - animatedHeading, pivot = center) {
                        // Golden Kaaba Needle pointing north
                        val needlePath = Path().apply {
                            moveTo(center.x, center.y - radius * 0.78f)
                            lineTo(center.x + 12.dp.toPx(), center.y)
                            lineTo(center.x, center.y - 10.dp.toPx())
                            lineTo(center.x - 12.dp.toPx(), center.y)
                            close()
                        }
                        drawPath(
                            path = needlePath,
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFFE5C058), Color(0xFFC89B38))
                            )
                        )

                        // South needle
                        val southPath = Path().apply {
                            moveTo(center.x, center.y + radius * 0.65f)
                            lineTo(center.x + 10.dp.toPx(), center.y)
                            lineTo(center.x, center.y + 10.dp.toPx())
                            lineTo(center.x - 10.dp.toPx(), center.y)
                            close()
                        }
                        drawPath(path = southPath, color = Color(0xFF7E8F87))
                    }

                    // Center pivot point
                    drawCircle(color = Color(0xFF0E4D34), radius = 10.dp.toPx(), center = center)
                    drawCircle(color = Color(0xFFE5C058), radius = 5.dp.toPx(), center = center)
                }

                // Kaaba icon indicator at top
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "N (Norte)",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "S (Sul)",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Compass calibration slider
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Orientação do Dispositivo:",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "${simulatedHeading.roundToInt()}°",
                            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary)
                        )
                    }
                    Slider(
                        value = simulatedHeading,
                        onValueChange = { simulatedHeading = it },
                        valueRange = 0f..360f,
                        modifier = Modifier.testTag("compass_heading_slider")
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            onClick = { simulatedHeading = qiblaBearing },
                            border = BorderStroke(1.dp, IslamicGoldPrimary)
                        ) {
                            Text("Apontar Diretamente para a Qibla")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TasbihCounterSubView(
    count: Int,
    target: Int,
    phrase: String,
    onTap: () -> Unit,
    onReset: () -> Unit,
    onSelectTarget: (Int) -> Unit,
    onSelectPhrase: (String) -> Unit
) {
    var showPhraseDialog by remember { mutableStateOf(false) }

    val phrases = listOf(
        "سُبْحَانَ اللَّهِ (SubhanAllah)",
        "الْحَمْدُ لِلَّهِ (Alhamdulillah)",
        "اللَّهُ أَكْبَرُ (Allahu Akbar)",
        "أَسْتَغْفِرُ اللَّهَ (Astaghfirullah)",
        "لَا إِلَٰهَ إِلَّا اللَّهُ (La ilaha illallah)",
        "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ (Salawat)"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("tasbih_counter_view"),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Phrase card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showPhraseDialog = true }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Frase de Dhikr:",
                            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                        Text(
                            text = phrase,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Mudar Frase",
                        tint = IslamicGoldDark
                    )
                }
            }
        }

        // Target Selector Chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(33, 99, 100).forEach { t ->
                    FilterChip(
                        selected = target == t,
                        onClick = { onSelectTarget(t) },
                        label = { Text("Meta: $t") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Main Touch Pad
        item {
            val progress = (count % target).toFloat() / target.toFloat()

            Box(
                modifier = Modifier
                    .size(240.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable { onTap() }
                    .testTag("tasbih_tap_button"),
                contentAlignment = Alignment.Center
            ) {
                // Subtle ring
                Canvas(modifier = Modifier.size(230.dp)) {
                    drawArc(
                        color = Color(0xFFE5C058),
                        startAngle = -90f,
                        sweepAngle = progress * 360f,
                        useCenter = false,
                        style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$count",
                        color = Color.White,
                        fontSize = 54.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Meta: $target",
                        color = IslamicGoldLight,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Toque para contar",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Reset button
        item {
            OutlinedButton(
                onClick = onReset,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Reiniciar Contador")
            }
        }
    }

    if (showPhraseDialog) {
        AlertDialog(
            onDismissRequest = { showPhraseDialog = false },
            title = { Text("Escolha a Frase de Dhikr") },
            text = {
                Column {
                    phrases.forEach { p ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelectPhrase(p)
                                    showPhraseDialog = false
                                }
                                .padding(vertical = 8.dp)
                        ) {
                            RadioButton(
                                selected = phrase == p,
                                onClick = {
                                    onSelectPhrase(p)
                                    showPhraseDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = p, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showPhraseDialog = false }) {
                    Text("Fechar")
                }
            }
        )
    }
}

@Composable
fun HijriCalendarSubView(hijriDate: String, gregorianDate: String) {
    val keyDates = listOf(
        Pair("1 de Ramadan", "Início do mês sagrado de Jejum e caridade"),
        Pair("Noite do Destino (Laylat al-Qadr)", "Últimos dez dias ímpares do Ramadan"),
        Pair("1 de Shawwal", "Eid al-Fitr (Celebração do término do Jejum)"),
        Pair("9 de Dhu al-Hijjah", "Dia de Arafah (pilar central do Hajj)"),
        Pair("10 de Dhu al-Hijjah", "Eid al-Adha (Festa do Sacrifício)"),
        Pair("1 de Muharram", "Ano Novo Islâmico"),
        Pair("10 de Muharram", "Dia de Ashura (Jejum da salvação de Moisés)"),
        Pair("12 de Rabi' al-Awwal", "Mawlid an-Nabi (Nascimento do Profeta Muhammad ﷺ)")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Data de Hoje:",
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = gregorianDate,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "🌙 $hijriDate",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "O calendário lunar islâmico baseia-se na rotação da Lua ao redor da Terra, com meses de 29 ou 30 dias.",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
                    )
                }
            }
        }

        item {
            Text(
                text = "Datas Notáveis do Ano Islâmico",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        items(keyDates) { (date, desc) ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        tint = IslamicGoldPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = date,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }
                }
            }
        }
    }
}
